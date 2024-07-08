package library.library_collection.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для {@link GlobalExceptionHandler}.
 */
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    /**
     * Инициализация Mockito перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Тест метода {@link GlobalExceptionHandler#handleGlobalException(Exception, WebRequest)},
     * который должен возвращать статус INTERNAL_SERVER_ERROR.
     */
    @Test
    void handleGlobalException_ShouldReturnInternalServerError() {
        Exception ex = new Exception("Test Exception");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = globalExceptionHandler.handleGlobalException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals("An unexpected error occurred", body.get("message"));
    }

    /**
     * Тест метода {@link GlobalExceptionHandler#handleValidationExceptions(MethodArgumentNotValidException)},
     * который должен возвращать статус BAD_REQUEST.
     */
    @Test
    void handleValidationExceptions_ShouldReturnBadRequest() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        FieldError fieldError = new FieldError("objectName", "fieldName", "defaultMessage");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = response.getBody();
        assertEquals(1, errors.size());
        assertEquals("defaultMessage", errors.get("fieldName"));
    }

    /**
     * Тест метода {@link GlobalExceptionHandler#handleDuplicateFieldException(UniqueFieldException)},
     * который должен возвращать статус BAD_REQUEST.
     */
    @Test
    void handleDuplicateFieldException_ShouldReturnBadRequest() {
        UniqueFieldException ex = new UniqueFieldException("Field must be unique");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleDuplicateFieldException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = response.getBody();
        assertEquals(1, errors.size());
        assertEquals("Field must be unique", errors.get("error"));
    }
}
