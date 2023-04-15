package com.high_con.grad;

import com.high_con.grad.util.initLogRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.high_con.grad.mapper")
public class MainApplication  extends SpringBootServletInitializer{
    public static void main(String[] args)  throws Exception{

        SpringApplication.run(MainApplication.class,args);
    }


    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(MainApplication.class);
    }
}
