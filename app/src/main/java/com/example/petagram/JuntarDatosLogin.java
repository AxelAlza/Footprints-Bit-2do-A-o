package com.example.petagram;


public class JuntarDatosLogin {

    String inputEmail;
    String inputPassword;

    public JuntarDatosLogin(String inputEmail, String inputPassword){
        this.inputEmail = inputEmail;
        this.inputPassword = inputPassword;
    }

    public String getInputEmail() {
        return inputEmail;
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }

}

