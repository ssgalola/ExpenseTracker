package ph.apper.android.magtanong.expensetracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_add_expense.*
import kotlinx.android.synthetic.main.fragment_add_expense.view.*
import ph.apper.android.magtanong.expensetracker.model.Expense
import ph.apper.android.magtanong.expensetracker.model.ExpenseCategory
import java.util.ArrayList

class  AddExpenseDialog : DialogFragment() {

    companion object {
        var expenseList: ArrayList<Expense> = ArrayList()

        fun addExpense(expense: Expense) {
            expenseList.add(expense)
            MainActivity.updateAdapter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_add_expense, container, false)

        view.btn_cancel.setOnClickListener {
            dismiss()
        }

        view.btn_add.setOnClickListener {

            var expense_name = et_add_expense.text.toString()
            var amount = et_add_amount.text.toString()
            var category = spinner_categ.selectedItem as String

            Log.d("amount @ btn", amount.toString())

            var expense = Expense(expense_name, amount.toFloat(), ExpenseCategory.getCategory(category))
            addExpense(expense)

            broadcastExpense(expense)

            dismiss()

            // logic where the expenses get added to the database
            //--saka na database, implement ko muna yung pag add into items sa recycler view
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

    }

    private fun init(view: View) {
        var categoryArray = arrayOf(
            ExpenseCategory.Needs.toString(),
            ExpenseCategory.Wants.toString(),
            ExpenseCategory.Savings.toString(),
            ExpenseCategory.Investments.toString(),
            ExpenseCategory.Others.toString()
        )

        val arrayCategoryAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, categoryArray)
        spinner_categ.adapter = arrayCategoryAdapter

        spinner_categ.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("Spinner Data", "msg: Selected Type: ${categoryArray[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }
        }
    }

    fun broadcastExpense(expense: Expense){
        Intent().also {
            it.setAction("ph.apper.android.api.broadcast.SENDEXPENSE")
            it.putExtra("Expense Amount", expense.amount)
            it.putExtra("Expense Category", expense.category.toString())
            context!!.sendBroadcast(it)

            Log.d("amount@broadcastExpense", expense.amount.toString())
        }
    }
}