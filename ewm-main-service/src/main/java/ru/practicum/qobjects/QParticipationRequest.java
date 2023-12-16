package ru.practicum.qobjects;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import ru.practicum.entity.ParticipationRequest;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QParticipationRequest is a Querydsl query type for ParticipationRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipationRequest extends EntityPathBase<ParticipationRequest> {

    private static final long serialVersionUID = 818411650L;

    public static final QParticipationRequest participationRequest = new QParticipationRequest("participationRequest");

    public final DateTimePath<java.time.LocalDateTime> created = createDateTime("created", java.time.LocalDateTime.class);

    public final NumberPath<Integer> event = createNumber("event", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> requester = createNumber("requester", Integer.class);

    public final StringPath status = createString("status");

    public QParticipationRequest(String variable) {
        super(ParticipationRequest.class, forVariable(variable));
    }

    public QParticipationRequest(Path<? extends ParticipationRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipationRequest(PathMetadata metadata) {
        super(ParticipationRequest.class, metadata);
    }

}

