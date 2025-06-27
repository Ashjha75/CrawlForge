package com.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;
import jakarta.persistence.Index;
import jakarta.persistence.CascadeType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "crawl_sessions",
        indexes = {
                @Index(name = "idx_crawl_user_id", columnList = "user_id"),
                @Index(name = "idx_crawl_status", columnList = "status"),
                @Index(name = "idx_crawl_created_at", columnList = "created_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "pages"})
public class CrawlSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    @Setter(AccessLevel.NONE)
    private Long sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "session_name", length = 100)
    private String sessionName;

    @Column(name = "seed_url", nullable = false, length = 500)
    private String seedUrl;

    @Column(name = "max_depth", nullable = false)
    @Builder.Default
    private Integer maxDepth = 3;

    @Column(name = "max_pages", nullable = false)
    @Builder.Default
    private Integer maxPages = 100;

    @Column(name = "thread_count", nullable = false)
    @Builder.Default
    private Integer threadCount = 5;

    @Column(name = "delay_ms", nullable = false)
    @Builder.Default
    private Integer delayMs = 1000;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private CrawlStatus status = CrawlStatus.PENDING;

    @Column(name = "pages_crawled", nullable = false)
    @Builder.Default
    private Integer pagesCrawled = 0;

    @Column(name = "pages_failed", nullable = false)
    @Builder.Default
    private Integer pagesFailed = 0;

    @Column(name = "total_links_found", nullable = false)
    @Builder.Default
    private Integer totalLinksFound = 0;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "crawlSession", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Page> pages = new HashSet<>();

    public enum CrawlStatus {
        PENDING, RUNNING, PAUSED, COMPLETED, FAILED, CANCELLED
    }

    // Business Methods
    public boolean isActive() {
        return status == CrawlStatus.RUNNING || status == CrawlStatus.PAUSED;
    }

    public boolean canResume() {
        return status == CrawlStatus.PAUSED || status == CrawlStatus.FAILED;
    }

    public double getSuccessRate() {
        if (pagesCrawled == 0) return 0.0;
        return ((double) (pagesCrawled - pagesFailed) / pagesCrawled) * 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CrawlSession)) return false;
        CrawlSession session = (CrawlSession) o;
        return sessionId != null && sessionId.equals(session.sessionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
