package guru.springframework.springairag.services;

import guru.springframework.springairag.model.Answer;
import guru.springframework.springairag.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jt, Spring Framework Guru.
 */
@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;
    private final SimpleVectorStore vectorStore;

    @Value("classpath:/templates/rag-prompt-template.st")
    private Resource ragPromptTemplate;

  @Override
  public Answer getAnswer(Question question) {

    // Søker etter de 5 mest relevante dokumentene i vector store basert på spørsmålet
    List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
        .query(question.question()).topK(5).build());

    // Henter ut innholdet (tekst) fra dokumentene
    List<String> contentList = documents.stream().map(Document::getContent).toList();

    // Lager en prompt-template basert på en forhåndsdefinert mal (ragPromptTemplate)
    PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);

    // Fyller inn prompten med spørsmålet og dokumentinnholdet
    Prompt prompt = promptTemplate.create(Map.of(
        "input", question.question(),
        "documents", String.join("\n", contentList)
    ));

    // Skriver ut innholdet fra dokumentene til konsollen (for debugging)
    contentList.forEach(System.out::println);

    // Sender prompten til chat-modellen (f.eks. OpenAI) og får et svar
    ChatResponse response = chatModel.call(prompt);

    // Returnerer svaret pakket inn i en Answer-record
    return new Answer(response.getResult().getOutput().getContent());
  }
}
