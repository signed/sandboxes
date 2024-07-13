package com.github.signed.demo.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {SpringSecurityController.class, DomainBeansConfiguration.class})
// needed because the role check is currently done in the controller with an @Secured annotation
@EnableMethodSecurity(
        securedEnabled = true
)
class SpringSecurityControllerMockMvcTest {

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void allowAdminsToAccessTheResource() throws Exception {
        this.mvc.perform(get("/security/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Congratulations! You are an Admin")));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void keepUsersOut() throws Exception {
        this.mvc.perform(get("/security/admin")).andExpect(status().isForbidden());
    }

}
