document.addEventListener('DOMContentLoaded', function() {
// Add this to your existing history-table.js

// Scroll to top function
function scrollToTop() {
    // Smooth scroll to top
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });

    // Optional: Focus on the URL input field after scrolling
    setTimeout(() => {
        const urlInput = document.querySelector('input[name="url"]') ||
                        document.querySelector('#url') ||
                        document.querySelector('.form-input');
        if (urlInput) {
            urlInput.focus();
        }
    }, 500); // Wait for scroll animation to complete
}

// Alternative function if you want to scroll to a specific form element
function scrollToForm() {
    const formSection = document.querySelector('.crawl-form-section') ||
                       document.querySelector('.form-container') ||
                       document.querySelector('form');

    if (formSection) {
        formSection.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
        });

        // Focus on first input
        setTimeout(() => {
            const firstInput = formSection.querySelector('input[type="url"], input[type="text"]');
            if (firstInput) {
                firstInput.focus();
            }
        }, 500);
    } else {
        // Fallback to scroll to top
        scrollToTop();
    }
}

    // Sample data - replace with actual API calls
    const sampleData = [
        {
            id: 1,
            url: 'https://example.com',
            status: 'completed',
            pages: 1247,
            depth: 3,
            threads: 4,
            duration: '2m 34s',
            created: '2025-06-22 14:30:00',
            dataExtracted: 15420
        },
        {
            id: 2,
            url: 'https://news-site.com',
            status: 'running',
            pages: 89,
            depth: 2,
            threads: 6,
            duration: '45s',
            created: '2025-06-22 15:15:00',
            dataExtracted: 0
        },
        {
            id: 3,
            url: 'https://ecommerce-store.com',
            status: 'failed',
            pages: 0,
            depth: 4,
            threads: 8,
            duration: '12s',
            created: '2025-06-22 13:45:00',
            dataExtracted: 0
        }
    ];

    let currentData = [...sampleData];
    let currentPage = 1;
    let pageSize = 10;
    let sortField = 'created';
    let sortDirection = 'desc';
    let selectedRows = new Set();

    // Initialize components
    initializeTable();
    initializeFilters();
    initializePagination();
    initializeSearch();
    initializeBulkActions();
    animateCounters();

    function initializeTable() {
        renderTable();
        setupSorting();
        setupRowSelection();
    }

    function renderTable() {
        const tableBody = document.getElementById('tableBody');
        const startIndex = (currentPage - 1) * pageSize;
        const endIndex = startIndex + pageSize;
        const pageData = currentData.slice(startIndex, endIndex);

        if (pageData.length === 0) {
            showEmptyState();
            return;
        }

        hideEmptyState();

        tableBody.innerHTML = pageData.map(row => `
            <tr data-id="${row.id}">
                <td class="checkbox-col">
                    <input type="checkbox" class="row-checkbox" value="${row.id}">
                </td>
                <td class="url-cell">
                    <a href="${row.url}" target="_blank" class="url-link" title="${row.url}">
                        ${truncateUrl(row.url, 40)}
                    </a>
                </td>
                <td>
                    <span class="status-badge ${row.status}">
                        <i class="bi bi-${getStatusIcon(row.status)}"></i>
                        ${row.status.charAt(0).toUpperCase() + row.status.slice(1)}
                    </span>
                </td>
                <td>${row.pages.toLocaleString()}</td>
                <td>${row.depth}</td>
                <td>${row.threads}</td>
                <td>${row.duration}</td>
                <td>${formatDate(row.created)}</td>
                <td class="actions-col">
                    <div class="action-buttons">
                        <button class="action-btn view" onclick="viewDetails(${row.id})" title="View Details">
                            <i class="bi bi-eye"></i>
                        </button>
                        ${row.status === 'failed' ? `
                            <button class="action-btn retry" onclick="retryTask(${row.id})" title="Retry">
                                <i class="bi bi-arrow-clockwise"></i>
                            </button>
                        ` : ''}
                        <button class="action-btn delete" onclick="deleteTask(${row.id})" title="Delete">
                            <i class="bi bi-trash"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `).join('');

        updatePaginationInfo();
    }

    function getStatusIcon(status) {
        const icons = {
            completed: 'check-circle',
            running: 'arrow-clockwise',
            failed: 'x-circle',
            paused: 'pause-circle'
        };
        return icons[status] || 'circle';
    }

    function truncateUrl(url, maxLength) {
        if (url.length <= maxLength) return url;
        return url.substring(0, maxLength) + '...';
    }

    function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
    }

    function setupSorting() {
        document.querySelectorAll('.sortable').forEach(header => {
            header.addEventListener('click', function() {
                const field = this.dataset.sort;

                if (sortField === field) {
                    sortDirection = sortDirection === 'asc' ? 'desc' : 'asc';
                } else {
                    sortField = field;
                    sortDirection = 'asc';
                }

                // Update UI
                document.querySelectorAll('.sortable').forEach(h => {
                    h.classList.remove('asc', 'desc');
                });
                this.classList.add(sortDirection);

                // Sort data
                sortData();
                renderTable();
            });
        });
    }

    function sortData() {
        currentData.sort((a, b) => {
            let aVal = a[sortField];
            let bVal = b[sortField];

            // Handle different data types
            if (sortField === 'created') {
                aVal = new Date(aVal);
                bVal = new Date(bVal);
            } else if (typeof aVal === 'string') {
                aVal = aVal.toLowerCase();
                bVal = bVal.toLowerCase();
            }

            if (aVal < bVal) return sortDirection === 'asc' ? -1 : 1;
            if (aVal > bVal) return sortDirection === 'asc' ? 1 : -1;
            return 0;
        });
    }

    function setupRowSelection() {
        // Select all checkbox
        document.getElementById('selectAll').addEventListener('change', function() {
            const checkboxes = document.querySelectorAll('.row-checkbox');
            checkboxes.forEach(checkbox => {
                checkbox.checked = this.checked;
                if (this.checked) {
                    selectedRows.add(parseInt(checkbox.value));
                } else {
                    selectedRows.delete(parseInt(checkbox.value));
                }
            });
            updateBulkActions();
        });

        // Individual row checkboxes
        document.addEventListener('change', function(e) {
            if (e.target.classList.contains('row-checkbox')) {
                const id = parseInt(e.target.value);
                if (e.target.checked) {
                    selectedRows.add(id);
                } else {
                    selectedRows.delete(id);
                }
                updateBulkActions();

                // Update select all checkbox
                const allCheckboxes = document.querySelectorAll('.row-checkbox');
                const checkedCheckboxes = document.querySelectorAll('.row-checkbox:checked');
                const selectAllCheckbox = document.getElementById('selectAll');

                if (checkedCheckboxes.length === 0) {
                    selectAllCheckbox.indeterminate = false;
                    selectAllCheckbox.checked = false;
                } else if (checkedCheckboxes.length === allCheckboxes.length) {
                    selectAllCheckbox.indeterminate = false;
                    selectAllCheckbox.checked = true;
                } else {
                    selectAllCheckbox.indeterminate = true;
                }
            }
        });
    }

    function updateBulkActions() {
        const bulkActions = document.getElementById('bulkActions');
        const selectedCount = document.querySelector('.selected-count');

        if (selectedRows.size > 0) {
            bulkActions.style.display = 'flex';
            selectedCount.textContent = `${selectedRows.size} selected`;
        } else {
            bulkActions.style.display = 'none';
        }
    }

    function initializeFilters() {
        const filterToggle = document.getElementById('filterToggle');
        const filterMenu = document.getElementById('filterMenu');

        filterToggle.addEventListener('click', function(e) {
            e.stopPropagation();
            filterMenu.classList.toggle('show');
        });

        document.addEventListener('click', function() {
            filterMenu.classList.remove('show');
        });

        filterMenu.addEventListener('click', function(e) {
            e.stopPropagation();
        });

        // Filter change handlers
        document.getElementById('statusFilter').addEventListener('change', applyFilters);
        document.getElementById('dateFilter').addEventListener('change', applyFilters);
    }

    function applyFilters() {
        const statusFilter = document.getElementById('statusFilter').value;
        const dateFilter = document.getElementById('dateFilter').value;

        currentData = sampleData.filter(row => {
            let matches = true;

            if (statusFilter && row.status !== statusFilter) {
                matches = false;
            }

            if (dateFilter && dateFilter !== 'custom') {
                const rowDate = new Date(row.created);
                const now = new Date();

                switch (dateFilter) {
                    case 'today':
                        matches = matches && rowDate.toDateString() === now.toDateString();
                        break;
                    case 'week':
                        const weekAgo = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
                        matches = matches && rowDate >= weekAgo;
                        break;
                    case 'month':
                        const monthAgo = new Date(now.getTime() - 30 * 24 * 60 * 60 * 1000);
                        matches = matches && rowDate >= monthAgo;
                        break;
                }
            }

            return matches;
        });

        currentPage = 1;
        renderTable();
        updatePagination();
    }

    function initializeSearch() {
        const searchInput = document.getElementById('historySearch');
        let searchTimeout;

        searchInput.addEventListener('input', function() {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(() => {
                const query = this.value.toLowerCase().trim();

                if (query === '') {
                    currentData = [...sampleData];
                } else {
                    currentData = sampleData.filter(row =>
                        row.url.toLowerCase().includes(query) ||
                        row.status.toLowerCase().includes(query)
                    );
                }

                currentPage = 1;
                renderTable();
                updatePagination();
            }, 300);
        });
    }

    function initializePagination() {
        document.getElementById('prevPage').addEventListener('click', () => {
            if (currentPage > 1) {
                currentPage--;
                renderTable();
                updatePagination();
            }
        });

        document.getElementById('nextPage').addEventListener('click', () => {
            const totalPages = Math.ceil(currentData.length / pageSize);
            if (currentPage < totalPages) {
                currentPage++;
                renderTable();
                updatePagination();
            }
        });

        document.getElementById('pageSize').addEventListener('change', function() {
            pageSize = parseInt(this.value);
            currentPage = 1;
            renderTable();
            updatePagination();
        });

        updatePagination();
    }

    function updatePagination() {
        const totalPages = Math.ceil(currentData.length / pageSize);
        const prevBtn = document.getElementById('prevPage');
        const nextBtn = document.getElementById('nextPage');
        const paginationNumbers = document.getElementById('paginationNumbers');

        prevBtn.disabled = currentPage === 1;
        nextBtn.disabled = currentPage === totalPages || totalPages === 0;

        // Generate page numbers
        let pages = [];
        const maxVisible = 5;

        if (totalPages <= maxVisible) {
            pages = Array.from({length: totalPages}, (_, i) => i + 1);
        } else {
            const start = Math.max(1, currentPage - 2);
            const end = Math.min(totalPages, start + maxVisible - 1);
            pages = Array.from({length: end - start + 1}, (_, i) => start + i);
        }

        paginationNumbers.innerHTML = pages.map(page => `
            <button class="page-number ${page === currentPage ? 'active' : ''}"
                    onclick="goToPage(${page})">${page}</button>
        `).join('');
    }

    function updatePaginationInfo() {
        const startIndex = (currentPage - 1) * pageSize + 1;
        const endIndex = Math.min(currentPage * pageSize, currentData.length);

        document.getElementById('showingStart').textContent = startIndex;
        document.getElementById('showingEnd').textContent = endIndex;
        document.getElementById('totalRecords').textContent = currentData.length;
    }

    function showEmptyState() {
        document.getElementById('emptyState').style.display = 'block';
        document.querySelector('.table-wrapper').style.display = 'none';
    }

    function hideEmptyState() {
        document.getElementById('emptyState').style.display = 'none';
        document.querySelector('.table-wrapper').style.display = 'block';
    }

    function initializeBulkActions() {
        document.querySelector('.bulk-btn.delete-btn').addEventListener('click', function() {
            if (selectedRows.size > 0) {
                if (confirm(`Are you sure you want to delete ${selectedRows.size} selected tasks?`)) {
                    // Implement bulk delete
                    console.log('Deleting tasks:', Array.from(selectedRows));
                    selectedRows.clear();
                    updateBulkActions();
                }
            }
        });

        document.querySelector('.bulk-btn.retry-btn').addEventListener('click', function() {
            if (selectedRows.size > 0) {
                // Implement bulk retry
                console.log('Retrying tasks:', Array.from(selectedRows));
                selectedRows.clear();
                updateBulkActions();
            }
        });

        document.querySelector('.bulk-btn.export-btn').addEventListener('click', function() {
            if (selectedRows.size > 0) {
                // Implement bulk export
                console.log('Exporting tasks:', Array.from(selectedRows));
            }
        });
    }

    function animateCounters() {
        document.querySelectorAll('[data-target]').forEach(counter => {
            const target = parseInt(counter.dataset.target);
            let current = 0;
            const increment = target / 100;

            const timer = setInterval(() => {
                current += increment;
                if (current >= target) {
                    current = target;
                    clearInterval(timer);
                }
                counter.textContent = Math.floor(current).toLocaleString();
            }, 20);
        });
    }

    // Global functions for onclick handlers
    window.goToPage = function(page) {
        currentPage = page;
        renderTable();
        updatePagination();
    };

    window.viewDetails = function(id) {
        const task = sampleData.find(t => t.id === id);
        if (task) {
            const modalBody = document.getElementById('modalBody');
            modalBody.innerHTML = `
                <div class="task-details">
                    <div class="detail-group">
                        <label>Target URL:</label>
                        <a href="${task.url}" target="_blank">${task.url}</a>
                    </div>
                    <div class="detail-group">
                        <label>Status:</label>
                        <span class="status-badge ${task.status}">
                            <i class="bi bi-${getStatusIcon(task.status)}"></i>
                            ${task.status.charAt(0).toUpperCase() + task.status.slice(1)}
                        </span>
                    </div>
                    <div class="detail-group">
                        <label>Pages Crawled:</label>
                        <span>${task.pages.toLocaleString()}</span>
                    </div>
                    <div class="detail-group">
                        <label>Data Points Extracted:</label>
                        <span>${task.dataExtracted.toLocaleString()}</span>
                    </div>
                    <div class="detail-group">
                        <label>Crawl Depth:</label>
                        <span>${task.depth} levels</span>
                    </div>
                    <div class="detail-group">
                        <label>Threads Used:</label>
                        <span>${task.threads}</span>
                    </div>
                    <div class="detail-group">
                        <label>Duration:</label>
                        <span>${task.duration}</span>
                    </div>
                    <div class="detail-group">
                        <label>Created:</label>
                        <span>${formatDate(task.created)}</span>
                    </div>
                </div>
            `;

            const modal = new bootstrap.Modal(document.getElementById('taskDetailsModal'));
            modal.show();
        }
    };

    window.retryTask = function(id) {
        if (confirm('Are you sure you want to retry this crawl task?')) {
            console.log('Retrying task:', id);
            // Implement retry logic
        }
    };

    window.deleteTask = function(id) {
        if (confirm('Are you sure you want to delete this crawl task?')) {
            console.log('Deleting task:', id);
            // Implement delete logic
        }
    };
});
