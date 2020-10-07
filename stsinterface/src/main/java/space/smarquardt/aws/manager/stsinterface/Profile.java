package space.smarquardt.aws.manager.stsinterface;

public class Profile {

  private final String mfaArn;
  private final String roleArn;
  private final String name;

  public Profile(String mfaArn, String roleArn, String name) {
    this.mfaArn = mfaArn;
    this.roleArn = roleArn;
    this.name = name;
  }

  public String getMfaArn() {
    return mfaArn;
  }

  public String getRoleArn() {
    return roleArn;
  }

  public String getName() {
    return name;
  }
}
