package ru.otus.spring;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.spring.service.impl.LibraryServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class LibraryMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryServiceImpl libraryService;

    @Test
    void onGetBookListPage_always_correctProcessing() throws Exception {
        MockHttpServletRequestBuilder request = get("/book");

        mockMvc.perform(request).andExpect(status().isOk());

        Mockito.verify(libraryService, Mockito.times(1)).getAllBooks();
        Mockito.verify(libraryService, Mockito.times(1)).getAllAuthors();
        Mockito.verify(libraryService, Mockito.times(1)).getAllGenres();
    }



}
