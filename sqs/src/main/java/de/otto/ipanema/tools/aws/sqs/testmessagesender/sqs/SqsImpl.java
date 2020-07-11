package de.otto.ipanema.tools.aws.sqs.testmessagesender.sqs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface.Result;
import de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface.Sqs;
import de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface.SqsAttribute;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

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

    return new Result(sendMessageResponse.sdkHttpResponse().statusCode());
  }
}
