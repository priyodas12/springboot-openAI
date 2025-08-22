CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE IF NOT EXISTS embeddings_data
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    content    TEXT         NOT NULL,
    embeddings VECTOR(1536) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_embeddings_data
    ON embeddings_data USING hnsw (embeddings vector_cosine_ops);