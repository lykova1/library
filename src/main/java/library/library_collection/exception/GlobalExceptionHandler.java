package library.library_collection.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений для централизованной обработки ошибок в приложении.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка неожиданных исключений.
     *
     * @param ex исключение типа Exception
     * @param request объект WebRequest для получения информации о запросе
     * @return ResponseEntity с деталями об ошибке и HTTP статусом INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An unexpected error occurred");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обработка исключений валидации входных данных.
     *
     * @param ex исключение типа MethodArgumentNotValidException
     * @return ResponseEntity с деталями об ошибках валидации и HTTP статусом BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработка исключений, связанных с уникальностью полей.
     *
     * @param ex исключение типа UniqueFieldException
     * @return ResponseEntity с деталями об ошибке уникальности поля и HTTP статусом BAD_REQUEST
     */
    @ExceptionHandler(UniqueFieldException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateFieldException(UniqueFieldException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
