package com.laboratory.user.management;

import com.laboratory.data.UserJDBC;

public class DDBBConnection {
    public static void main(String[] args) {
        UserJDBC user = new UserJDBC();

        user.insert("Miguel Feo", "12345");
    }
     
}
