package com.example.just.Dao;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1316133299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final NumberPath<Integer> blamedCount = createNumber("blamedCount", Integer.class);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath emoticon = createString("emoticon");

    public final ListPath<Member, QMember> likedMembers = this.<Member, QMember>createList("likedMembers", Member.class, QMember.class, PathInits.DIRECT2);

    public final QMember member;

    public final StringPath post_category = createString("post_category");

    public final StringPath post_content = createString("post_content");

    public final DateTimePath<java.sql.Timestamp> post_create_time = createDateTime("post_create_time", java.sql.Timestamp.class);

    public final NumberPath<Long> post_id = createNumber("post_id", Long.class);

    public final NumberPath<Long> post_like = createNumber("post_like", Long.class);

    public final NumberPath<Long> post_picture = createNumber("post_picture", Long.class);

    public final StringPath post_tag = createString("post_tag");

    public final BooleanPath secret = createBoolean("secret");

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

