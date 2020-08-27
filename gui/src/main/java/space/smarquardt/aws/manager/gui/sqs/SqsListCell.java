package space.smarquardt.aws.manager.gui.sqs;

import javafx.scene.control.ListCell;

import space.smarquardt.aws.manager.sqsinterface.SqsObject;

public class SqsListCell extends ListCell<SqsObject> {

  @Override
  protected void updateItem(SqsObject item, boolean empty) {
    super.updateItem(item, empty);
    if (!empty) {
      setText(item.getName());
    }
  }
}
