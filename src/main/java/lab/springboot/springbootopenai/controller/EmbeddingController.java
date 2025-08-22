package lab.springboot.springbootopenai.controller;

import lab.springboot.springbootopenai.dao.VectorRepository;
import lab.springboot.springbootopenai.model.EmbeddingRequest;
import lab.springboot.springbootopenai.model.EmbeddingResponse;
import lab.springboot.springbootopenai.service.EmbeddingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@RequestMapping("/open-api/v1")
@RestController
public class EmbeddingController {

    private final EmbeddingService embeddings;
    private final VectorRepository repo;

    public EmbeddingController(EmbeddingService embeddings, VectorRepository repo) {
        this.embeddings = embeddings;
        this.repo = repo;
    }


    @PostMapping(path = "/embeddings", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EmbeddingResponse> embedText(@RequestBody EmbeddingRequest embeddingRequest) {
        List<UUID> uuids = new ArrayList<>();
        log.debug("request for : " + embeddingRequest.getTexts());
        try {
            embeddingRequest.getTexts().forEach(text -> {
                float[] embeddings = this.embeddings.embed(text);
                log.debug("new embeddings created: {} at: {}", Arrays.toString(embeddings), Instant.now());
                uuids.add(repo.insert(text, embeddings));
            });
            return ResponseEntity.status(201).body(EmbeddingResponse.builder()
                    .createdEmbeddingIds(uuids.stream().map(String::valueOf).toList())
                    .createdTime(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            log.error("embeddings creation error: {}", e.getMessage());
            return ResponseEntity.status(500).body(EmbeddingResponse.builder()
                    .createdEmbeddingIds(uuids.stream().map(String::valueOf).toList())
                    .createdTime(LocalDateTime.now())
                    .build());
        }
    }

    @GetMapping(path = "/text/search", produces = "application/json")
    public ResponseEntity<List<VectorRepository.SearchRow>> search(@RequestParam String query, @RequestParam Integer limit) {
        try {
            float[] vec = this.embeddings.embed(query);
            log.info("embeddings for search created: {} at: {}", Arrays.toString(vec), Instant.now());
            List<VectorRepository.SearchRow> searchRowList = this.repo.searchByVector(vec, limit);
            return ResponseEntity.status(200).body(searchRowList.stream().sorted(Comparator.comparing(VectorRepository.SearchRow::distance)).toList());
        } catch (Exception e) {
            log.error("embeddings search error: {}", e.getMessage());
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }
}
