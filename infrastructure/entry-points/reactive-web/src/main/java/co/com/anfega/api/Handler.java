package co.com.anfega.api;

import co.com.anfega.api.dto.CreateTechnologyDTO;
import co.com.anfega.api.mapper.TechnologyDTOMapper;
import co.com.anfega.model.tecnology.gateways.TechnologyInputPort;
import com.crediya.library.api.BaseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler extends BaseHandler {
    private final TechnologyInputPort technologyInputPort;
    private final TechnologyDTOMapper technologyDTOMapper;

    public Mono<ServerResponse> listenSaveTechnologyUseCase(ServerRequest request) {
        return request.bodyToMono(CreateTechnologyDTO.class)
                .map(technologyDTOMapper::toModel)
                .flatMap(technologyInputPort::save)
                .map(technologyDTOMapper::toResponse)
                .flatMap(response -> created("Tecnologia creada con exito", response));
    }
}
