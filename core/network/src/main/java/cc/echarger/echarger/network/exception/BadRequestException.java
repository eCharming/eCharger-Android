package cc.echarger.echarger.network.exception;

public class BadRequestException extends RuntimeException {

    private int code;

    public BadRequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
