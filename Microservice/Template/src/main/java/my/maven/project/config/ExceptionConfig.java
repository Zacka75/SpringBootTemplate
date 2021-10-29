package my.maven.project.config;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	private Logger log = LoggerFactory.getLogger(ExceptionConfig.class);
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
	@ExceptionHandler(Exception.class)	
	public ResponseEntity<List<String>> handleGenericException(Exception ex, WebRequest request) throws Throwable {
		
		log.error("Exception: {}", ex);
		log.error("Exception Message: {}", ex.getMessage());
		log.error("Exception StackTrace: {}", ex.getStackTrace());
		log.error("Exception StackTrace toString: {}", ex.getStackTrace().toString());
		log.error("Exception fillInStackTrace: {}", ex.fillInStackTrace());
		log.error("Exception Cause: {}", ex.getCause());
		
        Throwable cause = ex.getCause();
        if (!(cause instanceof RollbackException)) throw cause;
        if (!(cause.getCause() instanceof ConstraintViolationException)) throw cause.getCause();
        ConstraintViolationException validationException = (ConstraintViolationException) cause.getCause();
        List<String> messages = validationException.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        
        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
		
	}	
	
	// IN CASO DI RequestValidator CHE LASCIA UNA ConstraintViolationException MA SOLO PER I SERVIZI REST
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Validation Request Error")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity< BaseResponse /*List<String>*/> handleConstraintViolationException(ConstraintViolationException ex) {
		
		log.error("Exception: {}", ex);
		log.error("Exception Message: {}", ex.getMessage());
		log.error("Exception StackTrace: {}", ex.getStackTrace());
		log.error("Exception StackTrace toString: {}", ex.getStackTrace().toString());
		log.error("Exception fillInStackTrace: {}", ex.fillInStackTrace());
		log.error("Exception Cause: {}", ex.getCause());
		
		BaseResponse response = new BaseResponse();
		
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		
        List<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        
        response.setReturnCode(HttpStatus.BAD_REQUEST.value());
        response.setReturnMessage(messages.get(0));

		return new ResponseEntity< BaseResponse >(response/*messages*/, HttpStatus.BAD_REQUEST);

	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="SQL Exception")
	@ExceptionHandler(SQLException.class)
	public void handleSQLException(SQLException ex, WebRequest request) {
		
		log.error("SQLException: " + ex);
		log.error("SQLException Message: " + ex.getMessage());
		log.error("SQLException StackTrace: " + ex.getStackTrace());
		log.error("SQLException StackTrace toString: " + ex.getStackTrace().toString());
		log.error("SQLException fillInStackTrace: " + ex.fillInStackTrace());
		log.error("SQLException ErrorCode: " + ex.getErrorCode());
		log.error("SQLException SQLState: " + ex.getSQLState());
		log.error("SQLException Cause: " + ex.getCause());
		log.error("SQLException initCause(ex): " + ex.initCause(ex));
		log.error("SQLException NextException: " + ex.getNextException());
		
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="SQL Grammar Exception")
	@ExceptionHandler(SQLGrammarException.class)
	public void handleSQLGrammarException(SQLGrammarException ex, WebRequest request) {
		
		log.error("SQLGrammarException: " + ex);
		log.error("SQLGrammarException Message: " + ex.getMessage());
		log.error("SQLGrammarException StackTrace: " + ex.getStackTrace());
		log.error("SQLGrammarException StackTrace toString: " + ex.getStackTrace().toString());
		log.error("SQLGrammarException fillInStackTrace: " + ex.fillInStackTrace());
		log.error("SQLGrammarException ErrorCode: " + ex.getErrorCode());
		log.error("SQLGrammarException SQL: " + ex.getSQL());
		log.error("SQLGrammarException SQL State: " + ex.getSQLState());
		log.error("SQLGrammarException Cause: " + ex.getCause());
		
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Persistence Exception")
	@ExceptionHandler(PersistenceException.class)
	public void handlePersistenceException(PersistenceException ex, WebRequest request) {
		
		log.error("PersistenceException: " + ex);
		log.error("PersistenceException Message: " + ex.getMessage());
		log.error("PersistenceException StackTrace: " + ex.getStackTrace());
		log.error("PersistenceException StackTrace toString: " + ex.getStackTrace().toString());
		log.error("PersistenceException fillInStackTrace: " + ex.fillInStackTrace());
		log.error("PersistenceException Cause: " + ex.getCause());
		
	}
	
}

@Data
//@AllArgsConstructor
@NoArgsConstructor
//@Builder
class BaseResponse {
	private int returnCode;
	private String returnMessage;
}
