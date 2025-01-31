package com.example.demo.controller;

import com.example.demo.service.KakaoApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Kakao API", description = "카카오 API 관련 서비스")
public class KakaoApiController {

    private final KakaoApiService kakaoApiService;

    public KakaoApiController(KakaoApiService kakaoApiService) {
        this.kakaoApiService = kakaoApiService;
    }

    @Operation(
            summary = "주소 검색",
            description = "사용자가 입력한 query를 기반으로 카카오 API를 통해 주소를 검색하고 결과를 반환합니다.",
            parameters = {
                    @Parameter(name = "query", description = "검색할 주소나 키워드", required = true)
            }
    )
    @GetMapping("/search-address")
    public String searchAddress(@RequestParam(name = "query") String query) {
        return kakaoApiService.searchAddress(query);
    }
}
