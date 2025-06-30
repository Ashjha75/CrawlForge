package com.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    @Setter(AccessLevel.NONE)
    private Long activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Session Activity Metrics
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

    // Recent Activity Tracking
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_crawl_activity")
    private LocalDateTime lastCrawlActivity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "first_crawl_activity")
    private LocalDateTime firstCrawlActivity;

    @Column(name = "sessions_today")
    @Builder.Default
    private Long sessionsToday = 0L;

    @Column(name = "sessions_this_week")
    @Builder.Default
    private Long sessionsThisWeek = 0L;

    @Column(name = "sessions_this_month")
    @Builder.Default
    private Long sessionsThisMonth = 0L;

    @Column(name = "sessions_this_year")
    @Builder.Default
    private Long sessionsThisYear = 0L;

    // Crawling Performance Metrics
    @Column(name = "total_pages_crawled")
    @Builder.Default
    private Long totalPagesCrawled = 0L;

    @Column(name = "total_pages_failed")
    @Builder.Default
    private Long totalPagesFailed = 0L;

    @Column(name = "average_success_rate")
    @Builder.Default
    private Double averageSuccessRate = 0.0;

    @Column(name = "personal_best_success_rate")
    private Double personalBestSuccessRate;

    @Column(name = "total_links_discovered")
    @Builder.Default
    private Long totalLinksDiscovered = 0L;

    @Column(name = "total_keywords_extracted")
    @Builder.Default
    private Long totalKeywordsExtracted = 0L;

    // Time-based Analytics
    @Column(name = "total_crawling_time_minutes")
    @Builder.Default
    private Long totalCrawlingTimeMinutes = 0L;

    @Column(name = "average_session_duration_minutes")
    @Builder.Default
    private Double averageSessionDurationMinutes = 0.0;

    @Column(name = "longest_session_duration_minutes")
    private Long longestSessionDurationMinutes;

    @Column(name = "shortest_session_duration_minutes")
    private Long shortestSessionDurationMinutes;

    // Domain and Content Preferences
    @Column(name = "unique_domains_explored")
    @Builder.Default
    private Integer uniqueDomainsExplored = 0;

    @Column(name = "most_crawled_domain", length = 255)
    private String mostCrawledDomain;

    @Column(name = "most_crawled_domain_count")
    @Builder.Default
    private Long mostCrawledDomainCount = 0L;

    // Behavioral Patterns (stored as JSON)
    @Column(name = "activity_pattern_json", columnDefinition = "TEXT")
    private String activityPatternJson;

    @Column(name = "hourly_distribution_json", columnDefinition = "TEXT")
    private String hourlyDistributionJson;

    @Column(name = "weekly_pattern_json", columnDefinition = "TEXT")
    private String weeklyPatternJson;

    @Column(name = "monthly_trend_json", columnDefinition = "TEXT")
    private String monthlyTrendJson;

    // Recent Sessions and Achievements (stored as JSON)
    @Column(name = "recent_sessions_json", columnDefinition = "TEXT")
    private String recentSessionsJson;

    @Column(name = "achievements_json", columnDefinition = "TEXT")
    private String achievementsJson;

    // User Preferences (stored as JSON)
    @Column(name = "preferences_json", columnDefinition = "TEXT")
    private String preferencesJson;

    @Column(name = "frequent_seed_urls_json", columnDefinition = "TEXT")
    private String frequentSeedUrlsJson;

    @Column(name = "custom_settings_json", columnDefinition = "TEXT")
    private String customSettingsJson;

    // Productivity Metrics
    @Column(name = "productivity_score")
    @Builder.Default
    private Double productivityScore = 0.0;

    @Column(name = "productivity_grade", length = 10)
    @Builder.Default
    private String productivityGrade = "N/A";

    @Column(name = "productivity_insights_json", columnDefinition = "TEXT")
    private String productivityInsightsJson;

    // Account Health and Security
    @Column(name = "login_attempts")
    @Builder.Default
    private Integer loginAttempts = 0;

    @Column(name = "email_verified")
    @Builder.Default
    private Boolean emailVerified = false;

    @Column(name = "newsletter_subscribed")
    @Builder.Default
    private Boolean newsletterSubscribed = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "account_created_at")
    private LocalDateTime accountCreatedAt;

    @Column(name = "days_since_registration")
    private Long daysSinceRegistration;

    @Column(name = "account_tier", length = 20)
    @Builder.Default
    private String accountTier = "BASIC";

    // Configuration
    @Column(name = "time_range", length = 20)
    @Builder.Default
    private String timeRange = "30d"; // "24h", "7d", "30d", "all"

    @Column(name = "activity_type", length = 50)
    @Builder.Default
    private String activityType = "USER"; // "USER", "ADMIN", "ANALYTICS"

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
    public boolean isActiveUser() {
        return lastCrawlActivity != null &&
                lastCrawlActivity.isAfter(LocalDateTime.now().minusDays(7));
    }

    public boolean isPowerUser() {
        return totalSessions != null && totalSessions >= 50 &&
                averageSuccessRate != null && averageSuccessRate >= 85.0;
    }

    public String getActivityLevel() {
        if (sessionsToday != null && sessionsToday >= 5) return "VERY_HIGH";
        if (sessionsToday != null && sessionsToday >= 3) return "HIGH";
        if (sessionsToday != null && sessionsToday >= 1) return "MEDIUM";
        if (lastCrawlActivity != null && lastCrawlActivity.isAfter(LocalDateTime.now().minusDays(3))) return "LOW";
        return "INACTIVE";
    }

    public Double getSessionCompletionRate() {
        if (totalSessions == null || totalSessions == 0) {
            return 0.0;
        }
        Long completed = completedSessions != null ? completedSessions : 0L;
        return (completed.doubleValue() / totalSessions.doubleValue()) * 100.0;
    }

    public Long getAveragePagesPerSession() {
        if (totalSessions == null || totalSessions == 0) {
            return 0L;
        }
        Long pages = totalPagesCrawled != null ? totalPagesCrawled : 0L;
        return pages / totalSessions;
    }

    public boolean hasRecentActivity() {
        return lastCrawlActivity != null &&
                lastCrawlActivity.isAfter(LocalDateTime.now().minusHours(24));
    }

    public String getExperienceLevel() {
        if (totalSessions == null) return "BEGINNER";

        if (totalSessions >= 100) return "EXPERT";
        if (totalSessions >= 50) return "ADVANCED";
        if (totalSessions >= 20) return "INTERMEDIATE";
        if (totalSessions >= 5) return "NOVICE";
        return "BEGINNER";
    }

    public boolean isEligibleForUpgrade() {
        return "BASIC".equals(accountTier) &&
                totalSessions != null && totalSessions >= 25 &&
                averageSuccessRate != null && averageSuccessRate >= 80.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserActivity that)) return false;
        return activityId != null && activityId.equals(that.activityId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Static Factory Methods
    public static UserActivity createEmpty(User user, String timeRange) {
        return UserActivity.builder()
                .user(user)
                .timeRange(timeRange)
                .activityType("USER")
                .accountCreatedAt(user.getCreatedAt())
                .daysSinceRegistration(java.time.Duration.between(user.getCreatedAt(), LocalDateTime.now()).toDays())
                .dataVersion(1)
                .build();
    }

    public static UserActivity createForAdmin(User user) {
        return UserActivity.builder()
                .user(user)
                .timeRange("30d")
                .activityType("ADMIN")
                .dataVersion(1)
                .build();
    }
}
