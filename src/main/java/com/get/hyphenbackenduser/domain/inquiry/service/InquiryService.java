package com.get.hyphenbackenduser.domain.inquiry.service;

import com.get.hyphenbackenduser.domain.inquiry.domain.Inquiry;
import com.get.hyphenbackenduser.domain.inquiry.domain.repository.InquiryRepository;
import com.get.hyphenbackenduser.domain.inquiry.exception.InquiryNotFoundException;
import com.get.hyphenbackenduser.domain.inquiry.presentation.dto.request.MakeInquiryRequest;
import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.global.annotation.ServiceWithTransactionalReadOnly;
import com.get.hyphenbackenduser.global.lib.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final SecurityService securityService;

    @Transactional(rollbackFor = Exception.class)
    public void makeInquiry(MakeInquiryRequest request) {
        User user = securityService.getAuthUserInfo();
        Inquiry inquiry = Inquiry.builder()
                .writer(user.getName())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getInquirys() {
        User user = securityService.getAuthUserInfo();
        return inquiryRepository.findAllByWriter(user.getName()).orElseThrow(() -> InquiryNotFoundException.EXCEPTION);
    }

    public Inquiry getInquiry(String id) {
        return inquiryRepository.findById(Long.parseLong(id)).orElseThrow(() -> InquiryNotFoundException.EXCEPTION);
    }
}

