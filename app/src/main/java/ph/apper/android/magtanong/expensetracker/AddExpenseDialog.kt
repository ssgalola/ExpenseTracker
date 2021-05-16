package ph.apper.android.magtanong.expensetracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.anychart.scales.DateTime
import kotlinx.android.synthetic.main.fragment_add_expense.*
import kotlinx.android.synthetic.main.fragment_add_expense.view.*
import ph.apper.android.magtanong.expensetracker.model.Expense
import ph.apper.android.magtanong.expensetracker.model.ExpenseCategory
import java.util.*

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

        var expenseDate = ""
        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            expenseDate = "$month/$day/$year"
        }

        view.btn_cancel.setOnClickListener {
            dismiss()
        }

        view.findViewById<Button>(R.id.btn_add).setOnClickListener {
            var expense_name = et_add_expense.text.toString()
            var amount = et_add_amount.text.toString()
            var category = spinner_categ.selectedItem as String

            if(expense_name.isEmpty()){
                et_add_expense.error = "Expense name is required."
                et_add_expense.requestFocus()
                return@setOnClickListener
            }
            if(amount.isEmpty()) {
                et_add_amount.error = "Amount is required."
                et_add_amount.requestFocus()
                return@setOnClickListener
            }

            if (amount.toFloat() == 0F){
                et_add_amount.error = "Amount must be greater than zero."
                et_add_amount.requestFocus()
                return@setOnClickListener
            }

            if(expense_name.isNotEmpty() and amount.isNotEmpty()) {
                var expense = Expense(expense_name,
                                      amount.toFloat(),
                                      ExpenseCategory.getCategory(category),
                                      expenseDate)

                addExpense(expense)
                broadcastExpense(expense)
                dismiss()
            }
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
            it.putExtra("Expense Name", expense.expense)
            it.putExtra("Expense Amount", expense.amount)
            it.putExtra("Expense Category", expense.category.toString())
            it.putExtra("Expense DateTime", expense.datetime)
            context!!.sendBroadcast(it)
        }
    }
}