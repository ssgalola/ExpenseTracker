package ph.apper.android.magtanong.expensetracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_expense.view.*
import kotlinx.android.synthetic.main.item_expense_view.view.*
import ph.apper.android.magtanong.expensetracker.R
import ph.apper.android.magtanong.expensetracker.model.Expense
import java.time.LocalDateTime

class ExpenseAdapter (val expenseList: ArrayList<Expense>, val context: Context) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

        class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val expense_name = view.tv_expense_name
            val amount = view.tv_amount
            val category = view.tv_category
            val dateTime = view.tv_datetime
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
            return ExpenseViewHolder(LayoutInflater.from(context).inflate(R.layout.item_expense_view, parent, false))
        }

        override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
            holder?.expense_name.text = expenseList.get(position).expense
            holder?.amount.text = expenseList.get(position).amount.toString()
            holder?.category.text = expenseList.get(position).category.toString()
            holder?.dateTime.text = expenseList.get(position).datetime.toString()
        }

        override fun getItemCount(): Int {
            return expenseList.size
        }

}