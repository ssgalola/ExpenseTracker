package ph.apper.android.magtanong.expensetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.core.gauge.pointers.Bar
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import org.joda.time.DateTime
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
            var expenseMonth = DateTime.now().monthOfYear.toFloat()

            Log.d("bar_amount", expenseAmount.toString())

            updateBarChart(expenseAmount.toString().toFloat(), expenseMonth)
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
        barChart!!.setNoDataText("Bar Chart")

        val l = barChart!!.legend
        l.isEnabled = false
    }

    private fun loadBarChartData() {
        entries.clear()
        for (i in months.indices) {
            entries.add(BarEntry(months[i], totals[i]))
        }

        Log.d("entries", entries.toString())

        val dataSet = BarDataSet(entries, "Months")
        val data = BarData()

        data.addDataSet(dataSet)

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