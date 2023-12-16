CREATE TABLE IF NOT EXISTS category
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    NOT
    NULL,
    name
    VARCHAR
(
    50
) NOT NULL,
    constraint pk_category primary key
(
    id
),
    constraint uq_category unique
(
    name
)
    );

CREATE TABLE IF NOT EXISTS users
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    NOT
    null,
    name
    VARCHAR
(
    256
) NOT NULL,
    email VARCHAR
(
    256
),
    constraint pk_user primary key
(
    id
),
    constraint uq_user UNIQUE
(
    name
)
    );

CREATE TABLE IF NOT EXISTS location
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    NOT
    null
    unique,
    lat
    VARCHAR
(
    100
),
    lon VARCHAR
(
    100
),
    constraint pk_location primary key
(
    id
)
    );

CREATE TABLE IF NOT EXISTS event
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    NOT
    NULL,
    initiator_id
    BIGINT
    references
    users
(
    id
),
    annotation VARCHAR
(
    2000
) NOT NULL,
    category_id BIGINT,
    description VARCHAR
(
    7000
),
    event_date TIMESTAMP WITHOUT TIME ZONE,
    created_on TIMESTAMP
                         WITHOUT TIME ZONE,
    location_id BIGINT references location
(
    id
),
    paid BOOLEAN,
    participant_limit BIGINT,
    request_moderation BOOLEAN,
    title VARCHAR
(
    120
),
    states VARCHAR
(
    20
),
    published_on TIMESTAMP
                         WITHOUT TIME ZONE,
    views BIGINT,
    confirmed_requests BIGINT,
    constraint pk_event primary key
(
    id
)
    );

CREATE TABLE IF NOT EXISTS request
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    NOT
    NULL,
    created
    TIMESTAMP
    WITHOUT
    TIME
    ZONE,
    event_id
    BIGINT,
    requester_id
    BIGINT,
    status
    varchar
(
    50
),
    constraint pk_request primary key
(
    id
)
    );

CREATE TABLE IF NOT EXISTS compilation
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    NOT
    NULL,
    title
    varchar
(
    250
),
    pinned BOOLEAN,
    constraint pk_compilation primary key
(
    id
)
    );

CREATE TABLE IF NOT EXISTS compilation_event
(
    compilation_id
    bigint
    references
    compilation
(
    id
),
    event_id bigint references event
(
    id
)
    );