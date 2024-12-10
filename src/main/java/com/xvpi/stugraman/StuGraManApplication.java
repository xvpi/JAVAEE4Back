package com.xvpi.stugraman;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.*;

@SpringBootApplication
@EntityScan("com.xvpi.beans")
@MapperScan("com.xvpi.stugraman.mapper")
public class StuGraManApplication {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(StuGraManApplication.class, args);
    }
}