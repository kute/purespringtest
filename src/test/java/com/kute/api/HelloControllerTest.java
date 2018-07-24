package com.kute.api;

import com.kute.controller.HelloController;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * created by kute on 2018/06/28 22:40
 */
public class HelloControllerTest {

    @Test
    public void testIndex() throws Exception {

        HelloController controller = new HelloController();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        RequestBuilder requestBuilder = get("/api/v1/hello/");

        ResultMatcher resultMatcher = status().is2xxSuccessful();

        mockMvc.perform(requestBuilder).andExpect(resultMatcher);

    }

}
