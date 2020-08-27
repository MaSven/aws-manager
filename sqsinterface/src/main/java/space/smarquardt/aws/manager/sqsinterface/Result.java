package space.smarquardt.aws.manager.sqsinterface;

public class Result {

    private final int status;
    private final String message;

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
