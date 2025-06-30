package com.controller;

import com.entity.CrawlStatistics;
import com.entity.DashboardData;
import com.entity.UserActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.AnalyticsService;
import com.service.DashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DashboardServlet.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private DashboardService dashboardService;
    private AnalyticsService analyticsService;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize services - you can use dependency injection here
        this.dashboardService = new DashboardService();
        this.analyticsService = new AnalyticsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if request wants JSON response (for AJAX calls)
        String acceptHeader = request.getHeader("Accept");
        boolean isJsonRequest = acceptHeader != null && acceptHeader.contains("application/json");

        if (isJsonRequest) {
            handleJsonRequest(request, response);
        } else {
            handleJspRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Handle POST requests for dashboard updates
        String action = request.getParameter("action");

        if ("refresh".equals(action)) {
            handleDashboardRefresh(request, response);
        } else if ("export".equals(action)) {
            handleExport(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid action\"}");
        }
    }

    private void handleJspRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get user ID from session (set by DashboardAuthFilter)
            Long userId = (Long) request.getAttribute("currentUserId");
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Get time range parameter
            String timeRange = request.getParameter("timeRange");
            if (timeRange == null || timeRange.trim().isEmpty()) {
                timeRange = "24h";
            }

            // Fetch dashboard data asynchronously
            CompletableFuture<DashboardData> dashboardFuture =
                    dashboardService.getDashboardDataAsync(userId, timeRange);

            CompletableFuture<CrawlStatistics> statsFuture =
                    analyticsService.getCrawlStatisticsAsync(userId, timeRange);

            CompletableFuture<UserActivity> activityFuture =
                    analyticsService.getUserActivityAsync(userId, timeRange);

            // Wait for all futures with timeout
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    dashboardFuture, statsFuture, activityFuture
            );

            try {
                allFutures.get(5, TimeUnit.SECONDS);

                // Set attributes for JSP
                request.setAttribute("dashboardData", dashboardFuture.get());
                request.setAttribute("crawlStatistics", statsFuture.get());
                request.setAttribute("userActivity", activityFuture.get());
                request.setAttribute("timeRange", timeRange);

            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Timeout or error fetching dashboard data, using cached values", e);

                // Fallback to cached data
                DashboardData cachedData = dashboardService.getCachedDashboardData(userId, timeRange);
                request.setAttribute("dashboardData", cachedData);
                request.setAttribute("hasError", true);
                request.setAttribute("errorMessage", "Using cached data due to service timeout");
            }

            // Set page attributes for your existing structure
            request.setAttribute("pageTitle", "CrawlForge Dashboard - Analytics & Insights");
            request.setAttribute("contentPage", "/jsp/dashboard/dashboard.jsp");
            request.setAttribute("pageCss", "dashboard.css,charts.css,analytics.css");
            request.setAttribute("pageJs", "dashboard.js,charts.js,analytics.js");

            // Forward to your index.jsp layout
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling JSP request", e);
            request.setAttribute("pageTitle", "Dashboard Error");
            request.setAttribute("contentPage", "/jsp/error/dashboard-error.jsp");
            request.setAttribute("errorMessage", "Unable to load dashboard data");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private void handleJsonRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Add CORS headers if needed
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        try (PrintWriter writer = response.getWriter()) {

            // Get user ID from request attributes (set by DashboardAuthFilter)
            Long userId = (Long) request.getAttribute("currentUserId");
            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                writer.write("{\"error\":\"Authentication required\",\"code\":401}");
                return;
            }

            // Get request parameters
            String timeRange = request.getParameter("timeRange");
            String dataType = request.getParameter("type");

            if (timeRange == null) timeRange = "24h";
            if (dataType == null) dataType = "all";

            // Handle different data type requests
            switch (dataType) {
                case "dashboard":
                    handleDashboardDataRequest(userId, timeRange, writer, response);
                    break;
                case "statistics":
                    handleStatisticsRequest(userId, timeRange, writer, response);
                    break;
                case "charts":
                    handleChartsRequest(userId, timeRange, writer, response);
                    break;
                case "activity":
                    handleActivityRequest(userId, timeRange, writer, response);
                    break;
                case "all":
                default:
                    handleAllDataRequest(userId, timeRange, writer, response);
                    break;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling JSON request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Internal server error\",\"code\":500}");
        }
    }

    private void handleAllDataRequest(Long userId, String timeRange, PrintWriter writer, HttpServletResponse response) {
        try {
            // Fetch all data asynchronously
            CompletableFuture<DashboardData> dashboardFuture =
                    dashboardService.getDashboardDataAsync(userId, timeRange);

            CompletableFuture<CrawlStatistics> statsFuture =
                    analyticsService.getCrawlStatisticsAsync(userId, timeRange);

            CompletableFuture<UserActivity> activityFuture =
                    analyticsService.getUserActivityAsync(userId, timeRange);

            // Wait for all with timeout
            CompletableFuture.allOf(dashboardFuture, statsFuture, activityFuture)
                    .get(5, TimeUnit.SECONDS);

            // Create combined response
            String jsonResponse = String.format(
                    "{\"success\":true,\"data\":{\"dashboard\":%s,\"statistics\":%s,\"activity\":%s},\"timestamp\":\"%s\"}",
                    objectMapper.writeValueAsString(dashboardFuture.get()),
                    objectMapper.writeValueAsString(statsFuture.get()),
                    objectMapper.writeValueAsString(activityFuture.get()),
                    java.time.LocalDateTime.now()
            );

            // Set ETag for caching
            String etag = "\"" + Integer.toHexString(jsonResponse.hashCode()) + "\"";
            response.setHeader("ETag", etag);
            response.setHeader("Cache-Control", "no-store");

            writer.write(jsonResponse);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error fetching all data, using cached fallback", e);

            // Fallback to cached data
            DashboardData cachedData = dashboardService.getCachedDashboardData(userId, timeRange);
            try {
                String fallbackResponse = String.format(
                        "{\"success\":true,\"cached\":true,\"data\":{\"dashboard\":%s},\"message\":\"Using cached data\"}",
                        objectMapper.writeValueAsString(cachedData)
                );
                writer.write(fallbackResponse);
            } catch (Exception jsonError) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.write("{\"error\":\"Failed to serialize cached data\",\"code\":500}");
            }
        }
    }

    private void handleDashboardDataRequest(Long userId, String timeRange, PrintWriter writer, HttpServletResponse response) {
        try {
            DashboardData data = dashboardService.getDashboardData(userId, timeRange);
            String jsonResponse = objectMapper.writeValueAsString(data);
            writer.write("{\"success\":true,\"data\":" + jsonResponse + "}");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching dashboard data", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write("{\"error\":\"Failed to fetch dashboard data\",\"code\":500}");
        }
    }

    private void handleStatisticsRequest(Long userId, String timeRange, PrintWriter writer, HttpServletResponse response) {
        try {
            CrawlStatistics stats = analyticsService.getCrawlStatistics(userId, timeRange);
            String jsonResponse = objectMapper.writeValueAsString(stats);
            writer.write("{\"success\":true,\"data\":" + jsonResponse + "}");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching statistics", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write("{\"error\":\"Failed to fetch statistics\",\"code\":500}");
        }
    }

    private void handleChartsRequest(Long userId, String timeRange, PrintWriter writer, HttpServletResponse response) {
        try {
            // Get chart data from analytics service
            var chartData = analyticsService.getChartData(userId, timeRange);
            String jsonResponse = objectMapper.writeValueAsString(chartData);
            writer.write("{\"success\":true,\"data\":" + jsonResponse + "}");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching chart data", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write("{\"error\":\"Failed to fetch chart data\",\"code\":500}");
        }
    }

    private void handleActivityRequest(Long userId, String timeRange, PrintWriter writer, HttpServletResponse response) {
        try {
            UserActivity activity = analyticsService.getUserActivity(userId, timeRange);
            String jsonResponse = objectMapper.writeValueAsString(activity);
            writer.write("{\"success\":true,\"data\":" + jsonResponse + "}");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching user activity", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write("{\"error\":\"Failed to fetch user activity\",\"code\":500}");
        }
    }

    private void handleDashboardRefresh(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        try (PrintWriter writer = response.getWriter()) {
            Long userId = (Long) request.getAttribute("currentUserId");
            String timeRange = request.getParameter("timeRange");

            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                writer.write("{\"error\":\"Authentication required\"}");
                return;
            }

            // Force refresh dashboard data
            dashboardService.refreshDashboardData(userId, timeRange);
            writer.write("{\"success\":true,\"message\":\"Dashboard refreshed successfully\"}");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error refreshing dashboard", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Failed to refresh dashboard\"}");
        }
    }

    private void handleExport(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Long userId = (Long) request.getAttribute("currentUserId");
            String format = request.getParameter("format"); // "csv" or "json"
            String timeRange = request.getParameter("timeRange");

            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Authentication required\"}");
                return;
            }

            // Use ExportUtil for generating exports
            if ("csv".equals(format)) {
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", "attachment; filename=dashboard-data.csv");
                dashboardService.exportToCsv(userId, timeRange, response.getOutputStream());
            } else {
                response.setContentType("application/json");
                response.setHeader("Content-Disposition", "attachment; filename=dashboard-data.json");
                dashboardService.exportToJson(userId, timeRange, response.getOutputStream());
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error exporting data", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Export failed\"}");
        }
    }
}
