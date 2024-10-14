package com.elyashevich.jdbc;

import com.elyashevich.jdbc.db.Util;


public class Application {

    public static void main(String[] args) {
        Util util = new Util();
        util.getConnection();
    }
}
