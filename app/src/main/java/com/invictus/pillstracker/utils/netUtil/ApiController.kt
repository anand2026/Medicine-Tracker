package com.invictus.pillstracker.utils.netUtil

import com.invictus.pillstracker.utils.netUtil.RetrofitInstance.mRetrofit

object ApiController {

    var mApiInterface: ApiWithParamsCalls = mRetrofit.create(ApiWithParamsCalls::class.java)

}