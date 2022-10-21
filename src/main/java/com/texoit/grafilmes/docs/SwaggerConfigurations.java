package com.texoit.grafilmes.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Carlos Lima on 21/10/2022
 */
@Configuration
public class SwaggerConfigurations {

    @Value("${info.app.version}")
    private String appVersion;

    @Bean
    public OpenAPI customCofiguration() {
        return new OpenAPI()
            .info(new Info().title("Golden Raspberry Awards API")
                .description("API para obter os produtores premiados.")
                .version(appVersion)
                .license(new License()
                    .name("")
                    .url("http://unlicense.org")));
    }
}
