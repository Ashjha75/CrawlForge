// Global Toast Notification System for CrawlForge - CENTERED VERSION
class ToastManager {
    constructor() {
        this.createToastContainer();
        this.setupGlobalErrorHandler();
        this.toastQueue = [];
        this.maxToasts = 5;
    }

    createToastContainer() {
        // Remove existing container if it exists
        const existingContainer = document.getElementById('toast-container');
        if (existingContainer) {
            existingContainer.remove();
        }

        const container = document.createElement('div');
        container.id = 'toast-container';
        container.className = 'toast-container';

        // Insert at the beginning of body to avoid z-index issues
        document.body.insertBefore(container, document.body.firstChild);
    }

    setupGlobalErrorHandler() {
        // Handle uncaught JavaScript errors
        window.addEventListener('error', (event) => {
            this.showToast('An unexpected error occurred', 'error');
        });

        // Handle unhandled promise rejections
        window.addEventListener('unhandledrejection', (event) => {
            this.showToast('Network error occurred', 'error');
        });
    }

    showToast(message, type = 'info', duration = 5000) {
        // Limit number of toasts
        if (this.toastQueue.length >= this.maxToasts) {
            this.removeOldestToast();
        }

        const toast = this.createToast(message, type);
        const container = document.getElementById('toast-container');

        if (!container) {
            this.createToastContainer();
            return this.showToast(message, type, duration);
        }

        container.appendChild(toast);
        this.toastQueue.push(toast);

        // Trigger animation after a small delay
        setTimeout(() => {
            toast.classList.add('show');
        }, 50);

        // Auto remove
        setTimeout(() => {
            this.removeToast(toast);
        }, duration);

        return toast;
    }

    createToast(message, type) {
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;

        const icon = this.getIcon(type);

        toast.innerHTML = `
            <div class="toast-content">
                <div class="toast-icon">
                    <i class="bi ${icon}"></i>
                </div>
                <div class="toast-message">${this.escapeHtml(message)}</div>
                <button class="toast-close" onclick="toastManager.removeToast(this.closest('.toast'))">
                    <i class="bi bi-x"></i>
                </button>
            </div>
            <div class="toast-progress"></div>
        `;

        return toast;
    }

    getIcon(type) {
        const icons = {
            success: 'bi-check-circle-fill',
            error: 'bi-exclamation-triangle-fill',
            warning: 'bi-exclamation-circle-fill',
            info: 'bi-info-circle-fill'
        };
        return icons[type] || icons.info;
    }

    removeToast(toast) {
        if (!toast || !toast.parentNode) return;

        toast.classList.add('hide');

        // Remove from queue
        const index = this.toastQueue.indexOf(toast);
        if (index > -1) {
            this.toastQueue.splice(index, 1);
        }

        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 400);
    }

    removeOldestToast() {
        if (this.toastQueue.length > 0) {
            this.removeToast(this.toastQueue[0]);
        }
    }

    clearAllToasts() {
        this.toastQueue.forEach(toast => this.removeToast(toast));
        this.toastQueue = [];
    }

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    // Convenience methods
    success(message, duration = 5000) {
        return this.showToast(message, 'success', duration);
    }

    error(message, duration = 7000) {
        return this.showToast(message, 'error', duration);
    }

    warning(message, duration = 6000) {
        return this.showToast(message, 'warning', duration);
    }

    info(message, duration = 5000) {
        return this.showToast(message, 'info', duration);
    }
}

// Initialize global toast manager when DOM is ready
let toastManager;

document.addEventListener('DOMContentLoaded', function() {
    toastManager = new ToastManager();
});

// Global functions for easy access
function showToast(message, type = 'info', duration = 5000) {
    if (!toastManager) {
        toastManager = new ToastManager();
    }
    return toastManager.showToast(message, type, duration);
}

function showSuccess(message, duration = 5000) {
    if (!toastManager) {
        toastManager = new ToastManager();
    }
    return toastManager.success(message, duration);
}

function showError(message, duration = 7000) {
    if (!toastManager) {
        toastManager = new ToastManager();
    }
    return toastManager.error(message, duration);
}

function showWarning(message, duration = 6000) {
    if (!toastManager) {
        toastManager = new ToastManager();
    }
    return toastManager.warning(message, duration);
}

function showInfo(message, duration = 5000) {
    if (!toastManager) {
        toastManager = new ToastManager();
    }
    return toastManager.info(message, duration);
}

// Clear all toasts function
function clearAllToasts() {
    if (toastManager) {
        toastManager.clearAllToasts();
    }
}

// Check for server-side messages on page load
document.addEventListener('DOMContentLoaded', function() {
    // Check URL parameters for error messages
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');
    const success = urlParams.get('success');

    if (error) {
        setTimeout(() => showError(getErrorMessage(error)), 500);
    }

    if (success) {
        setTimeout(() => showSuccess(getSuccessMessage(success)), 500);
    }
});

function getErrorMessage(errorCode) {
    const messages = {
        'system': 'A system error occurred. Please try again.',
        'validation': 'Please check your input and try again.',
        'auth': 'Authentication failed. Please sign in again.',
        'permission': 'You do not have permission to perform this action.',
        'network': 'Network error. Please check your connection.',
        'timeout': 'Request timed out. Please try again.'
    };
    return messages[errorCode] || 'An unexpected error occurred.';
}

function getSuccessMessage(successCode) {
    const messages = {
        'saved': 'Data saved successfully!',
        'deleted': 'Item deleted successfully!',
        'updated': 'Information updated successfully!',
        'sent': 'Message sent successfully!'
    };
    return messages[successCode] || 'Operation completed successfully!';
}
