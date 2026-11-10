package com.example.uthtodo.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.uthtodo.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    // Đăng nhập bằng Google
    suspend fun signInWithGoogle(context: Context): FirebaseUser? {
        return try {
            val webClientId = try {
                context.getString(R.string.default_web_client_id)
            } catch (_: Exception) {
                ""
            }

            if (webClientId.isBlank()) {
                return auth.currentUser
            }

            val credentialManager = CredentialManager.create(context)

            val googleOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleOption)
                .build()

            val result = credentialManager.getCredential(
                context,
                request
            )

            val credential = result.credential

            if (
                credential !is CustomCredential ||
                credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                return auth.currentUser
            }

            val googleToken = GoogleIdTokenCredential.createFrom(credential.data)
            val firebaseCredential = GoogleAuthProvider.getCredential(googleToken.idToken, null)
            val authResult = auth.signInWithCredential(firebaseCredential).await()

            authResult.user ?: auth.currentUser
        } catch (_: Exception) {
            auth.currentUser
        }
    }

    // Đăng nhập khách
    suspend fun signInAsGuest(): FirebaseUser? {
        return try {
            val result = auth.signInAnonymously().await()
            result.user ?: auth.currentUser
        } catch (_: Exception) {
            auth.currentUser
        }
    }

    // Đăng nhập Email
    suspend fun signInWithEmail(
        email: String,
        password: String
    ): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user ?: auth.currentUser
        } catch (_: Exception) {
            auth.currentUser
        }
    }

    // Đăng ký Email
    suspend fun registerWithEmail(
        email: String,
        password: String
    ): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user ?: auth.currentUser
        } catch (_: Exception) {
            auth.currentUser
        }
    }

    // Lấy tài khoản hiện tại
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Đăng xuất
    fun signOut() {
        try {
            auth.signOut()
        } catch (_: Exception) {
        }
    }
}
