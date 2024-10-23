package com.github.stilvergp.controller;

import com.github.stilvergp.App;
import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.entity.User;
import com.github.stilvergp.model.manager.UserManager;
import com.github.stilvergp.utils.Alerts;
import com.github.stilvergp.utils.Security;
import com.github.stilvergp.view.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    /**
     * Logs in the user using the session instance.
     *
     * @param user the user to be logged in.
     */
    public void login(User user) {
        Session.getInstance().login(user);
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    /**
     * Navigates to the main scene if the user credentials are valid.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void goToMain() throws IOException {
        User user = UserManager.findById(username.getText());
        if (user != null && user.isMyPassword(Security.hashPassword(password.getText()))) {
            login(user);
            App.currentController.changeScene(Scenes.MAIN, null);
        } else {
            Alerts.showErrorAlert("Error de inicio de sesión",
                    "Usuario o contraseña incorrectos, " +
                            "por favor intente nuevamente");
        }
    }

    /**
     * Opens the sign-in form to add a new user.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void signIn() throws IOException {
        username.clear();
        password.clear();
        App.currentController.openModal(Scenes.FORMSIGNIN, "Agregando usuario...", this, null);
    }

    /**
     * Saves a new user and adds it to the database.
     *
     * @param user the user to be saved.
     */
    public void saveUser(User user) {
        UserManager.add(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
