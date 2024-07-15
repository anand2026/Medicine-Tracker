package com.invictus.pillstracker.utils.googleBilling

import androidx.annotation.Keep
import com.android.billingclient.api.ProductDetails
import com.invictus.pillstracker.R
import splitties.resources.appStr

@Keep
data class PurchasePlanClass(
    val planName:String = "",
    val productId:String = "",
    val offPercentage:String = "",
    val perMonthPrice:String = "",
    val perMonthCut:String = "",
    val planPrice:String = "",
    val planPriceCut:String = "",
    val planInfoDesc:String = "",
    val timeAbbreviation:String = "",
)

fun ProductDetails.toAnnualPlan(monthlyPrice: Long?):PurchasePlanClass{
    val priceMacros = this.subscriptionOfferDetails?.firstOrNull()?.pricingPhases?.pricingPhaseList?.firstOrNull()?.priceAmountMicros
    val currency = this.subscriptionOfferDetails?.firstOrNull()?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice?.toCharArray()?.get(0)?:""
    val perMonthPrice = priceMacros?.div(1000000)?.div(12)
    val planPriceCut = monthlyPrice?.times(12)?.div(1000000)
    val perMonthCut = monthlyPrice?.div(1000000)
    val offPercentage = monthlyPrice?.toDouble()?.times(12)?.minus(priceMacros?:0)?.div(monthlyPrice.times(12))?.times(100)?.toInt()

    return PurchasePlanClass(
        planName = this.name,
        productId = this.productId,
        planPrice = this.subscriptionOfferDetails?.first()?.pricingPhases?.pricingPhaseList?.first()?.formattedPrice?:"",
        perMonthPrice = "$currency$perMonthPrice/Month",
        offPercentage = "$offPercentage%",
        planPriceCut = "$currency$planPriceCut",
        perMonthCut = "$currency$perMonthCut/Month",
        planInfoDesc = appStr(R.string.pay_every_twelve_month),
        timeAbbreviation = appStr(R.string.yearly)
    )
}

fun ProductDetails.toMonthlyPlan(monthlyPrice: Long?):PurchasePlanClass{

    val priceMacros = this.subscriptionOfferDetails?.firstOrNull()?.pricingPhases?.pricingPhaseList?.firstOrNull()?.priceAmountMicros
    val currency = this.subscriptionOfferDetails?.firstOrNull()?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice?.toCharArray()?.get(0)?:""
    val perMonthPrice = priceMacros?.div(1000000)?.div(1)
    val planPriceCut = monthlyPrice?.times(2)?.div(1000000)
    val perMonthCut = monthlyPrice?.times(2)?.div(1000000)
    val offPercentage = monthlyPrice?.toDouble()?.times(12)?.minus(priceMacros?:0)?.div(monthlyPrice.times(12))?.times(100)?.toInt()

    return PurchasePlanClass(
        planName = this.name,
        productId = this.productId,
        planPrice = this.subscriptionOfferDetails?.first()?.pricingPhases?.pricingPhaseList?.first()?.formattedPrice?:"",
        perMonthPrice = "$currency$perMonthPrice/Month",
        offPercentage = "$offPercentage%",
        planPriceCut = "$currency$planPriceCut",
        perMonthCut = "$currency$perMonthCut/Month",
        planInfoDesc = appStr(R.string.pay_every_one_month),
        timeAbbreviation = appStr(R.string.monthly)
    )
}

fun ProductDetails.toOneTimePlan(monthlyPrice: Long?):PurchasePlanClass{
    val priceMacros = this.oneTimePurchaseOfferDetails?.priceAmountMicros
    val currency = this.oneTimePurchaseOfferDetails?.formattedPrice?.toCharArray()?.get(0)?:""
    val perMonthPrice = priceMacros?.div(1000000)?.div(12)
    val planPriceCut = monthlyPrice?.times(13)?.div(1000000)
    val perMonthCut = monthlyPrice?.div(1000000)
    val offPercentage = monthlyPrice?.toDouble()?.times(13)?.minus(priceMacros?:0)?.div(monthlyPrice.times(12))?.times(100)?.toInt()

    //For This case We show original price in per month price tag
    return PurchasePlanClass(
        planName = this.name,
        productId = this.productId,
        planPrice = "",
        perMonthPrice =  "${this.oneTimePurchaseOfferDetails?.formattedPrice}",
        offPercentage = "$offPercentage%",
        planPriceCut = "",
        perMonthCut = "$currency$planPriceCut",
        planInfoDesc = appStr(R.string.access_for_life),
        timeAbbreviation = ""//appStr(R.string.one_time_only)
    )
}