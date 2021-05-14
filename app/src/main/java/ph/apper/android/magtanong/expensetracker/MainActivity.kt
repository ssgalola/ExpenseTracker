package ph.apper.android.magtanong.expensetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ph.apper.android.magtanong.expensetracker.adapter.ExpenseAdapter
import ph.apper.android.magtanong.expensetracker.adapter.FragmentAdapter
import ph.apper.android.magtanong.expensetracker.database.DatabaseHandler

class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var expenseAdapter: ExpenseAdapter

        var item = 0
        fun updateAdapter() {
            expenseAdapter.notifyItemInserted(item)
            item += 1
        }
    }

    lateinit var fragmentAdapter: FragmentAdapter

    val pieFragment = PieFragment()
    val barFragment = BarFragment()

    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var expenseName: String? = intent!!.getStringExtra("Expense Name")
            var expenseAmount: Float? = intent!!.getFloatExtra("Expense Amount", 0F)
            var expenseCategory: String? = intent!!.getStringExtra("Expense Category")
            var expenseDateTime: String? = intent!!.getStringExtra("Expense DateTime")

            saveExpenseDB(expenseName.toString(),
                          expenseAmount.toString().toFloat(),
                          expenseCategory.toString(),
                          expenseDateTime.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentAdapter = FragmentAdapter(supportFragmentManager)
        fragmentAdapter.add(pieFragment, "Pie Chart")
        fragmentAdapter.add(barFragment, "Bar Graph")
        view_pager.adapter = fragmentAdapter

        fab_add.setOnClickListener {
            var dialog = AddExpenseDialog()
            dialog.show(supportFragmentManager, "addExpense")
        }

        rv_expense.layoutManager = LinearLayoutManager(this.applicationContext)
        expenseAdapter = ExpenseAdapter(AddExpenseDialog.expenseList, this.applicationContext)
        rv_expense.adapter = expenseAdapter

        setupReceiver()
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun setupReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("ph.apper.android.api.broadcast.SENDEXPENSE")
        registerReceiver(receiver, intentFilter)
    }

    fun saveExpenseDB(expense: String, amount: Float, category: String, datetime: String) {
        val expense = expense
        val amount = amount
        val category = category
        val datetime = datetime
        val databaseHandler = DatabaseHandler(this)

        val status = databaseHandler.addExpense(expense, amount, category, datetime)
        if (status > -1) {
            Toast.makeText(applicationContext, "Expense recorded.", Toast.LENGTH_SHORT).show()
        }
    }
}