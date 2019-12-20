package com.netcracker.parser.repositories;

import com.netcracker.parser.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
