package space.smarquardt.aws.manager.sts.internal;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsGetSessionTokenCredentialsProvider;
import software.amazon.awssdk.services.sts.model.GetSessionTokenRequest;
import space.smarquardt.aws.manager.stsinterface.Profile;
import space.smarquardt.aws.manager.stsinterface.Sts;

public enum StsClientInstance {

    STS_CLIENT_INSTANCE;
    private StsClient stsAsyncClient;
    private StsClientInstance instance;
    private AwsCredentialsProvider awsCredentialsProvider;
    private SdkHttpClient sdkHttpClient;

    private Profile currentProfile;



    public SdkHttpClient getSdkHttpClient(){
        return this.sdkHttpClient;
    }



    public void setProfile(Profile profile){
        System.out.println("Changed profile to "+profile);
        this.currentProfile=profile;
    }

    public AwsCredentialsProvider awsCredentialsProvider(){
        return this.awsCredentialsProvider;
    }

    public static StsClientInstance instance(){
        if(STS_CLIENT_INSTANCE.instance!=null){
            return STS_CLIENT_INSTANCE;
        }
        return STS_CLIENT_INSTANCE;
    }

    public void connect(String tokencode){
        SdkHttpClient httpClient = ApacheHttpClient.builder().build();
        StsClient stsClient =StsClient.builder().httpClient(httpClient).build();
        System.out.println("One time passcode is "+tokencode);
        StsGetSessionTokenCredentialsProvider stsGetSessionTokenCredentialsProvider = StsGetSessionTokenCredentialsProvider.builder().stsClient(stsClient).asyncCredentialUpdateEnabled(true)
                .refreshRequest(GetSessionTokenRequest.builder().serialNumber(currentProfile.mfaArn()).tokenCode(tokencode).build()).build();
        stsClient = StsClient.builder().credentialsProvider(stsGetSessionTokenCredentialsProvider).httpClient(httpClient).build();
        System.out.println("Identity is "+stsClient.getCallerIdentity());
        StsClientInstance stsClientInstance = STS_CLIENT_INSTANCE;
        stsClientInstance.stsAsyncClient=stsClient;
        stsClientInstance.instance=stsClientInstance;
        stsClientInstance.awsCredentialsProvider= stsGetSessionTokenCredentialsProvider;
        stsClientInstance.sdkHttpClient=httpClient;
    }
}