package com.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pages",
        indexes = {
                @Index(name = "idx_page_url", columnList = "url"),
                @Index(name = "idx_page_session_id", columnList = "session_id"),
                @Index(name = "idx_page_status", columnList = "status_code"),
                @Index(name = "idx_page_crawled_at", columnList = "crawled_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"crawlSession", "outgoingLinks", "incomingLinks", "keywords"})
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id")
    @Setter(AccessLevel.NONE)
    private Long pageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private CrawlSession crawlSession;

    @Column(name = "url", nullable = false, length = 1000)
    private String url;

    @Column(name = "title", length = 500)
    private String title;

    @Column(name = "meta_description", length = 1000)
    private String metaDescription;

    @Column(name = "meta_keywords", length = 500)
    private String metaKeywords;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "status_code", nullable = false)
    private Integer statusCode;

    @Column(name = "content_length")
    private Long contentLength;

    @Column(name = "load_time_ms")
    private Long loadTimeMs;

    @Column(name = "depth_level", nullable = false)
    private Integer depthLevel;

    @Column(name = "word_count")
    private Integer wordCount;

    @Column(name = "internal_links_count")
    @Builder.Default
    private Integer internalLinksCount = 0;

    @Column(name = "external_links_count")
    @Builder.Default
    private Integer externalLinksCount = 0;

    @Column(name = "image_count")
    @Builder.Default
    private Integer imageCount = 0;

    @Column(name = "h1_tags", length = 1000)
    private String h1Tags;

    @Column(name = "h2_tags", length = 1000)
    private String h2Tags;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @CreationTimestamp
    @Column(name = "crawled_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime crawledAt;

    // Relationships
    @OneToMany(mappedBy = "sourcePage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Link> outgoingLinks = new HashSet<>();

    @OneToMany(mappedBy = "targetPage", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Link> incomingLinks = new HashSet<>();

    @OneToMany(mappedBy = "page", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Keyword> keywords = new HashSet<>();

    // Business Methods
    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }

    public boolean isRedirect() {
        return statusCode >= 300 && statusCode < 400;
    }

    public boolean isError() {
        return statusCode >= 400;
    }

    public String getDomain() {
        try {
            return new java.net.URL(url).getHost();
        } catch (Exception e) {
            return "unknown";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page page)) return false;
        return pageId != null && pageId.equals(page.pageId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
