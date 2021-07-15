package uevora.di.gbl.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import uevora.di.gbl.models.BookInfo
import uevora.di.gbl.models.BooksCatalog
import uevora.di.gbl.models.Item

interface GoogleBooksAPI {

    @GET(BOOKS_ENDPOINT)
    fun getBookList(@Query("q") value: String): Call<BooksCatalog>
}