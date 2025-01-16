package com.example.demo.repository;

import com.example.demo.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {

    Optional<Contents> findByLocation(String location);

}
