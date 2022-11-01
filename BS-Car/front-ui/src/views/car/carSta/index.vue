<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="车主昵称">
        <el-input
          v-model="queryParams['carUser.nickname']"
          placeholder="请输入车主昵称"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 150px"
        />
      </el-form-item>
      <el-form-item label="车辆型号" prop="carName">
        <el-input
          v-model="queryParams.carName"
          placeholder="请输入车辆型号"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 150px"
        />
      </el-form-item>
      <el-form-item label="车牌号" prop="carNumber">
        <el-input
          v-model="queryParams.carNumber"
          placeholder="请输入车牌号"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 150px"
        />
      </el-form-item>
      <el-form-item label="车辆状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择车辆状态" clearable style="width: 150px">
          <el-option
            v-for="dict in dict.type.car_car_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['car:carSta:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['car:carSta:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="carId" width="55"/>
<!--      <el-table-column label="车主id" align="center" prop="userId" />-->
      <el-table-column label="车主昵称" align="center" prop="userNickname" />
      <el-table-column label="车主手机号" align="center" prop="userPhone" />
<!--      <el-table-column label="车位id" align="center" prop="parkinglotId" />-->
      <el-table-column label="车位编号" align="center" prop="parkinglotSort" width="90"/>
      <el-table-column label="所属停车场" align="center" prop="carparkName" width="240"/>
      <el-table-column label="车辆型号" align="center" prop="carName" />
      <el-table-column label="车牌号" align="center" prop="carNumber" />
      <el-table-column label="车辆照片" align="center" prop="carPhoto" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.carPhoto" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="车辆状态" align="center" prop="carStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.car_car_type" :value="scope.row.carStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['car:carSta:edit']"
          >修改车辆状态</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['car:carSta:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改车辆对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车辆状态" prop="carStatus">
          <el-select v-model="form.carStatus" placeholder="请选择车辆状态">
            <el-option
              v-for="dict in dict.type.car_car_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- ============生成统计数据-->
    <el-button type="primary" size="mini" @click="queryChart">点击生成车辆状态的统计数据</el-button>


    <div align="center" v-show="showChart">
      <h2>当前所有车辆状态统计数据</h2>
    </div>

    <!-- 1.定义一片echarts区域用于图表显示 -->
    <div class="chart-container" v-show="showChart">
      <!--饼状图-->
      <span id="chart" class="chart" style="width:600px;height:400px;float: left;"/>
      <!--折线图-->
      <span id="lineChart" class="chart" style="width:600px;height:400px;float: right;">
      </span>
    </div>

  </div>
</template>

<script>
//引入Api
import { listCar, getCar, delCar, addCar, updateCar } from "@/api/car/car";
import {listCarpark} from "@/api/car/carpark";
//2.引入echarts
import echarts from 'echarts'
import { getCarStaChart } from "@/api/car/carSta";

export default {
  name: "Car",
  dicts: ['car_car_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 车辆表格数据
      carList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        userId: null,
        carName: null,
        carNumber: null,
        status: null,
        'carUser.nickname': null
      },
      carparkList: [],  //停车场数据
      showChart: false, //显示Chart图表的开关
      chartList: [],   //用于封装后端查到的数据，用于下面的图表展示
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {  //再页面渲染前执行
    //查询车辆列表数据
    this.getList();
    //查询所有停车场数据
    this.getCarparkList();
  },
  methods: {
    /** 查询车辆列表 */
    getList() {
      this.loading = true;
      listCar(this.queryParams).then(response => {
        this.carList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**  查询停车场相关数据 */
    getCarparkList(){
      listCarpark().then(response => {
        this.carparkList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        carId: null,
        userId: null,
        parkinglotId: null,
        carName: null,
        carNumber: null,
        photo: null,
        status: null,
        createTime: null,
        isDeleted: null,
        createBy: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams["carUser.nickname"] = null
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.carId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车辆";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const carId = row.carId || this.ids
      getCar(carId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改车辆状态";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.carId != null) {
            let formData = new FormData()
            formData.append("carId",this.form.carId)
            formData.append("carStatus",this.form.carStatus)
            updateCar(formData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCar(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const carIds = row.carId || this.ids;
      this.$modal.confirm('是否确认删除车辆编号为"' + carIds + '"的数据项？').then(function() {
        return delCar(carIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('car/car/export', {
        ...this.queryParams
      }, `car_${new Date().getTime()}.xlsx`)
    },

    /**   生成统计数据    */
    queryChart(){
      //发送请求查询图标想要展示的数据
      getCarStaChart().then(response => {
        //将查到的数据赋给this.chartList用于数据展示
        this.chartList = response.data
        //显示图表
        this.getChart()
        //打开显示开关
        this.showChart = true
      })
    },
    /**显示Chart图表 */
    getChart(){
      // 4.基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('chart'));
      var myChart2 = echarts.init(document.getElementById('lineChart'));
      //5.指定图表的配置项和数据
      var option = {
        title: {
          text: '饼状图',
          subtext: '',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '车辆状态',
            type: 'pie',
            radius: '70%',
            data: [
              { name: '未用', value: this.chartList[0] },
              { name: '已用', value: this.chartList[1] },
              { name: '损坏', value: this.chartList[2] },
              { name: '报修', value: this.chartList[3] },
              { name: '位置异常', value: this.chartList[4] },
              { name: '付费异常', value: this.chartList[5] },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      var option2 = {
        title: {  //图表标题
          text: '柱状图'
        },
        tooltip: {  //x坐标轴触发提示(鼠标放在图表上有提示)
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: ['未用','已用','损坏','报修','位置异常','付费异常']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [
              {
                value: this.chartList[0],
                itemStyle: {
                  color: '#66cc00'
                }
              },
              {
                value: this.chartList[1],
                itemStyle: {
                  color: '#79CDCD'
                }
              },
              {
                value: this.chartList[2],
                itemStyle: {
                  color: '#CD5555'
                }
              },
              {
                value: this.chartList[3],
                itemStyle: {
                  color: '#4f6fcb'
                }
              },
              {
                value: this.chartList[4],
                itemStyle: {
                  color: '#bbe14f'
                }
              },
              {
                value: this.chartList[5],
                itemStyle: {
                  color: '#a136c1'
                }
              },
            ],
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      };

      //6.使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
      myChart2.setOption(option2)
    }
  }
};
</script>
