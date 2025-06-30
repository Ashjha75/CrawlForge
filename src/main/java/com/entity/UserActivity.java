package com.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.Collections;
import java.util.Map;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserActivity {

    // User Identity
    private final Long userId;
    private final String username;
    private final String fullName;
    private final String email;
    private final User.UserStatus userStatus;

    // Session Activity Metrics
    private final Long totalSessions;
    private final Long activeSessions;
    private final Long completedSessions;
    private final Long failedSessions;
    private final Long pausedSessions;
    private final Long cancelledSessions;

    // Recent Activity Tracking
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime lastLoginAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime lastCrawlActivity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime firstCrawlActivity;

    private final Long sessionsToday;
    private final Long sessionsThisWeek;
    private final Long sessionsThisMonth;
    private final Long sessionsThisYear;

    // Crawling Performance Metrics
    private final Long totalPagesCrawled;
    private final Long totalPagesFailed;
    private final Double averageSuccessRate;
    private final Double personalBestSuccessRate;
    private final Long totalLinksDiscovered;
    private final Long totalKeywordsExtracted;

    // Time-based Analytics
    private final Long totalCrawlingTimeMinutes;
    private final Double averageSessionDurationMinutes;
    private final Long longestSessionDurationMinutes;
    private final Long shortestSessionDurationMinutes;

    // Domain and Content Preferences
    private final Integer uniqueDomainsExplored;
    private final String mostCrawledDomain;
    private final Long mostCrawledDomainCount;
    private final List<DomainActivity> topDomains;
    private final List<String> favoriteKeywords;

    // Behavioral Patterns
    private final ActivityPattern activityPattern;
    private final List<HourlyActivity> hourlyDistribution;
    private final List<DailyActivity> weeklyPattern;
    private final List<MonthlyActivity> monthlyTrend;

    // Recent Sessions Summary
    private final List<RecentSessionSummary> recentSessions;
    private final List<CrawlMilestone> achievements;

    // User Preferences and Settings
    private final UserPreferences preferences;
    private final List<String> frequentSeedUrls;
    private final Map<String, Object> customSettings;

    // Productivity Metrics
    private final Double productivityScore;
    private final String productivityGrade;
    private final List<String> productivityInsights;
    private final ComparisonMetrics comparison;

    // Account Health and Security
    private final Integer loginAttempts;
    private final Boolean emailVerified;
    private final Boolean newsletterSubscribed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime accountCreatedAt;

    private final Long daysSinceRegistration;
    private final String accountTier; // "BASIC", "PREMIUM", "ENTERPRISE"

    // Embedded Static Records for Detailed Activity Analysis
    @Builder
    @Getter
    @ToString
    public static class DomainActivity {
        private final String domain;
        private final Long sessionCount;
        private final Long pageCount;
        private final Double averageSuccessRate;
        private final Double averageLoadTime;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime lastCrawled;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime firstCrawled;

        private final Long totalCrawlingTime;
        private final String preferredDepth;
    }

    @Builder
    @Getter
    @ToString
    public static class ActivityPattern {
        private final String primaryTimeZone;
        private final Integer mostActiveHour; // 0-23
        private final String mostActiveDay; // MONDAY, TUESDAY, etc.
        private final String crawlingFrequency; // "DAILY", "WEEKLY", "MONTHLY", "IRREGULAR"
        private final Double averageSessionsPerDay;
        private final String preferredCrawlDepth;
        private final Integer preferredThreadCount;
        private final Integer preferredDelayMs;
        private final String behaviorType; // "POWER_USER", "CASUAL", "EXPLORER", "ANALYST"
    }

    @Builder
    @Getter
    @ToString
    public static class HourlyActivity {
        private final Integer hour; // 0-23
        private final Long sessionCount;
        private final Long pageCount;
        private final Double averageSuccessRate;
        private final String activityLevel; // "LOW", "MEDIUM", "HIGH", "PEAK"
    }

    @Builder
    @Getter
    @ToString
    public static class DailyActivity {
        private final String dayOfWeek;
        private final Long sessionCount;
        private final Long pageCount;
        private final Double averageSuccessRate;
        private final Double averageSessionDuration;
    }

    @Builder
    @Getter
    @ToString
    public static class MonthlyActivity {
        private final String monthYear; // "2025-06"
        private final Long sessionCount;
        private final Long pageCount;
        private final Double successRate;
        private final Integer uniqueDomains;
        private final String growthTrend; // "INCREASING", "DECREASING", "STABLE"
    }

    @Builder
    @Getter
    @ToString
    public static class RecentSessionSummary {
        private final Long sessionId;
        private final String sessionName;
        private final String seedUrl;
        private final CrawlSession.CrawlStatus status;
        private final Integer pagesCrawled;
        private final Integer pagesFailed;
        private final Double successRate;
        private final Long durationMinutes;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime startedAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime completedAt;

        private final String performanceRating; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
    }

    @Builder
    @Getter
    @ToString
    public static class CrawlMilestone {
        private final String achievementType; // "FIRST_CRAWL", "100_PAGES", "PERFECT_SESSION", etc.
        private final String title;
        private final String description;
        private final String badgeIcon;
        private final String badgeColor;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime achievedAt;

        private final Long relatedSessionId;
        private final Map<String, Object> metadata;
    }

    @Builder
    @Getter
    @ToString
    public static class UserPreferences {
        private final Integer defaultMaxDepth;
        private final Integer defaultMaxPages;
        private final Integer defaultThreadCount;
        private final Integer defaultDelayMs;
        private final Boolean autoResumeEnabled;
        private final Boolean emailNotificationsEnabled;
        private final Boolean darkModeEnabled;
        private final String preferredExportFormat; // "CSV", "JSON", "PDF"
        private final List<String> favoriteFilters;
        private final String dashboardLayout; // "COMPACT", "DETAILED", "MINIMAL"
    }

    @Builder
    @Getter
    @ToString
    public static class ComparisonMetrics {
        private final Double percentileRank; // User's rank compared to all users (0-100)
        private final Long averageUserSessions; // Platform average
        private final Double averageUserSuccessRate; // Platform average
        private final String performanceCategory; // "TOP_10", "ABOVE_AVERAGE", "AVERAGE", "BELOW_AVERAGE"
        private final List<String> strengthAreas;
        private final List<String> improvementAreas;
        private final String recommendedActions;
    }

    // Builder Defaults for Null Safety
    public static class UserActivityBuilder {
        private List<DomainActivity> topDomains = Collections.emptyList();
        private List<String> favoriteKeywords = Collections.emptyList();
        private List<HourlyActivity> hourlyDistribution = Collections.emptyList();
        private List<DailyActivity> weeklyPattern = Collections.emptyList();
        private List<MonthlyActivity> monthlyTrend = Collections.emptyList();
        private List<RecentSessionSummary> recentSessions = Collections.emptyList();
        private List<CrawlMilestone> achievements = Collections.emptyList();
        private List<String> frequentSeedUrls = Collections.emptyList();
        private List<String> productivityInsights = Collections.emptyList();
        private Map<String, Object> customSettings = Collections.emptyMap();
        private Long totalSessions = 0L;
        private Long activeSessions = 0L;
        private Long completedSessions = 0L;
        private Long failedSessions = 0L;
        private Long totalPagesCrawled = 0L;
        private Long totalPagesFailed = 0L;
        private Double averageSuccessRate = 0.0;
        private Integer uniqueDomainsExplored = 0;
        private String accountTier = "BASIC";
        private Integer loginAttempts = 0;
        private Boolean emailVerified = false;
        private Boolean newsletterSubscribed = false;
    }

    // Utility Methods for Activity Analysis
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

    public Duration getAccountAge() {
        if (accountCreatedAt == null) return Duration.ZERO;
        return Duration.between(accountCreatedAt, LocalDateTime.now());
    }

    // Factory Methods for Common User Activity Scenarios
    public static UserActivity newUser(Long userId, String username, String fullName, String email) {
        return UserActivity.builder()
                .userId(userId)
                .username(username)
                .fullName(fullName)
                .email(email)
                .userStatus(User.UserStatus.ACTIVE)
                .accountCreatedAt(LocalDateTime.now())
                .daysSinceRegistration(0L)
                .productivityScore(0.0)
                .productivityGrade("N/A")
                .preferences(UserPreferences.builder()
                        .defaultMaxDepth(3)
                        .defaultMaxPages(100)
                        .defaultThreadCount(5)
                        .defaultDelayMs(1000)
                        .autoResumeEnabled(false)
                        .emailNotificationsEnabled(true)
                        .darkModeEnabled(false)
                        .preferredExportFormat("JSON")
                        .dashboardLayout("DETAILED")
                        .build())
                .build();
    }

    public static UserActivity fromUser(User user) {
        return UserActivity.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .userStatus(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .loginAttempts(user.getLoginAttempts())
                .emailVerified(user.getEmailVerified())
                .newsletterSubscribed(user.getNewsletterSubscribed())
                .accountCreatedAt(user.getCreatedAt())
                .daysSinceRegistration(Duration.between(user.getCreatedAt(), LocalDateTime.now()).toDays())
                .build();
    }

    public static UserActivity empty(Long userId) {
        return UserActivity.builder()
                .userId(userId)
                .username("Unknown")
                .fullName("Unknown User")
                .userStatus(User.UserStatus.ACTIVE)
                .productivityScore(0.0)
                .productivityGrade("N/A")
                .build();
    }
}
