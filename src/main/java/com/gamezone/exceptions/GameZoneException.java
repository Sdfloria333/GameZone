package com.gamezone.exceptions;

/**
 * Root of the custom exception hierarchy for GameZone Unicesar.
 * <p>
 * This class is unchecked (extends {@link RuntimeException}) so that service
 * and repository method signatures do not need to declare it, preserving the
 * existing behavior of the system. All other custom exceptions in the system
 * extend this class, directly or indirectly, which lets callers choose to
 * catch a specific error category or this common type when the distinction
 * does not matter.
 */
public class GameZoneException extends RuntimeException {

    private final String errorCode;

    /**
     * Creates a new exception with only a descriptive message.
     *
     * @param message a human-readable description of what went wrong
     */
    public GameZoneException(String message) {
        super(message);
        this.errorCode = null;
    }

    /**
     * Creates a new exception with a descriptive message and an error code
     * that identifies the specific failure condition.
     *
     * @param message   a human-readable description of what went wrong
     * @param errorCode a short code identifying the specific error condition
     */
    public GameZoneException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Returns the error code associated with this exception, if any.
     *
     * @return the error code, or {@code null} if none was provided
     */
    public String getErrorCode() {
        return errorCode;
    }
}
