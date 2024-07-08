package library.library_collection.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность, представляющая книгу в библиотечной коллекции.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
@Schema(description = "Сущность книги")
public class Book {

    /**
     * Уникальный идентификатор книги.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    /**
     * Название книги.
     */
    @NotBlank
    @Schema(description = "Название")
    @Column(nullable = false)
    private String title;

    /**
     * Артикул книги, уникальный идентификатор.
     */
    @NotNull
    @Schema(description = "Артикул")
    @Column(nullable = false, unique = true)
    private String article;

    /**
     * Описание содержимого книги.
     */
    @NotBlank
    @Schema(description = "Описание")
    private String description;

    /**
     * Категория, к которой относится книга.
     */
    @NotBlank
    @Schema(description = "Категория")
    private String category;

    /**
     * Год публикации книги.
     */
    @Min(1900)
    @NotNull
    @Schema(description = "Дата публикации")
    private int releaseYear;

    /**
     * Количество экземпляров книги в библиотеке.
     */
    @Min(0)
    @Max(10)
    @NotNull
    @Schema(description = "Количество")
    private int quantity;

    /**
     * Время последнего изменения записи о книге.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @UpdateTimestamp
    private LocalDateTime lastModified;

    /**
     * Время создания записи о книге.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
