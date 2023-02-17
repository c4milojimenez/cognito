package co.com.example.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@Builder(toBuilder = true)
public class AWSCognitoConfig {
    @Builder.Default private final String accessKey = "";
    @Builder.Default private final String secretKey = "";
    @Builder.Default private Regions region = Regions.US_EAST_1;
    @Builder.Default private AuthFlowType authFlowType = AuthFlowType.USER_PASSWORD_AUTH;
}

