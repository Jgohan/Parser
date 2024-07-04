package com.netcracker.parser.repositories;

import com.netcracker.parser.entities.Template;
import org.springframework.data.repository.CrudRepository;

public interface TemplateRepository extends CrudRepository<Template, Long> {

    Boolean existsByTemplateString(String templateString);
}
