package co.com.anfega.api.helper.api;

import com.crediya.library.api.LocalApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class BaseHandler {

   protected <T> Mono<ServerResponse> ok(String message, T body) {
       log.debug("Respuesta OK: {} - {}", message, body);
       com.crediya.library.api.LocalApiResponse<T> response = new com.crediya.library.api.LocalApiResponse<>(message, body);
       return ServerResponse.ok().bodyValue(response);
   }

    protected <T> Mono<ServerResponse> ok(String message) {
        com.crediya.library.api.LocalApiResponse<T> response = new com.crediya.library.api.LocalApiResponse<>(message);
        return ServerResponse.ok().bodyValue(response);
    }

    protected <T> Mono<ServerResponse> created(String message,T body) {
        log.debug("Recurso creado: {}", body);
        com.crediya.library.api.LocalApiResponse<T> response = new LocalApiResponse<>(message, body);
        return ServerResponse.status(HttpStatus.CREATED).bodyValue(response);
    }

}
