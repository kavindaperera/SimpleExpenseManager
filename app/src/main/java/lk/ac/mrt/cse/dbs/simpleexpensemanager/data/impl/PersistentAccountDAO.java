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
        Cursor cursor = database.rawQuery("SELECT * FROM "+AccountTable.TABLE_NAME,null);
        List<Account> account_list = new ArrayList<>();
        while(cursor.moveToNext()){
            String account_no = cursor.getString(cursor.getColumnIndex(AccountTable.COLUMN_NAME_ACCOUNT_NO));
            String bank_name = cursor.getString(cursor.getColumnIndex(AccountTable.COLUMN_NAME_BANK_NAME));
            String account_holder_name = cursor.getString(cursor.getColumnIndex(AccountTable.COLUMN_NAME_ACCOUNT_HOLDER_NAME));
            double balance = cursor.getDouble(cursor.getColumnIndex(AccountTable.COLUMN_NAME_INITIAL_BALANCE));
            Account account = new Account(account_no, bank_name, account_holder_name, balance);
        }

        return account_list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor cursor = database.rawQuery("SELECT * FROM " + AccountTable.TABLE_NAME + " WHERE " + AccountTable.COLUMN_NAME_ACCOUNT_NO + " = '" + accountNo + "';", null);
        if (cursor.getCount() == 0) {
            throw new InvalidAccountException("Account is invalid");
        } else {
            cursor.moveToFirst();
            String bank_name = cursor.getString(cursor.getColumnIndex(AccountTable.COLUMN_NAME_BANK_NAME));
            String account_holder_name = cursor.getString(cursor.getColumnIndex(AccountTable.COLUMN_NAME_ACCOUNT_HOLDER_NAME));
            double balance = cursor.getDouble(cursor.getColumnIndex(AccountTable.COLUMN_NAME_INITIAL_BALANCE));
            Account account = new Account(accountNo, bank_name, account_holder_name, balance);
            return account;
        }
    }
    @Override
    public void addAccount(Account account) {

            SQLiteStatement statement=database.compileStatement("INSERT INTO "+ AccountTable.TABLE_NAME+"("+ AccountTable.COLUMN_NAME_ACCOUNT_NO+","+ AccountTable.COLUMN_NAME_BANK_NAME+","+
                    AccountTable.COLUMN_NAME_ACCOUNT_HOLDER_NAME+","+ AccountTable.COLUMN_NAME_INITIAL_BALANCE+") VALUES(?,?,?,?);");
            statement.bindString(1,account.getAccountNo());
            statement.bindString(2,account.getBankName());
            statement.bindString(3,account.getAccountHolderName());
            statement.bindDouble(4,account.getBalance());
            try{
                statement.executeInsert();
            }
            catch (SQLiteConstraintException ex){
                Log.e("Error","Integrity error occurred");
            }
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteStatement statement=database.compileStatement("DELETE FROM "+ AccountTable.TABLE_NAME+" WHERE "+ AccountTable.COLUMN_NAME_ACCOUNT_NO+" = ?;"); //avoid sql injection
        statement.bindString(1,accountNo);
        statement.executeUpdateDelete();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account=getAccount(accountNo);

        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }
        SQLiteStatement statement=database.compileStatement("UPDATE "+ AccountTable.TABLE_NAME+" SET "+ AccountTable.COLUMN_NAME_INITIAL_BALANCE+" = ? WHERE "+
                AccountTable.COLUMN_NAME_ACCOUNT_NO+" = ? ;" );
        statement.bindDouble(1,account.getBalance());
        statement.bindString(2,accountNo);
        statement.executeUpdateDelete();
    }
}
