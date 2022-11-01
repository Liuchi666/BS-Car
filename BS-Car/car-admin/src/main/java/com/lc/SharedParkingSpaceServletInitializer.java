package com.lc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 在tomcat等web容器中单独进行部署
 *
 * @author lc
 */
public class SharedParkingSpaceServletInitializer extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(SharedParkingSpaceApplication.class);
    }
}
