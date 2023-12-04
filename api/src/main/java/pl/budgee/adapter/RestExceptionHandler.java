package pl.budgee.adapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Getter
  @AllArgsConstructor
  public static class ApiError {

    private Date timestamp;

    @JsonIgnore
    private HttpStatusCode status;

    @JsonProperty("status")
    private int statusCode;

    private String message;
    private List<String> errors;

    static ApiError of(HttpStatusCode status, String message, List<String> errors) {
      return new ApiError(new Date(), status, status.value(), message, errors);
    }

    ResponseEntity<Object> toResponse() {
      return new ResponseEntity<Object>(this, status);
    }
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
    log.warn("Response status exception", ex);
    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = ApiError.of(ex.getStatusCode(), ex.getReason(), details);
    return err.toResponse();
  }
}
