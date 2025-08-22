package lab.springboot.springbootopenai.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EmbeddingResponse {

    @NotNull
    private List<String> createdEmbeddingIds;
    private LocalDateTime createdTime;
}
