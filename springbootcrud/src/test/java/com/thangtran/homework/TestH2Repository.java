package com.thangtran.homework;

import com.thangtran.homework.model.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<ContactInformation, Long> {

}
