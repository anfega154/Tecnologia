package co.com.anfega.api;

import co.com.anfega.api.config.TechnologyPath;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
    private final TechnologyPath technologyPath;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST(technologyPath.getTechnologies()), handler::listenSaveTechnologyUseCase);
    }
}
