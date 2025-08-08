package org.example.vo;

public class UserVO {
    private String username;
    private String password;

    public void setUsername(String name){
        this.username =name;
    }
    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
