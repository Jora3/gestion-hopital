package utils;

class DateFormatException extends RuntimeException {

    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
