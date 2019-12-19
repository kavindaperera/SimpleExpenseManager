package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DBContract.AccountTable;

public class PersistentAccountDAO implements AccountDAO {

    private transient SQLiteDatabase database;

    public PersistentAccountDAO(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public List<String> getAccountNumbersList() {
        Cursor cursor = database.rawQuery("SELECT "+ AccountTable.COLUMN_NAME_ACCOUNT_NO+" FROM "+ AccountTable.TABLE_NAME, null);
        List<String> account_number_list = new ArrayList<>();
        while (cursor.moveToNext()){
            String account_no = cursor.getString(cursor.getColumnIndex(AccountTable.COLUMN_NAME_ACCOUNT_NO));
            account_number_list.add(account_no);
        }
        return account_number_list;
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor cursor = database.rawQuery
        return null;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return null;
    }

    @Override
    public void addAccount(Account account) {

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
