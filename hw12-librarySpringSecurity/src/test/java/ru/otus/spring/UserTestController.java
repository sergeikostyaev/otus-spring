package ru.otus.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.controller.LibraryController;
import ru.otus.spring.security.SecurityConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LibraryController.class)
@Import(SecurityConfiguration.class)
public class UserTestController {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    public void onUserGetBooks_always_okStatus() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());

    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    public void onUserGetBookInfo_always_okStatus() throws Exception {
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk());

    }

}
