package com.invictus.pillstracker.utils.netUtil

import com.invictus.pillstracker.utils.*
import com.invictus.pillstracker.utils.firebaseDataUtils.FirebaseDataUtil
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiWithParamsCalls {

    //@DELETE(APIClass.DELETE_USER)
    @HTTP(method = "DELETE", path = APIClass.DELETE_USER, hasBody = true)
    suspend fun deleteUser(@Header("uid") uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "", @Body deleteUserParam: DeleteUserParams): Response<DeleteUserResponse>

    @HTTP(method = "DELETE", path = APIClass.DELETE_REASON, hasBody = true)
    suspend fun deleteReason(@Header("uid") uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "", @Body deleteUserParam: DeleteUserParams): Response<DeleteUserResponse>

    @POST(APIClass.SET_STREAK)
    suspend fun setStreak(@Header("uid") uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "", @Body setStreakParams: SetStreakParams): Response<DeleteUserResponse>

    @POST(APIClass.DRINK_HISTORY)
    suspend fun drinkHistory(@Header("uid") uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "", @Body drinkHistoryParams: DeleteUserParams): Response<DrinkHistoryResponse>

    @POST(APIClass.PUBLISH_REASON)
    suspend fun publishReasonWithImage(@Body pRequestDao: RequestBody): Response<DeleteUserResponse?>

    @POST(APIClass.SIGNUP_USER)
    suspend fun signupUser(@Header("uid") uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "", @Body signUpUserParams: SignUpUserParams): Response<DeleteUserResponse>

    @PUT(APIClass.LOGIN_USER)
    suspend fun loginUser(@Header("uid") uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "", @Body signUpUserParams: LoginUserParams): Response<LoginUserResponse>

    @GET(APIClass.GET_USER)
    suspend fun getUser(@Header("uid") uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: ""): Response<LoginUserResponse>

    @POST(APIClass.SET_USER)
    suspend fun setUser(@Header("uid") uid: String = FirebaseDataUtil.firebaseUser()?.uid ?: "", @Body setUserParam: SetUserParam): Response<DeleteUserResponse>

}