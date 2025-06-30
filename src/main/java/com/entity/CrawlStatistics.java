package com.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "crawl_statistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrawlStatistics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistics_id")
    @Setter(AccessLevel.NONE)
    private Long statisticsId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Core Session Statistics
    @Column(name = "total_sessions")
    @Builder.Default
    private Long totalSessions = 0L;
    
    @Column(name = "active_sessions")
    @Builder.Default
    private Long activeSessions = 0L;
    
    @Column(name = "completed_sessions")
    @Builder.Default
    private Long completedSessions = 0L;
    
    @Column(name = "failed_sessions")
    @Builder.Default
    private Long failedSessions = 0L;
    
    @Column(name = "paused_sessions")
    @Builder.Default
    private Long pausedSessions = 0L;
    
    @Column(name = "cancelled_sessions")
    @Builder.Default
    private Long cancelledSessions = 0L;
    
    // Page Crawling Metrics
    @Column(name = "total_pages_crawled")
    @Builder.Default
    private Long totalPagesCrawled = 0L;
    
    @Column(name = "total_pages_failed")
    @Builder.Default
    private Long totalPagesFailed = 0L;
    
    @Column(name = "total_pages_successful")
    @Builder.Default
    private Long totalPagesSuccessful = 0L;
    
    @Column(name = "overall_success_rate")
    @Builder.Default
    private Double overallSuccessRate = 0.0;
    
    @Column(name = "average_pages_per_session")
    @Builder.Default
    private Double averagePagesPerSession = 0.0;
    
    @Column(name = "max_pages_in_session")
    @Builder.Default
    private Integer maxPagesInSession = 0;
    
    @Column(name = "min_pages_in_session")
    @Builder.Default
    private Integer minPagesInSession = 0;
    
    // Performance Analytics
    @Column(name = "average_load_time_ms")
    @Builder.Default
    private Double averageLoadTimeMs = 0.0;
    
    @Column(name = "total_load_time_ms")
    @Builder.Default
    private Long totalLoadTimeMs = 0L;
    
    @Column(name = "average_session_duration_minutes")
    @Builder.Default
    private Double averageSessionDurationMinutes = 0.0;
    
    @Column(name = "fastest_page_load_ms")
    private Long fastestPageLoadMs;
    
    @Column(name = "slowest_page_load_ms")
    private Long slowestPageLoadMs;
    
    // Link Discovery Statistics
    @Column(name = "total_links_found")
    @Builder.Default
    private Long totalLinksFound = 0L;
    
    @Column(name = "total_internal_links")
    @Builder.Default
    private Long totalInternalLinks = 0L;
    
    @Column(name = "total_external_links")
    @Builder.Default
    private Long totalExternalLinks = 0L;
    
    @Column(name = "average_links_per_page")
    @Builder.Default
    private Double averageLinksPerPage = 0.0;
    
    @Column(name = "max_links_on_page")
    @Builder.Default
    private Integer maxLinksOnPage = 0;
    
    // Keyword Analysis Metrics
    @Column(name = "total_keywords_extracted")
    @Builder.Default
    private Long totalKeywordsExtracted = 0L;
    
    @Column(name = "average_keywords_per_page")
    @Builder.Default
    private Double averageKeywordsPerPage = 0.0;
    
    @Column(name = "average_keyword_density")
    @Builder.Default
    private Double averageKeywordDensity = 0.0;
    
    @Column(name = "unique_keyword_count")
    @Builder.Default
    private Integer uniqueKeywordCount = 0;
    
    // Error and Status Code Analytics
    @Column(name = "total_2xx_responses")
    @Builder.Default
    private Long total2xxResponses = 0L;
    
    @Column(name = "total_3xx_responses")
    @Builder.Default
    private Long total3xxResponses = 0L;
    
    @Column(name = "total_4xx_responses")
    @Builder.Default
    private Long total4xxResponses = 0L;
    
    @Column(name = "total_5xx_responses")
    @Builder.Default
    private Long total5xxResponses = 0L;
    
    @Column(name = "most_common_status_code")
    private Integer mostCommonStatusCode;
    
    @Column(name = "total_timeouts")
    @Builder.Default
    private Long totalTimeouts = 0L;
    
    @Column(name = "total_connection_errors")
    @Builder.Default
    private Long totalConnectionErrors = 0L;
    
    // Depth and Threading Analysis
    @Column(name = "average_depth_reached")
    @Builder.Default
    private Double averageDepthReached = 0.0;
    
    @Column(name = "max_depth_reached")
    @Builder.Default
    private Integer maxDepthReached = 0;
    
    @Column(name = "average_thread_utilization")
    @Builder.Default
    private Double averageThreadUtilization = 0.0;
    
    @Column(name = "total_thread_hours")
    @Builder.Default
    private Long totalThreadHours = 0L;
    
    // Time-based Trends
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "first_crawl_time")
    private LocalDateTime firstCrawlTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_crawl_time")
    private LocalDateTime lastCrawlTime;
    
    @Column(name = "crawls_last_24_hours")
    @Builder.Default
    private Long crawlsLast24Hours = 0L;
    
    @Column(name = "crawls_last_7_days")
    @Builder.Default
    private Long crawlsLast7Days = 0L;
    
    @Column(name = "crawls_last_30_days")
    @Builder.Default
    private Long crawlsLast30Days = 0L;
    
    @Column(name = "pages_last_24_hours")
    @Builder.Default
    private Long pagesLast24Hours = 0L;
    
    @Column(name = "pages_last_7_days")
    @Builder.Default
    private Long pagesLast7Days = 0L;
    
    @Column(name = "pages_last_30_days")
    @Builder.Default
    private Long pagesLast30Days = 0L;
    
    // Domain and Content Statistics
    @Column(name = "unique_domains_count")
    @Builder.Default
    private Integer uniqueDomainsCount = 0;
    
    @Column(name = "most_crawled_domain", length = 255)
    private String mostCrawledDomain;
    
    @Column(name = "most_crawled_domain_count")
    @Builder.Default
    private Long mostCrawledDomainCount = 0L;
    
    // Content Type Analysis
    @Column(name = "html_pages_count")
    @Builder.Default
    private Long htmlPagesCount = 0L;
    
    @Column(name = "image_links_count")
    @Builder.Default
    private Long imageLinksCount = 0L;
    
    @Column(name = "document_links_count")
    @Builder.Default
    private Long documentLinksCount = 0L;
    
    @Column(name = "other_content_count")
    @Builder.Default
    private Long otherContentCount = 0L;
    
    // Configuration
    @Column(name = "time_range", length = 20)
    @Builder.Default
    private String timeRange = "24h"; // "24h", "7d", "30d", "all"
    
    @Column(name = "statistics_type", length = 50)
    @Builder.Default
    private String statisticsType = "USER"; // "USER", "ADMIN", "GLOBAL"
    
    // Audit Fields
    @CreationTimestamp
    @Column(name = "generated_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime generatedAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;
    
    @Column(name = "data_version")
    @Builder.Default
    private Integer dataVersion = 1;
    
    // Business Methods
    public Double getSessionCompletionRate() {
        if (totalSessions == null || totalSessions == 0) {
            return 0.0;
        }
        Long completed = completedSessions != null ? completedSessions : 0L;
        return (completed.doubleValue() / totalSessions.doubleValue()) * 100.0;
    }
    
    public Double getErrorRate() {
        if (totalPagesCrawled == null || totalPagesCrawled == 0) {
            return 0.0;
        }
        Long failed = totalPagesFailed != null ? totalPagesFailed : 0L;
        return (failed.doubleValue() / totalPagesCrawled.doubleValue()) * 100.0;
    }
    
    public Double getLinkDiscoveryRate() {
        if (totalPagesCrawled == null || totalPagesCrawled == 0) {
            return 0.0;
        }
        Long links = totalLinksFound != null ? totalLinksFound : 0L;
        return links.doubleValue() / totalPagesCrawled.doubleValue();
    }
    
    public boolean hasRecentActivity() {
        return lastCrawlTime != null && 
               lastCrawlTime.isAfter(LocalDateTime.now().minusHours(24));
    }
    
    public String getPerformanceGrade() {
        if (overallSuccessRate == null) return "N/A";
        
        if (overallSuccessRate >= 95.0) return "A+";
        if (overallSuccessRate >= 90.0) return "A";
        if (overallSuccessRate >= 85.0) return "B+";
        if (overallSuccessRate >= 80.0) return "B";
        if (overallSuccessRate >= 75.0) return "C+";
        if (overallSuccessRate >= 70.0) return "C";
        return "D";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CrawlStatistics that)) return false;
        return statisticsId != null && statisticsId.equals(that.statisticsId);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    // Static Factory Methods
    public static CrawlStatistics createEmpty(User user, String timeRange) {
        return CrawlStatistics.builder()
            .user(user)
            .timeRange(timeRange)
            .statisticsType("USER")
            .dataVersion(1)
            .build();
    }
    
    public static CrawlStatistics createGlobal(String timeRange) {
        return CrawlStatistics.builder()
            .timeRange(timeRange)
            .statisticsType("GLOBAL")
            .dataVersion(1)
            .build();
    }
}
