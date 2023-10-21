package com.get.hyphenbackenduser.domain.inquiry.domain.repository;

import com.get.hyphenbackenduser.domain.inquiry.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    Optional<List<Inquiry>> findAllByWriter(String writer);
}
