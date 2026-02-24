
package com.ensark.nexusbanking.util;

import java.io.File;

public class DBUtils {
    public static void ensureDBFolder() {
        File dbFolder = new File("/storage/emulated/0/AideProjects/NexusBanking/database");
        if (!dbFolder.exists()) {
            boolean created = dbFolder.mkdirs();
            if (created) {
                System.out.println("Database folder created!");
            } else {
                System.out.println("Failed to create database folder!");
            }
        }
    }
}
