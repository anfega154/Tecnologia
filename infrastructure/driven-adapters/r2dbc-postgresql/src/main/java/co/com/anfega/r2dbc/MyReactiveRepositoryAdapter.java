package co.com.anfega.r2dbc;

import co.com.anfega.model.tecnology.Technology;
import co.com.anfega.model.tecnology.gateways.TechnologyRepository;
import co.com.anfega.r2dbc.entity.TechnologyEntity;
import co.com.anfega.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Technology,
        TechnologyEntity,
        Long,
        MyReactiveRepository
        > implements TechnologyRepository {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Technology.class));
    }

    @Override
    public Mono<Technology> save(Technology technology) {
        TechnologyEntity data = new TechnologyEntity();
        data.setName(technology.getName());
        data.setDescription(technology.getDescription());
        return repository.save(data)
                .map(savedData -> new Technology(
                        savedData.getId(),
                        savedData.getName(),
                        savedData.getDescription()
                ));
    }

    @Override
    public Mono<Technology> findByName(String name) {
        return repository.findByNameIgnoreCase(name)
                .map(entity -> new Technology(
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription()
                ));
    }

    @Override
    public Flux<Technology> findAll() {
        return repository.findAll()
                .map(entity -> new Technology(
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription()
                ));
    }

    @Override
    public Mono<Void> deleteByIds(List<Long> ids) {
        return repository.deleteAllById(ids)
                .onErrorResume(e -> Mono.error(new IllegalStateException("Error eliminando tecnologias: " + e.getMessage())));

    }
}
