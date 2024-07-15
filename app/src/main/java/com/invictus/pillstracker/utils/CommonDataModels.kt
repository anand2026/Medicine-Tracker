package com.invictus.pillstracker.utils

import androidx.annotation.Keep
import com.invictus.pillstracker.utils.firebaseDataUtils.FirebaseDataUtil


@Keep
data class SetStreakParams(
    val uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "",
    val noOfDrinks: Int = 0,
)

@Keep
data class DeleteUserParams(
    val uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "",
)

@Keep
data class SignUpUserParams(
    val email: String = FirebaseDataUtil.firebaseUser()?.email ?: "",
    val uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "",
    val provider: Boolean = true,
    val password: String = "",
    val name: String = "",
    val age: String = "",
    val gender: String = "",
    val habitReason: String = "",
    val weeklyDrinks: Int = 0,
    val weeklyAverageCost: Int = 0,
    val beverageType: String = "",
    val drinkingReason: String = "",
    val referralCode: String = "",
)

@Keep
data class LoginUserParams(
    val email: String = FirebaseDataUtil.firebaseUser()?.email ?: "",
    val uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "",
    val provider: Boolean = true,
    val password: String = "",
)

@Keep
data class LoginUserResponse(
    val status: String = "",
    val message: String = "",
    val data: LoginUserDataResponse? = null,
)

@Keep
data class LoginUserDataResponse(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val inspiration: String = "",
    val weeklyDrinks: Int = 0,
    val weeklyAverageCost: Int = 0,
    val beverageType: String = "",
    val age: String = "",
    val gender: String = "",
    val drinkingReason: String = "",
    val currentStreakStartTime: Long = 0,
    val referralCode: String = "",
    val habitReasonImage: String = "",
    val habitReason: String = "",
)

@Keep
data class DeleteUserResponse(
    val status: String = "",
)

@Keep
data class DrinkHistoryResponse(
    val status: String = "",
    val history: List<DrinkHistoryItemDataModelResponse> = emptyList(),
)

@Keep
data class DrinkHistoryItemDataModelResponse(
    val drinks: Int = 0,
    val date: Long = 0L,
)

@Keep
data class SetUserParam(
    val uid: String = "",
    val platform: String = "android",
    val fcmToken: String = "",
    val deviceAppVersion: String = "",
    val deviceCountry: String = "",
    val deviceCurrecyCode: String = "",
    val deviceLanguage: String = "",
    val deviceName: String = "",
    val deviceOsVersion: String = "",
    val deviceTimeZone: String = ""
)
