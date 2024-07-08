package library.library_collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс для запуска приложения Library Collection.
 */
@SpringBootApplication
public class LibraryCollectionApplication {

	/**
	 * Основной метод, который запускает приложение Spring Boot.
	 *
	 * @param args аргументы командной строки (обычно не используются)
	 */
	public static void main(String[] args) {
		SpringApplication.run(LibraryCollectionApplication.class, args);
	}

}
