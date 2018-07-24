package com.kute.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by kute on 2018/07/21 18:36
 */
@RestController
public class ErrorController {

    @GetMapping("/error")
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
