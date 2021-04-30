package ph.apper.android.magtanong.expensetracker.model

data class Expense(val expense:String, var amount:Double, var category:ExpenseCategory) {

    // just copied this from ShowTracker, di ko alam ginagawa nito
    constructor() : this("", 0.0, ExpenseCategory.Others)
}

enum class ExpenseCategory {
    Wants,
    Needs,
    Recurring,
    Sporadic,
    Investment,
    Others;

    companion object {
        fun getCategory(category: String) =
            when(category) {
                "Wants" -> Wants
                "Needs" -> Needs
                "Recurring" -> Recurring
                "Sporadic" -> Sporadic
                "Investment" -> Investment
                else -> Others
            }
    }
}
