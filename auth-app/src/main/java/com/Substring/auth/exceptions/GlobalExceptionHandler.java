package com.Substring.auth.exceptions;
// this file or GlobalExceptionHandler  java class we handle exception

import com.Substring.auth.dtos.ApiError;
import com.Substring.auth.dtos.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // by this annotation we can handle exception of the application
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


@ExceptionHandler({
        UsernameNotFoundException.class,
        BadCredentialsException.class,
        CredentialsExpiredException.class,
//        ExpiredJwtException.class,
//        JwtException.class,
//        AuthenticationException.class
})
    public ResponseEntity<ApiError> handleAuthException(Exception e, HttpServletRequest request){
    logger.info("Exception :{}",e.getClass().getName());
    var apierror =ApiError.of(HttpStatus.BAD_REQUEST.value(),"Bad request", e.getMessage(), request.getRequestURI());
      return ResponseEntity.badRequest().body(ApiError.of(HttpStatus.BAD_REQUEST.value(), "Bad request", e.getMessage(), request.getRequestURI()));
    }



    @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
       ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalServerError);
   }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception){
        ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(internalServerError);
    }
}
