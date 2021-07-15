package uevora.di.gbl.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uevora.di.gbl.R

class ListsAdapter(private val listNamesList: ArrayList<String>) :
    RecyclerView.Adapter<ListsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
                parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem: String = listNamesList[position]

        holder.value.text = currentItem
    }

    override fun getItemCount(): Int {
        return listNamesList.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val value: TextView = itemView.findViewById(R.id.list_item)
    }
}