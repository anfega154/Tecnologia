package co.com.anfega.api;

import co.com.anfega.api.dto.CreateTechnologyDTO;
import co.com.anfega.api.dto.DeleteTechnologyDTO;
import co.com.anfega.api.helper.api.BaseHandler;
import co.com.anfega.api.mapper.TechnologyDTOMapper;
import co.com.anfega.model.tecnology.gateways.TechnologyInputPort;
import jakarta.validation.Validator;
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
    private final Validator validator;

    public Mono<ServerResponse> listenSaveTechnologyUseCase(ServerRequest request) {
        return bodyToMonoValidated(validator, request, CreateTechnologyDTO.class)
                .map(technologyDTOMapper::toModel)
                .flatMap(technologyInputPort::save)
                .map(technologyDTOMapper::toResponse)
                .flatMap(response -> created("Tecnologia creada con exito", response));
    }

    public Mono<ServerResponse> listenGetAllTechnologiesUseCase(ServerRequest request) {
        return technologyInputPort.findAll()
                .map(technologyDTOMapper::toResponse)
                .collectList()
                .flatMap(list -> ok("Tecnologias encontradas", list));
    }

    public Mono<ServerResponse> listenDeleteTechnologiesByIds(ServerRequest request) {
        return bodyToMonoValidated(validator, request, DeleteTechnologyDTO.class)
                .map(DeleteTechnologyDTO::getIds)
                .flatMap(ids -> technologyInputPort.deleteByIds(ids)
                        .then(ok("Tecnologias eliminadas con exito")));
    }
}
