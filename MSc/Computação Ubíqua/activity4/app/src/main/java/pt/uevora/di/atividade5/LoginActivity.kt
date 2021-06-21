package pt.uevora.di.atividade5

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setup()
    }

    private fun setup() {
        findViewById<Button>(R.id.login_button).setOnClickListener {
            this.validateCredsAndRedirect()
        }

        viewModel._loginResultLiveData.observe(this) { loginResult ->
            if(!loginResult) {
                findViewById<TextView>(R.id.invalid_creds).visibility = View.VISIBLE
            } else {
                val intent = Intent(this, CounterActivity::class.java)

                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateCredsAndRedirect() {

        val username = findViewById<TextView>(R.id.username).text.toString()
        val password = findViewById<TextView>(R.id.password).text.toString()

        if (username.isEmpty() or password.isEmpty()) {
            findViewById<TextView>(R.id.invalid_creds).visibility = View.VISIBLE
            return
        }

        viewModel.areCredentialsValid(username, password)
    }
}