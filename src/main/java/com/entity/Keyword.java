package com.crawlforge.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "keywords",
        indexes = {
                @Index(name = "idx_keyword_page_id", columnList = "page_id"),
                @Index(name = "idx_keyword_word", columnList = "keyword"),
                @Index(name = "idx_keyword_frequency", columnList = "frequency")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "page")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    @Setter(AccessLevel.NONE)
    private Long keywordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    @Column(name = "keyword", nullable = false, length = 100)
    private String keyword;

    @Column(name = "frequency", nullable = false)
    private Integer frequency;

    @Column(name = "density")
    private Double density;

    @Enumerated(EnumType.STRING)
    @Column(name = "keyword_type")
    @Builder.Default
    private KeywordType keywordType = KeywordType.CONTENT;

    public enum KeywordType {
        TITLE, META, HEADING, CONTENT, ALT_TEXT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Keyword)) return false;
        Keyword keyword = (Keyword) o;
        return keywordId != null && keywordId.equals(keyword.keywordId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
