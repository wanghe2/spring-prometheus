package org.wh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ServletComponentScan
@EnableAsync
public class SpringPrometheusApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SpringPrometheusApplication.class,args);
    }
}
