/* (C) Said Zitouni 2025 */
package guru.springframework.springairag.config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Created by jt, Spring Framework Guru.
 */
@Configuration
@Slf4j
public class VectorStoreConfig {
    @Autowired
    VectorStoreProperties vectorStoreProperties;

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore =
                SimpleVectorStore.builder(embeddingModel).build();

        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());

        if (vectorStoreFile.exists()) {
            vectorStore.load(vectorStoreFile);
        } else {
            log.info("Vector store file does not exist");
            loadResources(vectorStoreProperties.getDocumentsToLoad()).forEach(resource -> {
                List<Document> chunks = chunkDocuments(resource);
                vectorStore.add(chunks);
            });
            vectorStore.save(vectorStoreFile);
        }
        return vectorStore;
    }

    private List<Document> chunkDocuments(Resource resource) {
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        List<Document> documents = reader.get();
        TextSplitter textSplitter = new TokenTextSplitter();
        return textSplitter.split(documents);
    }

    private List<Resource> loadResources(List<String> rootPath) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Path root = Paths.get(rootPath.getFirst());
        if (!Files.exists(root)) {
            throw new RuntimeException("Could not find root directory");
        }
        try (Stream<Path> paths = Files.walk(root)) {
            return paths.filter(Files::isRegularFile)
                    .map(path -> resourceLoader.getResource("file:" + path.toAbsolutePath()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error loading resources", e);
        }
    }
}
