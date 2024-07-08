package library.library_collection.controller;

import library.library_collection.model.Book;
import library.library_collection.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Контроллер для отображения и управления представлениями книг.
 */
@Controller
public class BookViewController {

    @Autowired
    private BookService bookService;

    /**
     * Отображение списка книг.
     *
     * @param model объект Model для передачи данных в представление
     * @return имя представления для отображения списка книг
     */
    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book-list";
    }

    /**
     * Отображение формы добавления книги.
     *
     * @param model объект Model для передачи данных в представление
     * @return имя представления для формы добавления книги
     */
    @GetMapping("/book-add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-add";
    }

    /**
     * Обработка запроса на добавление новой книги.
     *
     * @param book данные новой книги
     * @return перенаправление на страницу со списком книг
     */
    @PostMapping("/book-add")
    public String addBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    /**
     * Обработка запроса на удаление книги по идентификатору.
     *
     * @param id идентификатор книги для удаления
     * @return перенаправление на страницу со списком книг
     */
    @GetMapping("/book-delete/{id}")
    public String deleteBook(@PathVariable("id") UUID id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    /**
     * Отображение формы обновления информации о книге.
     *
     * @param id    идентификатор книги для обновления
     * @param model объект Model для передачи данных в представление
     * @return имя представления для формы обновления книги
     */
    @GetMapping("/book-update/{id}")
    public String updateUserForm(@PathVariable("id") UUID id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book-update";
    }

    /**
     * Обработка запроса на обновление информации о книге.
     *
     * @param book данные обновленной книги
     * @return перенаправление на страницу со списком книг
     */
    @PostMapping("/book-update")
    public String updateBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }
}
