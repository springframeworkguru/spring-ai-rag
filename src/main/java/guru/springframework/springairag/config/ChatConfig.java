/* (C) Said Zitouni 2025 */
package guru.springframework.springairag.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Autowired
    private ChatModel chatModel;

    @Autowired
    private SimpleVectorStore simpleVectorStore;

    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(new QuestionAnswerAdvisor(
                        simpleVectorStore, SearchRequest.builder().build()))
                .build();
    }
}
