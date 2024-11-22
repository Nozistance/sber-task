package io.nozistance.sbertask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SberTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SberTaskApplication.class, args);
    }

}
