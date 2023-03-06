package ru.yandex.practicum.sprint10

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

data class ListElement(
    val id: String,
    val name: String,
    @ColorInt
    val color: Int,
){
    companion object {
        fun createRandomElement(id: String): ListElement {
            return ListElement(
                id = id,
                name = "ListElement $id",
                color = Color.HSVToColor(arrayOf(Random.nextFloat().times(360), 1.0f, 1.0f).toFloatArray())
                // https://ru.wikipedia.org/wiki/HSV_(%D1%86%D0%B2%D0%B5%D1%82%D0%BE%D0%B2%D0%B0%D1%8F_%D0%BC%D0%BE%D0%B4%D0%B5%D0%BB%D1%8C)
            )
        }
    }
}

class ListElementViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.v_list_element, parentView, false)
) {
    private val image: View = itemView.findViewById(R.id.image)
    private val text: TextView = itemView.findViewById(R.id.text)

    fun bind(element: ListElement) {
        image.setBackgroundColor(element.color)
        text.text = element.name
    }
}