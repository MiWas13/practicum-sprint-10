package ru.yandex.practicum.sprint10

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = MainActivityAdapter().apply {
            items = (1..10).map {
                ListElement.createRandomElement(id = it.toString())
            }
            onListElementClickListener = OnListElementClickListener { item ->
                Toast.makeText(this@MainActivity, "Click on ${item.name}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val itemsRv: RecyclerView = findViewById(R.id.items_rv)
        itemsRv.layoutManager = GridLayoutManager(this, 3).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position.mod(4) == 0) {
                        3
                    } else {
                        1
                    }
                }
            }
        }
        itemsRv.adapter = adapter
    }

}

class MainActivityAdapter : RecyclerView.Adapter<ListElementViewHolder>() {

    var items: List<ListElement> = emptyList()
    var onListElementClickListener: OnListElementClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListElementViewHolder {
        return ListElementViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListElementViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onListElementClickListener?.onListElementClick(items[position])
        }
    }

}

fun interface OnListElementClickListener {
    fun onListElementClick(item: ListElement)

}

val list1 = listOf(
    ListElement(id = "1", name = "ListElement 1", Color.MAGENTA),
    ListElement(id = "2", name = "ListElement 2", Color.CYAN),
    ListElement(id = "3", name = "ListElement 3", Color.BLACK),
)

val list2 = listOf(
    ListElement(id = "1", name = "ListElement 1", Color.MAGENTA),
    ListElement(id = "3", name = "ListElement BLACK", Color.BLACK),
    ListElement(id = "2", name = "ListElement 2", Color.YELLOW),
)
