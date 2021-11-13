package com.example.something.demo.controllers;

import com.example.something.demo.models.Error;
import com.example.something.demo.models.Post;
import com.example.something.demo.models.Posts;
import com.example.something.demo.services.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostsController {
    private final PostService postService;
    private final Logger logger = LoggerFactory.getLogger(PostsController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPosts() throws JsonProcessingException {
        logger.debug("Fetching posts...");

        List<Post> posts = new ArrayList<>();

        try {
            posts = objectMapper.readValue(postService.getPosts(), new TypeReference<List<Post>>(){});

            Posts allPosts = new Posts();
            allPosts.setPosts(posts);

            return objectMapper.writeValueAsString(allPosts);
        } catch (JsonProcessingException e) {
            Error error = new Error();
            error.setErrorMessage(e.getMessage());

            return objectMapper.writeValueAsString(error);
        }
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPostsById(@PathVariable String id) throws JsonProcessingException {
        logger.debug("Fetching posts with id: " + id);

        List<Post> posts = new ArrayList<>();

        try {
            posts = objectMapper.readValue(postService.getPosts(), new TypeReference<List<Post>>(){});

            List<Post> filteredPosts = posts
                    .stream()
                    .filter(p -> p.getUserId().equals(id))
                    .collect(Collectors.toList());

            Posts finalPosts = new Posts();
            finalPosts.setPosts(filteredPosts);

            return objectMapper.writeValueAsString(finalPosts);
        } catch (JsonProcessingException e) {
            Error error = new Error();
            error.setErrorMessage(e.getMessage());

            return objectMapper.writeValueAsString(error);
        }
    }
}
