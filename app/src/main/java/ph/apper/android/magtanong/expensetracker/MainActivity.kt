package ph.apper.android.magtanong.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var anyChartView: AnyChartView? = null
    var categs = arrayOf("Wants", "Needs", "Recurring", "Sporadic", "Investment", "Others")
    var earnings = intArrayOf(500, 800, 1000, 700, 1500, 300)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        anyChartView = findViewById(R.id.chart_pie)

        setupPieChart();
    }

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