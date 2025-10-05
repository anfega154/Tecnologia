package co.com.anfega.r2dbc;

import co.com.anfega.r2dbc.entity.TechnologyEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MyReactiveRepository extends ReactiveCrudRepository<TechnologyEntity, Long>, ReactiveQueryByExampleExecutor<TechnologyEntity> {
    Mono<TechnologyEntity> findByNameIgnoreCase(String name);
}
