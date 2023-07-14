package ec.pichincha.challenge.infraestructure.exception;

import org.springframework.http.HttpStatus;

public class ApplicationError {

    private ApplicationError(){}

    public static final ApplicationException QUOTE_NOT_FOUND
            = new ApplicationException("Q-001", "Cotización no encontrada y/o expirada", HttpStatus.BAD_REQUEST);

    public static final ApplicationException QUOTE_INCORRECT
            = new ApplicationException("Q-002", "No coincide los datos de la cotización", HttpStatus.BAD_REQUEST);

    public static final ApplicationException NOT_CONTENT
            = new ApplicationException("Q-204", "Not content", HttpStatus.NO_CONTENT);

    public static final ApplicationException NOT_FOUND_REGISTER
            = new ApplicationException("Q-003", "No register found in database", HttpStatus.BAD_REQUEST);
}
