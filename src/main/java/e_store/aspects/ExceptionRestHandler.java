package e_store.aspects;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionRestHandler {

    private final MessageSource messageSource;

    public ExceptionRestHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException excc) {
        Map<String, String> excMap = excc.getAllErrors().stream().collect(Collectors.toMap(
                error -> ((FieldError) error).getField(), //В случае каких-либо кастомных аннотаций валидации перестанет работать
                error -> error.getDefaultMessage()
        ));
        return new ResponseEntity<>(excMap, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException excc) {
        Map<String, String> excMap = Map.of("error", excc.getMessage());
        return new ResponseEntity<>(excMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exc) {

        String EntityName = exc.getMessage();
        Locale locale = LocaleContextHolder.getLocale();
        String errorMsg = messageSource.getMessage("error.notfound", new Object[]{EntityName}, locale);

        return new ResponseEntity<>(errorMsg, HttpStatus.NOT_FOUND);
        //TODO сделать dto для этого?
    }
}
