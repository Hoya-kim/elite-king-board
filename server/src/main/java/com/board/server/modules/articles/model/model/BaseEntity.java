package com.board.server.modules.articles.model.model;

import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
