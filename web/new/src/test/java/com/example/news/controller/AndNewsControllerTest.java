package com.example.news.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class AndNewsControllerTest {
    @Autowired
    AndNewsController newsController;

    @Test
    public void testSendNewsRequest() throws IOException {
        System.out.println(newsController.SendNewsRequest("新闻", "0", "10"));
    }
}
