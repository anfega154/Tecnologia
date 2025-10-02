package co.com.anfega.usecase.tecnology;

import co.com.anfega.model.tecnology.Technology;
import co.com.anfega.model.tecnology.gateways.TechnologyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class TechnologyUseCaseTest {

    private TechnologyRepository technologyRepository;
    private TechnologyUseCase technologyUseCase;

    @BeforeEach
    void setUp() {
        technologyRepository = mock(TechnologyRepository.class);
        technologyUseCase = new TechnologyUseCase(technologyRepository);
    }

    @Test
    void shouldSaveTechnologyWhenNotExists() {
        Technology tech = new Technology("Java", "Lenguaje de programación");

        when(technologyRepository.findByName("Java")).thenReturn(Mono.empty());
        when(technologyRepository.save(tech)).thenReturn(Mono.just(tech));

        StepVerifier.create(technologyUseCase.save(tech))
                .expectNext(tech)
                .verifyComplete();

        verify(technologyRepository).findByName("Java");
        verify(technologyRepository).save(tech);
    }

    @Test
    void shouldFailWhenTechnologyAlreadyExists() {
        Technology tech = new Technology("Java", "Lenguaje de programación");

        when(technologyRepository.findByName("Java")).thenReturn(Mono.just(tech));

        StepVerifier.create(technologyUseCase.save(tech))
                .expectErrorMatches(e -> e instanceof IllegalStateException &&
                        e.getMessage().equals("El nombre ya existe"))
                .verify();

        verify(technologyRepository).findByName("Java");
        verify(technologyRepository, never()).save(any());
    }

    @Test
    void shouldFindAllTechnologies() {
        Technology tech1 = new Technology("Java", "Lenguaje de programación");
        Technology tech2 = new Technology("Kotlin", "Lenguaje moderno");

        when(technologyRepository.findAll()).thenReturn(Flux.just(tech1, tech2));

        StepVerifier.create(technologyUseCase.findAll())
                .expectNext(tech1)
                .expectNext(tech2)
                .verifyComplete();

        verify(technologyRepository).findAll();
    }

    @Test
    void shouldFailWhenNoTechnologiesFound() {
        when(technologyRepository.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(technologyUseCase.findAll())
                .expectErrorMatches(e -> e instanceof IllegalStateException &&
                        e.getMessage().equals("No hay tecnologías registradas"))
                .verify();

        verify(technologyRepository).findAll();
    }
}
