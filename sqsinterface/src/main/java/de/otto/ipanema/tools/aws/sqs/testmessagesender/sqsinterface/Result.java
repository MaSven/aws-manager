package de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface;

public class Result {

  private final int status;

  public Result(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}
