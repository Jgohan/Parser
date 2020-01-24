package com.netcracker.parser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

import static com.netcracker.parser.services.Constants.ATTRIBUTE;

@Entity
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String templateName;
    private String templateString;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private List<Message> messages;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private List<AttributeName> attributesNames;

    public int countAttributes() {
        return templateString.split(ATTRIBUTE, -1).length - 1;
    }


    public Template() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateString() {
        return templateString;
    }

    public void setTemplateString(String templateString) {
        this.templateString = templateString;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<AttributeName> getAttributesNames() {
        return attributesNames;
    }

    public void setAttributesNames(List<AttributeName> attributesNames) {
        this.attributesNames = attributesNames;
    }
}
