package com.rpa.dramascrapper.controller;


import com.rpa.dramascrapper.dto.Drama;
import com.rpa.dramascrapper.service.DramasService;
import com.rpa.dramascrapper.service.ReservedDramasService;
import com.rpa.dramascrapper.service.WatchedDramasService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnrollmentControllerTest {
    @InjectMocks
    public EnrollmentController enrollmentController;

    @Mock DramasService dramasService;
    @Mock ReservedDramasService reservedDramasService;
    @Mock WatchedDramasService watchedDramasService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.enrollmentController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void getEnrollment() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/enrollment").accept(MediaType.TEXT_HTML);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void postEnrollment() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2019-01-01");
        Drama dto = new Drama("EnrollmentControllerTest", date);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/enrollment")
                .param("name", "EnrollmentControllerTest")
                .param("firstAiringDate", "2019-01-01")
                .param("dramaType", "reserved");

        when(this.dramasService.addNewDramaToDramas(dto)).thenReturn(dto);
        when(this.reservedDramasService.addDramaToReserved(dto)).thenReturn(true);
        when(this.watchedDramasService.addDramasToWatched(dto)).thenReturn(true);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError())
                .andDo(print());

    }
}