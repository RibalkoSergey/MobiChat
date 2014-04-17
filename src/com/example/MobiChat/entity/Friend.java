package com.example.MobiChat.entity;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 3/4/14
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Friend {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean onLine;
    private byte[] foto;
    private Long countNotReadMessages;

    public Friend(Long id, String firstName, String lastName, String email, boolean onLine, byte[] foto, Long countNotReadMessages) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.onLine = onLine;
        this.foto = foto;
        this.countNotReadMessages = countNotReadMessages;
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

    public Long getCountNotReadMessages() {
        return countNotReadMessages;
    }

    public void setCountNotReadMessages(Long countNotReadMessages) {
        this.countNotReadMessages = countNotReadMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friend friend = (Friend) o;

        if (!id.equals(friend.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
