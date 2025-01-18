package guru.springframework.springairag.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
@Configuration
@ConfigurationProperties(prefix = "sfg.aiapp")
@Data
public class VectorStoreProperties {

    private String vectorStorePath;
    private List<Resource> documentsToLoad;

}
