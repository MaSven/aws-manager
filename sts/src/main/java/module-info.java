module aws.manager.sts.main {
    requires aws.manager.stsinterface.main;
    requires software.amazon.awssdk.services.sts;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.http.apache;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.http;
    requires software.amazon.awssdk.profiles;

    provides space.smarquardt.aws.manager.stsinterface.Sts with space.smarquardt.aws.manager.sts.StsImpl;
}
