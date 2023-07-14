package ec.pichincha.challenge.infraestructure.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{
    private final String code;
    private final String message;

    private final HttpStatus status;
    public ApplicationException(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
