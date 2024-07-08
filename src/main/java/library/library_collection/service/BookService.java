package library.library_collection.service;

import library.library_collection.exception.UniqueFieldException;
import library.library_collection.model.Book;
import library.library_collection.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

        @Autowired
        private BookRepo bookRepository;

        public Book saveBook(Book book) {
            try {
                return bookRepository.save(book);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueFieldException("Article must be unique");
            }
        }

        public void deleteById(UUID id) {
            bookRepository.deleteById(id);
        }

        public Book findById(UUID id) {
            return bookRepository.findById(id).orElse(null);

        }

        public List<Book> findAll() {
            return bookRepository.findAll();
        }

        public List<Book> searchBooks(String keyword, String sortField, String sortDir) {
            Sort sort = null;
            if (sortField != null && sortDir != null) {
                sort = Sort.by(Sort.Direction.fromString(sortDir.toUpperCase()), sortField);
            }

            if (keyword != null && !keyword.isEmpty()) {
                if (sort != null) {
                    return bookRepository.findByTitleContaining(keyword, sort);
                } else {
                    return bookRepository.findByTitleContainingIgnoreCase(keyword);
                }
            } else {
                if (sort != null) {
                    return bookRepository.findAll(sort);
                } else {
                    return bookRepository.findAll();
                }
            }
        }
        public Book updateBook(UUID id, Book bookDetails) {
            Optional<Book> bookOptional = bookRepository.findById(id);
            if (!bookOptional.isPresent()) {
                throw new RuntimeException("Book not found");
            }
            Book book = bookOptional.get();
            book.setTitle(bookDetails.getTitle());
            book.setArticle(bookDetails.getArticle());
            book.setQuantity(bookDetails.getQuantity());
            book.setCategory(bookDetails.getCategory());
            book.setDescription(bookDetails.getDescription());
            return bookRepository.save(book);
        }
    }




