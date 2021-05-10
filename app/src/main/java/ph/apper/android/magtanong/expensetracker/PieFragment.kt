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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import ph.apper.android.magtanong.expensetracker.model.MY_COLORS
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.getValue
import kotlin.collections.indexOf
import kotlin.collections.indices
import kotlin.collections.set


class PieFragment : Fragment() {

    private var pieChart: PieChart? = null
    var categs = arrayOf("Needs", "Wants", "Savings", "Investments", "Others")
    var totals = floatArrayOf(0F, 0F, 0F, 0F, 0F)
    var entriesHashMap = HashMap<String, Float>()
    var entries = ArrayList<PieEntry>()

    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var expenseAmount: Float? = intent!!.getFloatExtra("Expense Amount", 0F)
            var expenseCategory: String? = intent!!.getStringExtra("Expense Category")

            Log.d("amount", expenseAmount.toString())
            Log.d("category", expenseCategory.toString())
            updatePieChart(expenseAmount.toString().toFloat(), expenseCategory.toString())
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
        var view = inflater.inflate(R.layout.fragment_pie, container, false)

        pieChart = view.findViewById(R.id.chart_pie)
        setupPieChart()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        pieChart = view.findViewById(R.id.chart_pie)
//        setupPieChart()
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

    private fun setupPieChart() {
        var nhg_bold: Typeface? = ResourcesCompat.getFont(context!!.applicationContext, R.font.nhg_bold)

//        val nhg_bold = Typeface.createFromAsset(activity!!.assets, "font/nhg_bold.ttf")

        pieChart!!.isDrawHoleEnabled = true
        pieChart!!.holeRadius = 50F
        pieChart!!.transparentCircleRadius = 53F
        pieChart!!.setUsePercentValues(true)
        pieChart!!.setEntryLabelTextSize(10f)
        pieChart!!.setEntryLabelColor(Color.WHITE)
        pieChart!!.setEntryLabelTypeface(nhg_bold)
        pieChart!!.setCenterTextTypeface(Typeface.DEFAULT_BOLD)

        pieChart!!.description.isEnabled = false

        pieChart!!.setNoDataText("Please add expenses.")
        pieChart!!.setNoDataTextColor(Color.BLACK)
        pieChart!!.setNoDataTextTypeface(nhg_bold)

        pieChart!!.setTouchEnabled(false)

        val l = pieChart!!.legend
        l.isEnabled = false
    }

    // load data
    private fun loadPieChartData() {
        entries.clear()
        var total = 0F
        for (i in categs.indices){
            if (totals[i] > 0F){
                var entry = PieEntry(entriesHashMap.getValue(categs[i]), categs[i])
                entries.add(entry)
                total += totals[i]
            }
        }

        val colors = ArrayList<Int>()
        for (color in MY_COLORS) {
            colors.add(color)
        }

        var nhg_roman: Typeface? = ResourcesCompat.getFont(context!!.applicationContext, R.font.nhg_roman)

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(nhg_roman)
        pieChart!!.data = data

        pieChart!!.centerText = "Total:\n$total"
        pieChart!!.setCenterTextSize(18f)

        pieChart!!.invalidate()
    }

    fun updatePieChart(amount: Float, category: String) {
        var i = categs.indexOf(category)
        entriesHashMap[category] = totals[i] + amount
        totals[i] += amount

        loadPieChartData()
    }
}