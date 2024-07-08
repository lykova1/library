package library.library_collection.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для проверки валидации модели {@link Book}.
 */
class BookModelTest {

    private Validator validator;

    /**
     * Настройка валидатора перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        validator = factoryBean.getValidator();
    }

    /**
     * Тест для проверки, что валидация не проходит, если артикул книги пустой.
     */
    @Test
    void whenTitleIsBlank_thenValidationFails() {
        Book book = new Book();
        book.setTitle("");
        book.setArticle("12345");
        book.setDescription("Description");
        book.setCategory("Category");
        book.setReleaseYear(2022);
        book.setQuantity(5);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("не должно быть пустым");
    }

    /**
     * Тест для проверки, что валидация не проходит, если год публикации слишком мал.
     */
    @Test
    void whenReleaseYearIsTooLow_thenValidationFails() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setArticle("12345");
        book.setDescription("Description");
        book.setCategory("Category");
        book.setReleaseYear(1800);
        book.setQuantity(5);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("должно быть не меньше 1900");
    }

    /**
     * Тест для проверки, что валидация не проходит, если количество книг отрицательное.
     */
    @Test
    void whenQuantityIsNegative_thenValidationFails() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setArticle("12345");
        book.setDescription("Description");
        book.setCategory("Category");
        book.setReleaseYear(2022);
        book.setQuantity(-1);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("должно быть не меньше 0");
    }

    /**
     * Тест для проверки, что валидация проходит, если все поля книги заполнены корректно.
     */
    @Test
    void whenAllFieldsAreValid_thenValidationPasses() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setArticle("12345");
        book.setDescription("Description");
        book.setCategory("Category");
        book.setReleaseYear(2022);
        book.setQuantity(5);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertThat(violations).isEmpty();
    }
}
