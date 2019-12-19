package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

public class PersistentExpenseManager extends ExpenseManager {
    private transient Context context;

    public PersistentExpenseManager(Context context) throws ExpenseManagerException {
        this.context = context;
        setup();
    }

    @Override
    public void setup() throws ExpenseManagerException {
        SQLiteDatabase database=new DBHelper(context).getWritableDatabase();
        TransactionDAO inMemoryTransactionDAO = new PersistentTransactionDAO(database);
        setTransactionsDAO(inMemoryTransactionDAO);

        AccountDAO inMemoryAccountDAO = new PersistentAccountDAO(database);
        setAccountsDAO(inMemoryAccountDAO);
    }
}
