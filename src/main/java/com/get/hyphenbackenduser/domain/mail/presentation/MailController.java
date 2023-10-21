package com.get.hyphenbackenduser.domain.mail.presentation;

import com.get.hyphenbackenduser.domain.mail.presentation.dto.request.MailRequest;
import com.get.hyphenbackenduser.domain.mail.service.MailService;
import com.get.hyphenbackenduser.global.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.get.hyphenbackenduser.global.statics.response.ResponseMessageConstants.SUCCESSFUL_OK;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> mailSend(@Valid @RequestBody MailRequest request) {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, mailService.sendMail(request.getMail()));
    }
}
