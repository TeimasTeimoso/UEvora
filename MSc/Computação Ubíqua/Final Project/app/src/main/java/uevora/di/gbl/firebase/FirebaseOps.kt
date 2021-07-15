package uevora.di.gbl.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import uevora.di.gbl.models.FirebaseBook

internal const val DATABASE_REFERENCE =
    "https://gbl---glorified-book-lis-56aa7-default-rtdb.europe-west1.firebasedatabase.app"

class FirebaseOps {

    companion object Database {
        fun getDatabase(): FirebaseDatabase {
            return FirebaseDatabase.getInstance(DATABASE_REFERENCE)
        }

        fun writeListNameToFirebase(listName: String, mContext: Context?) {
            val firebaseDb = this.getDatabase()
            val userId = FirebaseAuth.getInstance().uid
            val dbRef: DatabaseReference = firebaseDb.getReference("$userId").child("listsNames")
            dbRef.push().setValue(listName).addOnSuccessListener {
                Toast.makeText(mContext, "List created!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(mContext, "List creation failed...", Toast.LENGTH_SHORT).show()
            }
        }

        fun writeBookToDefaultList(book: FirebaseBook, mContext: Context?) {
            val jsonBook = Gson().toJson(book)
            val firebaseDb = this.getDatabase()
            val userId = FirebaseAuth.getInstance().uid
            val dbRef: DatabaseReference = firebaseDb.getReference("$userId").child("lists").child("Reading")
            dbRef.push().setValue(jsonBook).addOnSuccessListener {
                Toast.makeText(mContext, "Added to List!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(mContext, "Failed to add to list", Toast.LENGTH_SHORT).show()
            }
        }

    }

}