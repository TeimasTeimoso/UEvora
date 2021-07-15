package uevora.di.gbl

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import uevora.di.gbl.firebase.FirebaseOps
import uevora.di.gbl.models.FirebaseBook

class BookDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val description = intent.getStringExtra("description")
        val thumbnail = intent.getStringExtra("thumbnail")

        findViewById<TextView>(R.id.book_detail_title).text = title
        findViewById<TextView>(R.id.book_detail_author).text = author
        findViewById<TextView>(R.id.book_detail_description).text = description

        Picasso.get()
            .load(thumbnail)
            .into(findViewById<ImageView>(R.id.book_detail_thumbnail))

        findViewById<Button>(R.id.add_to_list).setOnClickListener {
            val book = FirebaseBook(title, author, description, thumbnail)
            addToList(book)
        }
    }

    private fun addToList(book: FirebaseBook) {
        val mContext: Context? = applicationContext
        FirebaseOps.writeBookToDefaultList(book, mContext)
    }
}