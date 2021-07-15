package uevora.di.gbl.api

import android.content.Context
import android.util.Log
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory
import uevora.di.gbl.adapters.BooksAdapter
import uevora.di.gbl.models.BookInfo
import uevora.di.gbl.models.BooksCatalog
import uevora.di.gbl.models.Item

object GoogleBooksAPIClient {

    private val googleBooksAPI: GoogleBooksAPI by lazy {
        setup()
    }

    private fun setup(): GoogleBooksAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    fun getBookList(value: String, listener: DataRetriever) {
        googleBooksAPI.getBookList(value).enqueue(object : Callback<BooksCatalog> {
            override fun onResponse(
                call: Call<BooksCatalog>,
                response: Response<BooksCatalog>
            ) {
                Log.d("API", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    listener.onDataFetchedSuccess(response.body()!!)
                }
            }

            override fun onFailure(call: Call<BooksCatalog>, t: Throwable) {
                Log.e("API", "OnResponse: ${t.message}")
                listener.onDataFetchedFailed()
            }
        })
    }

}