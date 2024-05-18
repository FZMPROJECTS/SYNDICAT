package com.example.syndicat;

public class User {
    private String username;
    private String email;
    private String tel;
    private String pwd;
    private int homeNumber;

    public User(String username, String email, String tel, String pwd) {
        this.username = username;
        this.email = email;
        this.tel = tel;
        this.pwd = pwd;

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

    public int getHomeNumber() {
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

    public void setHomeNumber(int homeNumber) {
        this.homeNumber = homeNumber;
    }
}
