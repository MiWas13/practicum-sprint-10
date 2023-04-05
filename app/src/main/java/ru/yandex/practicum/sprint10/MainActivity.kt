package ru.yandex.practicum.sprint10

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)

        val adapter = MainActivityAdapter().apply {
            items = list1.toMutableList()
            onListElementClickListener = OnListElementClickListener { item ->
                val position = items.indexOf(item)
                if (position >= 4) {
                    items.remove(item)
                    notifyItemRemoved(position)
                } else{

                }
            }
        }



        button.setOnClickListener {
            val oldList = adapter.items
            val newList = list2.toMutableList()

            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldList.size
                }

                override fun getNewListSize(): Int {
                    return newList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = oldList[oldItemPosition]
                    val newItem = newList[newItemPosition]
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldItem = oldList[oldItemPosition]
                    val newItem = newList[newItemPosition]
                    return oldItem == newItem
                }

            })
            adapter.items = newList
            result.dispatchUpdatesTo(adapter)

        }

        val itemsRv: RecyclerView = findViewById(R.id.items_rv)
        itemsRv.layoutManager = LinearLayoutManager(this)
        itemsRv.adapter = adapter
    }

}

class MainActivityAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<ListElement> = mutableListOf()
    var onListElementClickListener: OnListElementClickListener? = null

    companion object {
        const val VIEW_TYPE_LIST_ELEMENT = 1
        const val VIEW_TYPE_PROBLEM = 2
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item is ListElement) {
            VIEW_TYPE_LIST_ELEMENT
        } else {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LIST_ELEMENT -> ListElementViewHolder(parent)
            VIEW_TYPE_PROBLEM -> ProblemViewHolder(parent)
            else -> throw java.lang.IllegalStateException("There is no viewhodler for $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (item is ListElement) {
            val listItemViewHolder = holder as ListElementViewHolder
            listItemViewHolder.bind(item)
            listItemViewHolder.itemView.setOnClickListener {
                onListElementClickListener?.onListElementClick(item)
            }
        } else {
            // do nothing
        }

    }

}

class Problem

class ProblemViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.v_problem, parentView, false)
) {

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
