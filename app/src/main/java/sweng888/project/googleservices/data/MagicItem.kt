package sweng888.project.googleservices.data

import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

data class MagicItemDatabaseFormat(
    val content: ArrayList<String> = arrayListOf("")
)

@Parcelize
data class MagicItem(
    val item_name: String? = "",
    val rarity: String = "",
    val description: String = ""
) :
    Parcelable {
    fun toDatabaseFormat(): MagicItemDatabaseFormat {
        return MagicItemDatabaseFormat(arrayListOf(rarity, description))
    }
}