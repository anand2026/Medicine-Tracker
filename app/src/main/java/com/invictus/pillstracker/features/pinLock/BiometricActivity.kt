package com.invictus.pillstracker.features.pinLock

//@RequiresApi(Build.VERSION_CODES.P)
//@OptIn(ExperimentalFoundationApi::class)
//class BiometricActivity : FragmentActivity() {
//
//    @OptIn(ExperimentalMaterialApi::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val biometricPromt = createBiometricPrompt(this)
//        val promptInfo = BiometricPrompt.PromptInfo.Builder()
//            .setTitle("Verify Identity to login")
//            .setSubtitle("Log in using your biometric credential")
//            .setNegativeButtonText("Use pin password")
//            .build()
//        biometricPromt.authenticate(promptInfo)
//    }
//
//    companion object {
//        fun newIntent(context: Context) = Intent(context, BiometricActivity::class.java)
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.P)
//private fun createBiometricPrompt(activity: FragmentActivity): BiometricPrompt {
//    val executor = ContextCompat.getMainExecutor(activity)
//
//    object : AuthenticationCallback() {
//        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//            super.onAuthenticationError(errorCode, errString)
////            if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
////                activity.finish()
////            }
//            activity.finish()
//        }
//
//        override fun onAuthenticationSucceeded(result: AuthenticationResult) {
//            super.onAuthenticationSucceeded(result)
//            activity.setResult(Activity.RESULT_OK)
//            activity.finish()
//        }
//    }
//    val biometricPrompt = BiometricPrompt(activity, executor, callback)
//
//    return biometricPrompt
//}


