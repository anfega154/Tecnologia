package co.com.anfega.api.helper.error;

import com.crediya.library.error.ExceptionStrategy;
import org.springframework.stereotype.Component;

@Component
public class RuntimeExceptionStrategy implements ExceptionStrategy {
    @Override
    public boolean supports(Throwable ex) {
        return ex instanceof RuntimeException;
    }

    @Override
    public org.springframework.http.HttpStatus getStatus(Throwable ex) {
        return org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getError(Throwable ex) {
        return "Runtime exception occurred: " + ex.getMessage();
    }
}
