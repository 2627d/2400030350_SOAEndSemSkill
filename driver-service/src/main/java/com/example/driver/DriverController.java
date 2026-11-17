package com.example.driver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/drivers/test")
    public String test() {
        return "Request handled by Driver Service running on port: " + port;
    }

    @GetMapping("/drivers")
    public String getDrivers() {
        return "Driver Service is working on port: " + port;
    }
}
