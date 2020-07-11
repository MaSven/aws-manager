module aws.sqs.testmessagesender.gui.main {
  requires javafx.controls;
  requires javafx.fxml;
  requires aws.sqs.testmessagesender.sqsinterface.main;

  opens de.otto.ipanema.tools.aws.sqs.testmessagesender.gui to
      javafx.graphics,
      javafx.fxml;
  uses de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface.Sqs;
}
