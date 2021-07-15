package uevora.di.gbl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater

        val searchBooksFragment = SearchBooksFragment()
        searchBooksFragment.onCreateOptionsMenu(menu, inflater)
        val searchBar = searchBooksFragment.getSearchElement(menu)
        this.observeSearchBar(searchBar)

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        /* Checks if any user is logged in */
        if (currentUser == null) {
            val signInIntent = Intent(this, LoginActivity::class.java)
            startActivity(signInIntent)
        }

        /* Event Listener to add list */
        findViewById<Button>(R.id.add_list_btn).setOnClickListener {
            var createListDialog = CreateListFragment()

            createListDialog.show(supportFragmentManager, "createListDialog")
        }

        findViewById<Button>(R.id.sign_out).setOnClickListener {
            mAuth.signOut()
            this.googleClientLogout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.get_lists).setOnClickListener {
            val intent = Intent(this, ShowListsActivity::class.java)
            startActivity(intent)
        }
    }

    /* Logout from google client */
    private fun googleClientLogout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()

    }


    private fun observeSearchBar(searchBar: SearchView) {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val intent = Intent(this@MainActivity, ShowBooksActivity::class.java)
                    intent.putExtra("query", query)
                    startActivity(intent)
                    Log.d("KEK", query)

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}