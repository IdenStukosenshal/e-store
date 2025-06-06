package e_store.enums;

public enum ErrorCode {
    NOT_FOUND("error.notFound"),
    TOO_LATE("error.tooLate"),
    ADDRESS_ON_MAP("error.address.notFoundOnMap"),
    ADDRESS_MORE_THAN_ONE("error.address.moreThanOne"),
    VALIDATION_FIELDS("error.validation.fields"),
    JSON("error.json"),
    INTERNAL("error.internal");

    ErrorCode(String msg) {
        this.msg = msg;
    }

    private final String msg;

    public String getMsg() {
        return msg;
    }
}
