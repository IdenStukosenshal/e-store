package e_store.aspects;

import e_store.dto.out.ErrorMessageDto;
import e_store.enums.ErrorCode;
import e_store.exceptions.LocalizedValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessageDto> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException excc) {
        Map<String, String> excMap = excc.getAllErrors().stream().collect(Collectors.toMap(
                error -> ((FieldError) error).getField(), //В случае каких-либо кастомных аннотаций валидации перестанет работать
                error -> error.getDefaultMessage()
        ));
        Locale locale = LocaleContextHolder.getLocale();
        String errorMsg = messageSource.getMessage(ErrorCode.VALIDATION_FIELDS.getMsg(), null, locale);
        ErrorMessageDto error = new ErrorMessageDto(errorMsg, excMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(LocalizedValidationException.class)
    public ResponseEntity<ErrorMessageDto> handleLocalizedValidationException(LocalizedValidationException exc) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMsg = messageSource.getMessage(exc.getMsgCode(), exc.getMsgArgs(), locale);
        ErrorMessageDto error = new ErrorMessageDto(errorMsg, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> handleInternalException(Exception excc) {
        String errorMsg = messageSource.getMessage(ErrorCode.INTERNAL.getMsg(), null, LocaleContextHolder.getLocale());
        ErrorMessageDto error = new ErrorMessageDto(errorMsg, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
