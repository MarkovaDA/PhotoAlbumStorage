package darya.markova.photostorage.security.util.exception;

/**
 * Информация об ошибках, возникающих при работе {@link darya.markova.photostorage.security.JwtAuthorizationTokenFilter}
 * со статусом {@link org.springframework.http.HttpStatus#FORBIDDEN}.
 */
public class ForbiddenExceptionResponseInfo {

    /**
     * Тип ошибки.
     */
    private Class exceptionType;

    /**
     * Сообщение об ошибке, полученное от объекта типа {@link Exception}.
     */
    private String nativeExceptionMessage;

    /**
     * Локализованное сообщение об ошибке, полученное от объекта типа {@link Exception}.
     */
    private String nativeLocalizedExceptionMessage;

    /**
     * Сообщение об ошибке.
     */
    private String customExceptionMessage;

    public ForbiddenExceptionResponseInfo(Exception ex, String customExceptionMessage) {
        this.exceptionType = ex.getClass();
        this.nativeExceptionMessage = ex.getMessage();
        this.nativeExceptionMessage = ex.getLocalizedMessage();
        this.customExceptionMessage = customExceptionMessage;
    }

    public Class getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(Class exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getNativeExceptionMessage() {
        return nativeExceptionMessage;
    }

    public void setNativeExceptionMessage(String nativeExceptionMessage) {
        this.nativeExceptionMessage = nativeExceptionMessage;
    }

    public String getNativeLocalizedExceptionMessage() {
        return nativeLocalizedExceptionMessage;
    }

    public void setNativeLocalizedExceptionMessage(String nativeLocalizedExceptionMessage) {
        this.nativeLocalizedExceptionMessage = nativeLocalizedExceptionMessage;
    }

    public String getCustomExceptionMessage() {
        return customExceptionMessage;
    }

    public void setCustomExceptionMessage(String customExceptionMessage) {
        this.customExceptionMessage = customExceptionMessage;
    }
}
