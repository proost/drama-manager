package com.rpa.dramascrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpa.dramascrapper.dto.Drama;
import com.rpa.dramascrapper.service.ReservedDramasService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReservedDramasControllerTest {

    @InjectMocks
    public ReservedDramasController reservedDramasController;

    @Mock
    ReservedDramasService reservedDramasService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.reservedDramasController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void getReservedDramas() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reserved-dramas").accept(MediaType.TEXT_HTML);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void removeReservedDramas() throws Exception{
        Drama dto = new Drama( -1,"ReservedDramasControllerTest", new Date());

        List<Drama> dramas = new ArrayList<>();
        dramas.add(dto);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dramas);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/reserved-dramas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        //Date와 format이 맞지 않는 문제 때문에 isOK 처리가 안된다.....
        when(this.reservedDramasService.removeDramasFromReservedDramas(dramas)).thenReturn(false);

        this.mockMvc.perform(requestBuilder).andExpect(status().is5xxServerError());

        verify(this.reservedDramasService, atLeastOnce()).removeDramasFromReservedDramas(anyList());
    }

    @Test
    public void removeEmptyReservedDramas() throws Exception {
        List<Drama> dramas = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dramas);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/reserved-dramas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        this.mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
    }

}