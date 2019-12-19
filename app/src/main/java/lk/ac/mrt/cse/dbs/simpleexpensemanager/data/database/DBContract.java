package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.provider.BaseColumns;

/**
 * Created by 170446L
 */

public class DBContract {
    private DBContract(){}

    public static class AccountTable implements BaseColumns{
        public static final String TABLE_NAME = "accounts";
        public static final String COLUMN_NAME_ACCOUNT_NO = "account_no";
        public static final String COLUMN_NAME_BANK_NAME = "bank_name";
        public static final String COLUMN_NAME_ACCOUNT_HOLDER_NAME ="account_holder_name";
        public static final String COLUMN_NAME_INITIAL_BALANCE ="balance";
    }

    public static class TransactionTable implements BaseColumns{
        public static final String TABLE_NAME = "transactions";
        public static final String COLUMN_NAME_ACCOUNT_NO="account_no";
        public static final String COLUMN_NAME_TRANSACTION_TYPE="trans_type";
        public static final String COLUMN_NAME_AMOUNT="amount";
        public static final String COLUMN_NAME_DATE="date";
    }
}
