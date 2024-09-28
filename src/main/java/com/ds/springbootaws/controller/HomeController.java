package com.ds.springbootaws.controller;

import com.ds.springbootaws.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

  @GetMapping("/hello/dto")
  public HelloResponseDto helloDto(@RequestParam String name, @RequestParam int amount) {
    return new HelloResponseDto(name, amount);
  }

}
