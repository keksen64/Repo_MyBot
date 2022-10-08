package ru.taksebe.telegram.mentalCalculation.telegram.commands.service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class SendTLG {
    public static void sendToTelegram() {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        //Add Telegram token (given Token is fake)
        String apiToken = "*******";

        //Add chatId (given chatId is fake)
        String chatId = "363125585";
        String text = "Hello_world!";

        urlString = String.format(urlString, apiToken, chatId, text);

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());

            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////
            //Additoinal Code is HERE
            ///////////////////////////////////////////////////////////////////////////////
            //getting text, we can set it to any TextView
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            //You can set this String to any TextView
            String response = sb.toString();
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
