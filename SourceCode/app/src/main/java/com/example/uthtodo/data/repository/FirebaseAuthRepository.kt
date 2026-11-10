package com.example.uthtodo.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.uthtodo.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Lưu thông tin hồ sơ vào tài liệu riêng của người dùng.
    fun syncUserProfileToFirebase(user: FirebaseUser?) {
        val currentUser = user ?: auth.currentUser ?: return
        val userData = mapOf(
            "uid" to currentUser.uid,
            "displayName" to currentUser.displayName,
            "email" to currentUser.email,
            "isAnonymous" to currentUser.isAnonymous,
            "lastLogin" to System.currentTimeMillis()
        )
        db.collection("users").document(currentUser.uid).set(userData)
            .addOnFailureListener { error ->
                Log.e(TAG, "Unable to sync the user profile to Firestore", error)
            }
    }

    // Đăng nhập bằng Google
    suspend fun signInWithGoogle(context: Context): FirebaseUser? {
        return try {
            val webClientId = try {
                context.getString(R.string.default_web_client_id)
            } catch (_: Exception) {
                ""
            }

            if (webClientId.isBlank()) {
                val current = auth.currentUser
                syncUserProfileToFirebase(current)
                return current
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
                val current = auth.currentUser
                syncUserProfileToFirebase(current)
                return current
            }

            val googleToken = GoogleIdTokenCredential.createFrom(credential.data)
            val firebaseCredential = GoogleAuthProvider.getCredential(googleToken.idToken, null)
            val authResult = auth.signInWithCredential(firebaseCredential).await()

            val signedUser = authResult.user ?: auth.currentUser
            syncUserProfileToFirebase(signedUser)
            signedUser
        } catch (_: Exception) {
            val current = auth.currentUser
            syncUserProfileToFirebase(current)
            current
        }
    }

    // Đăng nhập khách
    suspend fun signInAsGuest(): FirebaseUser? {
        return try {
            val result = auth.signInAnonymously().await()
            val user = result.user ?: auth.currentUser
            syncUserProfileToFirebase(user)
            user
        } catch (_: Exception) {
            val current = auth.currentUser
            syncUserProfileToFirebase(current)
            current
        }
    }

    // Đăng nhập Email
    suspend fun signInWithEmail(
        email: String,
        password: String
    ): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: auth.currentUser
            syncUserProfileToFirebase(user)
            user
        } catch (_: Exception) {
            val current = auth.currentUser
            syncUserProfileToFirebase(current)
            current
        }
    }

    // Đăng ký Email
    suspend fun registerWithEmail(
        email: String,
        password: String
    ): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: auth.currentUser
            syncUserProfileToFirebase(user)
            user
        } catch (_: Exception) {
            val current = auth.currentUser
            syncUserProfileToFirebase(current)
            current
        }
    }

    // Lấy tài khoản hiện tại
    fun getCurrentUser(): FirebaseUser? {
        val user = auth.currentUser
        syncUserProfileToFirebase(user)
        return user
    }

    // Đăng xuất
    fun signOut() {
        try {
            auth.signOut()
        } catch (_: Exception) {
        }
    }

    private companion object {
        const val TAG = "FirebaseAuthRepository"
    }
}
