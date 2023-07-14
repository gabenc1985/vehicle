package ec.pichincha.challenge.infraestructure.exception.handler;

import ec.pichincha.challenge.infraestructure.exception.ApplicationException;
import ec.pichincha.challenge.infraestructure.exception.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;

@ControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    ResponseEntity<ErrorResponse> handleApiException(ApplicationException applicationException) {
        var errorResponse = new ErrorResponse();
        errorResponse.setCode(applicationException.getCode());
        errorResponse.setMessage(applicationException.getMessage());
        //log.error("ApplicationExceptionHandler", applicationException);
        return new ResponseEntity<>(errorResponse, applicationException.getStatus());
    }

    @ExceptionHandler(ServerWebInputException.class)
    ResponseEntity<ErrorResponse> handleApiException(ServerWebInputException applicationException) {
        var errorResponse = new ErrorResponse();
        errorResponse.setCode("Q-400");
        errorResponse.setMessage(applicationException.getMessage());
        //log.error("ApplicationExceptionHandler", applicationException);
        return new ResponseEntity<>(errorResponse, applicationException.getStatusCode());
    }

}
