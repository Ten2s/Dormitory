package com.example.dormitory_friend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.auth.*
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.login_page.*
import com.kakao.auth.ISessionCallback
import com.kakao.util.helper.log.Logger





class LoginActivity : AppCompatActivity() {

    private var callback: SessionCallback = SessionCallback()

    companion object{
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        login_button.setOnClickListener {
            FirebaseUtils.auth.signInWithEmailAndPassword(login_email_area.text.toString(), login_password_area.text.toString())
                .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
                }
            }
        }
        //회원 가입
        join_in.setOnClickListener {
            val intent = Intent(this, JoininActivity::class.java)
            startActivity(intent)
        }
        //구글로그인
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        google_login.setOnClickListener {
            signIn()
        }

        //카카오 로그인
        kakao_login.setOnClickListener {
            callback = SessionCallback()
            Session.getCurrentSession().addCallback(callback)
            Session.getCurrentSession().checkAndImplicitOpen()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            redirectSignupActivity()
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Logger.e(exception)
                setContentView(R.layout.login_page)
            }
        }
    }

    protected fun redirectSignupActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    //구글 로그인
    private fun signIn() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)


        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseUtils.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = FirebaseUtils.auth.currentUser
                    val intent = Intent(this, CharicteristicActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }

            }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)


            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

}
