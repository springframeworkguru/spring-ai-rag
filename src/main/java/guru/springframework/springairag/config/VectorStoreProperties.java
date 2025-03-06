/* (C) Said Zitouni 2025 */
package guru.springframework.springairag.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jt, Spring Framework Guru.
 */
@Configuration
@ConfigurationProperties(prefix = "file.vector")
@Getter
@Setter
public class VectorStoreProperties {

    private String vectorStorePath;
    private List<String> documentsToLoad;
}
