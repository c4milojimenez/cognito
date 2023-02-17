package co.com.example.common.enums;

import lombok.Getter;

@Getter
public enum ChallengeResposesType {

    SMS_MFA_CODE("SMS_MFA_CODE"),
    USERNAME("USERNAME"),
    PASSWORD_CLAIM_SIGNATURE("PASSWORD_CLAIM_SIGNATURE"),
    PASSWORD_CLAIM_SECRET_BLOCK("PASSWORD_CLAIM_SECRET_BLOCK"),
    TIMESTAMP("TIMESTAMP"),
    NEW_PASSWORD("NEW_PASSWORD"),
    SECRET_HASH("SECRET_HASH"),
    SOFTWARE_TOKEN_MFA_CODE("SOFTWARE_TOKEN_MFA_CODE"),
    DEVICE_KEY("DEVICE_KEY"),
    SRP_A("SRP_A"),
    PASSWORD_VERIFIER("PASSWORD_VERIFIER");
    private final String value;

    ChallengeResposesType(String value){ this.value = value; }
}
