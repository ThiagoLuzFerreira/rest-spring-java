package com.thiago.restspringjava.controller;

import com.thiago.restspringjava.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping
public class MathController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/sum/{num1}/{num2}")
    public Double sum(@PathVariable(value = "num1" ) String num1, @PathVariable(value = "num2") String num2) {

        if(!isNumeric(num1) || !isNumeric(num2)){
            throw new ResourceNotFoundException("Please, set a numeric value");
        }
        
        return convertToDouble(num1) + Double.parseDouble(num2);
    }
    @GetMapping("/div/{num1}/{num2}")
    public Double division(@PathVariable(value = "num1" ) String num1, @PathVariable(value = "num2") String num2) {

        if(num2.equals("0")){
            throw new ArithmeticException("Can't divide by zero");
        }
        if(!isNumeric(num1) || !isNumeric(num2)){
            throw new ResourceNotFoundException("Please, set a numeric value");
        }

        return convertToDouble(num1) / Double.parseDouble(num2);
    }

    private Double convertToDouble(String strNumber) {
        if(strNumber == null) return 0D;
        String number = strNumber.replaceAll(",", ".");
        if(isNumeric(number)) return Double.parseDouble(number);
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if(strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
