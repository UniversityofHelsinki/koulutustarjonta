package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class SundialConfiguration {
    @JsonProperty("thread-pool-size")
    private String threadPoolSize;

    @JsonProperty("shutdown-on-unload")
    private String performShutdown;

    @JsonProperty("wait-on-shutdown")
    private String waitOnShutdown;

    @JsonProperty("start-delay-seconds")
    private String startDelay;

    @JsonProperty("start-scheduler-on-load")
    private String startOnLoad;

    @JsonProperty("global-lock-on-load")
    private String globalLockOnLoad;

}
