package sweng888.project.googleservices.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import edu.psu.sweng888.lessonfive_fragmentsui.adapter.MagicItemAdapter
import sweng888.project.googleservices.R
import sweng888.project.googleservices.data.MagicItem


class ItemListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_list_items, container, false)

        val recycler_view = rootView.findViewById<RecyclerView>(R.id.recycler_view)

        var magic_item_list = ArrayList<MagicItem>()
        var magic_item_adapter = MagicItemAdapter(magic_item_list)
        recycler_view.setAdapter(magic_item_adapter)

        val firebase_database = Firebase.database.reference
        firebase_database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val magic_item_list: ArrayList<MagicItem> = ArrayList()
                for (dataSnapshot in snapshot.children) {
                    val magic_item: MagicItem? = dataSnapshot.getValue(MagicItem::class.java)
                    if (magic_item != null) {
                        magic_item_list.add(magic_item)
                    }
                }
                magic_item_adapter = MagicItemAdapter(magic_item_list)
                recycler_view.setAdapter(magic_item_adapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(
                    "ItemListFragment", "Error retrieving items from database",
                    error.toException()
                )
            }
        })

        return rootView
    }
}