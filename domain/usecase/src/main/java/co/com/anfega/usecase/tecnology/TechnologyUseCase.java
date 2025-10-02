package co.com.anfega.usecase.tecnology;

import co.com.anfega.model.tecnology.Technology;
import co.com.anfega.model.tecnology.gateways.TechnologyInputPort;
import co.com.anfega.model.tecnology.gateways.TechnologyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TechnologyUseCase implements TechnologyInputPort {

    private final TechnologyRepository technologyRepository;

    public TechnologyUseCase(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public Mono<Technology> save(Technology technology) {
        return technologyRepository.findByName(technology.getName())
                .flatMap(existing -> Mono.<Technology>error(new IllegalStateException("El nombre ya existe")))
                .switchIfEmpty(Mono.defer(() -> technologyRepository.save(technology)));
    }

    @Override
    public Flux<Technology> findAll() {
        return technologyRepository.findAll()
                .switchIfEmpty(Flux.error(new IllegalStateException("No hay tecnologías registradas")));
    }

}
