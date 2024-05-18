package com.example.syndicat;

public class User {
    private String username;
    private String email;
    private String tel;
    private String pwd;
    private String homeNumber;


    public User(String email, String username, String tel, String pwd, String homeNumber) {
        this.username = username;
        this.email = email;
        this.tel = tel;
        this.pwd = pwd;
        this.homeNumber = homeNumber;
    }

    public User(String username, String tel, String homeNumber) {
        this.username = username;
        this.homeNumber = homeNumber;
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getPwd() {
        return pwd;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }
}
