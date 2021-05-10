package ph.apper.android.magtanong.expensetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import ph.apper.android.magtanong.expensetracker.model.MY_COLORS
import java.util.ArrayList

class BarFragment : Fragment() {
//
//    private var barChart: BarChart? = null
//    var entriesMonth = ArrayList<BarEntry>()
//    var months = floatArrayOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F)
//    var monthTotals = floatArrayOf(200F, 300F, 100F, 400F, 250F, 300F, 200F, 300F, 100F, 400F, 250F, 300F)
//
//
//    private val receiver = object : BroadcastReceiver(){
//        override fun onReceive(context: Context?, intent: Intent?) {
//            var expenseAmount: Float? = intent!!.getFloatExtra("Expense Amount", 0F)
//            var expenseCategory: String? = intent!!.getStringExtra("Expense Category")
//            var expenseDateTime: String? = intent!!.getStringExtra("Expense DateTime")
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setupReceiver()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        var view = inflater.inflate(R.layout.fragment_pie, container, false)
//
//        barChart = view.findViewById(R.id.chart_bar)
//        loadBarChartData()
//
//        return view
//    }
//
//    override fun onDestroyView() {
//        activity!!.unregisterReceiver(receiver)
//        super.onDestroyView()
//    }
//
//    private fun setupReceiver(){
//        val intentFilter = IntentFilter()
//        intentFilter.addAction("ph.apper.android.api.broadcast.SENDEXPENSE")
//        activity!!.registerReceiver(receiver, intentFilter)
//    }
//
//    private fun loadBarChartData() {
//        for (i in months.indices) {
//            entriesMonth.add(BarEntry(months[i], monthTotals[i]))
//        }
//
//        val dataSet = BarDataSet(entriesMonth, "Months")
//        val data = BarData()
//
//        data.addDataSet(dataSet)
//        barChart!!.data = data
//        barChart!!.invalidate()
//    }
//
//    fun updateBarChart(amount: Float, category: String) {
//    }

}