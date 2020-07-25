package space.smarquardt.aws.manager.stsinterface;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

import java.util.List;

public interface Sts {


  AwsCredentialsProvider getCurrentCredentials();

  List<Profile> getCurrentProfiles();

  void changeCurrentProfile(Profile profile);

  Profile getCurrentProfile();

  void reauthenticate();


  void connect(String tokenCode);




}
