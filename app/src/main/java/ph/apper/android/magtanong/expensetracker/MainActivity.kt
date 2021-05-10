package ph.apper.android.magtanong.expensetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.charts.Pie
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime
import ph.apper.android.magtanong.expensetracker.adapter.ExpenseAdapter
import ph.apper.android.magtanong.expensetracker.adapter.FragmentAdapter
import ph.apper.android.magtanong.expensetracker.database.DatabaseHandler
import ph.apper.android.magtanong.expensetracker.model.Expense
import ph.apper.android.magtanong.expensetracker.model.ExpenseCategory
import ph.apper.android.magtanong.expensetracker.model.MY_COLORS
import java.util.ArrayList

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
            var expenseId: Int? = intent!!.getIntExtra("Expense ID", 0)

            saveExpenseDB(expenseName.toString(),
                          expenseAmount.toString().toFloat(),
                          expenseCategory.toString(),
                          expenseDateTime.toString(),
                          expenseId.toString().toInt())

            Log.d("bcast name", expenseName.toString())
            Log.d("bcast amount", expenseAmount.toString())
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

    fun saveExpenseDB(expense: String, amount: Float, category: String, datetime: String, id: Int) {
        val id = id
        val expense = expense
        val amount = amount
        val category = category
        val datetime = datetime
        val databaseHandler = DatabaseHandler(this)

        val status = databaseHandler.addExpense(expense, amount, category, datetime, id)
        if (status > -1) {
            Toast.makeText(applicationContext, "Expense recorded.", Toast.LENGTH_SHORT).show()
        }
    }
}