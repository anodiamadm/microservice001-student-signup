package com.anodiam.StudentSignup.model;

import com.anodiam.StudentSignup.model.common.MessageResponse;
import javax.persistence.*;

@Entity
@Table(name = "mst_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer message_id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "language_id", referencedColumnName = "language_id")
    private Language language;

    private String message_code;

    private String message_desc;

    @Transient
    private MessageResponse messageResponse;

    public Message(Integer message_id,Language language,String message_code,String message_desc) {
        this.message_id = message_id;
        this.language = language;
        this.message_code = message_code;
        this.message_desc=message_desc;
    }

    protected Message(){}

    public MessageResponse getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getMessage_code() {
        return message_code;
    }

    public void setMessage_code(String message_code) {
        this.message_code = message_code;
    }

    public String getMessage_desc() {
        return message_desc;
    }

    public void setMessage_desc(String message_desc) {
        this.message_desc = message_desc;
    }
}