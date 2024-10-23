package com.github.stilvergp.controller;

import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.entity.Conversation;
import com.github.stilvergp.model.entity.Message;
import com.github.stilvergp.model.manager.ConversationManager;
import com.github.stilvergp.utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExportConversationController extends Controller implements Initializable {

    private ObservableList<Conversation> conversations;

    @FXML
    private ListView<Conversation> conversationList;

    @Override
    public void onOpen(Object input) throws IOException {
    }

    @Override
    public void onClose(Object output) {

    }

    public void exportToTxt(Event event) {
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        Conversation conversation = conversationList.getSelectionModel().getSelectedItem();
        if (conversation != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar a...");
            String loggedInUsername = Session.getInstance().getLoggedInUser().getUsername();
            String otherUser = loggedInUsername.equals(conversation.getUser1()) ? conversation.getUser2() : conversation.getUser1();
            fileChooser.setInitialFileName("conversation-with-" + otherUser + ".txt");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("archivos TXT", "*.txt"));
            File file = fileChooser.showSaveDialog(window);
            if (file != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("Conversación con " + otherUser + "\n");
                    writer.write("ID: " + conversation.getId() + "\n");
                    writer.write("Numero total de mensajes: " + ConversationManager.countConversationMessages(conversation) + "\n");
                    writer.write("Palabra mas repetida en los mensajes: " + ConversationManager.mostRepeatedWordInConversation(conversation) + "\n");
                    writer.write("Numero total de mensajes del usuario " + conversation.getUser1() + ": " +
                            ConversationManager.numberOfMessagesFromUser(conversation.getUser1(), conversation) + "\n");
                    writer.write("Numero total de mensajes del usuario " + conversation.getUser2() + ": " +
                            ConversationManager.numberOfMessagesFromUser(conversation.getUser2(), conversation) + "\n");

                    for (Message message : conversation.getMessages()) {
                        writer.write("-> Mensaje\n");
                        writer.write("    Remitente: " + message.getSender() + "\n");
                        writer.write("    Receptor: " + message.getReceiver() + "\n");
                        writer.write("    Dia y hora del mensaje: " + message.getDate().getDayOfMonth() + " de "
                                + message.getDate().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) + " de " + message.getDate().getYear()
                                + " a las " + message.getDate().getHour() + ":" + message.getDate().getMinute() + " horas \n");
                        writer.write("    Texto del mensaje: " + message.getText() + "\n");
                    }
                    Alerts.showInformationAlert("Exportación realizada","Archivo guardado exitosamente en:" + file.getAbsolutePath());
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alerts.showErrorAlert("Error al exportar conversación", "Ruta inválida");
            }
        } else {
            Alerts.showErrorAlert("Error al exportar conversación", "Debe seleccionar primero una conversación");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Conversation> conversations = ConversationManager.loadConversationsForUser(Session.getInstance().getLoggedInUser());
        this.conversations = FXCollections.observableArrayList(conversations);
        this.conversationList.setItems(this.conversations);
        this.conversationList.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(Conversation conversation, boolean empty) {
                super.updateItem(conversation, empty);
                if (empty || conversation == null) {
                    setText(null);
                } else {
                    String otherUser = Session.getInstance().getLoggedInUser().getUsername()
                            .equals(conversation.getUser1()) ? conversation.getUser2() : conversation.getUser1();
                    setText("Chat con " + otherUser);
                }
            }
        });
    }
}
