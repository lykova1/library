package library.library_collection.repository;

import library.library_collection.model.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для доступа к данным книги.
 */
public interface BookRepo extends JpaRepository<Book, UUID> {

    /**
     * Найти книги по части названия, игнорируя регистр.
     *
     * @param keyword ключевое слово для поиска в названии книги
     * @return список книг, содержащих указанное ключевое слово в названии
     */
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    /**
     * Найти книги по части названия и отсортировать результаты.
     *
     * @param keyword ключевое слово для поиска в названии книги
     * @param sort    параметры сортировки результатов
     * @return список книг, содержащих указанное ключевое слово в названии и отсортированных по заданным критериям
     */
    List<Book> findByTitleContaining(String keyword, Sort sort);
}
