package ph.apper.android.magtanong.expensetracker

import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChartView
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import java.util.ArrayList

class ExpenseChart : AppCompatActivity() {
    var anyChartView: AnyChartView? = null
    var categs = arrayOf("Wants", "Recurring", "Needs")
    var earnings = intArrayOf(500, 800, 2000)
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