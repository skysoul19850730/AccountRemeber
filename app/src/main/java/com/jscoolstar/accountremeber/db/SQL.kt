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
            val C_password = "password"
            val C_tip = "tip"
            val C_bindphone = "bindphone"
            val C_bindmail = "bindmail"
            val C_create_time = "create_time"

            val SQL_CREATE_TABLE =
                    "CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$C_PLATFORM TEXT," +
                            "$C_password TEXT," +
                            "$C_tip TEXT," +
                            "$C_bindphone TEXT," +
                            "$C_bindmail TEXT," +
                            "$C_create_time TEXT)";
        }

        //DB 2
        object DB2 {

            val C_ACCOUNT = "account"
            val C_CATEID = "cateid"

            val SQL_ADD_COLUMN_ACCOUNT = "ALTER TABLE $TABLENAME ADD COLUMN $C_ACCOUNT TEXT"
            val SQL_ADD_COLUMN_CATEID = "ALTER TABLE $TABLENAME ADD COLUMN $C_CATEID INTEGER"
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
        object DB2 {

            val C_cateName = "catename"

            val SQL_CREATE_TABLE =
                    "CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$C_cateName TEXT)";
        }
    }


}