package co.com.anfega.usecase.tecnology;

import co.com.anfega.model.tecnology.Technology;
import co.com.anfega.model.tecnology.gateways.TechnologyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class TechnologyUseCaseTest {

    private TechnologyRepository technologyRepository;
    private TechnologyUseCase technologyUseCase;

    @BeforeEach
    void setUp() {
        technologyRepository = mock(TechnologyRepository.class);
        technologyUseCase = new TechnologyUseCase(technologyRepository);
    }

    @Test
    void shouldSaveTechnologySuccessfully() {
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
    void shouldFailWhenNameIsNull() {
        Technology tech = new Technology(null, "Descripción válida");

        when(technologyRepository.findByName(any())).thenReturn(Mono.empty());

        StepVerifier.create(technologyUseCase.save(tech))
                .expectErrorMatches(e -> e.getMessage().equals("El nombre es obligatorio"))
                .verify();
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        Technology tech = new Technology("   ", "Descripción válida");

        when(technologyRepository.findByName(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(technologyUseCase.save(tech))
                .expectErrorMatches(e -> e.getMessage().equals("El nombre es obligatorio"))
                .verify();
    }

    @Test
    void shouldFailWhenDescriptionIsNull() {
        Technology tech = new Technology("Java", null);

        when(technologyRepository.findByName(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(technologyUseCase.save(tech))
                .expectErrorMatches(e -> e.getMessage().equals("La descripción es obligatoria"))
                .verify();
    }

    @Test
    void shouldFailWhenDescriptionIsBlank() {
        Technology tech = new Technology("Java", "   ");

        when(technologyRepository.findByName(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(technologyUseCase.save(tech))
                .expectErrorMatches(e -> e.getMessage().equals("La descripción es obligatoria"))
                .verify();
    }

    @Test
    void shouldFailWhenNameIsTooLong() {
        String longName = "a".repeat(51);
        Technology tech = new Technology(longName, "Descripción válida");

        when(technologyRepository.findByName(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(technologyUseCase.save(tech))
                .expectErrorMatches(e -> e.getMessage().equals("El nombre no puede superar los 50 caracteres"))
                .verify();
    }

    @Test
    void shouldFailWhenDescriptionIsTooLong() {
        String longDescription = "a".repeat(91);
        Technology tech = new Technology("Java", longDescription);

        when(technologyRepository.findByName(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(technologyUseCase.save(tech))
                .expectErrorMatches(e -> e.getMessage().equals("La descripción no puede superar los 90 caracteres"))
                .verify();
    }

    @Test
    void shouldFailWhenNameAlreadyExists() {
        Technology tech = new Technology("Java", "Lenguaje de programación");

        when(technologyRepository.findByName("Java")).thenReturn(Mono.just(tech));

        StepVerifier.create(technologyUseCase.save(tech))
                .expectErrorMatches(e -> e.getMessage().equals("El nombre ya existe"))
                .verify();

        verify(technologyRepository).findByName("Java");
        verify(technologyRepository, never()).save(any());
    }
}
