package ru.taksebe.telegram.mentalCalculation.telegram.commands.service;

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.telegram.telegrambots.meta.api.objects.Chat;
        import org.telegram.telegrambots.meta.api.objects.User;
        import org.telegram.telegrambots.meta.bots.AbsSender;
        import ru.taksebe.telegram.mentalCalculation.Utils;

/**
 */
public class SearchFilmCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    public SearchFilmCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String toLog = getCommandIdentifier() + " || " + getDescription() + " || " + user.toString() + " || " + chat.toString() + " || " + absSender.toString();
        System.out.println(toLog);
        DBConn.logIn(toLog);
        String userName = Utils.getUserName(user);
        System.out.println(userName+ "вызов SearchFilmCommand");
        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));

        String text = "Эта команда позволяет получить подборку фильмов похожих на выбранный. \n Чтобы получить подборку- напиши одним сообщением /film @название@ \n Например: /film москва слезам не верит \n А еще, я пока не умею уточнять какой именно фильм нужен, так что пиши без ошибок \uD83D\uDE1B";
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                text);
        DBConn.logOut(text + " || " + toLog);
        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}

