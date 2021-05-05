package ph.apper.android.magtanong.expensetracker.model

import android.graphics.Color
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import ph.apper.android.magtanong.expensetracker.R

data class Expense(val expense:String, var amount:Float, var category:ExpenseCategory) {

    // just copied this from ShowTracker, di ko alam ginagawa nito
    constructor() : this("", 0F, ExpenseCategory.Others)
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

val MY_COLORS = intArrayOf(
    Color.rgb(163, 63, 209),
    Color.rgb(108, 0, 158),
    Color.rgb(154, 100, 179),
    Color.rgb(57, 21, 74),
    Color.rgb(114, 97, 122))


