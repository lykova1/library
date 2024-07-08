package library.library_collection.service;

import library.library_collection.model.Book;
import library.library_collection.repository.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тесты для проверки функциональности класса BookService.
 * Использует Mockito для создания моков зависимостей.
 */
class BookServiceTest {

    @Mock
    private BookRepo bookRepository;

    @InjectMocks
    private BookService bookService;

    /**
     * Подготовка перед каждым тестом: инициализация моков и зависимостей.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест сохранения книги в базе данных.
     * Проверяет, что книга успешно сохраняется и сохраненная книга имеет правильный заголовок.
     */
    @Test
    void testSaveBook() {
        // Подготовка данных для теста
        Book book = new Book();
        book.setTitle("Test Book");

        // Задаем поведение мока: при сохранении любой книги возвращать подготовленную книгу
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Вызываем метод сервиса для сохранения книги
        Book savedBook = bookService.saveBook(book);

        // Проверяем результаты
        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
    }

    /**
     * Тест удаления книги по её идентификатору.
     * Проверяет, что удаление выполняется без ошибок и метод удаления вызывается один раз.
     */
    @Test
    void testDeleteById() {
        // Подготовка данных для теста
        UUID bookId = UUID.randomUUID();

        // Задаем поведение мока: метод deleteById не делает ничего при вызове с заданным идентификатором книги
        doNothing().when(bookRepository).deleteById(bookId);

        // Проверяем, что при вызове сервиса метод удаления книги не вызывает исключений и вызывается один раз
        assertDoesNotThrow(() -> bookService.deleteById(bookId));
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    /**
     * Тест обновления информации о книге.
     * Проверяет, что обновление происходит корректно и заголовок книги успешно изменяется.
     */
    @Test
    void testUpdateBook() {
        // Подготовка данных для теста
        UUID bookId = UUID.randomUUID();
        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Old Title");

        Book updatedDetails = new Book();
        updatedDetails.setTitle("New Title");

        // Задаем поведение мока: при запросе по идентификатору возвращать существующую книгу,
        // при сохранении книги возвращать ту же самую книгу
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        // Вызываем метод сервиса для обновления информации о книге
        Book updatedBook = bookService.updateBook(bookId, updatedDetails);

        // Проверяем результаты
        assertNotNull(updatedBook);
        assertEquals("New Title", updatedBook.getTitle());
    }

    /**
     * Тест поиска книг по ключевому слову с сортировкой.
     * Проверяет, что метод поиска по заголовку книги вызывается с правильными параметрами.
     */
    @Test
    void testSearchBooks() {
        // Подготовка данных для теста
        String keyword = "Test";
        String sortField = "title";
        String sortDir = "asc";

        // Вызываем метод сервиса для поиска книг по ключевому слову с заданными параметрами сортировки
        bookService.searchBooks(keyword, sortField, sortDir);

        // Проверяем, что метод репозитория вызывается один раз с правильными параметрами сортировки
        verify(bookRepository, times(1)).findByTitleContaining(keyword, Sort.by(Sort.Direction.ASC, sortField));
    }
}
