package de.otto.ipanema.tools.aws.sqs.testmessagesender.gui;

import de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface.Sqs;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ServiceLoader;

public class Controller  {
    @FXML
    public Button messagePickerButton;
    @FXML
    public TextField messageFileText;
    @FXML
    public TextField attributeFiletext;
    @FXML
    public Button attributeFileButton;
    @FXML
    public TextArea resultText;
    @FXML
    public TextField queueUrlTextField;
    private Path queuBodyPath;
    private Path attributePath;
    private Stage stage;
    private StringProperty queueUrlBinding = new SimpleStringProperty();
    private Sqs sqs;

    public Controller() {
        this.sqs = ServiceLoader.load(Sqs.class).findFirst().orElseThrow(() -> new RuntimeException("No SQS found"));

    }


    public void initialize() {
        Bindings.bindBidirectional(this.queueUrlTextField.textProperty(),queueUrlBinding);

    }



    public void openMessagePicker(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Body of message");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json", "*.json"));
        this.queuBodyPath = fileChooser.showOpenDialog(Stage.getWindows().get(0)).toPath();
        this.messageFileText.setText(queuBodyPath.toString());
    }

    public void openAttributePicker(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select attributes");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"), new FileChooser.ExtensionFilter("CSV", "*.csv"));
        this.attributePath = fileChooser.showOpenDialog(Stage.getWindows().get(0)).toPath();
        this.attributeFiletext.setText(attributePath.toString());
    }

    public void sendMessage(ActionEvent actionEvent) {
        try {
            final var result = this.sqs.sendMessage(this.queueUrlBinding.getValue(), Files.readString(this.queuBodyPath), Sqs.generateAttributes(this.attributePath));
            this.resultText.setText(result.getStatus()+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
