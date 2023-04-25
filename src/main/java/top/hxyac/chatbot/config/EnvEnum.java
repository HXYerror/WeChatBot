package top.hxyac.chatbot.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hxy
 */

public enum EnvEnum {
    DEV("dev"),PROD("prod");

    private String env;

    private EnvEnum(String env) {
        this.env = env;
    }

    public String getEnv() {
        return env;
    }
}
