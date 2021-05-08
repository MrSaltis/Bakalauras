package com.example.bakalauras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_PASS = "USER_PASS";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_ROLE = "USER_ROLE";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String RESTORANAI_TABLE = "RESTORANAI_TABLE";
    public static final String COLUMN_RESTORANAS_ID = "RESTORANAS_ID";
    public static final String COLUMN_RESTORANAS_NAME = "RESTORANAS_NAME";
    public static final String COLUMN_RESTORANAS_TYPE = "RESTORANAS_TYPE";
    public static final String COLUMN_RESTORANAS_CITY = "RESTORANAS_CITY";
    public static final String COLUMN_RESTORANAS_XCOORD = "RESTORANAS_XCOORD";
    public static final String COLUMN_RESTORANAS_YCOORD = "RESTORANAS_YCOORD";
    public static final String COLUMN_RESTORANAS_ADDRESS = "RESTORANAS_ADDRESS";
    public static final String COLUMN_RESTORANAS_TEL = "RESTORANAS_TEL";
    public static final String COLUMN_RESTORANAS_EMAIL = "RESTORANAS_EMAIL";
    public static final String COLUMN_RESTORANAS_WORKHOURS = "RESTORANAS_WORKHOURS";
    public static final String COLUMN_RESTORANAS_URL = "RESTORANAS_URL";
    public static final String COLUMN_RESTORANAS_ABOUT = "RESTORANAS_ABOUT";
    public static final String COLUMN_RESTORANAS_IMAGE = "RESTORANAS_IMAGE";
    public static final String MARSRUTO_TABLE = "MARSRUTO_TABLE";
    public static final String ISIMINTI_TABLE = "ISIMINTI_TABLE";
    public static final String COLUMN_MARSRUTO_ID = "MARSRUTO_ID";
    public static final String COLUMN_M_VARTOTOJO_ID = "M_VARTOTOJO_ID";
    public static final String COLUMN_M_RESTORANO_ID = "M_RESTORANO_ID";
    public static final String COLUMN_ISIMINTI_ID = "ISIMINTI_ID";
    public static final String COLUMN_I_VARTOTOJO_ID = "I_VARTOTOJO_ID";
    public static final String COLUMN_I_RESTORANO_ID = "I_RESTORANO_ID";
    public static final String KOMENTARAI_TABLE = "KOMENTARAI_TABLE";
    public static final String COLUMN_KOMENTARAS_ID = "KOMENTARAS_ID";
    public static final String COLUMN_K_VARTOTOJAS_ID = "VARTOTOJAS_ID";
    public static final String COLUMN_K_RESTORANAS_ID = "RESTORANAS_ID";
    public static final String COLUMN_KOMENTARAS = "KOMENTARAS";

    //byte[] paveiksliukas;


    public DataBaseHelper(@Nullable Context context) {
        super(context, "bakalauro.db", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT UNIQUE NOT NULL, " + COLUMN_USER_PASS + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_ROLE + " INTEGER)";
        db.execSQL(createTableStatement);

        String createTableStatement2 = "CREATE TABLE IF NOT EXISTS " + RESTORANAI_TABLE + " (" + COLUMN_RESTORANAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_RESTORANAS_NAME + " TEXT, " +
                COLUMN_RESTORANAS_TYPE + " TEXT, " + COLUMN_RESTORANAS_CITY + " TEXT, " + COLUMN_RESTORANAS_XCOORD + " REAL, " + COLUMN_RESTORANAS_YCOORD + " REAL, " + COLUMN_RESTORANAS_ADDRESS + " TEXT, " +
                COLUMN_RESTORANAS_TEL + " INTEGER, " + COLUMN_RESTORANAS_EMAIL + " TEXT, " + COLUMN_RESTORANAS_WORKHOURS + " TEXT, " + COLUMN_RESTORANAS_URL + " TEXT, " + COLUMN_RESTORANAS_ABOUT + " TEXT, " +
                COLUMN_RESTORANAS_IMAGE + " BLOB)";
        db.execSQL(createTableStatement2);

        String createTableStatement3 = "CREATE TABLE IF NOT EXISTS " + MARSRUTO_TABLE + " (" + COLUMN_MARSRUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_M_VARTOTOJO_ID + " INTEGER, " + COLUMN_M_RESTORANO_ID + " INTEGER)";
        db.execSQL(createTableStatement3);

        String createTableStatement4 = "CREATE TABLE IF NOT EXISTS " + ISIMINTI_TABLE + " (" + COLUMN_ISIMINTI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_I_VARTOTOJO_ID + " INTEGER, " + COLUMN_I_RESTORANO_ID + " INTEGER)";
        db.execSQL(createTableStatement4);

        String createTableStatement5 = "CREATE TABLE IF NOT EXISTS " + KOMENTARAI_TABLE + " (" + COLUMN_KOMENTARAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_K_VARTOTOJAS_ID + " INTEGER, " + COLUMN_K_RESTORANAS_ID + " INTEGER, " + COLUMN_KOMENTARAS + " TEXT)";
        db.execSQL(createTableStatement5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS "+ RESTORANAI_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS "+ MARSRUTO_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS "+ ISIMINTI_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ KOMENTARAI_TABLE);
        onCreate(db);
    }


    public boolean addUser(Vartotojas vartotojas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, vartotojas.getVardas());
        cv.put(COLUMN_USER_PASS, vartotojas.getSlaptazodis());
        cv.put(COLUMN_USER_EMAIL, vartotojas.getPastas());
        cv.put(COLUMN_USER_ROLE, vartotojas.getRole());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1){
            //db.close();
            return false;
        }
        else {
            //db.close();
            return true;
        }
    }

    public boolean updateUser(Vartotojas vartotojas){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, vartotojas.getVardas());
        cv.put(COLUMN_USER_PASS, vartotojas.getSlaptazodis());
        cv.put(COLUMN_USER_EMAIL, vartotojas.getPastas());
        cv.put(COLUMN_USER_ROLE, vartotojas.getRole());

        Cursor cursor = db.rawQuery("SELECT * FROM "+ USER_TABLE + " WHERE " + COLUMN_USER_ID + " = ?", new String[] {Integer.toString(vartotojas.getID())});
        if (cursor.getCount()>0) {
            long insert = db.update(USER_TABLE, cv, COLUMN_USER_ID + " = ?", new String[]{Integer.toString(vartotojas.getID())});
            if (insert == -1) {
                db.close();
                cursor.close();
                return false;
            } else {
                db.close();
                cursor.close();
                return true;
            }
        }
        else {
            db.close();
            cursor.close();
            return false;
        }
    }

    public boolean getUser(String name, String pass){

        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USER_NAME= " + name + " AND USER_PASS= " + pass;
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USER_NAME= ? AND USER_PASS= ?";
        Cursor cursor = db.rawQuery(queryString, new String[] {name, pass});

        if (cursor.moveToFirst()){
            //cursor.close();
            //db.close();
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }
    }

    public List<Vartotojas> getAllUsers() {

        List<Vartotojas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int UserID = cursor.getInt(0);
                String UserName = cursor.getString(1);
                String UserPass = cursor.getString(2);
                String UserMail = cursor.getString(3);
                int UserRole = cursor.getInt(4);

                Vartotojas vartotojas = new Vartotojas(UserID, UserName, UserPass, UserMail, UserRole);

                returnList.add(vartotojas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Vartotojas> getAllUsersSearch(String tekstas) {

        List<Vartotojas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USER_NAME LIKE '?' OR USER_EMAIL LIKE '?'";


        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cursor = db.rawQuery(queryString, new String[] {"%"+ tekstas +"%", "%"+ tekstas +"%"});

        //Cursor cursor = db.query(true, USER_TABLE, new String[] {COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_PASS, COLUMN_USER_EMAIL, COLUMN_USER_ROLE},
        //       COLUMN_USER_NAME + ", " + COLUMN_USER_EMAIL, new String[] {"%"+ tekstas +"%"},  " LIKE ?", null, null, null);

        Cursor cursor = db.query(true, USER_TABLE, new String[] {COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_PASS,
                        COLUMN_USER_EMAIL, COLUMN_USER_ROLE }, COLUMN_USER_NAME + " LIKE ?",
                new String[] {"%"+ tekstas+ "%" }, null, null, null,
                null);

        if(cursor.moveToFirst()) {
            do {
                int UserID = cursor.getInt(0);
                String UserName = cursor.getString(1);
                String UserPass = cursor.getString(2);
                String UserMail = cursor.getString(3);
                int UserRole = cursor.getInt(4);

                Vartotojas vartotojas = new Vartotojas(UserID, UserName, UserPass, UserMail, UserRole);

                returnList.add(vartotojas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Vartotojas> getUserFullbyName(String name) {

        List<Vartotojas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USER_NAME= ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {name});

        if(cursor.moveToFirst()) {
            do {
                int UserID = cursor.getInt(0);
                String UserName = cursor.getString(1);
                String UserPass = cursor.getString(2);
                String UserMail = cursor.getString(3);
                int UserRole = cursor.getInt(4);

                Vartotojas vartotojas = new Vartotojas(UserID, UserName, UserPass, UserMail, UserRole);

                returnList.add(vartotojas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Vartotojas> getUserFullbyID(int id) {

        List<Vartotojas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USER_ID= ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {Integer.toString(id)});

        if(cursor.moveToFirst()) {
            do {
                int UserID = cursor.getInt(0);
                String UserName = cursor.getString(1);
                String UserPass = cursor.getString(2);
                String UserMail = cursor.getString(3);
                int UserRole = cursor.getInt(4);

                Vartotojas vartotojas = new Vartotojas(UserID, UserName, UserPass, UserMail, UserRole);

                returnList.add(vartotojas);
            } while(cursor.moveToNext());
        }
        else{

        }
        //db.close();
        //cursor.close();
        return returnList;
    }


    public boolean addRestoranas(Restoranas restoranas){
        byte [] paveiksliukas = getBitmapAsByteArray(restoranas.getPaveiksliukas());


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RESTORANAS_NAME, restoranas.getPavadinimas());
        cv.put(COLUMN_RESTORANAS_TYPE, restoranas.getTipas());
        cv.put(COLUMN_RESTORANAS_CITY, restoranas.getMiestas());
        cv.put(COLUMN_RESTORANAS_XCOORD, restoranas.getXcoord());
        cv.put(COLUMN_RESTORANAS_YCOORD, restoranas.getYcoord());
        cv.put(COLUMN_RESTORANAS_ADDRESS, restoranas.getAdresas());
        cv.put(COLUMN_RESTORANAS_TEL, restoranas.getTelNr());
        cv.put(COLUMN_RESTORANAS_EMAIL, restoranas.getElPastas());
        cv.put(COLUMN_RESTORANAS_WORKHOURS, restoranas.getDarboLaikas());
        cv.put(COLUMN_RESTORANAS_URL, restoranas.getURL());
        cv.put(COLUMN_RESTORANAS_ABOUT, restoranas.getAprasymas());
        cv.put(COLUMN_RESTORANAS_IMAGE, paveiksliukas);

        long insert = db.insert(RESTORANAI_TABLE, null, cv);
        if (insert == -1){
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }

    public boolean updateRestoranas(Restoranas restoranas){
        byte[] paveiksliukas = getBitmapAsByteArray(restoranas.getPaveiksliukas());


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RESTORANAS_NAME, restoranas.getPavadinimas());
        cv.put(COLUMN_RESTORANAS_TYPE, restoranas.getTipas());
        cv.put(COLUMN_RESTORANAS_CITY, restoranas.getMiestas());
        cv.put(COLUMN_RESTORANAS_XCOORD, restoranas.getXcoord());
        cv.put(COLUMN_RESTORANAS_YCOORD, restoranas.getYcoord());
        cv.put(COLUMN_RESTORANAS_ADDRESS, restoranas.getAdresas());
        cv.put(COLUMN_RESTORANAS_TEL, restoranas.getTelNr());
        cv.put(COLUMN_RESTORANAS_EMAIL, restoranas.getElPastas());
        cv.put(COLUMN_RESTORANAS_WORKHOURS, restoranas.getDarboLaikas());
        cv.put(COLUMN_RESTORANAS_URL, restoranas.getURL());
        cv.put(COLUMN_RESTORANAS_ABOUT, restoranas.getAprasymas());
        cv.put(COLUMN_RESTORANAS_IMAGE, paveiksliukas);

        Cursor cursor = db.rawQuery("SELECT * FROM "+ RESTORANAI_TABLE + " WHERE " + COLUMN_K_RESTORANAS_ID + "= ?", new String[] {Integer.toString(restoranas.getRestoranoID())});
        if (cursor.getCount()>0) {
            long insert = db.update(RESTORANAI_TABLE, cv, COLUMN_K_RESTORANAS_ID + " = ?", new String[]{Integer.toString(restoranas.getRestoranoID())});
            if (insert == -1) {
                db.close();
                cursor.close();
                return false;
            } else {
                db.close();
                cursor.close();
                return true;
            }
        }
        else {
            db.close();
            cursor.close();
            return false;
        }
    }

    public boolean deleteRestoranas(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "DELETE FROM " + RESTORANAI_TABLE + " WHERE " + COLUMN_RESTORANAS_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[] {Integer.toString(id)});

        if(cursor.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }

    public List<Restoranas> getRestoranai() {

        List<Restoranas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RESTORANAI_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Restoranas> getRestoranasSingle(int i) {
        List<Restoranas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RESTORANAI_TABLE + " WHERE " + COLUMN_K_RESTORANAS_ID + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        //String id = String.valueOf(i);
        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(i)});

        if(cursor.moveToFirst()){
            do{
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);

            } while (cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Restoranas> getRestoranasSearch(String city, String type, String tekstas) {
        List<Restoranas> returnList = new ArrayList<>();

        //String queryString = "SELECT * FROM " + RESTORANAI_TABLE + " WHERE RESTORANAS_CITY = ? AND RESTORANAS_TYPE = ? AND RESTORANAS_NAME = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        /*Cursor cursor = db.query(true, RESTORANAI_TABLE, new String[] {COLUMN_RESTORANAS_ID, COLUMN_RESTORANAS_NAME, COLUMN_RESTORANAS_CITY,
                        COLUMN_RESTORANAS_XCOORD, COLUMN_RESTORANAS_YCOORD, COLUMN_RESTORANAS_TYPE, COLUMN_RESTORANAS_ADDRESS, COLUMN_RESTORANAS_TEL,
                        COLUMN_RESTORANAS_EMAIL, COLUMN_RESTORANAS_WORKHOURS, COLUMN_RESTORANAS_URL, COLUMN_RESTORANAS_ABOUT, COLUMN_RESTORANAS_IMAGE},
                        COLUMN_RESTORANAS_NAME + " LIKE ?" + " AND WHERE RESTORANAS_CITY = ? AND RESTORANAS_TYPE = ?",
                new String[] {"%"+ tekstas+ "%", city, type}, null, null, null,
                null);*/

        Cursor cursor = db.query(true, RESTORANAI_TABLE, new String[] {COLUMN_RESTORANAS_ID, COLUMN_RESTORANAS_NAME, COLUMN_RESTORANAS_CITY,
                        COLUMN_RESTORANAS_XCOORD, COLUMN_RESTORANAS_YCOORD, COLUMN_RESTORANAS_TYPE, COLUMN_RESTORANAS_ADDRESS, COLUMN_RESTORANAS_TEL,
                        COLUMN_RESTORANAS_EMAIL, COLUMN_RESTORANAS_WORKHOURS, COLUMN_RESTORANAS_URL, COLUMN_RESTORANAS_ABOUT, COLUMN_RESTORANAS_IMAGE},
                "WHERE RESTORANAS_CITY = ? AND RESTORANAS_TYPE = ? AND RESTORANAS_NAME LIKE ?",
                new String[] {city, type, "%"+ tekstas+ "%"}, null, null, null,
                null);

        if(cursor.moveToFirst()){
            do{
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);

            } while (cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Restoranas> getRestoranaiCityType(String city, String type) {
        List<Restoranas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RESTORANAI_TABLE + " WHERE RESTORANAS_CITY = ? AND RESTORANAS_TYPE = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        //String id = String.valueOf(i);
        Cursor cursor = db.rawQuery(queryString, new String[] {city, type});

        if(cursor.moveToFirst()){
            do{
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);

            } while (cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public boolean addMarsrutoSarasas(MarsrutoSarasas marsrutoSarasas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_M_VARTOTOJO_ID, marsrutoSarasas.getVartotojoID());
        cv.put(COLUMN_M_RESTORANO_ID, marsrutoSarasas.getRestoranoID());

        long insert = db.insert(MARSRUTO_TABLE, null, cv);
        if (insert == -1){
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }

    public boolean addIsimintiSarasas(IsimintiSarasas isimintiSarasas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_I_VARTOTOJO_ID, isimintiSarasas.getVartotojoID());
        cv.put(COLUMN_I_RESTORANO_ID, isimintiSarasas.getRestoranoID());

        long insert = db.insert(ISIMINTI_TABLE, null, cv);
        if (insert == -1){
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }

    public List<Restoranas> getMarsrutas(int id) {

        List<Restoranas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM RESTORANAI_TABLE AS R LEFT JOIN MARSRUTO_TABLE AS M ON R." + COLUMN_K_RESTORANAS_ID + " = M.M_RESTORANO_ID WHERE M.M_VARTOTOJO_ID = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(id)});

        if(cursor.moveToFirst()) {
            do {
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Restoranas> getIsiminti(int id) {

        List<Restoranas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM RESTORANAI_TABLE AS R LEFT JOIN ISIMINTI_TABLE AS I ON R." + COLUMN_K_RESTORANAS_ID + " = I.I_RESTORANO_ID WHERE I.I_VARTOTOJO_ID = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(id)});

        if(cursor.moveToFirst()) {
            do {
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Restoranas> getIsimintiMiestas(int id, String miestas) {

        List<Restoranas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM RESTORANAI_TABLE AS R LEFT JOIN ISIMINTI_TABLE AS I ON R." + COLUMN_K_RESTORANAS_ID + " = I.I_RESTORANO_ID WHERE I.I_VARTOTOJO_ID = ? AND R.RESTORANAS_CITY = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(id), miestas});

        if(cursor.moveToFirst()) {
            do {
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Restoranas> getIsimintiTipas(int id, String tipas) {

        List<Restoranas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM RESTORANAI_TABLE AS R LEFT JOIN ISIMINTI_TABLE AS I ON R." + COLUMN_K_RESTORANAS_ID + " = I.I_RESTORANO_ID WHERE I.I_VARTOTOJO_ID = ? AND R.RESTORANAS_TYPE = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(id), tipas});

        if(cursor.moveToFirst()) {
            do {
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public List<Restoranas> getIsimintiMiestasTipas(int id, String miestas, String tipas) {

        List<Restoranas> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM RESTORANAI_TABLE AS R LEFT JOIN ISIMINTI_TABLE AS I ON R." + COLUMN_K_RESTORANAS_ID + " = I.I_RESTORANO_ID WHERE I.I_VARTOTOJO_ID = ? AND R.RESTORANAS_CITY = ? AND R.RESTORANAS_TYPE = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(id), miestas, tipas});

        if(cursor.moveToFirst()) {
            do {
                int RestoranoID = cursor.getInt(0);
                String RestoranoPavadinimas = cursor.getString(1);
                String RestoranoMiestas = cursor.getString(3);
                double RestoranoXcoord = cursor.getDouble(4);
                double RestoranoYcoord = cursor.getDouble(5);
                String RestoranoTipas = cursor.getString(2);
                String RestoranoAdresas = cursor.getString(6);
                int RestoranoTel = cursor.getInt(7);
                String RestoranoPastas = cursor.getString(8);
                String RestoranoDarboLaikas = cursor.getString(9);
                String RestoranoURL = cursor.getString(10);
                String RestoranoAprasymas = cursor.getString(11);
                byte[] RestoranoPaveiksliukas = cursor.getBlob(12);

                Restoranas restoranas = new Restoranas(RestoranoID, RestoranoPavadinimas, RestoranoMiestas,
                        RestoranoXcoord, RestoranoYcoord, RestoranoTipas, RestoranoAdresas, RestoranoTel,
                        RestoranoPastas, RestoranoDarboLaikas, RestoranoURL, RestoranoAprasymas,
                        BitmapFactory.decodeByteArray(RestoranoPaveiksliukas, 0, RestoranoPaveiksliukas.length));

                returnList.add(restoranas);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public boolean deleteMarsrutas(int Rid, int Vid){
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "DELETE FROM " + MARSRUTO_TABLE + " WHERE " + COLUMN_M_RESTORANO_ID + " = ? AND " + COLUMN_M_VARTOTOJO_ID +" = ?";
        Cursor cursor = db.rawQuery(queryString, new String[] {Integer.toString(Rid),  Integer.toString(Vid)});

        if(cursor.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteIsimintas(int Rid, int Vid){
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "DELETE FROM " + ISIMINTI_TABLE + " WHERE " + COLUMN_I_RESTORANO_ID + " = ? AND " + COLUMN_I_VARTOTOJO_ID +" = ?";
        Cursor cursor = db.rawQuery(queryString, new String[] {Integer.toString(Rid), Integer.toString(Vid)});

        if(cursor.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }

    public List<Komentaras> getKomentaras(int id) {

        List<Komentaras> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM KOMENTARAI_TABLE WHERE RESTORANAS_ID = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(id)});

        if(cursor.moveToFirst()) {
            do {
                int Komentaro_ID = cursor.getInt(0);
                int Vartotojo_ID = cursor.getInt(1);
                int Restorano_ID = cursor.getInt(2);
                String Komentaras = cursor.getString(3);

                Komentaras komentaras = new Komentaras(Komentaro_ID, Vartotojo_ID, Restorano_ID, Komentaras);

                returnList.add(komentaras);
            } while(cursor.moveToNext());
        }
        else{

        }
        db.close();
        cursor.close();
        return returnList;
    }

    public boolean addKomentaras(Komentaras komentaras){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_K_VARTOTOJAS_ID, komentaras.getVartotojoID());
        cv.put(COLUMN_K_RESTORANAS_ID, komentaras.getRestoranoID());
        cv.put(COLUMN_KOMENTARAS, komentaras.getKomentaras());


        long insert = db.insert(KOMENTARAI_TABLE, null, cv);
        if (insert == -1){
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }


}
