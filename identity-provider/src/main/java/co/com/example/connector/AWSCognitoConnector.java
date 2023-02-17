package co.com.example.connector;

import co.com.example.common.enums.AuthParametersType;
import co.com.example.common.enums.ChallengeResposesType;
import co.com.example.common.utils.GsonUtils;
import co.com.example.config.AWSCognitoConfig;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Builder
public class AWSCognitoConnector {
    private final AWSCognitoConfig config;
    private final AWSCognitoIdentityProvider client;

    public AWSCognitoConnector(AWSCognitoConfig config){
        this.config = config;
        this.client = buildClient(AWSCognitoIdentityProviderClientBuilder.standard());
    }

    public <T> T getSession(String clientId, String username, String password, Class<T> responseClass){
        InitiateAuthResult result = client.initiateAuth(getAuthRequest(clientId, username, password));

        return GsonUtils.getInstance().objectToModel(result, responseClass);
    }

    public <T> T validateSession(String challengeName, String clientId, String session, String username, String password, Class<T> responseClass){
        RespondToAuthChallengeResult result;

        result =  client.respondToAuthChallenge(getAuthChallengeRequest(challengeName, clientId, session, username, password));

        return GsonUtils.getInstance().objectToModel(result, responseClass);

    }

    private InitiateAuthRequest getAuthRequest(String clientId, String username, String password){
        InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest();
        initiateAuthRequest.setAuthFlow(config.getAuthFlowType());
        initiateAuthRequest.setClientId(clientId);
        initiateAuthRequest.addAuthParametersEntry(AuthParametersType.USERNAME.getName(), username);
        initiateAuthRequest.addAuthParametersEntry(AuthParametersType.PASSWORD.getName(), password);

        return initiateAuthRequest;
    }

    private RespondToAuthChallengeRequest getAuthChallengeRequest(String challengeName, String clientId, String session, String username, String newPassword){
        RespondToAuthChallengeRequest authChallengeRequest = new RespondToAuthChallengeRequest();
        authChallengeRequest.setChallengeName(challengeName);
        authChallengeRequest.setClientId(clientId);
        authChallengeRequest.setSession(session);

        if(Objects.equals(challengeName, ChallengeNameType.NEW_PASSWORD_REQUIRED.toString())) {
            authChallengeRequest.setChallengeResponses(getChallengeResponses(username, newPassword));
        }

        return authChallengeRequest;
    }

    private Map<String, String> getChallengeResponses(String username, String password){
        Map<String, String> challengeResponses = new LinkedHashMap<>();
        challengeResponses.put(ChallengeResposesType.USERNAME.getValue(), username);
        challengeResponses.put(ChallengeResposesType.NEW_PASSWORD.getValue(), password);

        return challengeResponses;
    }
    private AWSCredentialsProvider getProviderCredentials(){
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey()));
    }
    private AWSCognitoIdentityProvider buildClient(AWSCognitoIdentityProviderClientBuilder builder){
        builder.withCredentials(getProviderCredentials())
                .withRegion(config.getRegion());
        return builder.build();
    }

}
