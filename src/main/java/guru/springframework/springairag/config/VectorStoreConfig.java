package guru.springframework.springairag.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by jt, Spring Framework Guru.
 */
@Configuration
public class VectorStoreConfig {

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel, VectorStoreProperties vectorStoreProperties) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();

        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());

        //todo add data
        return simpleVectorStore;
    }
}
