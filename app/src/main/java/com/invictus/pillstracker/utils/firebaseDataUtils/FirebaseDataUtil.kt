package com.invictus.pillstracker.utils.firebaseDataUtils

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.invictus.pillstracker.utils.GoogleSignInUtils.initSignIn
import splitties.init.appCtx
import splitties.systemservices.activityManager
import timber.log.Timber

object FirebaseDataUtil {

    //firebase related
    private var firebaseAuth: FirebaseAuth? = null

    fun firebaseUser(): FirebaseUser? {
        return firebaseAuth()?.currentUser
    }

    fun firebaseAuth(): FirebaseAuth? {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance(firebaseApp())
        }
        return firebaseAuth
    }

    fun firebaseApp(): FirebaseApp {
        return FirebaseApp.getInstance()
    }

    fun firebaseAnalyticsInstance(context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }


    fun firebaseCrashlyticsInstance(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    fun firebaseAuthWithGoogle(idToken: String, context: Context, isSuccess: ((Boolean, Boolean) -> Unit)) {

        fun signOut() {
            try {
                initSignIn(context).signOut()
            } catch (e: Exception) {
                Timber.d(e)
            }
        }

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseDataUtil.firebaseAuth()?.signInWithCredential(credential)?.addOnSuccessListener { task ->
            Timber.d("==>>== signInWithCredential:success")
            isSuccess.invoke(true, task?.additionalUserInfo?.isNewUser ?: false)
        }?.addOnFailureListener {
            Timber.d("failed to signInWithCredential with ${it.message}")
            signOut()
            isSuccess.invoke(false, false)
        }?.addOnCanceledListener {
            signOut()
            isSuccess.invoke(false, false)
        }
    }

    fun signOut() {
        Timber.d("firebaseAuth().signOut ==>>")
        runCatching {
            firebaseAuth()?.signOut()
            initSignIn(appCtx).signOut().addOnCompleteListener {
                activityManager.clearApplicationUserData()
            }
        }
    }



}