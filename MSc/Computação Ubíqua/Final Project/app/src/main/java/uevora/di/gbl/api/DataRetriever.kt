package uevora.di.gbl.api

import uevora.di.gbl.models.BookInfo
import uevora.di.gbl.models.BooksCatalog

interface DataRetriever {

    fun onDataFetchedSuccess(book: BooksCatalog)

    fun onDataFetchedFailed()
}