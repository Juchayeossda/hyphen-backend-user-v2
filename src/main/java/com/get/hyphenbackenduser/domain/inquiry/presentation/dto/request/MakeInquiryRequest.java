package com.get.hyphenbackenduser.domain.inquiry.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.INQUIRY_CONTENT_NOT_BLANK;
import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.INQUIRY_TITLE_NOT_BLANK;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MakeInquiryRequest {

    @NotBlank(message = INQUIRY_TITLE_NOT_BLANK)
    private String title;

    @NotBlank(message = INQUIRY_CONTENT_NOT_BLANK)
    private String content;
}
