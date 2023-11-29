package ru.otus.spring;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.spring.service.impl.LibraryServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class LibraryMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryServiceImpl libraryService;

    @Test
    void onGetAllBooks_always_correctProcessing() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/books");

        mockMvc.perform(request).andExpect(status().isOk());

        Mockito.verify(libraryService, Mockito.times(1)).getAllBooks();
    }

    @Test
    void onGetBookById_always_correctProcessing() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/books/{id}",1L);

        mockMvc.perform(request).andExpect(status().isOk());

        Mockito.verify(libraryService, Mockito.times(1)).getBookById(Mockito.any());
    }

    @Test
    void onAddBook_always_correctProcessing() throws Exception {

        String newBookJson = "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"book\",\n" +
                "  \"genre\": {\"id\": 1\n" +
                "  },\n" +
                "  \"author\": {\"id\": 1\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBookJson))
                .andExpect(status().isOk());

        Mockito.verify(libraryService, Mockito.times(1)).saveBook(Mockito.any());
    }



}
