package library.library_collection.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import library.library_collection.model.Book;
import library.library_collection.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST контроллер для управления книгами.
 */
@Validated
@Tag(name = "Книги", description = "Управление книгами")
@RestController
@RequestMapping("/api")
public class BookRestController {

    @Autowired
    private BookService bookService;

    /**
     * Добавление книги в базу данных.
     *
     * @param book данные книги для добавления
     * @return объект ResponseEntity с сохраненной книгой и статусом CREATED
     */
    @Operation(
            summary = "Добавление книги",
            description = "Позволяет добавить новую книгу в базу данных"
    )
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {

        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    /**
     * Удаление книги из базы данных по идентификатору.
     *
     * @param id идентификатор книги для удаления
     * @return объект ResponseEntity с пустым телом и статусом NO_CONTENT
     */
    @Operation(
            summary = "Удаление книги",
            description = "Позволяет удалить книгу из базы данных"
    )
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") @NotNull UUID id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обновление информации о книге в базе данных.
     *
     * @param id          идентификатор книги для обновления
     * @param bookDetails обновленные данные книги
     * @return объект ResponseEntity с обновленной книгой и статусом OK
     */
    @Operation(
            summary = "Обновление информации о книге",
            description = "Принимает обновленные данные книги и сохраняет их в базе данных."
    )
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") UUID id, @Valid @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Получение списка всех книг.
     *
     * @param keyword  ключевое слово для поиска книг (опционально)
     * @param sortField поле для сортировки книг (опционально)
     * @param sortDir  направление сортировки (ASC или DESC, опционально)
     * @return объект ResponseEntity со списком книг и статусом OK
     */
    @Operation(
            summary = "Список книг",
            description = "Позволяет вывести список всех книг"
    )
    @GetMapping("/books")
    public ResponseEntity<List<Book>> listBooks(@RequestParam(value = "keyword", required = false) String keyword,
                                                @RequestParam(value = "sortField", required = false) String sortField,
                                                @RequestParam(value = "sortDir", required = false) String sortDir) {
        List<Book> books = bookService.searchBooks(keyword, sortField, sortDir);
        return ResponseEntity.ok(books);
    }
}
