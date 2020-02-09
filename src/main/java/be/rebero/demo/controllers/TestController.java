package be.rebero.demo.controllers;

import be.rebero.demo.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/addNumbers")
    public ResponseEntity<Integer> addNumbers() {
        return ResponseEntity.ok(testService.addNumbers(2, 3));
    }
}
