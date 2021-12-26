package com.example.todopome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
public class TodoPomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoPomeApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
