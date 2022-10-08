package ru.taksebe.telegram.mentalCalculation.telegram.commands.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SendMessageTLG {
    public static void sendToTelegram(String text) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        //Add Telegram token (given Token is fake)
        String apiToken = "****";

        //Add chatId (given chatId is fake)
        String chatId = "201661507";
        //String text = "Hello_world!";
DBConn.logOut(text + " || " + chatId);
        urlString = String.format(urlString, apiToken, chatId, text);

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
