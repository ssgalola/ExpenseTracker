package ph.apper.android.magtanong.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

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
}

/*
to-do:
1. show categories in spinner
2. fix the main activity layout (the chart and recyclerview alignment is weird)
3. cardview layout
4. ako gagawa nito ah

pag mas marami nang code:
1. integrate pie chart with expense tracker data
2. decouple pie chart code
3. others

gagawin ko dapat pero baka gusto mo (di ako nagpaparinig, inooffer ko talaga):
1. UI tweaks
2. change app icon
*/