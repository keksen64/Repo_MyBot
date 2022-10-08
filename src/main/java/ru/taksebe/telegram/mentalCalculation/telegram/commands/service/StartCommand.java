package ru.taksebe.telegram.mentalCalculation.telegram.commands.service;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.taksebe.telegram.mentalCalculation.Utils;

import java.util.Arrays;

/**
 * Команда "Старт"
 */
public class StartCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String toLog = getCommandIdentifier() + " || " + getDescription() + " || " + user.toString() + " || " + chat.toString() + " || " + absSender.toString();
        System.out.println(toLog);
        DBConn.logIn(toLog);
        String userName = Utils.getUserName(user);
        if(DBConn.getCount(user.getId().toString(),"1002")==0){

        }else {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                    "Мне лень, попробуй позже☺ ");
            return;
        }

        System.out.println("-----------------------");
        System.out.println(Utils.getUserName(user));
        System.out.println(absSender.getMe());
        System.out.println(chat.getId());
        System.out.println(chat.getTitle());
        System.out.println(chat.getFirstName());
        System.out.println(chat.getLastName()); //предложить работу но я врядли соглашусь написать по поводу ошибок или не корректной работы
        System.out.println(chat.getUserName());
        System.out.println(Arrays.toString(strings));
        System.out.println("-----------------------");


        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        String text = userName + " - жопка\uD83D\uDCA9 ";
        System.out.println(text);
        String text0 = "Ghbdtn! z ,jn rjnjhsq evttn ghbcskfnm lbrgbr d kbxre\n" +
                "...черт я опять забыл переключиться\uD83D\uDE48" +
                "\nПривет! Мне всего 3 дня, и я пока не определился зачем я нужен\uD83E\uDD7A" +
                "\nТак что возможно, у нас с тобой есть что-то общее\uD83D\uDE02" +
                "\nПока что у меня есть лишь пара команд, но возможно они тебе будут полезны." +
                "\nА еще я ленивый, обзываюсь, и меня включают на пару часов в день, как электричество в Северной Корее." +
                "\n\nЕсли что-то сломалось, работает неправильно или у Вас есть какие-то предложения, то можно написать сюда @kekss_pekss";
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                text0);
        DBConn.setUser(user.getId().toString(),user.getUserName(),user.getFirstName(),user.getLastName(),user.getIsBot().toString());

        DBConn.logOut(text0 + " || " + toLog);
        Thread.sleep(17600);
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                text);
        DBConn.eventFilm(user.getId().toString(),"1002", "старт");
        DBConn.logOut(text + " || " + toLog);
        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}