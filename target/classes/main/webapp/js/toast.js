// Global Toast Notification System for CrawlForge
class ToastManager {
    constructor() {
        this.createToastContainer();
        this.setupGlobalErrorHandler();
    }

    createToastContainer() {
        if (!document.getElementById('toast-container')) {
            const container = document.createElement('div');
            container.id = 'toast-container';
            container.className = 'toast-container';
            document.body.appendChild(container);
        }
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
        const toast = this.createToast(message, type);
        const container = document.getElementById('toast-container');

        container.appendChild(toast);

        // Trigger animation
        setTimeout(() => toast.classList.add('show'), 100);

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
                <div class="toast-message">${message}</div>
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
        toast.classList.add('hide');
        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 300);
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

// Initialize global toast manager
const toastManager = new ToastManager();

// Global functions for easy access
function showToast(message, type = 'info', duration = 5000) {
    return toastManager.showToast(message, type, duration);
}

function showSuccess(message, duration = 5000) {
    return toastManager.success(message, duration);
}

function showError(message, duration = 7000) {
    return toastManager.error(message, duration);
}

function showWarning(message, duration = 6000) {
    return toastManager.warning(message, duration);
}

function showInfo(message, duration = 5000) {
    return toastManager.info(message, duration);
}

// Check for server-side messages on page load
document.addEventListener('DOMContentLoaded', function() {
    // Check URL parameters for error messages
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');
    const success = urlParams.get('success');

    if (error) {
        showError(getErrorMessage(error));
    }

    if (success) {
        showSuccess(getSuccessMessage(success));
    }

    // Check for session-based messages (if available in JSP)
    if (typeof serverMessage !== 'undefined' && serverMessage) {
        showToast(serverMessage.message, serverMessage.type);
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
