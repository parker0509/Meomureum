package com.example.demo.service;


import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Iterator;

@Service
public class KakaoApiService {

    @Value("${kakao_api_key}")
    private String restApiKey; // Kakao REST API 키를 application.properties 또는 application.yml에 저장

    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/address.json"; // 기본 형식은 JSON

    @Autowired
    private AddressRepository addressRepository;

    public String searchAddress(String query) {
        // 요청 URL에 쿼리 파라미터를 추가합니다.
        String url = UriComponentsBuilder.fromHttpUrl(KAKAO_API_URL)
                .queryParam("query", query)
                .build().toString();

        // RestTemplate을 통해 GET 요청을 보냅니다.
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더에 Kakao REST API 키 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + restApiKey); // KakaoAK 뒤에 API 키를 추가

        // GET 요청을 보낼 준비
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 응답 내용 반환
        String responseBody = response.getBody();

        // JSON 응답을 파싱하여 DB에 저장
        saveAddressesToDb(responseBody);

        return responseBody;
    }

    private void saveAddressesToDb(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode documentsNode = rootNode.path("documents");

            // documents 배열을 순회하며 주소 정보를 DB에 저장
            Iterator<JsonNode> elements = documentsNode.elements();
            while (elements.hasNext()) {
                JsonNode document = elements.next();

                Address address = new Address();
                address.setAddressName(document.path("address_name").asText());
                address.setRegion1DepthName(document.path("address").path("region_1depth_name").asText());
                address.setRegion2DepthName(document.path("address").path("region_2depth_name").asText());
                address.setRegion3DepthName(document.path("address").path("region_3depth_name").asText());
                address.setRegion4DepthName(document.path("address").path("region_4depth_name").asText());
                address.setX(document.path("x").asText());
                address.setY(document.path("y").asText());


                // DB에 저장
                addressRepository.save(address);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 에러 발생 시 출력
        }
    }

    public Address getCoordinatesFromAddress(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(KAKAO_API_URL)
                .queryParam("query", query)
                .build().toString();

        // RestTemplate을 사용해 Kakao API에 GET 요청을 보냄
        RestTemplate restTemplate = new RestTemplate();

        // Kakao API 키를 헤더에 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + restApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 응답 파싱
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode documentsNode = rootNode.path("documents");

            if (documentsNode.size() > 0) {
                JsonNode document = documentsNode.get(0);
                String addressName = document.path("address_name").asText();
                String x = document.path("x").asText();  // 위도
                String y = document.path("y").asText();  // 경도
                String region1Name = document.path("region_1depth_name").asText();
                String region2Name = document.path("region_2depth_name").asText();
                String region3Name = document.path("region_3depth_name").asText();
                String region4Name = document.path("region_4depth_name").asText();

                // 디버깅용 출력
                System.out.println("Region 1: " + region1Name);
                System.out.println("Region 2: " + region2Name);
                System.out.println("Region 3: " + region3Name);
                System.out.println("Region 4: " + region4Name);

                // Address 객체 생성
                Address address = new Address();
                address.setAddressName(addressName);
                address.setRegion1DepthName(address.getRegion1DepthName());
                address.setRegion2DepthName(address.getRegion2DepthName());
                address.setRegion3DepthName(address.getRegion3DepthName());
                address.setRegion4DepthName(address.getRegion4DepthName());
                address.setX(x);  // 위도
                address.setY(y);  // 경도

                return address;  // 위도와 경도를 포함한 Address 객체 반환
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // 실패 시 null 반환
    }
}
