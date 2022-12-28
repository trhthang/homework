package com.thangtran.homework.repositories;

import com.thangtran.homework.model.ContactInformation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactInformation, Long> {
    @Query("SELECT c FROM ContactInformation c WHERE c.name like ?1% or c.name like %?2")
    List<ContactInformation> search(String firstName, String lastName);
}
