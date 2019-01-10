package com.jscoolstar.accountremeber.db

/**
 * Created by Administrator on 2018/3/30.
 */
object SQL {

    object ACCOUNT {
        val TABLENAME = "account"

        // DB 1
        object DB1 {
            val C_PLATFORM = "platform"
            val C_ACCOUNT = "account"
            val C_password = "password"
            val C_tip = "tip"
            val C_bindphone = "bindphone"
            val C_bindmail = "bindmail"
            val C_create_time = "create_time"
            val C_CATEID = "cateid"
            val C_UserID = "userid"
            val SQL_CREATE_TABLE =
                    "CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$C_PLATFORM TEXT," +
                            "$C_password TEXT," +
                            "$C_tip TEXT," +
                            "$C_bindphone TEXT," +
                            "$C_bindmail TEXT," +
                            "$C_ACCOUNT TEXT," +
                            "$C_CATEID INTEGER," +
                            "$C_UserID INTEGER," +
                            "$C_create_time TEXT)";
        }
    }

    object EXTRA {
        val TABLENAME = "extracolumn"
        //DB 1
        object DB1 {

            val C_aid = "aid"
            val C_key = "key"
            val C_value = "value"
            val SQL_CREATE_TABLE =
                    "CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$C_aid INTEGER," +
                            "$C_key TEXT," +
                            "$C_value TEXT)";
        }
    }

    object CATE {
        val TABLENAME = "cates"
        //db2
        object DB1 {

            val C_cateName = "catename"
            val C_userId = "userid"

            val SQL_CREATE_TABLE =
                    "CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$C_userId INTEGER,"+
                            "$C_cateName TEXT)";
        }
    }

    object User{
        val TABLENAME = "user"
        object DB1{
            val C_UserName = "username"
            val C_Password="password"
            val C_passwordTip="passwordtip"
            val C_leftTryTimes="lefttrytimes"
            val C_LastWrongTime="lastwrondtime"

            val SQL_CREATE_TABLE =
                    "CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$C_Password TEXT,"+
                            "$C_passwordTip TEXT,"+
                            "$C_LastWrongTime TEXT,"+
                            "$C_leftTryTimes INTEGER,"+
                            "$C_UserName TEXT)";
        }
    }

}