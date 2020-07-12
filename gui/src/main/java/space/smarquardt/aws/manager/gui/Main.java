package space.smarquardt.aws.manager.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.airhacks.afterburner.injection.Injector;

import space.smarquardt.aws.manager.gui.sqs.SqsView;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    SqsView sqsView = new SqsView();
    Scene scene = new Scene(sqsView.getView());
    primaryStage.setTitle("AWS Manager");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    Injector.forgetAll();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
