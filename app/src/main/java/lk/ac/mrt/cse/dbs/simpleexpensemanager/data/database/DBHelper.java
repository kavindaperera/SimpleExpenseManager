package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DBContract.AccountTable;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DBContract.TransactionTable;


public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "170446L.db";

    public DBHelper(Context context) {
        super (context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AccountTable.TABLE_NAME + " (" +
                AccountTable.COLUMN_NAME_ACCOUNT_NO +" VARCHAR PRIMARY KEY ," +
                AccountTable.COLUMN_NAME_BANK_NAME + " VARCHAR ," +
                AccountTable.COLUMN_NAME_ACCOUNT_HOLDER_NAME +"VARCHAR ," +
                AccountTable.COLUMN_NAME_INITIAL_BALANCE+ " DOUBLE" + " );");

        db.execSQL("CREATE TABLE " + TransactionTable.TABLE_NAME + " (" +
                TransactionTable._ID + " INTEGER PRIMARY KEY ," +
                TransactionTable.COLUMN_NAME_ACCOUNT_NO + " VARCHAR ," +
                TransactionTable.COLUMN_NAME_DATE + " DATE ," +
                TransactionTable.COLUMN_NAME_TRANSACTION_TYPE + " VARCHAR ," +
                TransactionTable.COLUMN_NAME_AMOUNT+" DOUBLE ,"+
                " FOREIGN KEY("+ TransactionTable.COLUMN_NAME_ACCOUNT_NO+") REFERENCES "+ AccountTable.TABLE_NAME+"("+ AccountTable.COLUMN_NAME_ACCOUNT_NO+")"+
                " );");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + AccountTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TransactionTable.TABLE_NAME);
        onCreate(db);
    }

}
