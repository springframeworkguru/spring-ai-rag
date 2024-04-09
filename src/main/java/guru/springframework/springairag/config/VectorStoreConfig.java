package guru.springframework.springairag.config;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;

import java.io.File;

/**
 * Created by jt, Spring Framework Guru.
 */
public class VectorStoreConfig {
    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingClient embeddingClient, VectorStoreProperties vectorStoreProperties) {
        var store =  new SimpleVectorStore(embeddingClient);
        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());

        //to do add data
        return store;
    }
}
