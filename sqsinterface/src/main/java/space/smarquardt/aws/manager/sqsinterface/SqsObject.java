package space.smarquardt.aws.manager.sqsinterface;

public class SqsObject {

    private final String name;
    private final String url;


    public SqsObject(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
