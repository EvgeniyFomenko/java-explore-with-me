CREATE TABLE IF NOT EXISTS statistic (
    id BIGINT GENERATED  BY DEFAULT AS IDENTITY NOT NULL,
    app varchar(255),
    url varchar(255),
    ip varchar(255),
    create_time timestamp WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_stat PRIMARY KEY(id)
    );