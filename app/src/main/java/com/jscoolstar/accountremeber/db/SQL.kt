package com.jscoolstar.accountremeber.db

/**
 * Created by Administrator on 2018/3/30.
 */
object SQL {
    val T_ACCOUNT = "account"
    val TA_PLATFORM = "platform"
    val TA_password = "password"
    val TA_tip = "tip"
    val TA_bindphone = "bindphone"
    val TA_bindmail = "bindmail"
    val TA_create_time = "create_time"



    val T_EXTRA_COLUMN="extracolumn"
    val TE_aid="aid"
    val TE_key="key"
    val TE_value="value"

    val CREATE_T_ACCOUNT =
            "CREATE TABLE IF NOT EXISTS " + T_ACCOUNT + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "platform TEXT," +
                    "password TEXT," +
                    "tip TEXT," +
                    "bindphone TEXT," +
                    "bindmail TEXT," +
                    "create_time TEXT)";

    val CREATE_T_EXTRA_COLUMN=
            "CREATE TABLE IF NOT EXISTS " + T_EXTRA_COLUMN + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "aid INTEGER," +
                    "key TEXT," +
                    "value TEXT)";
}