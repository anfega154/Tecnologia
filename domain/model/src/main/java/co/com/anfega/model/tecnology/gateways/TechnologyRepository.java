package co.com.anfega.model.tecnology.gateways;

import co.com.anfega.model.tecnology.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TechnologyRepository {
    Mono<Technology> save(Technology technology);
    Mono<Technology> findByName(String name);
    Flux<Technology> findAll();
    Mono<Void> deleteByIds(List<Long> ids);
}
