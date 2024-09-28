package com.ds.springbootaws.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ds.springbootaws.domain.posts.Posts;
import com.ds.springbootaws.domain.posts.PostsRepository;
import com.ds.springbootaws.dto.PostsSaveRequestDto;
import com.ds.springbootaws.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class PostsApiControllerTest {

  private static final String SURL = "http://localhost:";

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .build();
  }

  @BeforeEach
  public void cleanUp() throws Exception {
    //postsRepository.deleteByTitle("titles");
    //postsRepository.deleteByTitle("title1");
  }

  @Test
  @WithMockUser(roles="USER")
  @DisplayName("Post 등록")
  public void savePost() throws Exception{
    //given
    String title = "titles";
    String content = "content";
    PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
        .title(title)
        .content(content)
        .author("choidasol95@gmail.com")
        .build();

    String url = SURL + port + "/api/v1/posts";

    //when
    mockMvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(postsSaveRequestDto)))
        .andExpect(status().isOk());


    //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, postsSaveRequestDto, Long.class);

    //then
    // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    // assertThat(responseEntity.getBody()).isGreaterThan(0L);
    List<Posts> posts = postsRepository.findAll();
    assertThat(posts.get(0).getTitle()).isEqualTo(title);
    assertThat(posts.get(0).getContent()).isEqualTo(content);
  }

  @Test
  @WithMockUser(roles="USER")
  @DisplayName("Post 수정")
  public void updatePosts() throws Exception{
    //given
    Posts savedPost = postsRepository.save(Posts.builder().title("titles").content("content").build());

    Long updateId = savedPost.getId();
    String expectedTitle = "title1";
    String expectedContent = "content1";

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    String url = SURL + port + "/api/v1/posts/" + updateId;

    mockMvc.perform(put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().is2xxSuccessful());

    //when
    //HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
    //ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT ,requestEntity, Long.class);

    //then
    //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    // assertThat(responseEntity.getBody()).isGreaterThan(0L);

    Posts posts = postsRepository.findById(updateId).orElseThrow(() ->
        new IllegalArgumentException("해당 게시글이 존재하지 않음"));
    assertThat(posts.getTitle()).isEqualTo(expectedTitle);
    assertThat(posts.getContent()).isEqualTo(expectedContent);
  }

  @Test
  @WithMockUser(roles="USER")
  @DisplayName("Post 조회")
  public void findById() throws Exception{
    Posts posts = postsRepository.save(Posts.builder().title("title gg").content("content").build());

    Long id = posts.getId();

    String url = SURL + port + "/api/v1/posts/" + id;

    mockMvc.perform(get(url)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    /*
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<PostsResponseDto> responseEntity = restTemplate.getForEntity(url, PostsResponseDto.class);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    */

    Posts responsePosts = postsRepository.findById(id).orElseGet(() -> {return new Posts();});
    assertThat(responsePosts.getId()).isEqualTo(id);
    assertThat(responsePosts.getTitle()).isEqualTo(posts.getTitle());
    assertThat(responsePosts.getContent()).isEqualTo(posts.getContent());
  }
}