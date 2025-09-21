package co.com.anfega.model.tecnology.gateways;

import co.com.anfega.model.tecnology.Technology;
import reactor.core.publisher.Mono;

public interface TechnologyInputPort {
    Mono<Technology> save(Technology technology);
}
