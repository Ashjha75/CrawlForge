<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/main/webapp/css/history.css">

<!-- Crawl History Section -->
<section class="history-section">
    <div class="container">
        <!-- History Header -->
        <div class="history-header">
            <div class="header-content">
                <div class="header-info">
                    <h2 class="history-title">
                        <i class="bi bi-clock-history"></i>
                        Crawl History
                    </h2>
                    <p class="history-subtitle">Track and manage your previous crawling operations</p>
                </div>
                <div class="header-actions">
                    <div class="search-container">
                        <input type="text" class="search-input" placeholder="Search history..." id="historySearch">
                        <i class="bi bi-search search-icon"></i>
                    </div>
                    <div class="filter-dropdown">
                        <button class="filter-btn" id="filterToggle">
                            <i class="bi bi-funnel"></i>
                            <span>Filter</span>
                        </button>
                        <div class="filter-menu" id="filterMenu">
                            <div class="filter-group">
                                <label>Status</label>
                                <select class="filter-select" id="statusFilter">
                                    <option value="">All Status</option>
                                    <option value="completed">Completed</option>
                                    <option value="running">Running</option>
                                    <option value="failed">Failed</option>
                                    <option value="paused">Paused</option>
                                </select>
                            </div>
                            <div class="filter-group">
                                <label>Date Range</label>
                                <select class="filter-select" id="dateFilter">
                                    <option value="">All Time</option>
                                    <option value="today">Today</option>
                                    <option value="week">This Week</option>
                                    <option value="month">This Month</option>
                                    <option value="custom">Custom Range</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <button class="export-btn">
                        <i class="bi bi-download"></i>
                        <span>Export</span>
                    </button>
                </div>
            </div>
        </div>

        <!-- History Stats Cards -->
        <div class="history-stats">
            <div class="stat-card">
                <div class="stat-icon completed">
                    <i class="bi bi-check-circle"></i>
                </div>
                <div class="stat-content">
                    <div class="stat-value" data-target="247">0</div>
                    <div class="stat-label">Completed</div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon running">
                    <i class="bi bi-arrow-clockwise"></i>
                </div>
                <div class="stat-content">
                    <div class="stat-value" data-target="3">0</div>
                    <div class="stat-label">Running</div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon failed">
                    <i class="bi bi-x-circle"></i>
                </div>
                <div class="stat-content">
                    <div class="stat-value" data-target="12">0</div>
                    <div class="stat-label">Failed</div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon total">
                    <i class="bi bi-graph-up"></i>
                </div>
                <div class="stat-content">
                    <div class="stat-value" data-target="15420">0</div>
                    <div class="stat-label">Total Pages</div>
                </div>
            </div>
        </div>

        <!-- History Table -->
        <div class="history-table-container">
            <div class="table-header">
                <div class="table-controls">
                    <div class="bulk-actions" id="bulkActions" style="display: none;">
                        <span class="selected-count">0 selected</span>
                        <button class="bulk-btn delete-btn">
                            <i class="bi bi-trash"></i>
                            Delete
                        </button>
                        <button class="bulk-btn retry-btn">
                            <i class="bi bi-arrow-clockwise"></i>
                            Retry
                        </button>
                        <button class="bulk-btn export-btn">
                            <i class="bi bi-download"></i>
                            Export
                        </button>
                    </div>
                    <div class="pagination-info">
                        Showing <span id="showingStart">1</span>-<span id="showingEnd">10</span> of <span id="totalRecords">247</span> results
                    </div>
                </div>
            </div>

            <div class="table-wrapper">
                <table class="history-table" id="historyTable">
                    <thead class="table-head">
                    <tr>
                        <th class="checkbox-col">
                            <input type="checkbox" class="select-all" id="selectAll">
                        </th>
                        <th class="sortable" data-sort="url">
                            <span>Target URL</span>
                            <i class="bi bi-chevron-expand sort-icon"></i>
                        </th>
                        <th class="sortable" data-sort="status">
                            <span>Status</span>
                            <i class="bi bi-chevron-expand sort-icon"></i>
                        </th>
                        <th class="sortable" data-sort="pages">
                            <span>Pages</span>
                            <i class="bi bi-chevron-expand sort-icon"></i>
                        </th>
                        <th class="sortable" data-sort="depth">
                            <span>Depth</span>
                            <i class="bi bi-chevron-expand sort-icon"></i>
                        </th>
                        <th class="sortable" data-sort="threads">
                            <span>Threads</span>
                            <i class="bi bi-chevron-expand sort-icon"></i>
                        </th>
                        <th class="sortable" data-sort="duration">
                            <span>Duration</span>
                            <i class="bi bi-chevron-expand sort-icon"></i>
                        </th>
                        <th class="sortable" data-sort="created">
                            <span>Created</span>
                            <i class="bi bi-chevron-expand sort-icon"></i>
                        </th>
                        <th class="actions-col">Actions</th>
                    </tr>
                    </thead>
                    <tbody class="table-body" id="tableBody">
                    <!-- Dynamic rows will be inserted here -->
                    </tbody>
                </table>
            </div>

            <!-- Empty State -->
            <div class="empty-state" id="emptyState" style="display: none;">
                <div class="empty-icon">
                    <i class="bi bi-inbox"></i>
                </div>
                <h3 class="empty-title">No crawl history found</h3>
                <p class="empty-description">Start your first crawl task to see history here</p>
                <button class="empty-cta-btn" onclick="scrollToTop()">
                    <i class="bi bi-plus-circle"></i>
                    Create New Crawl
                </button>
            </div>

            <!-- Loading State -->
            <div class="loading-state" id="loadingState" style="display: none;">
                <div class="loading-spinner"></div>
                <p>Loading crawl history...</p>
            </div>
        </div>

        <!-- Pagination -->
        <div class="pagination-container">
            <div class="pagination-controls">
                <button class="pagination-btn" id="prevPage" disabled>
                    <i class="bi bi-chevron-left"></i>
                    Previous
                </button>
                <div class="pagination-numbers" id="paginationNumbers">
                    <!-- Dynamic pagination numbers -->
                </div>
                <button class="pagination-btn" id="nextPage">
                    Next
                    <i class="bi bi-chevron-right"></i>
                </button>
            </div>
            <div class="page-size-selector">
                <label>Show:</label>
                <select class="page-size-select" id="pageSize">
                    <option value="10">10</option>
                    <option value="25">25</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                </select>
                <span>per page</span>
            </div>
        </div>
    </div>
</section>

<!-- Task Details Modal -->
<div class="modal fade" id="taskDetailsModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i class="bi bi-info-circle"></i>
                    Crawl Task Details
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body" id="modalBody">
                <!-- Dynamic content -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="retryTask">
                    <i class="bi bi-arrow-clockwise"></i>
                    Retry Task
                </button>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/history.js"></script>
