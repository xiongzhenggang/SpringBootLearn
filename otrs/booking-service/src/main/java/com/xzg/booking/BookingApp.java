package com.xzg.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 *
 * @author Sourabh Sharma
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class BookingApp {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BookingApp.class, args);
    }

}
