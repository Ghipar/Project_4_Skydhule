package com.example.project_4;

public class API {
    String host = "http://192.168.1.17:8000";
    String APIlogin = host + "/api/login";
    String APIReg = host + "/api/register";
    String APIVerEml =  host + "/api/forgotPass";
    String APIfPass = host + "/api/users/updatepass";
    String APIuser = host + "/api/user";
    String APIupdate = host + "/api/users/update";
    String APIcuaca = "https://api.openweathermap.org/data/2.5/weather?";
    String APIforecast= "https://api.openweathermap.org/data/2.5/forecast?";
    String APIaddskejul= host + "/api/schejul/";
    String APIgetskejul= host + "/api/getschejul";
    String APIgetberita= host + "/api/berita";
    String APIlogout= host + "/api/logout";
}
