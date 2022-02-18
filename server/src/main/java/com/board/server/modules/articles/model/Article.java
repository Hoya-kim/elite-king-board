package com.board.server.modules.articles.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Article{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private String id;
    @Column(nullable = false)
    @Setter
    private String title;
    @Column(nullable = false)
    @Setter private String content;
    @Column(nullable = false)
    @Setter private Integer viewCount;
    @Column(nullable = false)
    @Setter private Integer likeCount;
    @Column(nullable = true)
    @Setter private String hashtag;
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    @Setter private String createdBy;
    @Column(nullable = false)
    private LocalDateTime modifiedAt;
    @Column(nullable = false)
    @Setter private String modifiedBy;

    public Article(String title, String content, Integer viewCount, Integer likeCount,
        String hashtag, String createdBy) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.hashtag = hashtag;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }
}
