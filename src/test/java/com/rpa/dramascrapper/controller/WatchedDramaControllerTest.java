package com.rpa.dramascrapper.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class WatchedDramaControllerTest {

    @InjectMocks
    public WatchedDramasController watchedDramaController;

    @Mock
    WatchedDramasService watchedDramaService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.watchedDramaController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void getWatchedDrama() throws Exception {
        Map<String, Object> testObj = new HashMap<>();
        testObj.put("id", 0);
        testObj.put("name", "WatchedDramaControllerTest");
        testObj.put("first_airing_date", "2020-01-01");
        testObj.put("last_watched_season", 1);

        when(this.watchedDramaService.getWatchedDramaById(0)).thenReturn(testObj);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/watched-drama/0").accept(MediaType.TEXT_HTML);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());

    }


}
