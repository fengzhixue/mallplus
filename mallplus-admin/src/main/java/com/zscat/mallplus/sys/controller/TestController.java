package com.zscat.mallplus.sys.controller;


import com.zscat.mallplus.sys.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {
    String prefix = "common/generator";
    @Autowired
    GeneratorService generatorService;


}
