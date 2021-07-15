package uevora.di.gbl

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import uevora.di.gbl.firebase.FirebaseOps

class CreateListFragment : DialogFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var mDialogView: View = inflater.inflate(R.layout.create_list_dialog, container, false)

        mDialogView.findViewById<Button>(R.id.cancel_create_list_btn).setOnClickListener {
            dismiss()
        }

        mDialogView.findViewById<Button>(R.id.create_list_btn).setOnClickListener {
            val listNameField = mDialogView.findViewById<EditText>(R.id.listName)
            val listName = listNameField.text.toString()

            this.createList(listName)

            dismiss()
        }

        return mDialogView
    }

    private fun createList(listName: String) {
        val mContext: Context? = context
        FirebaseOps.writeListNameToFirebase(listName, mContext)
    }
}