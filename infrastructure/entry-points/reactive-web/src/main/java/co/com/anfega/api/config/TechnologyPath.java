package co.com.anfega.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "routes.paths")
public class TechnologyPath {
    private String technologies;
}
