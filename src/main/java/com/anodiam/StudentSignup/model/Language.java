package com.anodiam.StudentSignup.model;

import com.anodiam.StudentSignup.model.common.MessageResponse;
import javax.persistence.*;

@Entity
@Table(name = "mst_language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer language_id;

    private String language_code;

    private String language_desc;

    @Transient
    private MessageResponse messageResponse;

    public Language(Integer language_id,String language_code,String language_desc) {
        this.language_id = language_id;
        this.language_code = language_code;
        this.language_desc=language_desc;
    }

    protected Language(){}

    public MessageResponse getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public Integer getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Integer language_id) {
        this.language_id = language_id;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getLanguage_desc() {
        return language_desc;
    }

    public void setLanguage_desc(String language_desc) {
        this.language_desc = language_desc;
    }
}