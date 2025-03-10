package sweng888.project.googleservices

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var m_firebase_auth: FirebaseAuth
    private lateinit var m_email_edit_text_view: EditText
    private lateinit var m_password_edit_text_view: EditText
    private lateinit var m_login_button_view: Button
    private lateinit var m_signup_button_view: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        m_email_edit_text_view = findViewById(R.id.email_field)
        m_password_edit_text_view = findViewById(R.id.password_field)
        m_login_button_view = findViewById(R.id.login_button)
        m_signup_button_view = findViewById(R.id.signup_text_button)

        m_login_button_view.setOnClickListener(this)
        m_signup_button_view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val email = m_email_edit_text_view.getText().toString().trim()
        val password = m_password_edit_text_view.getText().toString().trim()

        /** Create an instance of the Firebase Authentication component */
        m_firebase_auth = FirebaseAuth.getInstance()

        // Error handling for empty username
        if (email.isEmpty()) {
            //Display error message
            Toast.makeText(
                this@LoginActivity,
                "Please input your username",
                Toast.LENGTH_LONG
            ).show()
            m_email_edit_text_view.setHint("An email is required")
            m_email_edit_text_view.setHintTextColor(Color.RED)
            return
        }

        // Error handling for empty password
        if (password.isEmpty()) {
            //Display error message
            Toast.makeText(
                this@LoginActivity,
                "Please input your password",
                Toast.LENGTH_LONG
            ).show()
            m_password_edit_text_view.setHint("Ensure your password is correct")
            m_password_edit_text_view.setHintTextColor(Color.RED)
            return
        }

        /** If the user's input have the correct information, we need to invoke the
         * signInWithEmailAndPassword method.*/
        when (v?.id) {
            R.id.login_button -> signIn(email, password)
            R.id.signup_text_button -> signUp(email, password)
        }

    }

    /**
     * Handle signing in with user-supplied email and password
     */
    private fun signIn(email: String, password: String) {
        m_firebase_auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(
                        this@LoginActivity,
                        MainNavigationActivity::class.java
                    )
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                } else {
                    val exception_string_array = task.exception.toString()
                        .split(":")
                    var exception_string = ""
                    if (exception_string_array.size > 1) {
                        exception_string = ": " + exception_string_array[1].trim()
                    }

                    Toast.makeText(
                        this@LoginActivity,
                        "Authentication failed" + exception_string,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun signUp(email: String, password: String) {
        m_firebase_auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = m_firebase_auth.currentUser
                    val intent = Intent(
                        this@LoginActivity,
                        MainNavigationActivity::class.java
                    )
                    intent.putExtra("email", user?.email)
                    startActivity(intent)
                    finish()
                } else {
                    val exception_string_array = task.exception.toString()
                        .split(":")
                    var exception_string = ""
                    if (exception_string_array.size > 1) {
                        exception_string = ": " + exception_string_array[1].trim()
                    }

                    Toast.makeText(
                        this@LoginActivity,
                        "Account creation failed" + exception_string,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
