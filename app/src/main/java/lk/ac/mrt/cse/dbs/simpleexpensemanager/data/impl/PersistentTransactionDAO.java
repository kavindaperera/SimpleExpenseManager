package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DBContract.TransactionTable;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO, Serializable {

    private transient SQLiteDatabase database;
    public PersistentTransactionDAO(SQLiteDatabase database) {
        this.database=database;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteStatement statement=database.compileStatement("INSERT INTO "+ TransactionTable.TABLE_NAME+"("+ TransactionTable.COLUMN_NAME_ACCOUNT_NO+","+ TransactionTable.COLUMN_NAME_TRANSACTION_TYPE+","+
                TransactionTable.COLUMN_NAME_DATE+","+ TransactionTable.COLUMN_NAME_AMOUNT+") VALUES(?,?,?,?);");
        statement.bindString(1,accountNo);
        statement.bindString(2,expenseType.name());
        statement.bindString(3,date.toString());
        statement.bindDouble(4,amount);
        statement.executeInsert();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {

        Cursor cursor=database.rawQuery("SELECT * FROM "+ TransactionTable.TABLE_NAME,null);
        List<Transaction> transaction_list=new ArrayList<>();

        while(cursor.moveToNext()){
            String account_no = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_NAME_ACCOUNT_NO));
            double amount = cursor.getDouble(cursor.getColumnIndex(TransactionTable.COLUMN_NAME_AMOUNT));
            String trans_type = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_NAME_TRANSACTION_TYPE));
            String date = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_NAME_DATE));
            Transaction transaction = new Transaction(new Date(date),account_no, ExpenseType.valueOf(trans_type),amount);
            transaction_list.add(transaction);
        }
        return transaction_list;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        Cursor cursor=database.rawQuery("SELECT * FROM "+ TransactionTable.TABLE_NAME+" LIMIT "+limit,null);
        List<Transaction> transaction_list=new ArrayList<>();
        while(cursor.moveToNext()){
            String account_no = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_NAME_ACCOUNT_NO));
            double amount = cursor.getDouble(cursor.getColumnIndex(TransactionTable.COLUMN_NAME_AMOUNT));
            String trans_type = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_NAME_TRANSACTION_TYPE));
            String date = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_NAME_DATE));
            Transaction transaction =new Transaction(new Date(date),account_no, ExpenseType.valueOf(trans_type),amount);
            transaction_list.add(transaction);
        }

        return  transaction_list;
    }
}
