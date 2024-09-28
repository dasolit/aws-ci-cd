package com.ds.springbootaws.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostsRepositoryTest {

  @Autowired
  private PostsRepository postsRepository;

  @BeforeEach
  public void cleanup(){
    postsRepository.deleteByTitle("테스트 게시글");
  }

  @Test
  @DisplayName("게시글 저장 불러오기")
  public void saveBoardAndLoad(){
    //given
    String title = "테스트 게시글";
    String content = "테스트 본문";
    postsRepository.save(Posts.builder()
            .title(title)
            .content(content)
            .author("choidasol95@gmail.com")
        .build());

    //when
    List<Posts> posts = postsRepository.findAll();

    //then
    Posts post = posts.get(0);
    assertThat(post.getId()).isGreaterThan(0L);
  }

  @Test
  @DisplayName("게시글 등록 테스트 (auditing)")
  public void saveBoardAndAuditingTest() {
    //given
    LocalDateTime now = LocalDateTime.of(2023,12,31,0,0,0);
    postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("choidasol95@gmail.com")
        .build());

    //when
    List<Posts> posts = postsRepository.findAll();

    //then
    Posts post = posts.get(0);

    assertThat(post.getCreatedDate()).isAfter(now);
    assertThat(post.getLastModifiedDate()).isAfter(now);
  }
}