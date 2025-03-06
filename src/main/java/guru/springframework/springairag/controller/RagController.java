/* (C) Said Zitouni 2025 */
package guru.springframework.springairag.controller;

import guru.springframework.springairag.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class RagController {
    private final ChatClient chatClient;

    public RagController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/rag")
    public Flux<String> sendMessage(@RequestBody Question question) {
        return chatClient.prompt(question.question()).stream().content();
    }
}
