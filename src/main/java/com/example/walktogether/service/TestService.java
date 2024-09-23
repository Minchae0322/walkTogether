package com.example.walktogether.service;



import com.example.walktogether.annotation.EndTimeLoggable;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @EndTimeLoggable
    public String testMethod() {
        return "Test Result";
    }
}

