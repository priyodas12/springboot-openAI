package lab.springboot.springbootopenai.dao;

import com.pgvector.PGvector;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class VectorRepository {

    private final JdbcTemplate jdbc;


    public VectorRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public UUID insert(String content, float[] embedding) {
        return jdbc.queryForObject(
                "INSERT INTO public.embeddings_data (content, embeddings) VALUES (?, ?) RETURNING id",
                (rs, rowNum) -> UUID.randomUUID(),
                content,
                new PGvector(embedding)
        );
    }


    public List<SearchRow> searchByVector(float[] query, int topK) {
        String sql = """
                SELECT id, content, (embeddings <=> ?) AS distance
                FROM public.embeddings_data
                ORDER BY embeddings <=> ?
                LIMIT ?
                """;
        return jdbc.query(
                sql,
                new Object[]{new PGvector(query), new PGvector(query), topK},
                new SearchRowMapper()
        );
    }


    public record SearchRow(UUID id, String content, double distance) {
    }


    private static class SearchRowMapper implements RowMapper<SearchRow> {
        @Override
        public SearchRow mapRow(ResultSet rs, int rowNum) throws SQLException {
            UUID id = UUID.fromString(rs.getString("id"));
            String content = rs.getString("content");
            double distance = rs.getDouble("distance");
            return new SearchRow(id, content, distance);
        }

    }
}
