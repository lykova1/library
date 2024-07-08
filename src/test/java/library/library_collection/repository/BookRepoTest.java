package library.library_collection.repository;

import library.library_collection.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для проверки функциональности репозитория {@link BookRepo}.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class BookRepoTest {

    @Autowired
    private BookRepo bookRepo;

    /**
     * Тест для проверки поиска книг по заголовку без учета регистра.
     */
    @Test
    void testFindByTitleContainingIgnoreCase() {
        // Создаем и сохраняем книгу
        Book book = new Book();
        book.setTitle("Test Book");
        book.setArticle("12345");
        book.setDescription("Description");
        book.setCategory("Category");
        book.setReleaseYear(2022);
        book.setQuantity(5);
        bookRepo.save(book);

        // Выполняем поиск по заголовку без учета регистра
        List<Book> books = bookRepo.findByTitleContainingIgnoreCase("test");

        // Проверяем, что книга найдена
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book");
    }

    /**
     * Тест для проверки поиска книг по заголовку с сортировкой.
     */
    @Test
    void testFindByTitleContainingWithSort() {
        // Создаем и сохраняем книгу
        Book book = new Book();
        book.setTitle("Test Book");
        book.setArticle("12345");
        book.setDescription("Description");
        book.setCategory("Category");
        book.setReleaseYear(2022);
        book.setQuantity(5);
        bookRepo.save(book);

        // Выполняем поиск по заголовку с сортировкой
        List<Book> books = bookRepo.findByTitleContaining("Test", Sort.by(Sort.Direction.ASC, "title"));

        // Проверяем, что книга найдена
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book");
    }
}
