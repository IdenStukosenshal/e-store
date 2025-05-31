package e_store.exceptions;

public class LocalizedValidationException extends RuntimeException {

    private final String msgCode;
    private final Object[] msgArgs;

    public LocalizedValidationException(String msgCode, Object... msgArgs) {
        this.msgCode = msgCode;
        this.msgArgs = msgArgs;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public Object[] getMsgArgs() {
        return msgArgs;
    }
}
