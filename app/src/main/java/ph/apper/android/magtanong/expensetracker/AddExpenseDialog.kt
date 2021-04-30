package ph.apper.android.magtanong.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_add_expense.view.*

class AddExpenseDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_add_expense, container, false)

        view.btn_cancel.setOnClickListener{
            dismiss()
        }

        view.btn_add.setOnClickListener {
            TODO()

            // logic where the expenses get added to the database
        }

        return view
    }
}