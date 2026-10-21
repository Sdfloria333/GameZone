package com.gamezone.exceptions;

/**
 * Thrown when a file read or write operation fails: a corrupted file, a
 * denied permission, a full disk, among others. Wraps the original cause
 * (usually an {@link java.io.IOException}) so the underlying failure is not
 * lost.
 */
public class PersistenceException extends GameZoneException {

    /**
     * Creates a new exception wrapping the original cause of a persistence
     * failure, keeping its stack trace available through {@link #getCause()}.
     *
     * @param message a description of the persistence operation that failed
     * @param cause   the original exception that caused this failure (e.g. an IOException)
     */
    public PersistenceException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
