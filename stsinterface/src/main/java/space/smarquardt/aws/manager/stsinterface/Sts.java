package space.smarquardt.aws.manager.stsinterface;

import java.util.List;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

public interface Sts {

  AwsCredentialsProvider getCurrentCredentials();

  List<Profile> getCurrentProfiles();

  void changeCurrentProfile(Profile profile);

  Profile getCurrentProfile();

  void reauthenticate();

  List<String> getMfaDevices();

  void assumeRole(Profile profile);

  void connect(String tokenCode, String mfaDeviceArn);
}
