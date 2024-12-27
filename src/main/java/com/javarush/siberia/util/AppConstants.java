package com.javarush.siberia.util;

public final class AppConstants {
    // Errors and access
    public static final String NO_ACCESS = "No access";
    public static final String ERROR_CANT_FIND_USER = "Can't find user";
    public static final String ERROR_CANT_UPDATE_USER = "Can't update user";
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid username or password";
    public static final String ERROR_USER_EXISTS = "User already exists or invalid input";

    // Success message
    public static final String SUCCESS_USER_UPDATE = "User update successful";
    public static final String MESSAGE_AUTHOR_PANEL = "Author panel - you can create new quests";

    // Session and parameters
    public static final String SESSION_USER = "user";
    public static final String PARAM_EDIT_USERNAME  = "editUsername";
    public static final String PARAM_OLD_USERNAME   = "oldUsername";
    public static final String PARAM_NEW_USERNAME   = "newUsername";
    public static final String PARAM_NEW_PASSWORD   = "newPassword";
    public static final String PARAM_NEW_ROLE       = "newRole";
    public static final String PARAM_USERNAME       = "username";
    public static final String PARAM_PASSWORD       = "password";
    public static final String PARAM_RESTART        = "restart";
    public static final String PARAM_CHOICE         = "choice";
    public static final String PARAM_QUEST_ID       = "questId";

    // JSP and paths
    public static final String JSP_ADMIN            = "/WEB-INF/admin.jsp";
    public static final String JSP_INDEX            = "/WEB-INF/index.jsp";
    public static final String JSP_LOGIN            = "/WEB-INF/login.jsp";
    public static final String JSP_OFFICE           = "/WEB-INF/office.jsp";
    public static final String JSP_QUEST            = "/WEB-INF/quest.jsp";
    public static final String JSP_REGISTER         = "/WEB-INF/register.jsp";
    public static final String JSP_RESULT           = "/WEB-INF/result.jsp";
    public static final String JSP_STATS            = "/WEB-INF/stats.jsp";

    // Titles and Atr
    public static final String ATTR_TITLE            = "title";
    public static final String ATTR_MESSAGE          = "message";
    public static final String ATTR_ERROR            = "error";
    public static final String ATTR_EDIT_USER        = "editUser";
    public static final String ATTR_USERS            = "users";
    public static final String ATTR_ERROR_MESSAGE    = "errorMessage";
    public static final String ATTR_STEP             = "step";
    public static final String ATTR_LOGGED_IN        = "loggedIn";
    public static final String ATTR_QUESTS           = "quests";
    public static final String ATTR_ADMIN_PANEL      = "Admin-panel";
    public static final String ATTR_MAIN_TITLE       = "Главная";
    public static final String ATTR_QUEST_TITLE      = "Квест";
    public static final String ATTR_RESULT_TITLE     = "Результат";
    public static final String ATTR_QUEST_MSG        = "Quest ";
    public static final String ATTR_CREATED_MSG      = " created!";
    public static final String ATTR_STEP_MSG         = "Step !";
    public static final String ATTR_ADD_TO_QUEST_MSG = " added to quest ";

    // Quest param
    public static final String PARAM_ACTION         = "action";
    public static final String ACTION_CREATE_QUEST  = "createQuest";
    public static final String ACTION_ADD_STEP      = "addStep";
    public static final String PARAM_STEP_ID        = "stepId";
    public static final String PARAM_TEXT           = "text";
    public static final String PARAM_IMAGE_PATH     = "imagePath";
    public static final String PARAM_OPTION1        = "option1";
    public static final String PARAM_OPTION2        = "option2";
    public static final String PARAM_NEXT1          = "next1";
    public static final String PARAM_NEXT2          = "next2";
    public static final String PARAM_END            = "end";
    public static final String PARAM_VICTORY        = "victory";

    private AppConstants() {
    }

}