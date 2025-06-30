package com.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;
import java.util.Map;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartData {

    // Chart Metadata
    private final String chartId;
    private final String chartTitle;
    private final ChartType chartType;
    private final String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime generatedAt;

    private final Long userId;
    private final String timeRange; // "24h", "7d", "30d", "all"

    // Chart.js Compatible Data Structure
    private final ChartDataset datasets;
    private final List<String> labels;
    private final ChartOptions options;
    private final ChartColors colors;

    // Raw Data for Export/Processing
    private final List<DataPoint> rawData;
    private final Map<String, Object> metadata;

    // Summary Statistics
    private final Long totalDataPoints;
    private final Double maxValue;
    private final Double minValue;
    private final Double averageValue;
    private final String topCategory;
    private final Long topCategoryValue;

    // Embedded Static Records for Chart.js Integration
    @Builder
    @Getter
    @ToString
    public static class ChartDataset {
        private final String label;
        private final List<Double> data;
        private final List<String> backgroundColor;
        private final List<String> borderColor;
        private final String borderWidth;
        private final String fill;
        private final String tension;
        private final String pointRadius;
        private final String pointHoverRadius;
        private final List<String> hoverBackgroundColor;
        private final List<String> hoverBorderColor;

        // Multiple datasets for complex charts
        private final List<SingleDataset> datasets;

        @Builder
        @Getter
        @ToString
        public static class SingleDataset {
            private final String label;
            private final List<Double> data;
            private final String backgroundColor;
            private final String borderColor;
            private final Integer borderWidth;
            private final Boolean fill;
            private final Double tension;
            private final Integer pointRadius;
            private final Integer pointHoverRadius;
            private final String type; // "line", "bar", "doughnut", etc.
        }
    }

    @Builder
    @Getter
    @ToString
    public static class ChartOptions {
        private final Boolean responsive;
        private final Boolean maintainAspectRatio;
        private final ChartScales scales;
        private final ChartLegend legend;
        private final ChartTooltip tooltip;
        private final ChartAnimation animation;
        private final ChartPlugins plugins;

        @Builder
        @Getter
        @ToString
        public static class ChartScales {
            private final ChartAxis x;
            private final ChartAxis y;

            @Builder
            @Getter
            @ToString
            public static class ChartAxis {
                private final Boolean display;
                private final String type;
                private final String position;
                private final ChartGrid grid;
                private final ChartTicks ticks;
                private final String title;

                @Builder
                @Getter
                @ToString
                public static class ChartGrid {
                    private final Boolean display;
                    private final String color;
                    private final Integer lineWidth;
                }

                @Builder
                @Getter
                @ToString
                public static class ChartTicks {
                    private final Boolean display;
                    private final String color;
                    private final Integer fontSize;
                    private final String fontFamily;
                    private final Integer maxTicksLimit;
                    private final String callback;
                }
            }
        }

        @Builder
        @Getter
        @ToString
        public static class ChartLegend {
            private final Boolean display;
            private final String position;
            private final ChartLegendLabels labels;

            @Builder
            @Getter
            @ToString
            public static class ChartLegendLabels {
                private final String color;
                private final Integer fontSize;
                private final String fontFamily;
                private final Integer padding;
                private final Boolean usePointStyle;
            }
        }

        @Builder
        @Getter
        @ToString
        public static class ChartTooltip {
            private final Boolean enabled;
            private final String mode;
            private final Boolean intersect;
            private final String backgroundColor;
            private final String titleColor;
            private final String bodyColor;
            private final String borderColor;
            private final Integer borderWidth;
        }

        @Builder
        @Getter
        @ToString
        public static class ChartAnimation {
            private final Integer duration;
            private final String easing;
            private final Boolean animateRotate;
            private final Boolean animateScale;
        }

        @Builder
        @Getter
        @ToString
        public static class ChartPlugins {
            private final ChartTitle title;
            private final ChartSubtitle subtitle;

            @Builder
            @Getter
            @ToString
            public static class ChartTitle {
                private final Boolean display;
                private final String text;
                private final String color;
                private final Integer fontSize;
                private final String fontFamily;
                private final String position;
            }

            @Builder
            @Getter
            @ToString
            public static class ChartSubtitle {
                private final Boolean display;
                private final String text;
                private final String color;
                private final Integer fontSize;
            }
        }
    }

    @Builder
    @Getter
    @ToString
    public static class ChartColors {
        private final List<String> primary;
        private final List<String> secondary;
        private final List<String> success;
        private final List<String> warning;
        private final List<String> danger;
        private final List<String> info;
        private final String backgroundGradient;
        private final String borderGradient;

        // Predefined color schemes
        public static ChartColors getDefaultColors() {
            return ChartColors.builder()
                    .primary(List.of("#10a37f", "#0d8465", "#0a6b52"))
                    .secondary(List.of("#40414f", "#2b2c39", "#1f1f2b"))
                    .success(List.of("#1db954", "#1ed760", "#17a34a"))
                    .warning(List.of("#ff9900", "#ffb84d", "#f59e0b"))
                    .danger(List.of("#ff4757", "#ff3742", "#ef4444"))
                    .info(List.of("#3b82f6", "#60a5fa", "#2563eb"))
                    .backgroundGradient("linear-gradient(135deg, rgba(16, 163, 127, 0.1) 0%, rgba(16, 163, 127, 0.05) 100%)")
                    .borderGradient("linear-gradient(135deg, #10a37f 0%, #0d8465 100%)")
                    .build();
        }

        public static ChartColors getDarkModeColors() {
            return ChartColors.builder()
                    .primary(List.of("#10a37f", "#0d8465", "#0a6b52"))
                    .secondary(List.of("#c5c5d2", "#ffffff", "#5c5f6e"))
                    .success(List.of("#22c55e", "#16a34a", "#15803d"))
                    .warning(List.of("#f59e0b", "#d97706", "#b45309"))
                    .danger(List.of("#ef4444", "#dc2626", "#b91c1c"))
                    .info(List.of("#3b82f6", "#2563eb", "#1d4ed8"))
                    .backgroundGradient("linear-gradient(135deg, rgba(64, 65, 79, 0.9) 0%, rgba(31, 31, 43, 0.8) 100%)")
                    .borderGradient("linear-gradient(135deg, #10a37f 0%, #0d8465 100%)")
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    public static class DataPoint {
        private final String label;
        private final Double value;
        private final String category;
        private final String color;
        private final Map<String, Object> additionalData;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime timestamp;

        public static DataPoint of(String label, Double value) {
            return DataPoint.builder()
                    .label(label)
                    .value(value)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        public static DataPoint of(String label, Double value, String category) {
            return DataPoint.builder()
                    .label(label)
                    .value(value)
                    .category(category)
                    .timestamp(LocalDateTime.now())
                    .build();
        }
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

        @JsonProperty("type")
        public String getChartJsType() {
            return chartJsType;
        }
    }

    // Builder Defaults for Null Safety
    public static class ChartDataBuilder {
        private List<String> labels = Collections.emptyList();
        private List<DataPoint> rawData = Collections.emptyList();
        private Map<String, Object> metadata = Collections.emptyMap();
        private ChartColors colors = ChartColors.getDefaultColors();
        private LocalDateTime generatedAt = LocalDateTime.now();
        private Long totalDataPoints = 0L;
        private Double maxValue = 0.0;
        private Double minValue = 0.0;
        private Double averageValue = 0.0;
        private String timeRange = "24h";
    }

    // Utility Methods for Chart Processing
    public boolean hasData() {
        return rawData != null && !rawData.isEmpty() && totalDataPoints > 0;
    }

    public boolean isRealtimeChart() {
        return chartType == ChartType.LINE && "realtime".equals(metadata.get("mode"));
    }

    public String getFormattedTitle() {
        return chartTitle != null ? chartTitle : "Chart Data";
    }

    public List<String> getColorPalette() {
        if (colors != null && colors.getPrimary() != null) {
            return colors.getPrimary();
        }
        return ChartColors.getDefaultColors().getPrimary();
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

    // Factory Methods for Common Chart Types
    public static ChartData createBarChart(String title, List<String> labels, List<Double> data, Long userId) {
        return ChartData.builder()
                .chartId("bar_" + System.currentTimeMillis())
                .chartTitle(title)
                .chartType(ChartType.BAR)
                .userId(userId)
                .labels(labels)
                .datasets(ChartDataset.builder()
                        .label(title)
                        .data(data)
                        .backgroundColor(ChartColors.getDefaultColors().getPrimary())
                        .borderColor(ChartColors.getDefaultColors().getPrimary())
                        .borderWidth("1")
                        .build())
                .options(ChartOptions.builder()
                        .responsive(true)
                        .maintainAspectRatio(false)
                        .build())
                .totalDataPoints((long) data.size())
                .maxValue(data.stream().mapToDouble(Double::doubleValue).max().orElse(0.0))
                .minValue(data.stream().mapToDouble(Double::doubleValue).min().orElse(0.0))
                .averageValue(data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0))
                .build();
    }

    public static ChartData createPieChart(String title, List<String> labels, List<Double> data, Long userId) {
        return ChartData.builder()
                .chartId("pie_" + System.currentTimeMillis())
                .chartTitle(title)
                .chartType(ChartType.PIE)
                .userId(userId)
                .labels(labels)
                .datasets(ChartDataset.builder()
                        .label(title)
                        .data(data)
                        .backgroundColor(ChartColors.getDefaultColors().getPrimary())
                        .hoverBackgroundColor(ChartColors.getDefaultColors().getSecondary())
                        .build())
                .options(ChartOptions.builder()
                        .responsive(true)
                        .maintainAspectRatio(false)
                        .legend(ChartOptions.ChartLegend.builder()
                                .display(true)
                                .position("bottom")
                                .build())
                        .build())
                .totalDataPoints((long) data.size())
                .build();
    }

    public static ChartData createLineChart(String title, List<String> labels, List<Double> data, Long userId) {
        return ChartData.builder()
                .chartId("line_" + System.currentTimeMillis())
                .chartTitle(title)
                .chartType(ChartType.LINE)
                .userId(userId)
                .labels(labels)
                .datasets(ChartDataset.builder()
                        .label(title)
                        .data(data)
                        .borderColor(List.of("#10a37f"))
                        .backgroundColor(List.of("rgba(16, 163, 127, 0.1)"))
                        .fill("true")
                        .tension("0.4")
                        .pointRadius("4")
                        .pointHoverRadius("6")
                        .build())
                .options(ChartOptions.builder()
                        .responsive(true)
                        .maintainAspectRatio(false)
                        .scales(ChartOptions.ChartScales.builder()
                                .y(ChartOptions.ChartScales.ChartAxis.builder()
                                        .display(true)
                                        .type("linear")
                                        .build())
                                .build())
                        .build())
                .totalDataPoints((long) data.size())
                .build();
    }

    public static ChartData empty(Long userId) {
        return ChartData.builder()
                .chartId("empty_" + System.currentTimeMillis())
                .chartTitle("No Data Available")
                .chartType(ChartType.BAR)
                .userId(userId)
                .description("No data available for the selected time range")
                .build();
    }
}
