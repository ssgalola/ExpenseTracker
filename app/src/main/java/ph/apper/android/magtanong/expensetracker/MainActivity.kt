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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
import ph.apper.android.magtanong.expensetracker.adapter.ExpenseAdapter
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

    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var expenseAmount: Float? = intent!!.getFloatExtra("Expense Amount", 0F)
            var expenseCategory: String? = intent!!.getStringExtra("Expense Category")

            Log.d("amount", expenseAmount.toString())
            Log.d("category", expenseCategory.toString())
            updatePieChart(expenseAmount.toString().toFloat(), expenseCategory.toString())
        }
    }

    private var pieChart: PieChart? = null
    var categs = arrayOf("Needs", "Wants", "Savings", "Investments", "Others")
    var totals = floatArrayOf(0F, 0F, 0F, 0F, 0F)
    var entries = ArrayList<PieEntry>()


    fun populateEntries(){
        entries.add(PieEntry(totals[0], categs[0]))
        entries.add(PieEntry(totals[1], categs[1]))
        entries.add(PieEntry(totals[2], categs[2]))
        entries.add(PieEntry(totals[3], categs[3]))
        entries.add(PieEntry(totals[4], categs[4]))
    }

    /*
    fun populateEntries() {
        for (i in categs.indices) {
            if (totals[i] > 0F) {
                entries.add(PieEntry(totals[i], categs[i]))
            } else {
                entries.add(PieEntry(totals[i], ""))
            }
        }
    }
    */

    fun populateEntries(totals:Float, categs:String) {
        entries.add(PieEntry(totals, categs))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pieChart = findViewById(R.id.chart_pie)

        populateEntries()
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

        setupReceiver()
    }

    // format pie chart
    private fun setupPieChart() {
        var nhg_bold: Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.nhg_bold)

        pieChart!!.isDrawHoleEnabled = true
        pieChart!!.holeRadius = 50F
        pieChart!!.transparentCircleRadius = 53F
        pieChart!!.setUsePercentValues(true)
        pieChart!!.setEntryLabelTextSize(10f)
        pieChart!!.setEntryLabelColor(Color.WHITE)
        pieChart!!.setEntryLabelTypeface(nhg_bold)
        pieChart!!.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart!!.setCenterTextSize(20f)
        pieChart!!.description.isEnabled = false

        val l = pieChart!!.legend
        l.isEnabled = false
    }

    // load data
    private fun loadPieChartData() {
        for (i in categs.indices) {
            var entry = PieEntry(totals[i], categs[i])
            entries.set(i, entry)
        }
        //--binago ko kasi kapag add entry lagi, nagkakaroon ng bagong entries
        //kahit same categories

        // entries.clear(); <- clear entries

        val colors = ArrayList<Int>()
        for (color in MY_COLORS) {
            colors.add(color)
        }

        var nhg_roman: Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.nhg_roman)

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(nhg_roman)
        pieChart!!.data = data
        pieChart!!.invalidate()
    }

    fun updatePieChart(amount:Float, category: String) {
        when (category) {
            "Needs" -> totals[0] += amount
            "Wants" -> totals[1] += amount
            "Savings" -> totals[2] += amount
            "Investment" -> totals[3] += amount
            "Others" -> totals[4] += amount
        }
        loadPieChartData()
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
}


/*
--ganto comments ni sharmaine--

to-do:
1. [fixed] show categories in spinner --sori nagawa ko na to bago ko nabasa notes ahu--
2. [fixed] fix the main activity layout (the chart and recyclerview alignment is weird)
    --pacheck nung sinend kong pie chart sa telegram paapprove lang boss then integrate ko--
3. cardview layout
4. night mode proof

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
5. database (eto non-negotiable to gagawin ko talaga pero sa dulo na) --clown noises--
*/