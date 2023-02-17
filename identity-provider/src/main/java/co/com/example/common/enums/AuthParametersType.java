package co.com.example.common.enums;

import lombok.Getter;

@Getter
public enum AuthParametersType {

    USERNAME("USERNAME"),
    SRP_A("SRP_A"),
    SECRET_HASH("SECRET_HASH"),
    DEVICE_KEY("DEVICE_KEY"),
    REFRESH_TOKEN("REFRESH_TOKEN"),
    PASSWORD("PASSWORD");

    private final String name;

    AuthParametersType(String name) { this.name = name; }
}