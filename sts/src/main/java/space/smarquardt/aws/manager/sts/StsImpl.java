package space.smarquardt.aws.manager.sts;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.profiles.ProfileFile;
import space.smarquardt.aws.manager.sts.internal.StsClientInstance;
import space.smarquardt.aws.manager.stsinterface.Profile;
import space.smarquardt.aws.manager.stsinterface.Sts;

import java.util.List;
import java.util.stream.Collectors;

public class StsImpl implements Sts {

    private StsClientInstance instance;

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
    public void reauthenticate() {

    }

    @Override
    public void connect(String tokenCode) {
        this.instance.connect(tokenCode);
    }



    public List<Profile> getProfiles() {
        ProfileFile profileFile = ProfileFile.defaultProfileFile();
        return profileFile.profiles().values().stream().filter(profile -> profile.property("mfa_serial").isPresent()).map(profile -> new Profile(profile.property("mfa_serial").orElseThrow(() -> new RuntimeException("No mfa_serial found")), profile.property("role_arn").orElseThrow(() -> new RuntimeException("no Role arn")), profile.name())).collect(Collectors.toList());
    }


}
