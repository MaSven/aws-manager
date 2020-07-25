module aws.manager.gui.Main {
  requires java.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires afterburner.fx;
  requires javafx.fxml;
  requires java.annotation;

  opens space.smarquardt.aws.manager.gui to
      javafx.graphics,
      javafx.base;
  opens space.smarquardt.aws.manager.gui.sqs to
      afterburner.fx,
      javafx.fxml;

  requires aws.manager.sqsinterface.Main;

  uses space.smarquardt.aws.manager.sqsinterface.Sqs;

  requires aws.manager.stsinterface.main;

  uses space.smarquardt.aws.manager.stsinterface.Sts;
}
