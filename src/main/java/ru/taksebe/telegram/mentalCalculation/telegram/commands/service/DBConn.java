package ru.taksebe.telegram.mentalCalculation.telegram.commands.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DBConn {

    public static void setUser(String id, String login, String fName, String lName, String isBot){
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "bot", "bot");
            Statement statement = connection.createStatement();
           // System.out.println(message);
            if(login!=null){
                login = login.replace("\'","ЗАМЕНА_КАВЫЧКИ");
            }
            if(fName!=null){
                fName = fName.replace("\'","ЗАМЕНА_КАВЫЧКИ");
            }
            if(lName!=null){
                lName = lName.replace("\'","ЗАМЕНА_КАВЫЧКИ");
            }
            if(isBot!=null){
                isBot = isBot.replace("\'","ЗАМЕНА_КАВЫЧКИ");
            }

            statement.executeUpdate("call adduser("+ id +",'"+ login +"','"+ fName+"', '"+lName+"', '"+isBot+"');");
            //resp = resultSet.getString("responce");
            connection.close();


        }catch (Exception e){
           // System.out.println("Внимание! Входящее ообщение не залогировано. || " + message);
            System.out.println(e);
        }
    }


    public static void logIn(String message){
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "bot", "bot");
            Statement statement = connection.createStatement();
            System.out.println(message);
            String mes = message.replace("\'","ЗАМЕНА_КАВЫЧКИ");
            statement.executeUpdate("call logrequest('" + mes + "');");
            //resp = resultSet.getString("responce");
            connection.close();
        }catch (Exception e){
            System.out.println("Внимание! Входящее ообщение не залогировано. || " + message);
            System.out.println(e);
        }
    }
    public static void logOut(String message){
        try {
            String mes = message.replace("\'","ЗАМЕНА_КАВЫЧКИ");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "bot", "bot");
            Statement statement = connection.createStatement();
            statement.executeUpdate("call logresponce('" + mes + "');");
            //resp = resultSet.getString("responce");
            connection.close();
        }catch (Exception e){
            System.out.println("Внимание! Исходящее сообщение не залогировано. || " + message);
            System.out.println(e);
        }
    }

    public static void eventFilm(String userId,String opId, String message){
        try {
            String mes = message.replace("\'","ЗАМЕНА_КАВЫЧКИ");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "bot", "bot");
            Statement statement = connection.createStatement();
            statement.executeUpdate("call eventfilm("+ userId + ","+opId+",'"+ mes + "');");
            //resp = resultSet.getString("responce");
            connection.close();
        }catch (Exception e){
            System.out.println("Внимание! Сообщение о событии не залогировано. || " + message);
            System.out.println(e);
        }
    }


        public static String getRequest () {
        String resp = "ОЙ! Что-то сломалось((";
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "bot", "bot")) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select bot.randomAnswer() as responce;");
                while (resultSet.next()) {
                    resp = resultSet.getString("responce");
                }
                return resp;
            }
            catch (Exception  e) {
                System.out.println("Connection failure.");
                e.printStackTrace();
                return resp;
            }
        }

    public static int getCount (String userId, String eventId) {
        int resp = 1;
        String functionResp ="";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "bot", "bot")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select bot.evencount("+ userId +","+eventId+") as responce;");
            while (resultSet.next()) {
                functionResp = resultSet.getString("responce");
            }
            return Integer.parseInt(functionResp);
        }
        catch (Exception  e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return resp;
        }
    }


    public static int getCountStart (String userId) {
        int resp = 1;
        String functionResp ="";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "bot", "bot")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select bot.eventstartcount("+ userId +") as responce;");
            while (resultSet.next()) {
                functionResp = resultSet.getString("responce");
            }
            return Integer.parseInt(functionResp);
        }
        catch (Exception  e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return resp;
        }
    }

}
