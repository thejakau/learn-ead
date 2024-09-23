package com.sltc.dait.library.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SimulateIntegrationController {
    @GetMapping("/simulatedelay")
    @CircuitBreaker(name = "integratesimulation", fallbackMethod = "fallback")
    public ResponseEntity<String> simulateDelay(@RequestParam int delay) throws InterruptedException {
        Thread.sleep(delay * 1000);
        return ResponseEntity.ok("Response after " + delay + " seconds");
    }

    public ResponseEntity<String> fallback(int delay, Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fallback response");
    }
}
