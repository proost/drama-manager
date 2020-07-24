package com.rpa.dramascrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpa.dramascrapper.dto.Drama;
import com.rpa.dramascrapper.service.DramasService;
import com.rpa.dramascrapper.service.WatchedDramasService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class WatchedDramasControllerTest {

    @InjectMocks
    public WatchedDramasController watchedDramasController;

    @Mock
    WatchedDramasService watchedDramasService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.watchedDramasController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void getReservedDramas() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/watched-dramas").accept(MediaType.TEXT_HTML);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void removeWatchedDramas() throws Exception{
        List<Map<String, Object>> dramas = new ArrayList<>();
        Map<String, Object> drama = new HashMap<>();
        drama.put("dramaId", -1);
        dramas.add(drama);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dramas);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/watched-dramas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        when(this.watchedDramasService.removeWatchedDramas(dramas)).thenReturn(true);

        this.mockMvc.perform(requestBuilder).andExpect(status().isOk());

        verify(this.watchedDramasService, atLeastOnce()).removeWatchedDramas(anyList());
    }

    @Test
    public void removeEmptyWatchedDramas() throws Exception {
        List<Drama> dramas = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dramas);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/watched-dramas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        this.mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
    }

}