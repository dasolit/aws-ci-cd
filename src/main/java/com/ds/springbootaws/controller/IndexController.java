package com.ds.springbootaws.controller;

import com.ds.springbootaws.config.auth.LoginUser;
import com.ds.springbootaws.config.auth.dto.SessionUser;
import com.ds.springbootaws.dto.PostsResponseDto;
import com.ds.springbootaws.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;

  @GetMapping("/")
  public String index(Model model, @LoginUser SessionUser user) {
    model.addAttribute("posts", postsService.findAllDesc());
    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    return "index";
  }

  @GetMapping("/post/save")
  public String savePost() {
    return "posts-save";
  }

  @GetMapping("/posts/update/{id}")
  public String updatePost(@PathVariable("id") Long id, Model model) {
    PostsResponseDto responseDto = postsService.findById(id);
    model.addAttribute("post", responseDto);
    return "posts-update";
  }
}
