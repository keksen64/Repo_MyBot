package ru.taksebe.telegram.mentalCalculation.telegram;


import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.taksebe.telegram.mentalCalculation.Utils;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.service.*;
import ru.taksebe.telegram.mentalCalculation.telegram.functionalFilm.*;
import ru.taksebe.telegram.mentalCalculation.telegram.nonCommand.NonCommand;
import ru.taksebe.telegram.mentalCalculation.telegram.nonCommand.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Собственно, бот
 */
public final class Bot extends TelegramLongPollingCommandBot {
    private Logger logger = LoggerFactory.getLogger(Bot.class);

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    @Getter
    private static final Settings defaultSettings = new Settings(1, 15,1);
    private final NonCommand nonCommand;

    /**
     * Настройки файла для разных пользователей. Ключ - уникальный id чата
     */
    @Getter
    private static Map<Long, Settings> userSettings;

    public Bot(String botName, String botToken) throws Exception {
        super();
        logger.debug("Конструктор суперкласса отработал");
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        logger.debug("Имя и токен присвоены");

        this.nonCommand = new NonCommand();
        logger.debug("Класс обработки сообщения, не являющегося командой, создан");

        register(new StartCommand("start", "Старт"));
        logger.debug("Команда start создана");


        register(new SearchFilmCommand("getfilm", "получить подборку фильмов"));
        logger.debug("Команда minus создана");


        register(new HelpCommand("help","Помощь"));
        logger.debug("Команда help создана");



        BotCommandScopeDefault scope = new BotCommandScopeDefault();
        List commandList = new ArrayList<Object>();
        commandList.add(new BotCommand("buttons", "получить кнопачки"));
        commandList.add(new BotCommand("getfilm", "получить подборку фильмов"));
        commandList.add(new BotCommand("start", "старт"));
        this.execute(new SetMyCommands(commandList, scope, "ru"));
        SendTLG.sendToTelegram();
         }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Ответ на запрос, не являющийся командой
     */
    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {

        if(update.hasMessage()){
            Message msg = update.getMessage();
            User us = msg.getFrom();
            DBConn.setUser(us.getId().toString(),us.getUserName(),us.getFirstName(),us.getLastName(),us.getIsBot().toString());
            System.out.println("===========================");
            System.out.println(msg);
            System.out.println("aaaaaaaa"+update.getCallbackQuery());
            String messageToLog = msg.toString();
            DBConn.logIn(messageToLog);
            System.out.println("===========================");
            Long chatId = msg.getChatId();
             Long userID = msg.getFrom().getId();
            String userName = Utils.getUserName(msg);
            String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
            System.out.println("=========================" + answer);
            //setAnswer(chatId, userName, answer, msg);
            if(update.getMessage().hasText()){
                if(update.getMessage().getText().equals("/buttons")){
                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                        System.out.println("/buttons || "+update.getMessage());
                        DBConn.logOut("/buttons || "+update.getMessage());
                        return;
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if(update.getMessage().getText().length()>3){
                    System.out.println(update.getMessage().getText().substring(0,4));
                    if(update.getMessage().getText().substring(0,4).equals("/msg")){
                        System.out.println(update.getMessage().getFrom().getId());
                        if(update.getMessage().getFrom().getId().equals(363125585L)){
                            System.out.println("ok");
                            try {
                                update.getMessage().getText().substring(4);
                                SendMessageTLG.sendToTelegram(update.getMessage().getText().substring(5));

                            }catch (Exception e){

                            }
                        }

                    }
                    if(update.getMessage().getText().substring(0,5).equals("/film")){
                        String recomendation = "Что-то пошло не так \uD83D\uDE44";
                        String film = "";
                        try {
                             film = update.getMessage().getText().substring(6);
                        }catch (Exception e)   {
                            setAnswer(chatId, userName, "/film  ❌\uD83D\uDE45\u200D♂️\uD83D\uDE45\u200D♀️\n/film охотники за приведениями  ✅✅", msg);
                        Thread.sleep(2900);
                            setAnswer(chatId, userName, "ты че как хлебушек \uD83C\uDF1D", msg);
                            return;
                        }
                        if(DBConn.getCount(userID.toString(),"1001")==0){
                            try {
                                recomendation = RegExp.getRecomendationFilm(HttpFilmRequest.recomendationFilm(RegExp.getSearchFilm(HttpFilmRequest.searchFilm(film))));
                                //recomendation = HttpFilmRequest.recomendationFilm(RegExp.getSearchFilm(HttpFilmRequest.searchFilm(film)));
                                DBConn.eventFilm(userID.toString(),"1001",recomendation);
                            }catch (Exception e){
                                setAnswer(chatId, userName, recomendation, msg);
                            }
                            try {
                                setAnswer(chatId, userName, recomendation, msg);
                            }catch (Exception e) {

                                setAnswer(chatId, userName, "ошибка вызова ответа\uD83E\uDEE3", msg);
                            }
                        }else {
                            setAnswer(chatId, userName, "я ленивый бот, сделать запрос можно раз в 30 сек\uD83D\uDE0C", msg);
                        }
                    }
                    else {
                        setAnswer(chatId, userName, answer, msg);
                    }
                }
                else {
                    setAnswer(chatId, userName, answer, msg);
                }
            }
        }else if(update.hasCallbackQuery()){
            try {
                CallbackQuery qq = update.getCallbackQuery();
                DBConn.logIn(qq.toString());
                Message mm = qq.getMessage();
                User us = mm.getFrom();
                DBConn.setUser(us.getId().toString(),us.getUserName(),us.getFirstName(),us.getLastName(),us.getIsBot().toString());
                SendMessage mmess = new SendMessage();
                mmess.setText(
                        update.getCallbackQuery().getData());
                mmess.setChatId(String.valueOf(mm.getChatId()));
                execute(mmess);
                DBConn.logOut(mmess.getText() + " || " + mmess.toString() + " || " + qq.toString());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Получение настроек по id чата. Если ранее для этого чата в ходе сеанса работы бота настройки не были установлены,
     * используются настройки по умолчанию
     */
    public static Settings getUserSettings(Long chatId) {
        Map<Long, Settings> userSettings = Bot.getUserSettings();
        Settings settings = userSettings.get(chatId);
        if (settings == null) {
            return defaultSettings;
        }
        return settings;
    }
    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Тык");
        inlineKeyboardButton1.setCallbackData("кнопачки!!!");
        inlineKeyboardButton2.setText("Тык2");
        inlineKeyboardButton2.setCallbackData("кноооопачки!!!");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        InlineKeyboardButton in = new InlineKeyboardButton();
        in.setText("ыхы");
        in.setCallbackData("еще кнопачкииии!!!");
        System.out.println("+++++++++++++++++++++ " + in.toString());

        keyboardButtonsRow1.add(in);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage mes = new SendMessage();
        mes.setChatId(String.valueOf(chatId));
        mes.setText("Здрасть");
        mes.setReplyMarkup(inlineKeyboardMarkup);
        return mes;
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     * @param text текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text, Message msg) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
            DBConn.logOut(text + " || " + msg.toString());
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }
}