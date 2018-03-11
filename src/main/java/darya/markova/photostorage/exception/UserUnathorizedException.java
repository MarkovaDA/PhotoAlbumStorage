package darya.markova.photostorage.exception;

public class UserUnathorizedException extends Exception {

    private static final String EXCEPTION_TEXT = "User unathorized.";

    private static final String LOCALIZED_EXCEPTION_TEXT = "Пользователь неавторизован - неверные данные пользователя.";

    private String message;

    private String localizedMessage;

    public UserUnathorizedException() {
        this.message = EXCEPTION_TEXT;
        this.localizedMessage = LOCALIZED_EXCEPTION_TEXT;
        this.setStackTrace(new StackTraceElement[]{});
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }
}
