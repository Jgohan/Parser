package com.netcracker.parser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "messages")
@Setter
@Getter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private List<Attribute> attributes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private ZonedDateTime creationTime;


    public Message(Template template, User author, ZonedDateTime creationTime) {
        this.template = template;
        this.author = author;
        this.creationTime = creationTime;
    }
}
