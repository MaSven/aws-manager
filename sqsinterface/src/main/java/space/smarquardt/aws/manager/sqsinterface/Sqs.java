package space.smarquardt.aws.manager.sqsinterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Interface to send sqs messages */
public interface Sqs {
  /**
   * Send a message to a specific queue
   *
   * @param body Body of the message
   * @param attributes Attributes of the message
   * @return Was is successfull?
   */
  CompletableFuture<Result> sendMessageAsync(
      String queueName, String body, List<SqsAttribute> attributes);

  /**
   * Send a message to a specific queue
   *
   * @param body Body of the message
   * @param attributes Attributes of the message
   * @return Was is successfull?
   */
  Result sendMessage(String queueName, String body, List<SqsAttribute> attributes);

  static List<SqsAttribute> generateAttributes(Path attributeFile) throws IOException {
    return Files.readAllLines(attributeFile).stream()
        .map(Sqs::generateAttributes)
        .collect(Collectors.toList());
  }

  static List<SqsAttribute> generateAttributesFromBody(String attributes) {
    return Stream.of(attributes.split("\\R"))
        .map(Sqs::generateAttributes)
        .collect(Collectors.toList());
  }

  static SqsAttribute generateAttributes(String attributeString) {
    final var attributeArguments = attributeString.split(",");
    return new SqsAttribute(
        attributeArguments[0],
        attributeArguments[1],
        SqsAttribute.SqsType.valueOf(attributeArguments[2]));
  }

  /** @return */
  CompletableFuture<List<SqsObject>> getCurrentQueues();
}
