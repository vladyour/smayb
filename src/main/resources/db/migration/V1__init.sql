CREATE TABLE history
(
    id                 bigserial PRIMARY KEY,
    amount             numeric(19, 2),
    datetime           timestamp WITH TIME ZONE
);

CREATE INDEX history_id_index
    ON history (id DESC);
