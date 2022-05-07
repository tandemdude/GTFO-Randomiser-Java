package io.github.tandemdude.gtforandomiser.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("randomiser")
@Data
public class RundownConfig {
    private int currentRundownId;
    private Map<Integer, Map<String, List<String>>> weapons;
    private Map<Integer, Map<String, List<String>>> stages;
    private List<String> handicaps;
}
