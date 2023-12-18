package ru.practicum.qobjects;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QCompilation is a Querydsl query type for Compilation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompilation extends EntityPathBase<Compilation> {

    private static final long serialVersionUID = 1599075535L;

    public static final QCompilation compilation = new QCompilation("compilation");

    public final ListPath<Event, QEvent> event = this.<Event, QEvent>createList("event", Event.class, QEvent.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath pinned = createBoolean("pinned");

    public final StringPath title = createString("title");

    public QCompilation(String variable) {
        super(Compilation.class, forVariable(variable));
    }

    public QCompilation(Path<? extends Compilation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompilation(PathMetadata metadata) {
        super(Compilation.class, metadata);
    }

}

