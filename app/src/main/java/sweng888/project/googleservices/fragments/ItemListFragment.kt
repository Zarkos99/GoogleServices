package sweng888.project.googleservices.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import edu.psu.sweng888.lessonfive_fragmentsui.adapter.MagicItemAdapter
import sweng888.project.googleservices.R
import sweng888.project.googleservices.data.MagicItem
import java.util.Locale


class ItemListFragment : Fragment() {

    private lateinit var m_magic_item_adapter: MagicItemAdapter
    private lateinit var m_firebase_database: DatabaseReference
    private lateinit var m_recycler_view: RecyclerView

    private var m_active_query = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_list_items, container, false)

        m_recycler_view = rootView.findViewById(R.id.recycler_view)
        val search_view = rootView.findViewById<SearchView>(R.id.search_items)

        var magic_item_list = ArrayList<MagicItem>()
        var magic_item_adapter = MagicItemAdapter(magic_item_list)
        m_recycler_view.setAdapter(magic_item_adapter)

        // Initialize FlexBox Layout Manager for recyclerview
        val layout_manager = FlexboxLayoutManager(context)
        layout_manager.apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
        m_recycler_view.layoutManager = layout_manager

        // Create firebase database reference and establish data change listener
        // with centralized search query filtering
        m_firebase_database = Firebase.database.reference
        m_firebase_database.child("Magic Items").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                updateFilteredMagicItemList()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(
                    "ItemListFragment", "Error retrieving items from database",
                    error.toException()
                )
            }
        })

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var nonnull_query = ""
                if (query != null) {
                    nonnull_query = query
                }

                setActiveQuery(nonnull_query)
                updateFilteredMagicItemList()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                var nonnull_query = ""
                if (newText != null) {
                    nonnull_query = newText
                }

                setActiveQuery(nonnull_query)
                updateFilteredMagicItemList()
                return false
            }
        })
        return rootView
    }

    fun setActiveQuery(new_query: String) {
        var lowercase_string = ""
        if (new_query.isEmpty()) {
            m_active_query = new_query
            return
        } else if (new_query.length > 1) {
            lowercase_string = new_query.slice(IntRange(1, new_query.length - 1))
        }

        // Force first character to be uppercase to match capitalization precedent in database and
        // establish somewhat of a "fuzzy" search
        m_active_query =
            new_query[0].uppercase(Locale.ROOT) + lowercase_string
    }

    fun updateFilteredMagicItemList() {
        m_firebase_database.child("Magic Items").orderByKey().startAt(m_active_query).get()
            .addOnSuccessListener {
                updateMagicItemList(it.children)
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Search query unsuccessful",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun updateMagicItemList(iterable: Iterable<DataSnapshot>) {
        val magic_item_list: ArrayList<MagicItem> = ArrayList()
        for (data_snapshot in iterable) {
            val magic_item = MagicItem(
                data_snapshot.key,
                data_snapshot.child("content").child("0").value.toString(),
                data_snapshot.child("content").child("1").value.toString()
            )
            // Avoid adding null objects
            if (magic_item.rarity != "null" && magic_item.description != "null") {
                magic_item_list.add(magic_item)
            }
        }
        m_magic_item_adapter = MagicItemAdapter(magic_item_list)
        m_recycler_view.setAdapter(m_magic_item_adapter)
    }

}