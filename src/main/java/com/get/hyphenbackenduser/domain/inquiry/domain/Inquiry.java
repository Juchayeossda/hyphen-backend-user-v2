package com.get.hyphenbackenduser.domain.inquiry.domain;

import com.get.hyphenbackenduser.domain.inquiry.enums.InquiryStatus;
import com.get.hyphenbackenduser.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Inquiry extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private InquiryStatus status;

    @Column(nullable = true)
    private String answer;

    @PrePersist
    public void prePersist() {
        this.status = this.answer == null ? InquiryStatus.NO_ANSWER : InquiryStatus.ANSWERS_PRESENT;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Builder
    public Inquiry(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
