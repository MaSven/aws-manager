package space.smarquardt.aws.manager.gui.sqs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import space.smarquardt.aws.manager.sqsinterface.Sqs;
import space.smarquardt.aws.manager.sqsinterface.SqsObject;
import space.smarquardt.aws.manager.stsinterface.Profile;
import space.smarquardt.aws.manager.stsinterface.Sts;

public class SqsPresenter {
  @FXML public Button refresh;
  @FXML public TextArea responseField;
  @FXML public Button sendCurrent;
  @FXML public TextArea attributesField;
  @FXML public TextField attributesPath;
  @FXML public TextField filePath;
  @FXML public TextArea messageContent;
  @FXML public ChoiceBox<Profile> profileChoiceBox;
  @FXML public ChoiceBox<String> mfaChoice;
  @FXML ListView<SqsObject> sqsList;
  private ObservableList<SqsObject> selectedItems;
  private final StringProperty messageBody = new SimpleStringProperty();
  private final StringProperty attributesBody = new SimpleStringProperty();
  private ReadOnlyProperty<Profile> selectedProfile;
  private final Sqs sqs;
  private final Sts sts;
  private ReadOnlyProperty<String> seletedMfa;


    public SqsPresenter() {
        this.sqs =
                ServiceLoader.load(Sqs.class)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("could not load SQS"));
        this.sts =
                ServiceLoader.load(Sts.class)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("could not load STS"));
    }

    public void initialize() {

    final var awsProfiles = FXCollections.observableArrayList(this.sts.getCurrentProfiles());
    this.sqsList.setCellFactory(param -> new SqsListCell());
    Bindings.bindBidirectional(messageBody, messageContent.textProperty());
    Bindings.bindBidirectional(attributesBody, this.attributesField.textProperty());
    this.sqsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    this.selectedItems = this.sqsList.selectionModelProperty().get().getSelectedItems();
    this.profileChoiceBox.setConverter(
            createProfileConverter());
    selectedProfile = this.profileChoiceBox.getSelectionModel().selectedItemProperty();
    this.selectedProfile.addListener(
        (observable, oldValue, newValue) -> this.sts.changeCurrentProfile(newValue));
    this.profileChoiceBox.setItems(awsProfiles);
    this.seletedMfa= this.mfaChoice.getSelectionModel().selectedItemProperty();
  }

    private StringConverter<Profile> createProfileConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(Profile object) {
                return object.name();
            }

            @Override
            public Profile fromString(String string) {
                return sts.getCurrentProfiles().stream()
                        .filter(profile -> profile.name().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        };
    }

    public void loadQueues() {
    this.sqs
        .getCurrentQueues()
        .thenAcceptAsync(
            sqsObjects -> {
              ObservableList<SqsObject> objects = FXCollections.observableArrayList(sqsObjects);
              this.sqsList.setItems(objects);
            });
  }

  private Optional<Path> filechooser(
      Collection<FileChooser.ExtensionFilter> filters, String title) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(title);
    fileChooser.getExtensionFilters().addAll(filters);
    File file = fileChooser.showOpenDialog(Stage.getWindows().get(0));
    return file == null ? Optional.empty() : Optional.of(file.toPath());
  }

  public void chooseMessage(ActionEvent actionEvent) {
    this.filechooser(
            List.of(new FileChooser.ExtensionFilter("Select Messagebody", "*.json")),
            "Choose Message Body")
        .ifPresent(
            path -> {
              try {
                this.messageBody.setValue(Files.readString(path));
                this.filePath.setText(path.toString());
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
  }

  public void chooseAttributes(ActionEvent actionEvent) {
    this.filechooser(
            List.of(new FileChooser.ExtensionFilter("Select Attributes", "*.csv")),
            "Choose Attribute Values")
        .ifPresent(
            path -> {
              try {
                this.attributesBody.setValue(Files.readString(path));
                this.attributesPath.setText(path.toString());
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
  }

  public void sendCurrent(ActionEvent actionEvent) {
    this.sqs
        .sendMessageAsync(
            this.selectedItems.get(0).url(),
            this.messageBody.get(),
            Sqs.generateAttributesFromBody(this.attributesBody.get()))
        .thenAcceptAsync(result -> this.responseField.setText(result.toString()));
  }

  public void connetToAwsWithProfile(ActionEvent actionEvent) {

  }

  public void secondFatorAuth(ActionEvent actionEvent) {
    TextInputDialog textInputDialog = new TextInputDialog();
    textInputDialog.setTitle("Pleas insert MFA token");
    textInputDialog
        .showAndWait()
        .ifPresent(
            s -> {
              this.sts.connect(s);
              this.loadQueues();
            });
  }
}
