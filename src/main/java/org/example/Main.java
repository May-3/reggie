package org.example;

import jakarta.servlet.annotation.WebFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * ClassName: ${NAME}
 * Package: org.example
 * Description:
 *
 * @Autehor 屈子岩
 * @Create ${DATE} ${TIME}
 * @Version 1.0
 */
@SpringBootApplication
@ServletComponentScan
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}