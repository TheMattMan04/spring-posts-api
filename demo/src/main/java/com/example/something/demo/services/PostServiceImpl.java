package com.example.something.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PostServiceImpl implements PostService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${endpoint.url}")
    private String url;

    @Override
    public String getPosts() {
        ResponseEntity<String> postsResult = restTemplate.getForEntity(url, String.class);

        return postsResult.getBody();
    }
 }
