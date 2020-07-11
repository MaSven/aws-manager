package de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface to send sqs messages
 */
public interface Sqs {
    /**
     * Send a message to a specific queue
     *
     * @param body       Body of the message
     * @param attributes Attributes of the message
     * @return Was is successfull?
     */
    Result sendMessage(String queueName, String body, List<SqsAttribute> attributes);

    static List<SqsAttribute> generateAttributes(Path attributeFile) throws IOException {
        return
                Files.readAllLines(attributeFile).stream()
                        .map(s -> s.split(","))
                        .map(
                                attributeArguments ->
                                        new SqsAttribute(
                                                attributeArguments[0],
                                                attributeArguments[1],
                                                SqsAttribute.SqsType.valueOf(attributeArguments[2])))
                        .collect(Collectors.toList());
    }
}
