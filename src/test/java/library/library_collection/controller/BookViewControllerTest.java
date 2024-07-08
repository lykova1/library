package library.library_collection.controller;

import library.library_collection.model.Book;
import library.library_collection.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тестовый класс для {@link BookViewController}.
 */
@WebMvcTest(BookViewController.class)
class BookViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    /**
     * Тест для метода {@link BookViewController#listBooks(org.springframework.ui.Model)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testListBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-list"))
                .andExpect(model().attributeExists("books"));
    }

    /**
     * Тест для метода {@link BookViewController#addBookForm(org.springframework.ui.Model)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testAddBookForm() throws Exception {
        mockMvc.perform(get("/book-add"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-add"))
                .andExpect(model().attributeExists("book"));
    }

    /**
     * Тест для метода {@link BookViewController#addBook(Book)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testAddBook() throws Exception {
        mockMvc.perform(post("/book-add")
                        .param("title", "Test Book")
                        .param("article", "12345")
                        .param("description", "Description")
                        .param("category", "Category")
                        .param("releaseYear", "2022")
                        .param("quantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    /**
     * Тест для метода {@link BookViewController#deleteBook(UUID)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testDeleteBook() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(get("/book-delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    /**
     * Тест для метода {@link BookViewController#updateUserForm(UUID, org.springframework.ui.Model)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testUpdateUserForm() throws Exception {
        UUID id = UUID.randomUUID();
        Book book = new Book();
        book.setId(id);
        Mockito.when(bookService.findById(id)).thenReturn(book);

        mockMvc.perform(get("/book-update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("book-update"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", book));
    }

    /**
     * Тест для метода {@link BookViewController#updateBook(Book)}.
     *
     * @throws Exception если происходит ошибка выполнения запроса
     */
    @Test
    void testUpdateBook() throws Exception {
        mockMvc.perform(post("/book-update")
                        .param("title", "Updated Book")
                        .param("article", "12345")
                        .param("description", "Updated Description")
                        .param("category", "Updated Category")
                        .param("releaseYear", "2022")
                        .param("quantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }
}
