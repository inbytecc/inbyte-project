package com.inbyte.component.admin.marketing.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.model.ShortLinkParam;
import com.inbyte.component.admin.marketing.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信短链
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
@RestController
@RequestMapping("wx/mp")
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    @GetMapping("short-link")
    public R<String> getShortLink(@ModelAttribute ShortLinkParam param) {
        return shortLinkService.getShortLink(param);
    }

}
