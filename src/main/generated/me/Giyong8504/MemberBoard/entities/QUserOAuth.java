package me.Giyong8504.MemberBoard.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserOAuth is a Querydsl query type for UserOAuth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOAuth extends EntityPathBase<UserOAuth> {

    private static final long serialVersionUID = -1558118265L;

    public static final QUserOAuth userOAuth = new QUserOAuth("userOAuth");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    public final StringPath name = createString("name");

    public final StringPath picture = createString("picture");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final EnumPath<me.Giyong8504.MemberBoard.commons.Role> role = createEnum("role", me.Giyong8504.MemberBoard.commons.Role.class);

    public QUserOAuth(String variable) {
        super(UserOAuth.class, forVariable(variable));
    }

    public QUserOAuth(Path<? extends UserOAuth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserOAuth(PathMetadata metadata) {
        super(UserOAuth.class, metadata);
    }

}

