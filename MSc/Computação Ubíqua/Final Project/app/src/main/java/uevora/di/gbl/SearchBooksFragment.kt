package uevora.di.gbl

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import uevora.di.gbl.api.GoogleBooksAPI
import uevora.di.gbl.api.GoogleBooksAPIClient

class SearchBooksFragment : Fragment() {
    lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("A", activity.toString())
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        menu.findItem(R.id.search_books).setOnActionExpandListener(object :
            MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                Log.d("BAR", "expanded bar")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                Log.d("BAR", "bar collapsed")
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    public fun getSearchElement(menu: Menu): SearchView {
        return menu.findItem(R.id.search_books).actionView as SearchView
    }
}