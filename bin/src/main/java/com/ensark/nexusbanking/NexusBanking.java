package com.ensark.nexusbanking;

import com.ensark.nexusbanking.service.AuthService;
import com.ensark.nexusbanking.util.DBUtils;
import java.sql.SQLException;

public class NexusBanking {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBUtils.ensureDBFolder();

        AuthService auth = new AuthService();
        auth.start();
    }
}
