package space.smarquardt.aws.manager.sts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.profiles.ProfileFile;
import space.smarquardt.aws.manager.sts.internal.StsClientInstance;
import space.smarquardt.aws.manager.stsinterface.Profile;
import space.smarquardt.aws.manager.stsinterface.Sts;

public class StsImpl implements Sts {

  private final StsClientInstance instance;

  public StsImpl() {
    this.instance = StsClientInstance.instance();
  }

  @Override
  public AwsCredentialsProvider getCurrentCredentials() {
    return instance.awsCredentialsProvider();
  }

  @Override
  public List<Profile> getCurrentProfiles() {
    return getProfiles();
  }

  @Override
  public void changeCurrentProfile(Profile profile) {
    this.instance.setProfile(profile);
  }

  @Override
  public Profile getCurrentProfile() {
    return null;
  }

  @Override
  public void reauthenticate() {}

  @Override
  public List<String> getMfaDevices() {
    return ProfileFile.defaultProfileFile().profiles().values().stream()
        .map(profile -> profile.property("mfa_serial"))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

  @Override
  public void assumeRole(Profile profile) {
    this.instance.assumeRole();
  }

  @Override
  public void connect(String tokenCode, String mfaDeviceArn) {
    this.instance.connect(tokenCode, mfaDeviceArn);
  }

  public List<Profile> getProfiles() {
    ProfileFile profileFile = ProfileFile.defaultProfileFile();
    return profileFile.profiles().values().stream()
        .filter(profile -> profile.property("mfa_serial").isPresent())
        .map(
            profile ->
                new Profile(
                    profile.property("mfa_serial").orElse(null),
                    profile.property("role_arn").orElse(null),
                    profile.name()))
        .collect(Collectors.toList());
  }
}
