package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/consult")
@Tag(name = "ConsultAPI", description = "상담 API") // Consult API의 전체적인 설명
public class ConsultController {


    // 상담 페이지로 이동하는 엔드포인트
    @Operation(
            summary = "상담 페이지 이동", // 엔드포인트의 간단한 설명
            description = "상담 페이지로 이동하는 GET 요청입니다. 사용자가 상담 페이지를 볼 수 있도록 반환합니다."
    )
    @GetMapping
    public String getConsult() {
        return "consult";
    }




}
