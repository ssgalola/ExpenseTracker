package ph.apper.android.magtanong.expensetracker.model

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
