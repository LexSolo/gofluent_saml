package com.testtask.gofluent.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.testtask.gofluent.config.SamlLoginProperties;
import com.testtask.gofluent.config.SecurityConfig;
import com.testtask.gofluent.config.SignOnLoginConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SecurityConfig.class, SignOnLoginConfig.class, SamlLoginProperties.class})
@AutoConfigureMockMvc
@WebMvcTest(SamlController.class)
public class SamlControllerIT {

  @Autowired private MockMvc mockMvc;

  @Test
  public void index() throws Exception {
    mockMvc
        .perform(get("/"))
        .andExpect(view().name("index"))
        .andExpect(status().isOk());
  }

  @Test
  public void secured() throws Exception {
    mockMvc
        .perform(get("/secured/hello")
            .with(csrf())
            .param("name", "top.strategy1233@gmail.com")
        )
        .andDo(print())
        .andExpect(status().is3xxRedirection());
  }
}
