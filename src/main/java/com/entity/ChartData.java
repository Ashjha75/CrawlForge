package com.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chart_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chart_id")
    @Setter(AccessLevel.NONE)
    private Long chartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Chart Metadata
    @Column(name = "chart_title", length = 200)
    private String chartTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "chart_type", nullable = false)
    @Builder.Default
    private ChartType chartType = ChartType.BAR;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "time_range", length = 20)
    @Builder.Default
    private String timeRange = "24h"; // "24h", "7d", "30d", "all"

    // Chart.js Compatible Data Structure (stored as JSON)
    @Column(name = "datasets_json", columnDefinition = "TEXT")
    private String datasetsJson;

    @Column(name = "labels_json", columnDefinition = "TEXT")
    private String labelsJson;

    @Column(name = "options_json", columnDefinition = "TEXT")
    private String optionsJson;

    @Column(name = "colors_json", columnDefinition = "TEXT")
    private String colorsJson;

    // Raw Data for Export/Processing
    @Column(name = "raw_data_json", columnDefinition = "TEXT")
    private String rawDataJson;

    @Column(name = "metadata_json", columnDefinition = "TEXT")
    private String metadataJson;

    // Summary Statistics
    @Column(name = "total_data_points")
    @Builder.Default
    private Long totalDataPoints = 0L;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "average_value")
    private Double averageValue;

    @Column(name = "top_category", length = 100)
    private String topCategory;

    @Column(name = "top_category_value")
    private Long topCategoryValue;

    // Configuration
    @Column(name = "is_realtime")
    @Builder.Default
    private Boolean isRealtime = false;

    @Column(name = "refresh_interval_seconds")
    @Builder.Default
    private Integer refreshIntervalSeconds = 60;

    @Column(name = "is_cached")
    @Builder.Default
    private Boolean isCached = false;

    @Column(name = "cache_expires_at")
    private LocalDateTime cacheExpiresAt;

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

    // Static Factory Methods
    public static ChartData createBarChart(User user, String title, String timeRange) {
        return ChartData.builder()
                .user(user)
                .chartTitle(title)
                .chartType(ChartType.BAR)
                .timeRange(timeRange)
                .refreshIntervalSeconds(60)
                .dataVersion(1)
                .build();
    }

    public static ChartData createPieChart(User user, String title, String timeRange) {
        return ChartData.builder()
                .user(user)
                .chartTitle(title)
                .chartType(ChartType.PIE)
                .timeRange(timeRange)
                .refreshIntervalSeconds(60)
                .dataVersion(1)
                .build();
    }

    public static ChartData createLineChart(User user, String title, String timeRange) {
        return ChartData.builder()
                .user(user)
                .chartTitle(title)
                .chartType(ChartType.LINE)
                .timeRange(timeRange)
                .isRealtime(true)
                .refreshIntervalSeconds(30)
                .dataVersion(1)
                .build();
    }

    // Business Methods
    public boolean hasData() {
        return totalDataPoints != null && totalDataPoints > 0 &&
                rawDataJson != null && !rawDataJson.trim().isEmpty();
    }

    public boolean isRealtimeChart() {
        return isRealtime != null && isRealtime &&
                metadataJson != null && metadataJson.contains("\"mode\":\"realtime\"");
    }

    public String getFormattedTitle() {
        return chartTitle != null ? chartTitle : "Chart Data";
    }

    public Double getDataRange() {
        if (maxValue != null && minValue != null) {
            return maxValue - minValue;
        }
        return 0.0;
    }

    public boolean isLargeDataset() {
        return totalDataPoints != null && totalDataPoints > 1000;
    }

    public boolean isCacheValid() {
        return isCached != null && isCached &&
                cacheExpiresAt != null &&
                cacheExpiresAt.isAfter(LocalDateTime.now());
    }

    public void markCacheExpired() {
        this.isCached = false;
        this.cacheExpiresAt = null;
    }

    public void setCacheExpiry(int minutes) {
        this.isCached = true;
        this.cacheExpiresAt = LocalDateTime.now().plusMinutes(minutes);
    }

    public boolean needsRefresh() {
        if (generatedAt == null) return true;
        return generatedAt.isBefore(LocalDateTime.now().minusSeconds(refreshIntervalSeconds));
    }

    public String getFormattedTimeRange() {
        return switch (timeRange) {
            case "24h" -> "Last 24 Hours";
            case "7d" -> "Last 7 Days";
            case "30d" -> "Last 30 Days";
            case "all" -> "All Time";
            default -> "Custom Range";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChartData that)) return false;
        return chartId != null && chartId.equals(that.chartId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Chart Type Enumeration
    public enum ChartType {
        BAR("bar"),
        LINE("line"),
        PIE("pie"),
        DOUGHNUT("doughnut"),
        RADAR("radar"),
        POLAR_AREA("polarArea"),
        SCATTER("scatter"),
        BUBBLE("bubble"),
        MIXED("mixed");

        private final String chartJsType;

        ChartType(String chartJsType) {
            this.chartJsType = chartJsType;
        }

        public String getChartJsType() {
            return chartJsType;
        }
    }
}
