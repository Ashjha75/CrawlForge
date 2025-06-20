package com.ashish.exception;

public class CrawlLimitException extends Exception {
    public CrawlLimitException(String message) {
        super(message);
    }
}

