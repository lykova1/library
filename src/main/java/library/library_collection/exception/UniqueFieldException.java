package library.library_collection.exception;

/**
 * Исключение, выбрасываемое при попытке создать объект с неправильными или недопустимыми значениями уникальных полей.
 */
public class UniqueFieldException extends RuntimeException {

    /**
     * Конструктор для создания исключения с пользовательским сообщением.
     *
     * @param message сообщение об ошибке, объясняющее причину исключения
     */
    public UniqueFieldException(String message) {
        super(message);
    }
}
