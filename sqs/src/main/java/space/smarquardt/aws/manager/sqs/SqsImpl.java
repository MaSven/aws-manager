package space.smarquardt.aws.manager.sqs;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import io.vavr.API;
import io.vavr.CheckedFunction1;
import io.vavr.collection.Stream;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import space.smarquardt.aws.manager.sqsinterface.Result;
import space.smarquardt.aws.manager.sqsinterface.Sqs;
import space.smarquardt.aws.manager.sqsinterface.SqsAttribute;
import space.smarquardt.aws.manager.sqsinterface.SqsObject;

public class SqsImpl implements Sqs {

  private final SqsClient sqsClient;

  public SqsImpl() {
    this.sqsClient =
        SqsClient.builder()
            .httpClient(ApacheHttpClient.builder().build())
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
  }

  @Override
  public CompletableFuture<Result> sendMessageAsync(
      String queueName, String body, List<SqsAttribute> attributes) {
    return CompletableFuture.supplyAsync(() -> this.sendMessage(queueName, body, attributes));
  }

  @Override
  public Result sendMessage(String queueName, String body, List<SqsAttribute> attributes) {
    Map<String, MessageAttributeValue> messageAttributes =
        attributes.stream()
            .collect(
                Collectors.toMap(
                    SqsAttribute::getName,
                    attribute ->
                        MessageAttributeValue.builder()
                            .dataType(attribute.getSqsType().toString())
                            .stringValue(attribute.getValue())
                            .build()));
    SendMessageRequest sendMessageRequest =
        SendMessageRequest.builder()
            .messageBody(body)
            .queueUrl(queueName)
            .messageAttributes(messageAttributes)
            .build();
    final var sendMessageResponse = this.sqsClient.sendMessage(sendMessageRequest);

    return new Result(
        sendMessageResponse.sdkHttpResponse().statusCode(), sendMessageResponse.toString());
  }

  @Override
  public CompletableFuture<List<SqsObject>> getCurrentQueues() {
    return CompletableFuture.supplyAsync(
        () -> {
          ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().build();
          ListQueuesResponse listQueuesResponse = this.sqsClient.listQueues(listQueuesRequest);
          return listQueuesResponse.queueUrls().stream()
              .map(API.unchecked((CheckedFunction1<String, URI>) URI::new))
              .map(
                  uri ->
                      new SqsObject(
                          Stream.of(uri.getPath().split("/"))
                              .findLast(Objects::nonNull)
                              .getOrElse(""),
                          uri.toString()))
              .collect(Collectors.toList());
        });
  }
}
