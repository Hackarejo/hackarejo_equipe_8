package com.anjosi.promovalley.openhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anjosi on 16/04/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMP = "PRODUTO";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_COMP = "name";

    public static final String TABLE_MERCADO = "MERCADO";
    public static final String COLUMN_NAME_MERC = "name";
    public static final String COLUMN_ENDE_MERC = "endereco";

    public static final String TABLE_ITEM = "ITEM_PRODUTO";
//    public static final String COLUMN_LOCA_IT = "location";
    public static final String COLUMN_PRIC_IT = "price";
    public static final String COLUMN_FK_PROD = "FK_PRODUTO";
    public static final String COLUMN_FK_MER = "FK_MERCADO";

    public DataBaseHelper(Context context) {
        super(context, "meubanco.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        CreateTable(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        CreateTable(db);
    }

    private void CreateTable(SQLiteDatabase db) {
        StringBuilder bd = new StringBuilder();

        bd.append("CREATE TABLE ");
        bd.append(TABLE_ITEM);
        bd.append("(");
        bd.append(COLUMN_ID);
        bd.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
//        bd.append(COLUMN_LOCA_IT);
//        bd.append(" TEXT, ");
        bd.append(COLUMN_PRIC_IT);
        bd.append(" FLOAT(10,2), ");
        bd.append(COLUMN_FK_PROD);
        bd.append(" INTEGER,");
        bd.append(COLUMN_FK_MER);
        bd.append(" INTEGER ");
        bd.append(");");

        db.execSQL(bd.toString());

        bd = new StringBuilder();

        bd.append("CREATE TABLE ");
        bd.append(TABLE_COMP);
        bd.append("(");
        bd.append(COLUMN_ID);
        bd.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        bd.append(COLUMN_NAME_COMP);
        bd.append(" VARCHAR(60)");
        bd.append(");");

        db.execSQL(bd.toString());

        bd = new StringBuilder();

        bd.append("CREATE TABLE ");
        bd.append(TABLE_MERCADO);
        bd.append("(");
        bd.append(COLUMN_ID);
        bd.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        bd.append(COLUMN_NAME_MERC);
        bd.append(" VARCHAR(60), ");
        bd.append(COLUMN_ENDE_MERC);
        bd.append(" VARCHAR(120) ");
        bd.append(");");

        db.execSQL(bd.toString());

        bd = new StringBuilder();
    }
}
