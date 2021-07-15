package uevora.di.gbl

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uevora.di.gbl.adapters.BooksAdapter
import uevora.di.gbl.api.DataRetriever
import uevora.di.gbl.api.GoogleBooksAPIClient
import uevora.di.gbl.models.BooksCatalog

class ShowBooksActivity: AppCompatActivity(), DataRetriever {

    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        val recyclerview_books = findViewById<RecyclerView>(R.id.rv_books)
        recyclerview_books.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_books.layoutManager = linearLayoutManager

        val query: String = intent.getStringExtra("query").toString()

        GoogleBooksAPIClient.getBookList(query, this)
    }

    override fun onDataFetchedSuccess(book: BooksCatalog) {
        Log.d("SBA", "onDataFetchedSuccess")

        val myAdapter  = BooksAdapter(book)
        myAdapter.notifyDataSetChanged()
        findViewById<RecyclerView>(R.id.rv_books).adapter = myAdapter

    }

    override fun onDataFetchedFailed() {
        Log.e("SBA", "onDataFetchedFailed")
    }
}