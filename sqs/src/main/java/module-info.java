module aws.sqs.testmessagesender.sqs.main {
  requires java.base;
  requires software.amazon.awssdk.auth;
  requires software.amazon.awssdk.services.sqs;
  requires aws.sqs.testmessagesender.sqsinterface.main;
  requires java.xml;
  requires software.amazon.awssdk.http;
  requires software.amazon.awssdk.http.apache;
  requires com.fasterxml.jackson.databind;

  provides de.otto.ipanema.tools.aws.sqs.testmessagesender.sqsinterface.Sqs with
      de.otto.ipanema.tools.aws.sqs.testmessagesender.sqs.SqsImpl;
}
