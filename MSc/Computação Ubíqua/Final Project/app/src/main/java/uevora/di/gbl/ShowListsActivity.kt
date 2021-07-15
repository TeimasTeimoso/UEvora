package uevora.di.gbl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uevora.di.gbl.firebase.FirebaseOps
import uevora.di.gbl.adapters.ListsAdapter

class ShowListsActivity: AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var listsArrayList: ArrayList<String>
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        mRecyclerView = findViewById(R.id.book_lists)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        userId = FirebaseAuth.getInstance().uid.toString()

        listsArrayList = arrayListOf<String>()
        this.getListsData()
    }

    private fun getListsData() {
        mDatabaseRef = FirebaseOps.getDatabase().getReference(userId).child("listsNames")

        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (listsSnapshot in snapshot.children) {
                        val listName = listsSnapshot.value.toString()
                        listsArrayList.add(listName!!)
                    }

                    mRecyclerView.adapter = ListsAdapter(listsArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}