package com.hoangquoc.mydaisyhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.hoangquoc.mydaisyhotel","com.hoangquoc.mydaisyhotel.security"})
public class MyDaisyHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyDaisyHotelApplication.class, args);
    }

}
