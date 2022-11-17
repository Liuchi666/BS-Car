package com.lc.car.service.impl;

import com.lc.car.domain.CarUser;
import com.lc.car.domain.vo.front.RegisterVo;
import com.lc.car.mapper.CarUserMapper;
import com.lc.car.service.FrontRegisterService;
import com.lc.car.tools.MD5;
import com.lc.common.exception.ServiceException;
import com.lc.common.utils.DateUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/20
 * @description
 */
@Service
public class FrontRegisterServiceImpl implements FrontRegisterService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CarUserMapper carUserMapper;

    /**
     * 通过腾讯云SMS服务发送短信
     */
    @Override
    public boolean send(String code, String phone) {
        //如果手机号为空，直接return
        if (!StringUtils.hasText(phone)) {
            return false;
        }

        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
             * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
             * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
             * 以免泄露密钥对危及你的财产安全。
             * CAM密匙查询: https://console.cloud.tencent.com/cam/capi*/
            /**  密钥 */
            Credential cred =
                    new Credential("XXX", "XXX");

            // 实例化一个http选项，可选，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            // 设置代理
            // httpProfile.setProxyHost("真实代理ip");
            // httpProfile.setProxyPort(真实代理端口);
            /* SDK默认使用POST方法。
             * 如果你一定要使用GET方法，可以在这里设置。GET方法无法处理一些较大的请求 */
            httpProfile.setReqMethod("POST");
            /* SDK有默认的超时时间，非必要请不要进行调整
             * 如有需要请在代码中查阅以获取最新的默认值 */
            httpProfile.setConnTimeout(60);
            /* SDK会自动指定域名。通常是不需要特地指定域名的，但是如果你访问的是金融区的服务
             * 则必须手动指定域名，例如sms的上海金融区域名： sms.ap-shanghai-fsi.tencentcloudapi.com */
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            /* SDK默认用TC3-HMAC-SHA256进行签名
             * 非必要请不要修改这个字段 */
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 你可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest request = new SendSmsRequest();

            /* 填充请求参数,这里request对象的成员变量即对应接口的入参
             * 你可以通过官网接口文档或跳转到request对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台: https://console.cloud.tencent.com/smsv2
             * sms helper: https://cloud.tencent.com/document/product/382/3773 */

            /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
            /** 在腾讯云官网-> 短信控制台 -> 应用管理 -> 应用列表中查看  */
            String sdkAppId = "XXX";
            request.setSmsSdkAppId(sdkAppId);

            /** 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 [短信控制台]->签名管理 查看 */
            String signName = "ikun集中营公众号";
            request.setSignName(signName);

            /* 国际/港澳台短信 SenderId: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
            String senderid = "";
            request.setSenderId(senderid);

            /* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
            String sessionContext = "xxx";
            request.setSessionContext(sessionContext);

            /* 短信号码扩展号: 默认未开通，如需开通请联系 [sms helper] */
            String extendCode = "";
            request.setExtendCode(extendCode);

            /** 模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台]->正文模板管理 查看 */
            String templateId = "XXX";
            request.setTemplateId(templateId);

            /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
             * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号 */
            //示例：String[] phoneNumberSet = {"+8621212313123", "+8612345678902", "+8612345678903"};
            /**  想要发送验证码的手机号 */
            String[] phoneNumberSet = {"+86" + phone};
            request.setPhoneNumberSet(phoneNumberSet);

            /** 模板参数: 若无模板参数，则设置为空
             *      注意：我的腾讯云SMS模板是：
             *          {code1}用于修改登录密码的身份验证，请于{code2}分钟内填写，如非本人操作，请忽略本短信。
             *          即有两个参数要传递，一个验证码，一个验证码有效期
             */
            //示例：String[] templateParamSet = {"5678"};
            //这里验证码有效期跟存进redis的验证码的有效期相同
            String[] templateParamSet = {code,"2"};
            request.setTemplateParamSet(templateParamSet);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse response = client.SendSms(request);

            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(response));

            // 也可以取出单个值，你可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
            System.out.println(response.getRequestId());

            //没有异常，就返回true
            return true;
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  用户注册
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取用户输入的注册信息
        String nickname = registerVo.getNickname();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        String confirmPassword = registerVo.getConfirmPassword();

        //非空判断
        if(!StringUtils.hasText(nickname) || !StringUtils.hasText(phone)
                || !StringUtils.hasText(password)|| !StringUtils.hasText(code)|| !StringUtils.hasText(confirmPassword)){
            throw new ServiceException("请填写完整的用户信息");
        }

        //判断两次输入的密码是否一致
        if(!confirmPassword.equals(password)){
            throw new ServiceException("两次密码前后不一致");
        }

        //首先判断验证码是否正确
        //获取redis中的验证码
        String redisCode = (String) redisTemplate.opsForValue().get(phone);
        if(redisCode == null){
            throw new ServiceException("验证码已过期");
        }
        if(!redisCode.equals(code)){
            throw new ServiceException("验证码错误");
        }

        //判断用户名是否存在
        int count = carUserMapper.selectCarUserByNickname(nickname);
        if(count > 0){
            throw new ServiceException("该用户名已存在");
        }

        //判断手机号是否存在
        count = carUserMapper.selectCarUserByPhone(phone);
        if(count > 0){
            throw new ServiceException("该手机号已存在");
        }

        //对用户输入的密码进行加密
        password = MD5.encrypt(registerVo.getPassword());

        CarUser user = new CarUser();
        //将RegisterVo对象中的属性拷贝到userInfo对象中去(除了password属性,因为要存加密后的密码)
        BeanUtils.copyProperties(registerVo,user,password,code,confirmPassword);
        //将加密后的密码封装进user
        user.setPassword(password);
        //设置一个默认头像
        user.setAvatar("/profile/avatar/2022/10/25/blob_20221025162459A021.jpeg");
        user.setCreateTime(DateUtils.getNowDate());
        //调用方法插入用户数据
        carUserMapper.insertCarUser(user);
    }
}
