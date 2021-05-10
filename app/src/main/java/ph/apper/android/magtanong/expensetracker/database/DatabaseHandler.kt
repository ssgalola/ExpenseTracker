package ph.apper.android.magtanong.expensetracker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ph.apper.android.magtanong.expensetracker.model.Expense

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "expenseTrackerDB"
        private val TABLE_EXPENSES = "expensesTable"
        private val KEY_ID = "id"
        private val KEY_EXPENSE = "expense"
        private val KEY_CATEGORY = "category"
        private val KEY_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXPENSES_TABLE =
                ("CREATE TABLE " + TABLE_EXPENSES + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY," +
                        KEY_EXPENSE + " TEXT," +
                        KEY_CATEGORY + " TEXT," +
                        KEY_AMOUNT + " FLOAT" +
                        ")")

        db?.execSQL(CREATE_EXPENSES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES)
        onCreate(db)
    }

    fun addExpense(expenseData: Expense): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        // populate data
        //contentValues.put(KEY_ID, expenseData.id)
        contentValues.put(KEY_EXPENSE, expenseData.expense)
        contentValues.put(KEY_CATEGORY, expenseData.category.toString())
        contentValues.put(KEY_AMOUNT, expenseData.amount)

        // insert row
        val success = db.insert(TABLE_EXPENSES, null, contentValues)
        db.close()

        return success
    }

    /*
    fun deleteExpense(expenseData: Expense): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        // populate data
        contentValues.put(KEY_ID, expenseData.id)

        // delete row
        val success = db.delete(TABLE_EXPENSES, "id="+expenseData.id, null)
        db.close()

        return success
    }
    */

}