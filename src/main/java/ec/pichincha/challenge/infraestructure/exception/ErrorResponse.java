package ec.pichincha.challenge.infraestructure.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;
}
