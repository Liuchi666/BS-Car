package com.lc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author lc
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SharedParkingSpaceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SharedParkingSpaceApplication.class, args);
        System.out.println("============项目启动成功============");
    }
}
