package com.netcracker.parser.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String templateString;
    private String templateName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private List<Message> messages;

    public int countAttributes() {
        return templateString.split("_att_", -1).length-1;
    }

    public String[] splitTemplateStringByAttributes() {
        String[] substrings = templateString.split("_att_");
        String[] extendedSubstrings;
        if(templateString.endsWith("_att_")) {
            extendedSubstrings = new String[substrings.length + 1];

            System.arraycopy(substrings, 0, extendedSubstrings, 0, substrings.length);
            extendedSubstrings[extendedSubstrings.length - 1] = "";
            substrings = extendedSubstrings;
        }
        return substrings;
    }


    public Template() {

    }

    public Template(String templateString) {
        this.templateString = templateString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateString() {
        return templateString;
    }

    public void setTemplateString(String templateString) {
        this.templateString = templateString;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
