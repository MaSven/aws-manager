import space.smarquardt.aws.manager.sqsinterface.Sqs;

module aws.manager.cli.main {
  requires java.base;
  requires aws.manager.sqsinterface.Main;

  uses Sqs;

  requires commons.cli;
}
