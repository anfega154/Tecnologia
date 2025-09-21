package co.com.anfega.usecase.tecnology;

import co.com.anfega.model.tecnology.Technology;
import co.com.anfega.model.tecnology.gateways.TechnologyInputPort;
import co.com.anfega.model.tecnology.gateways.TechnologyRepository;
import reactor.core.publisher.Mono;

public class TechnologyUseCase implements TechnologyInputPort {

    private final TechnologyRepository technologyRepository;

    public TechnologyUseCase(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public Mono<Technology> save(Technology technology) {
        return validateTechnology(technology)
                .then(technologyRepository.findByName(technology.getName())
                        .flatMap(existing -> Mono.<Technology>error(new IllegalArgumentException("El nombre ya existe")))
                        .switchIfEmpty(technologyRepository.save(technology))
                );
    }

    private Mono<Void> validateTechnology(Technology technology) {
        if (technology.getName() == null || technology.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("El nombre es obligatorio"));
        }
        if (technology.getDescription() == null || technology.getDescription().isBlank()) {
            return Mono.error(new IllegalArgumentException("La descripción es obligatoria"));
        }
        if (technology.getName().length() > 50) {
            return Mono.error(new IllegalArgumentException("El nombre no puede superar los 50 caracteres"));
        }
        if (technology.getDescription().length() > 90) {
            return Mono.error(new IllegalArgumentException("La descripción no puede superar los 90 caracteres"));
        }
        return Mono.empty();
    }
}
