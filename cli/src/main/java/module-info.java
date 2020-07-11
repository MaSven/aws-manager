module aws.sqs.testmessagesender.cli.main {
  requires java.base;
  requires aws.sqs.testmessagesender.sqsinterface.main;

  uses de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface.Sqs;

  requires commons.cli;
}
