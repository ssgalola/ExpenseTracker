package ph.apper.android.magtanong.expensetracker.model

import android.graphics.Color
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.anychart.scales.DateTime
import ph.apper.android.magtanong.expensetracker.R
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Expense(val expense:String,
                   var amount:Float,
                   var category:ExpenseCategory,
                   val datetime: String) {

    constructor() : this("", 0F, ExpenseCategory.Others, "")
}

enum class ExpenseCategory {
    Needs,
    Wants,
    Savings,
    Investments,
    Others;

    companion object {
        fun getCategory(category: String) =
            when(category) {
                "Needs" -> Needs
                "Wants" -> Wants
                "Savings" -> Savings
                "Investments" -> Investments
                else -> Others
            }
    }
}

val MY_COLORS_PIE = intArrayOf(
    Color.rgb(185, 66, 255),
    Color.rgb(163, 63, 209),
    Color.rgb(157, 0, 249),
    Color.rgb(108, 0, 158),
    Color.rgb(67, 0, 107))

val MY_COLORS_BAR = intArrayOf(
    Color.rgb(185, 66, 255),
    Color.rgb(163, 63, 209),
    Color.rgb(157, 0, 249),
    Color.rgb(108, 0, 158),
    Color.rgb(67, 0, 107),
    Color.rgb(100,50,168),
    Color.rgb(185, 66, 255),
    Color.rgb(163, 63, 209),
    Color.rgb(157, 0, 249),
    Color.rgb(108, 0, 158),
    Color.rgb(67, 0, 107),
    Color.rgb(100,50,168))

val lightGrey = Color.rgb(235,237,237)
