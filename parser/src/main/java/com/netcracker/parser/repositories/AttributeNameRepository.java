package com.netcracker.parser.repositories;

import com.netcracker.parser.entities.AttributeName;
import com.netcracker.parser.entities.Template;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AttributeNameRepository extends CrudRepository<AttributeName, Long> {
    Optional<AttributeName> findByTemplate(Template template);
}
