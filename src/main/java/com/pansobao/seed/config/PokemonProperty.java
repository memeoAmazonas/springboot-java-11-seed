package com.pansobao.seed.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Data
@Component
@ConfigurationProperties(prefix = "pokemon")
public class PokemonProperty {
    private String urlBase;
    private String urlName;
    private String urlType;
    private String urlAbility;

    public String getUrl(String url, String complement) {
        return urlBase.concat(url).concat(complement);
    }

    public String getUrl(String url) {
        return urlBase.concat(url);
    }
}
