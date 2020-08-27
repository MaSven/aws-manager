import space.smarquardt.aws.manager.sqs.SqsImpl;
import space.smarquardt.aws.manager.sqsinterface.Sqs;

module aws.manager.sqs.Main {
  requires java.base;
  requires software.amazon.awssdk.auth;
  requires software.amazon.awssdk.services.sqs;
  requires aws.manager.sqsinterface.Main;
  requires java.xml;
  requires software.amazon.awssdk.http;
  requires software.amazon.awssdk.http.apache;
  requires com.fasterxml.jackson.databind;
  requires io.vavr;
  requires aws.manager.stsinterface.main;

  uses space.smarquardt.aws.manager.stsinterface.Sts;

  provides Sqs with
      SqsImpl;
}
