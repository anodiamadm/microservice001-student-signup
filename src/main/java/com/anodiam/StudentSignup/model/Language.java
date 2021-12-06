package com.anodiam.StudentSignup.model;

import com.anodiam.StudentSignup.model.common.MessageResponse;
import javax.persistence.*;

@Entity
@Table(name = "mst_language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="language_id")
    private Integer languageId;

    @Column(name="language_code")
    private String languageCode;

    @Column(name="language_desc")
    private String languageDesc;

    @Transient
    private MessageResponse messageResponse;

    public Language(Integer languageId,String languageCode,String languageDesc) {
        this.languageId = languageId;
        this.languageCode = languageCode;
        this.languageDesc=languageDesc;
    }

    protected Language(){}

    public MessageResponse getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageDesc() {
        return languageDesc;
    }

    public void setLanguageDesc(String languageDesc) {
        this.languageDesc = languageDesc;
    }
}
