package com.ds.springbootaws.service.posts;

import com.ds.springbootaws.domain.posts.Posts;
import com.ds.springbootaws.domain.posts.PostsRepository;
import com.ds.springbootaws.dto.PostsListResponseDto;
import com.ds.springbootaws.dto.PostsResponseDto;
import com.ds.springbootaws.dto.PostsSaveRequestDto;
import com.ds.springbootaws.dto.PostsUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {

  private final PostsRepository postsRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postsRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다." + id));
    posts.update(requestDto.getTitle(), requestDto.getContent());
    return id;
  }

  public PostsResponseDto findById(Long id) {
    Posts entity = postsRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다." + id));
    return new PostsResponseDto(entity);
  }

  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    return postsRepository.findAllDesc()
        .stream()
        .map(PostsListResponseDto::new)
        .collect(
        Collectors.toList());
  }

  @Transactional
  public void delete(Long id) {
    Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다." + id));
    postsRepository.delete(posts);
  }
}
