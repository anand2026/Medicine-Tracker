package com.invictus.pillstracker.features.premiumPage

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.ProductDetails
import com.invictus.pillstracker.utils.googleBilling.GoogleBillingConfig
import com.invictus.pillstracker.utils.googleBilling.GoogleBillingSdkOperation
import com.invictus.pillstracker.utils.googleBilling.PurchasePlanClass
import com.invictus.pillstracker.utils.googleBilling.toAnnualPlan
import com.invictus.pillstracker.utils.googleBilling.toMonthlyPlan
import com.invictus.pillstracker.utils.googleBilling.toOneTimePlan

class PremiumViewModel(
    initialState: PremiumState,
    val repo: PremiumRepository,
) : MavericksViewModel<PremiumState>(initialState) {

    init {
        initPricingList()
    }

    private fun initPricingList(){
        GoogleBillingSdkOperation.productsDetailsUpdate.setOnEach { updatedList ->

            val discountOffer = updatedList.firstOrNull { it.productId == GoogleBillingConfig.MONTHLY_PLAN }?.subscriptionOfferDetails?.first()?.pricingPhases?.pricingPhaseList?.first()?.priceAmountMicros

            val planList = updatedList.map {
                if(it.productType == BillingClient.ProductType.SUBS){
                  when(it.subscriptionOfferDetails?.first()?.pricingPhases?.pricingPhaseList?.first()?.billingPeriod){
                      "P1M" -> it.toMonthlyPlan(discountOffer)
                      "P1Y" -> it.toAnnualPlan(discountOffer)
                      else -> it.toAnnualPlan(discountOffer)
                  }
                }else{
                    it.toOneTimePlan(discountOffer)
                }
            }
            copy(productList = updatedList,productPlanList = planList)
        }
    }

    companion object : MavericksViewModelFactory<PremiumViewModel, PremiumState> {
        override fun create(viewModelContext: ViewModelContext,state: PremiumState): PremiumViewModel {
            return PremiumViewModel(state, PremiumRepository())
        }
    }

}

data class PremiumState(
    val productList: List<ProductDetails> = emptyList(),
    val productPlanList: List<PurchasePlanClass> = emptyList(),
) : MavericksState
