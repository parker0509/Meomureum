package com.example.demo.controller;


import com.example.demo.service.KakaoApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoApiController {

    private KakaoApiService kakaoApiService;


    @GetMapping("/search-address")
    public String searchAddress(@RequestParam(name = "query") String query){
        return kakaoApiService.searchAddress(query);
    }
}
