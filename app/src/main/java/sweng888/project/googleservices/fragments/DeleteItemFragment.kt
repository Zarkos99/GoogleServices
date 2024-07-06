package sweng888.project.googleservices.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import sweng888.project.googleservices.R
import java.util.Locale

class DeleteItemFragment : Fragment(), View.OnClickListener {

    private lateinit var m_item_name_edit_text: EditText
    private lateinit var m_confirm_button: Button
    private lateinit var m_clear_button: Button
    private lateinit var m_firebase_database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_remove_magic_item, container, false)

        m_firebase_database = Firebase.database.reference
        m_item_name_edit_text = rootView.findViewById(R.id.remove_item_name)

        // Get references to the Button views
        m_confirm_button = rootView.findViewById(R.id.button_confirm)
        m_clear_button = rootView.findViewById(R.id.button_clear)

        m_confirm_button.setOnClickListener(this)
        m_clear_button.setOnClickListener(this)

        return rootView
    }

    override fun onClick(view: View) {
        when (view.getId()) {
            R.id.button_confirm -> confirm()
            R.id.button_clear -> clearFields()
            else -> {}
        }
    }

    fun getItemNameUserInput(): String {
        var user_input = m_item_name_edit_text.text.toString()
            .trim()
        var lowercase_string = ""
        if (user_input.isEmpty()) {
            return user_input
        } else if (user_input.length > 1) {
            lowercase_string = user_input.slice(IntRange(1, user_input.length - 1))
        }

        return user_input[0].uppercase(Locale.ROOT) + lowercase_string
    }

    fun confirm() {
        val name = getItemNameUserInput()

        if (name.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        m_firebase_database.child("Magic Items").child(name)
            .setValue(null).addOnSuccessListener {
                Toast.makeText(context, "Magic item successfully removed", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to remove magic item", Toast.LENGTH_SHORT)
                    .show()
            }

        clearFields()

    }

    fun clearFields() {
        m_item_name_edit_text.setText("")
    }
}