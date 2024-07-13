package com.github.signed.demo.http;

import com.github.signed.demo.core.BusinessLogic;
import com.github.signed.demo.core.BusinessLogicResult;
import com.github.signed.demo.core.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BusinessLogicController.class, DomainBeansConfiguration.class})
class BusinessLogicControllerStatusCodesTest {

    @MockBean
    BusinessLogic businessLogic;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(value = "annabelle")
    void replaceBusinessLogicWithMock() throws Exception {
        when(businessLogic.callLogic(any(), any())).thenReturn(BusinessLogicResult.success(new BusinessLogicResult.BusinessLogicOk(new Price(BigDecimal.TWO))));
        this.mvc.perform(post("/logic/verbose").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "product": "cheese",
                                  "quantity": 2
                                }"""))
                .andExpect(status().isOk());
    }
}
