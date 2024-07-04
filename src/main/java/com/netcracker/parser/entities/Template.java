package com.netcracker.parser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.netcracker.parser.services.Constants.ATTRIBUTE;

@Entity
@Table(name = "templates")
@Setter
@Getter
@NoArgsConstructor
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
}
