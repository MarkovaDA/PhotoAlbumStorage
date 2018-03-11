package darya.markova.photostorage.controller;

import darya.markova.photostorage.exception.UserUnathorizedException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralAdviceController {

    @ExceptionHandler(UserUnathorizedException.class)
    public ResponseEntity handleUserUnathorizedException(UserUnathorizedException ex) {
        return new ResponseEntity(ex, HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler(SignatureException.class)
//    public ResponseEntity handleJwtSignatureException(SignatureException ex) {
//        return new ResponseEntity(ex, HttpStatus.FORBIDDEN);
//    }
}
