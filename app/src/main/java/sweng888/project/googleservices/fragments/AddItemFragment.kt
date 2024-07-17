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
import edu.psu.sweng888.lessonfive_fragmentsui.adapter.MagicItemAdapter
import sweng888.project.googleservices.R
import sweng888.project.googleservices.data.MagicItem


class AddItemFragment : Fragment(), View.OnClickListener {
    private lateinit var m_item_name_edit_text: EditText
    private lateinit var m_item_rarity_edit_text: EditText
    private lateinit var m_item_desc_edit_text: EditText
    private lateinit var m_confirm_button: Button
    private lateinit var m_clear_button: Button
    private lateinit var m_firebase_database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_add_magic_item, container, false)

        m_firebase_database = Firebase.database.reference
        // Get references to the EditText views
        m_item_name_edit_text = rootView.findViewById(R.id.new_item_name)
        m_item_rarity_edit_text = rootView.findViewById(R.id.new_item_rarity)
        m_item_desc_edit_text = rootView.findViewById(R.id.new_item_description)

        // Get references to the Button views
        m_confirm_button = rootView.findViewById(R.id.button_confirm)
        m_clear_button = rootView.findViewById(R.id.button_clear)

        m_confirm_button.setOnClickListener(this)
        m_clear_button.setOnClickListener(this)

        return rootView
    }

    /**
     * Execute appropriate logic depending on which button view was selected
     */
    override fun onClick(view: View) {
        when (view.getId()) {
            R.id.button_confirm -> confirm()
            R.id.button_clear -> clearFields()
            else -> {}
        }
    }

    /**
     * Create a new magic item and add it to the database
     */
    fun confirm() {
        val name = m_item_name_edit_text.text.toString().trim()
        val rarity = m_item_rarity_edit_text.text.toString().trim()
        val description = m_item_desc_edit_text.text.toString().trim()

        // Ensure none of the user inputs are empty
        if (name.isEmpty() || rarity.isEmpty() || description.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        /** Create a new instance of Magic Item  */
        val new_magic_item = MagicItem(name, rarity, description)

        m_firebase_database.child("Magic Items").child(new_magic_item.item_name!!)
            .setValue(new_magic_item.toDatabaseFormat()).addOnSuccessListener {
                Toast.makeText(context, "Magic item successfully created", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to create magic item", Toast.LENGTH_SHORT)
                    .show()
            }


        clearFields()

    }

    fun clearFields() {
        m_item_name_edit_text.setText("")
        m_item_rarity_edit_text.setText("")
        m_item_desc_edit_text.setText("")
    }
}