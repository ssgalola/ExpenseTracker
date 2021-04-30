package ph.apper.android.magtanong.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_expense.*
import ph.apper.android.magtanong.expensetracker.adapter.ExpenseAdapter
import ph.apper.android.magtanong.expensetracker.model.Expense
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var expenseAdapter: ExpenseAdapter

        fun updateAdapter() {
            expenseAdapter.notifyItemInserted(0)
        }
    }

    var anyChartView: AnyChartView? = null
    var categs = arrayOf("Wants", "Needs", "Recurring", "Sporadic", "Investment", "Others")
    var earnings = doubleArrayOf(500.00, 800.00, 1000.00, 700.00, 1500.00, 300.00)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        anyChartView = findViewById(R.id.chart_pie)
        setupPieChart();

        // open 'add expense' dialog window
        fab_add.setOnClickListener {
            var dialog = AddExpenseDialog()

            dialog.show(supportFragmentManager, "addExpense")
        }

        rv_expense.layoutManager = LinearLayoutManager(this.applicationContext)
        expenseAdapter = ExpenseAdapter(AddExpenseDialog.expenseList, this.applicationContext)
        rv_expense.adapter = expenseAdapter
    }

    // need to decouple this, put it in ExpenseAdapter? basta kung san din uupdate yung RecyclerView
    fun setupPieChart() {
        val pie = AnyChart.pie()
        val dataEntries: MutableList<DataEntry> = ArrayList()
        for (i in categs.indices) {
            dataEntries.add(ValueDataEntry(categs[i], earnings[i]))
        }
        pie.data(dataEntries)
        anyChartView!!.setChart(pie)
    }

    fun updatePieChart(expense: Expense) {
        var e_name = expense.expense
        var e_amount = expense.amount
        var e_category = expense.category.toString()

        when (e_category) {
            "Wants" -> earnings[0] += e_amount
            "Needs" -> earnings[1] += e_amount
            "Recurring" -> earnings[2] += e_amount
            "Sporadic" -> earnings[3] += e_amount
            "Investment" -> earnings[4] += e_amount
            "Others" -> earnings[5] += e_amount
        }
        setupPieChart()
    }
}


/*
--ganto comments ni sharmaine--

to-do:
1. show categories in spinner --sori nagawa ko na to bago ko nabasa notes ahu--
2. fix the main activity layout (the chart and recyclerview alignment is weird)
    --pacheck nung sinend kong pie chart sa telegram paapprove lang boss then integrate ko--
3. cardview layout
4. ako gagawa nito ah

pag mas marami nang code:
1. integrate pie chart with expense tracker data
2. decouple pie chart code
3. others

gagawin ko dapat pero baka gusto mo (di ako nagpaparinig, inooffer ko talaga):
--dami pang sinabi gagawin ko naman talaga HAHA--
1. UI tweaks
2. change app icon
--pero ez lang naman to ako bahala--

--gusto ko sana mag add ng:
1. delete all expenses per category
2. delete all expenses
3. edit expense (pero mahirap to so optional lang yan pag sinipag charot)
4. warning na bawal empty ang expense and amount
5. database (eto non-negotiable to gagawin ko talaga pero sa dulo na)
*/