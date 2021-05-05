package ph.apper.android.magtanong.expensetracker

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
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

    private var pieChart: PieChart? = null
    var entries = ArrayList<PieEntry>()
    var categs = arrayOf("Needs", "Wants", "Savings", "Investments", "Others")
    var totals = floatArrayOf(500F, 800F, 1000F, 700F, 200F)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pieChart = findViewById(R.id.chart_pie)
        setupPieChart()
        loadPieChartData()

        // open 'add expense' dialog window
        fab_add.setOnClickListener {
            var dialog = AddExpenseDialog()

            dialog.show(supportFragmentManager, "addExpense")
        }

        rv_expense.layoutManager = LinearLayoutManager(this.applicationContext)
        expenseAdapter = ExpenseAdapter(AddExpenseDialog.expenseList, this.applicationContext)
        rv_expense.adapter = expenseAdapter
    }

    // format pie chart
    private fun setupPieChart() {
        pieChart!!.isDrawHoleEnabled = true
        pieChart!!.holeRadius = 50F
        pieChart!!.transparentCircleRadius = 53F
        pieChart!!.setUsePercentValues(true)
        pieChart!!.setEntryLabelTextSize(10f)
        pieChart!!.setEntryLabelColor(Color.BLACK)
        pieChart!!.setCenterTextTypeface(Typeface.DEFAULT_BOLD)

        pieChart!!.setCenterTextSize(20f)
        pieChart!!.description.isEnabled = false
    }

    // load data
    private fun loadPieChartData() {
        for (i in categs.indices) {
            entries.add(PieEntry(totals[i], categs[i]))
        }
        // entries.clear();

        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart!!.data = data
        pieChart!!.invalidate()

    }

    fun updatePieChart(expense: Expense) {
        var e_name = expense.expense
        var e_amount = expense.amount
        var e_category = expense.category.toString()

        when (e_category) {
            "Needs" -> totals[0] += e_amount
            "Wants" -> totals[1] += e_amount
            "Savings" -> totals[2] += e_amount
            "Investment" -> totals[3] += e_amount
            "Others" -> totals[4] += e_amount
        }
        loadPieChartData()
    }
}


/*
--ganto comments ni sharmaine--

to-do:
1. [fixed] show categories in spinner --sori nagawa ko na to bago ko nabasa notes ahu--
2. [fixed] fix the main activity layout (the chart and recyclerview alignment is weird)
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