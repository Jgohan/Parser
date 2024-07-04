package com.netcracker.parser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "attributes")
@Getter
@Setter
@NoArgsConstructor
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;
    private Integer position;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private Message message;


    public Attribute(Integer position, Message message) {
        this.position = position;
        this.message = message;
    }
}
