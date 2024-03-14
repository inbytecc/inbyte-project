package com.inbyte.component.app.user;

import com.inbyte.component.app.user.model.EmailForgetPwdProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户配置
 */
@Component
@ConfigurationProperties(prefix = "inbyte.component.user")
public class ComponentUserProperties {

    private List<String> avatars;

    private EmailForgetPwdProperties email;

    public List<String> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<String> avatars) {
        this.avatars = avatars;
    }

    public EmailForgetPwdProperties getEmail() {
        return email;
    }

    public void setEmail(EmailForgetPwdProperties email) {
        this.email = email;
    }
}