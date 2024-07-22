package com.inbyte.component.common.email;

public interface MailService {
    void sendSimpleMail(String to, String subject, String content);
}
