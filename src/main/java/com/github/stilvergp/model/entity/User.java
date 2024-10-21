package com.github.stilvergp.model.entity;

import com.github.stilvergp.utils.Security;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;
import java.util.Random;

@XmlType(propOrder = { "id", "name", "username", "password", "email" })
@XmlRootElement
public class User {
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;

    public User() {}

    public User(String name, String username, String password, String email) {
        this.id = generateId();
        this.name = name;
        this.username = username;
        setPassword(password);
        this.email = email;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Security.hashPassword(password);
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMyPassword(String password) {
        return this.password.equals(Security.hashPassword(password));
    }

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
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
