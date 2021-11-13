package com.example.something.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Post {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;
}
