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
import ph.apper.android.magtanong.expensetracker.adapter.ExpenseAdapter
import ph.apper.android.magtanong.expensetracker.adapter.FragmentAdapter
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
            var expenseAmount: Float? = intent!!.getFloatExtra("Expense Amount", 0F)
            var expenseCategory: String? = intent!!.getStringExtra("Expense Category")

            Log.d("amount", expenseAmount.toString())
            Log.d("category", expenseCategory.toString())
//            updatePieChart(expenseAmount.toString().toFloat(), expenseCategory.toString())
        }
    }

    private var pieChart: PieChart? = null
    var categs = arrayOf("Needs", "Wants", "Savings", "Investments", "Others")
    var totals = floatArrayOf(0F, 0F, 0F, 0F, 0F)
    var entriesHashMap = HashMap<String, Float>()
    var entries = ArrayList<PieEntry>()

    // <--bar chart code-->
    private var barChart: BarChart? = null
    var entriesMonth = ArrayList<BarEntry>()
    var months = floatArrayOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F) // float dapat data type talaga
    var monthTotals = floatArrayOf(200F, 300F, 100F, 400F, 250F, 300F, 200F, 300F, 100F, 400F, 250F, 300F)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        pieChart = findViewById(R.id.chart_pie)

//        setupPieChart()

        fragmentAdapter = FragmentAdapter(supportFragmentManager)
        fragmentAdapter.add(pieFragment, "Pie Chart")
        fragmentAdapter.add(barFragment, "Bar Graph")
        view_pager.adapter = fragmentAdapter

        // open 'add expense' dialog window
        fab_add.setOnClickListener {
            var dialog = AddExpenseDialog()

            dialog.show(supportFragmentManager, "addExpense")
        }

        rv_expense.layoutManager = LinearLayoutManager(this.applicationContext)
        expenseAdapter = ExpenseAdapter(AddExpenseDialog.expenseList, this.applicationContext)
        rv_expense.adapter = expenseAdapter

        setupReceiver()
    }

    // format pie chart
//    private fun setupPieChart() {
//        var nhg_bold: Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.nhg_bold)
//
//        pieChart!!.isDrawHoleEnabled = true
//        pieChart!!.holeRadius = 50F
//        pieChart!!.transparentCircleRadius = 53F
//        pieChart!!.setUsePercentValues(true)
//        pieChart!!.setEntryLabelTextSize(10f)
//        pieChart!!.setEntryLabelColor(Color.WHITE)
//        pieChart!!.setEntryLabelTypeface(nhg_bold)
//        pieChart!!.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
//
//        pieChart!!.description.isEnabled = false
//
//        pieChart!!.setNoDataText("Please add expenses.")
//        pieChart!!.setNoDataTextColor(Color.BLACK)
//        pieChart!!.setNoDataTextTypeface(nhg_bold)
//
//        val l = pieChart!!.legend
//        l.isEnabled = false
//    }
//
//    // load data
//    private fun loadPieChartData() {
//        entries.clear()
//        var total = 0F
//        for (i in categs.indices){
//            if (totals[i] > 0F){
//                var entry = PieEntry(entriesHashMap.getValue(categs[i]), categs[i])
//                entries.add(entry)
//                total += totals[i]
//            }
//        }
//
//        val colors = ArrayList<Int>()
//        for (color in MY_COLORS) {
//            colors.add(color)
//        }
//
//        var nhg_roman: Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.nhg_roman)
//
//        val dataSet = PieDataSet(entries, "")
//        dataSet.colors = colors
//        val data = PieData(dataSet)
//        data.setDrawValues(true)
//        data.setValueFormatter(PercentFormatter(pieChart))
//        data.setValueTextSize(12f)
//        data.setValueTextColor(Color.WHITE)
//        data.setValueTypeface(nhg_roman)
//        pieChart!!.data = data
//
//        pieChart!!.centerText = "Total:\n$total"
//        pieChart!!.setCenterTextSize(18f)
//
//        pieChart!!.invalidate()
//    }
//
//    fun updatePieChart(amount:Float, category: String) {
//        var i = categs.indexOf(category)
//        entriesHashMap[category] = totals[i] + amount
//        totals[i] += amount
//
//        loadPieChartData()
//    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun setupReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("ph.apper.android.api.broadcast.SENDEXPENSE")
        registerReceiver(receiver, intentFilter)
    }

    // <--bar chart code-->
    private fun loadBarChartData() {
        for (i in months.indices) {
            entriesMonth.add(BarEntry(months[i], monthTotals[i]))
        }

        val dataSet = BarDataSet(entriesMonth, "Months")
        val data = BarData()

        data.addDataSet(dataSet)
        barChart!!.data = data
        barChart!!.invalidate()
    }
}