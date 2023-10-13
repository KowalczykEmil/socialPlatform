package com.example.socialplatform.repository;

import com.example.socialplatform.schema.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String tagName);

    @Override
    long count();

    @NotNull
    @Override
    List<Tag> findAll();


}
