package com.github.stilvergp.model.manager;

import com.github.stilvergp.model.entity.Conversation;
import com.github.stilvergp.model.entity.Message;
import com.github.stilvergp.model.entity.User;
import com.github.stilvergp.utils.XMLManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationManager {
    private static final String DIRECTORY_PATH = "conversations/";

    public static void saveConversation(Conversation conversation) {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File user1Directory = new File(DIRECTORY_PATH + "/" + conversation.getUser1());
        if (!user1Directory.exists()) {
            user1Directory.mkdirs();
        }
        File user2Directory = new File(DIRECTORY_PATH + "/" + conversation.getUser2());
        if (!user2Directory.exists()) {
            user2Directory.mkdirs();
        }
        String user1FilePath = user1Directory + "/conversation-with-" + conversation.getUser2() + ".xml";
        String user2FilePath = user2Directory + "/conversation-with-" + conversation.getUser1() + ".xml";
        XMLManager.writeXML(conversation, user1FilePath);
        XMLManager.writeXML(conversation, user2FilePath);
    }

    public static List<Conversation> loadConversationsForUser(User user) {
        List<Conversation> conversations = new ArrayList<>();
        String userFolderPath = DIRECTORY_PATH + user.getUsername();
        File userFolder = new File(userFolderPath);
        if (userFolder.exists() && userFolder.isDirectory()) {
            File[] conversationFiles = userFolder.listFiles((_, name) -> name.startsWith("conversation-with-") && name.endsWith(".xml"));
            if (conversationFiles != null) {
                for (File file : conversationFiles) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        Conversation conversation = XMLManager.readXML(new Conversation(), fis);
                        if (conversation != null) {
                            conversations.add(conversation);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return conversations;
    }

    public static int countConversationMessages(Conversation conversation) {
        int count = 0;
        if (conversation != null) {
            count = conversation.getMessages().size();
        }
        return count;
    }

    public static String mostRepeatedWordInConversation(Conversation conversation) {
        String mostRepeatedWord = "";
        int maxCount = 0;
        if (conversation != null) {
            for (Message message : conversation.getMessages()) {
                String[] words = message.getText().split(" ");
                Map<String, Integer> wordCountMap = new HashMap<>();
                for (String word : words) {
                    word = word.toLowerCase();
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                    if (wordCountMap.get(word) > maxCount) {
                        maxCount = wordCountMap.get(word);
                        mostRepeatedWord = word;
                    }
                }
            }
        }
        return mostRepeatedWord;
    }

    public static int numberOfMessagesFromUser(String username, Conversation conversation) {
        int count = 0;
        if (conversation != null) {
            for (Message message : conversation.getMessages()) {
                if (message.getSender().equals(username)) {
                    count++;
                }
            }
        }
        return count;
    }

}