package com.tpls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication

public class tplsApplication {

    public static void  main( String[] args) {
        SpringApplication.run(tplsApplication.class,args);

    }
}
