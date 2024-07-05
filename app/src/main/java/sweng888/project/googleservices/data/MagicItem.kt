package sweng888.project.googleservices.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MagicItemDatabaseFormat(
    val item_name: String = "",
    val content: ArrayList<String> = arrayListOf("")
) {
    fun toMagicItem(): MagicItem {
        return MagicItem(item_name, content.get(0), content.get(1))
    }
}

@Parcelize
data class MagicItem(
    val item_name: String = "",
    val rarity: String = "",
    val description: String = ""
) :
    Parcelable {
    fun toDatabaseFormat(): MagicItemDatabaseFormat {
        return MagicItemDatabaseFormat(item_name, arrayListOf(rarity, description))
    }
}