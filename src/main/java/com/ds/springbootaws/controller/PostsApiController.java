package com.ds.springbootaws.controller;

import com.ds.springbootaws.dto.PostsResponseDto;
import com.ds.springbootaws.dto.PostsSaveRequestDto;
import com.ds.springbootaws.dto.PostsUpdateRequestDto;
import com.ds.springbootaws.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostsApiController {

  private final PostsService postsService;

  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsSaveRequestDto requestDto){
    return postsService.save(requestDto);
  }

  @PutMapping("/api/v1/posts/{id}")
  public Long update(
      @PathVariable Long id,
      @RequestBody PostsUpdateRequestDto requestDto
  ){
    return postsService.update(id,requestDto);
  }

  @GetMapping("/api/v1/posts/{id}")
  public PostsResponseDto findById(@PathVariable Long id){
    return postsService.findById(id);
  }

  @DeleteMapping("/api/v1/posts/{id}")
  public Long delete(@PathVariable Long id){
    postsService.delete(id);
    return id;
  }

}
