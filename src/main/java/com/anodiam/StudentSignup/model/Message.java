package com.anodiam.StudentSignup.model;

import com.anodiam.StudentSignup.model.common.MessageResponse;
import javax.persistence.*;

@Entity
@Table(name = "mst_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
    private Integer messageId;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "language_id", referencedColumnName = "language_id")
    private Language language;

    @Column(name="message_code")
    private String messageCode;

    @Column(name="message_desc")
    private String messageDesc;

    @Transient
    private MessageResponse messageResponse;

    public Message(Integer messageId,Language language,String messageCode,String messageDesc) {
        this.messageId = messageId;
        this.language = language;
        this.messageCode = messageCode;
        this.messageDesc=messageDesc;
    }

    protected Message(){}

    public MessageResponse getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageDesc() {
        return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc = messageDesc;
    }
}