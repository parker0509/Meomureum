package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/consult")

@Tag(name = "ConsultAPI", description = " 상담 API")
public class ConsultController {


    //consultAPI

    @Operation(summary = "consult GetMapping (ex)", description = "상담 페이지 이동!")
    @GetMapping
    public String getConsult() {
        return "consult";
    }




}
