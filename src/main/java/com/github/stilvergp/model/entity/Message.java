package com.github.stilvergp.model.entity;

import com.github.stilvergp.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@XmlType(propOrder = { "id", "sender", "receiver", "text", "date"})
@XmlRootElement
public class Message {
    private String id;
    private String sender;
    private String receiver;
    private String text;
    private LocalDateTime date;

    public Message() {}

    public Message(String sender, String receiver, String text) {
        this.id = generateId();
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = LocalDateTime.now();
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @XmlElement
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @XmlElement
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement
    public LocalDateTime getDate(){return date;}

    public void setDate(LocalDateTime date){this.date = date;}

    private String generateId() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-";
        StringBuilder id = new StringBuilder(5);
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(caracteres.length());
            id.append(caracteres.charAt(index));
        }

        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
