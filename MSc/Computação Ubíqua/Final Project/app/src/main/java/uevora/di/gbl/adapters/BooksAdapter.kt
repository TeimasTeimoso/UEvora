package uevora.di.gbl.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uevora.di.gbl.BookDetailsActivity
import uevora.di.gbl.R
import uevora.di.gbl.models.BooksCatalog

class BooksAdapter(private val bookResponse: BooksCatalog) :
    RecyclerView.Adapter<BooksAdapter.BookCatalogViewHolder>() {
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookCatalogViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(mContext).inflate(
            R.layout.list_book_item,
            parent, false)
        return BookCatalogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookCatalogViewHolder, position: Int) {
        var title: String = ""
        var author: String = ""
        var description: String = ""
        var thumbnail: String = ""
        if (bookResponse.items[position].volumeInfo.title != null) {
            title = bookResponse.items[position].volumeInfo.title
        }
        if (bookResponse.items[position].volumeInfo.authors != null) {
           author = bookResponse.items[position].volumeInfo.authors[0]
        }
        if (bookResponse.items[position].volumeInfo.description != null) {
            description = bookResponse.items[position].volumeInfo.description
        }
        if(bookResponse.items[position].volumeInfo.imageLinks != null) {
            thumbnail = bookResponse.items[position].volumeInfo.imageLinks.thumbnail.replace("http", "https")

            Picasso.get()
                .load(thumbnail)
                .into(holder.thumbnail)
        }

        holder.title.text = title
        holder.author.text = author
        holder.description.text = description

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, BookDetailsActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("author", author)
            intent.putExtra("description", description)
            intent.putExtra("thumbnail", thumbnail)
            mContext.startActivity(intent)
        }
    }

    class BookCatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.b_title)
        val author: TextView = itemView.findViewById(R.id.b_author)
        val description: TextView = itemView.findViewById(R.id.b_description)
        val thumbnail: ImageView = itemView.findViewById(R.id.b_thumbnail)
    }

    override fun getItemCount(): Int {
        return bookResponse.items.size
    }
}