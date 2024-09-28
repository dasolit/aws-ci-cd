package com.ds.springbootaws.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("메인페이지 로딩 테스트")
  public void getIndex() {
    String body = this.restTemplate.getForObject("/", String.class);

    assertThat(body.contains("스프링 부트로 시작하는 웹 서비스")).isTrue();
  }


}