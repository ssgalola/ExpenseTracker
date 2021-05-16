package ph.apper.android.magtanong.expensetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import org.joda.time.DateTime
import ph.apper.android.magtanong.expensetracker.model.MY_COLORS_BAR
import java.util.ArrayList

class BarFragment : Fragment() {

    private var barChart: BarChart? = null
    var months = floatArrayOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F)
    var totals = floatArrayOf(0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F)
    var entriesHashMap: HashMap<Float, Float> =  hashMapOf(1F to 0F,)
    var entries = ArrayList<BarEntry>()

    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var expenseAmount: Float? = intent!!.getFloatExtra("Expense Amount", 0F)
            var expenseCategory: String? = intent!!.getStringExtra("Expense Category")
            var expenseMonth = intent!!.getStringExtra("Expense DateTime")
            var strExpenseMonth = expenseMonth.toString().take(2)
            if (strExpenseMonth[1] ==  '/'){
                strExpenseMonth = strExpenseMonth[0].toString()
            }

            Log.d("bar_date", expenseMonth.toString())

            updateBarChart(expenseAmount.toString().toFloat(), strExpenseMonth.toFloat())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupReceiver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_bar, container, false)

        barChart = view.findViewById(R.id.chart_bar)
        setupBarChart()

        return view
    }

    override fun onDestroyView() {
        activity!!.unregisterReceiver(receiver)
        super.onDestroyView()
    }

    private fun setupReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("ph.apper.android.api.broadcast.SENDEXPENSE")
        activity!!.registerReceiver(receiver, intentFilter)
    }

    private fun setupBarChart() {
        var nhg_bold: Typeface? = ResourcesCompat.getFont(context!!.applicationContext, R.font.nhg_bold)

        var left: YAxis = barChart!!.getAxis(YAxis.AxisDependency.LEFT)
        var right: YAxis = barChart!!.getAxis(YAxis.AxisDependency.RIGHT)

        barChart!!.setDrawGridBackground(false)
        barChart!!.setDrawBorders(false)
        barChart!!.xAxis.setDrawGridLines(false)
        barChart!!.xAxis.setDrawLabels(true)
        barChart!!.xAxis.setDrawAxisLine(false)
        barChart!!.xAxis.typeface = nhg_bold
        barChart!!.description.isEnabled = false

        left.setDrawGridLines(false)
        left.setDrawAxisLine(false)
        left.setDrawLabels(false)

        right.setDrawGridLines(false)
        right.setDrawAxisLine(false)
        right.setDrawLabels(false)

        val l = barChart!!.legend
        l.isEnabled = false
    }

    private fun loadBarChartData() {
        entries.clear()
        for (i in months.indices) {
            entries.add(BarEntry(months[i], totals[i]))
        }

        val colors = ArrayList<Int>()
        for (color in MY_COLORS_BAR) {
            colors.add(color)
        }

        var nhg_roman: Typeface? = ResourcesCompat.getFont(context!!.applicationContext, R.font.nhg_roman)

        val dataSet = BarDataSet(entries, "Months")
        dataSet.colors = colors
        val data = BarData()
        data.addDataSet(dataSet)
        data.setDrawValues(true)
        data.setValueTextSize(12f)
        data.setValueTypeface(nhg_roman)

        barChart!!.data = data
        barChart!!.invalidate()
    }

    fun updateBarChart(amount: Float, month: Float) {
        var i = month.toInt() - 1
        Log.d("index", i.toString())
        totals[i] += amount

        loadBarChartData()
    }

}

/*
to-do:
1. fix UI of bar chart
2. pwede ba white & purple ulit yung theme --GGGG

paalis ng grid and kahit x axis na lang
*/