package de.otto.ipanema.tools.aws.sqs.testmessagesender.gui;

import de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface.Sqs;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ServiceLoader;

public class Main extends Application {




    @Override
    public void start(Stage stage) throws Exception {


        this.getClass().getModule().getResourceAsStream("main.fxml");

        final var resource = Main.class.getResource("/main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent root = fxmlLoader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
