package com.example.omorenomalaver.myloveaffair.Helpers;

/**
 * Created by omorenomalaver on 24/05/2016.
 */
public class Configuration {
    int _id;
    String _background;

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    String _password;

    public Configuration(){

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_background() {
        return _background;
    }

    public void set_background(String _background) {
        this._background = _background;
    }


}
