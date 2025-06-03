package e_store.enums;

public enum ErrorCode {
    NOT_FOUND("error.notfound"),
    TOO_LATE("error.toolate"),
    ADDRESS_ON_MAP("error.address.onmap"),
    ADDRESS_MORE_THAN_ONE("error.address.more.thanone"),
    VALIDATION_FIELDS("validation.fields.error"),
    JSON("json.error"),
    INTERNAL("internal.error");

    ErrorCode(String msg) {
        this.msg = msg;
    }

    private final String msg;

    public String getMsg() {
        return msg;
    }
}
