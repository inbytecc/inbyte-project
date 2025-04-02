package com.inbyte.component.common.ai.customer.service.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.ai.customer.service.model.ChatParam;
import com.inbyte.component.common.ai.customer.service.service.AiCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI客服接口
 */
@RestController
@RequestMapping("ai/customer/service")
public class AiCustomerServiceController {

    @Autowired
    private AiCustomerService aiCustomerService;

    /**
     * AI智能问答
     */
    @GetMapping("chat")
    public R<String> chat(@ModelAttribute ChatParam param) {
        if ("bailuguapikey".equals(param.getApiKey())) {
            param.setMctNo("junyou");
            param.setVenueId("4");
            return aiCustomerService.chatOnWechat(param);
        }
        return R.failure("apiKey错误");
    }

}