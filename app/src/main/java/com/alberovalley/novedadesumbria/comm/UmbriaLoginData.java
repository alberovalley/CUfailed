package com.alberovalley.novedadesumbria.comm;

/**
 * Created by frank on 10/02/17.
 */

/**
 * POJO used to transfer the user's login data
 *
 * @author frank
 */
public class UmbriaLoginData {
    // ////////////////////////////////////////////////////////////
    // Constants
    // ////////////////////////////////////////////////////////////
    public static final String userNameTAG = "ACCESO";
    public static final String passwordTAG = "CLAVE";
    // ////////////////////////////////////////////////////////////
    // Attributes
    // ////////////////////////////////////////////////////////////
    public String userName = "";
    public String password = "";

    // ////////////////////////////////////////////////////////////
    // Constructor
    // ////////////////////////////////////////////////////////////
    public UmbriaLoginData(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}