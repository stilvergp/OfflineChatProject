package com.github.stilvergp.model.manager;

import com.github.stilvergp.model.entity.User;
import com.github.stilvergp.utils.XMLManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final String DIRECTORY_PATH = "users/";

    public static void add(User user) {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String filename = DIRECTORY_PATH + user.getUsername() + ".xml";
        if (new File(filename).exists()) {
            System.out.println("El usuario ya existe.");
        } else {
            XMLManager.writeXML(user, filename);
        }

    }

    public static User findById(String username) {
        User user = null;
        File file = new File(DIRECTORY_PATH + username + ".xml");
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                user = XMLManager.readXML(new User(), fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public static List<User> findAll() {
        List<User> users = new ArrayList<>();
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    users.add(XMLManager.readXML(new User(), fis));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }
}
