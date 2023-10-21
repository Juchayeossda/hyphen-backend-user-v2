package com.get.hyphenbackenduser.domain.inquiry.presentation;

import com.get.hyphenbackenduser.domain.inquiry.domain.Inquiry;
import com.get.hyphenbackenduser.domain.inquiry.presentation.dto.request.MakeInquiryRequest;
import com.get.hyphenbackenduser.domain.inquiry.service.InquiryService;
import com.get.hyphenbackenduser.global.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.get.hyphenbackenduser.global.statics.response.ResponseMessageConstants.SUCCESSFUL_CREATED;
import static com.get.hyphenbackenduser.global.statics.response.ResponseMessageConstants.SUCCESSFUL_OK;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<Object> makeInquiry(@Valid @RequestBody MakeInquiryRequest request) {
        inquiryService.makeInquiry(request);
        return ResponseData.of(HttpStatus.CREATED.value(), SUCCESSFUL_CREATED);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<Inquiry>> getInquirys() {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, inquiryService.getInquirys());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Inquiry> getInquiry(@PathVariable("id") String id) {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, inquiryService.getInquiry(id));
    }
}
