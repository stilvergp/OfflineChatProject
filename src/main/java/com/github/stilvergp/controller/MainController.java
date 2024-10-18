package com.github.stilvergp.controller;

import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.entity.Conversation;
import com.github.stilvergp.model.manager.ConversationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    private ObservableList<Conversation> conversations;

    @Override
    public void onOpen(Object input) throws IOException {

    }

    public void reloadConversationsFromXml() {
//        List<Conversation> conversations;
//        conversations = ConversationManager.findConversationsByUser(Session.getInstance().getLoggedInUser());
//        this.conversations = FXCollections.observableArrayList(conversations);
//        lo que sea aqui
//        tableView.setItems(this.rooms);
    }
    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
