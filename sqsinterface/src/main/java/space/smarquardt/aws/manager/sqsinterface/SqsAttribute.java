package space.smarquardt.aws.manager.sqsinterface;

public class SqsAttribute {
  private final String name;
  private final String value;

  public enum SqsType {
    String,
    Number,
    Binary
  }

  private final SqsType sqsType;

  public SqsAttribute(String name, String value, SqsType sqsType) {
    this.name = name;
    this.value = value;
    this.sqsType = sqsType;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public SqsType getSqsType() {
    return sqsType;
  }
}
