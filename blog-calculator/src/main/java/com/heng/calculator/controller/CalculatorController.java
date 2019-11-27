package com.heng.calculator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cal")
public class CalculatorController {

    @RequestMapping("/calculator")
    public String calculator(@RequestParam(value = "str")String str){
        return null;
    }
}
