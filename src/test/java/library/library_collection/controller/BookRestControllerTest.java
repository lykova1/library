package library.library_collection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.library_collection.model.Book;
import library.library_collection.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тестовый класс для {@link BookRestController}.
 */
@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Метод инициализации, который выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода {@link BookRestController#addBook(Book)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testAddBook() throws Exception {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setArticle("TB-001");
        book.setDescription("Test Description");
        book.setCategory("Test Category");
        book.setReleaseYear(2020);
        book.setQuantity(10);

        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    /**
     * Тест для метода {@link BookRestController#deleteBook(UUID)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testDeleteBook() throws Exception {
        UUID bookId = UUID.randomUUID();

        mockMvc.perform(delete("/api/books/{id}", bookId.toString()))
                .andExpect(status().isNoContent());
    }

    /**
     * Тест для метода {@link BookRestController#updateBook(UUID, Book)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testUpdateBook() throws Exception {
        UUID bookId = UUID.randomUUID();
        Book book = new Book();
        book.setTitle("Updated Title");
        book.setArticle("Updated-Article");
        book.setDescription("Updated Description");
        book.setCategory("Updated Category");
        book.setReleaseYear(2021);
        book.setQuantity(5);

        when(bookService.updateBook(any(UUID.class), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/{id}", bookId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.article").value("Updated-Article"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.category").value("Updated Category"))
                .andExpect(jsonPath("$.releaseYear").value(2021))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    /**
     * Тест для метода {@link BookRestController#listBooks(String, String, String)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testListBooks() throws Exception {
        when(bookService.searchBooks(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Тест для глобальной обработки исключений в контроллере.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testGlobalException() throws Exception {
        when(bookService.findById(any(UUID.class))).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/books/" + UUID.randomUUID()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }
}
