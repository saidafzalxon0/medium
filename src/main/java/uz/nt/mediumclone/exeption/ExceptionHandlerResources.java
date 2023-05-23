package uz.nt.mediumclone.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerResources {
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException exception){
            Map<String,String> map = new HashMap<>();
            exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            });
            return map;
        }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotSavedException.class)
        public Map<String,String> handleBusinessException(UserNotSavedException e){
            Map<String,String> map =  new HashMap<>();
            map.put("errorMessage",e.getMessage());
            return map;
        }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String,String> handleBusinessException(UserNotFoundException e){
        Map<String,String> map =  new HashMap<>();
        map.put("errorMessage",e.getMessage());
        return map;
    }
}
