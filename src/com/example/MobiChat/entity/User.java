package com.example.MobiChat.entity;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 3/30/14
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class User {
    public Long id;
    public String firstName;
    public String lastName;
    private String login;
    private String password;
    public String email;
    public boolean onLine;
    public byte[] foto;

    public User() {}

    public User(Long id, String firstName, String lastName, String email, boolean onLine, byte[] foto) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.onLine = onLine;
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
