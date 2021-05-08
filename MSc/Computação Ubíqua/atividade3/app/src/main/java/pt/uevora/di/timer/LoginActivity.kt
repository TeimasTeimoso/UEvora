package pt.uevora.di.timer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class LoginActivity: AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setup()
    }

    private fun setup() {
        findViewById<Button>(R.id.btn_auth).setOnClickListener {

            viewModel.loginResultLiveData.observe(this) { loginResult ->
                if(loginResult) {
                    val intent = Intent(this, CounterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun areCredentialsValid() {
        val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()

        if (username.isEmpty()) {
            findViewById<TextView>(R.id.error_cred_txtview).visibility = View.VISIBLE
            return
        }

        val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()

        if (password.isEmpty()) {
            findViewById<TextView>(R.id.error_cred_txtview).visibility = View.VISIBLE
            return
        }

        viewModel.areCredtentialsValid(username, password)
    }
}