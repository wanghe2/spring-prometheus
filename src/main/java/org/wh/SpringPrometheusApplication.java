package org.wh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringPrometheusApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SpringPrometheusApplication.class,args);
    }
}
