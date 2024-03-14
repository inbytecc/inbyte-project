package com.inbyte.component.common.email;

import com.inbyte.commons.model.dto.R;

public interface MailService {
    R sendSimpleMail(String to, String subject, String content);
}
