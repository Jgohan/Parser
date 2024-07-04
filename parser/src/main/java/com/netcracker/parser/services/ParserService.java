package com.netcracker.parser.services;

import com.netcracker.parser.entities.Message;
import com.netcracker.parser.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParserService {

    ResponseEntity<?> parseString(String string, User author);

    List<Message> getMessages(Long templateId);
}
