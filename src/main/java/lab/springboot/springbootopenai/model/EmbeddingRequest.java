package lab.springboot.springbootopenai.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EmbeddingRequest {
    @NotNull
    private List<String> texts;
}
