package com.invictus.pillstracker.utils.googleBilling

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.invictus.pillstracker.data.sharedPrefs.PillsTrackerSharedPrefs
import com.invictus.pillstracker.utils.CommonUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.toImmutableList
import timber.log.Timber

interface BillingDelegate {
    val productsDetails: List<ProductDetails>
    fun registerBilling(
        activity: AppCompatActivity,
    )

    fun launchBillingFlow(productId: String)
}


object GoogleBillingSdkOperation : BillingDelegate, DefaultLifecycleObserver, PurchasesUpdatedListener {


    private var countStateForInAppSubsFetch = 0
    private lateinit var activity: AppCompatActivity

    override var productsDetails: List<ProductDetails> = listOf()
    private val _productsDetailsStateFlow: MutableStateFlow<List<ProductDetails>> = MutableStateFlow(productsDetails)
    var productsDetailsUpdate: StateFlow<List<ProductDetails>> = _productsDetailsStateFlow.asStateFlow()

    private lateinit var billingClient: BillingClient

    private val subsList = listOf(GoogleBillingConfig.MONTHLY_PLAN, GoogleBillingConfig.YEARLY_PLAN)
    private val inAppList = listOf(GoogleBillingConfig.LIFETIME_PLAN)


    private fun addProductDetailsToMap(productDetails: ProductDetails) {
        val details = productsDetails.toMutableList()
        if (!details.contains(productDetails)) details.add(productDetails)
        productsDetails = details
        _productsDetailsStateFlow.value = details.toImmutableList()
    }

    override fun registerBilling(
        activity: AppCompatActivity,
    ) {
        this.activity = activity
        this.activity.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        billingClient = BillingClient.newBuilder(activity.applicationContext)
            .setListener(this)
            .enablePendingPurchases()
            .build()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        startConnection()
    }

    fun startConnection(currentAttempt: Int = 1) {
        val maxAttempt = 3

        if (currentAttempt > maxAttempt) return

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                    Timber.d("==>onBillingSetupFinished_68 Working Billing")


                    val queryProductSubs = QueryProductDetailsParams.newBuilder().setProductList(
                        subsList.map { productId ->
                            QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(productId)
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        }
                    ).build()
                    val queryProductInApps = QueryProductDetailsParams.newBuilder().setProductList(
                        inAppList.map { productId ->
                            QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(productId)
                                .setProductType(BillingClient.ProductType.INAPP)
                                .build()
                        }
                    ).build()



                    billingClient.queryProductDetailsAsync(queryProductSubs, ::onProductDetailsResponse)
                    billingClient.queryProductDetailsAsync(queryProductInApps, ::onProductDetailsResponse)

                    billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(), ::onProductPurchasedResponse)
                    billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(), ::onProductPurchasedResponse)

                } else {
                    Timber.d("==>onBillingSetupFinished_94 Not Working billing")
                }
            }

            override fun onBillingServiceDisconnected() {
                Timber.d("==>onBillingServiceDisconnected_99 Disconnected")
                startConnection(currentAttempt + 1)
            }
        })
    }


    private fun onProductDetailsResponse(
        billingResult: BillingResult,
        productDetails: List<ProductDetails>
    ) {
        val responseCode = billingResult.responseCode
        val debugMessage = billingResult.debugMessage
        when (responseCode) {
            BillingClient.BillingResponseCode.OK -> {

                if (productDetails.isEmpty()) {
                    Timber.d("Found empty product details")
                } else {
                    for (productDetail in productDetails) {
//                        Timber.d("==>onProductDetailsResponse_108 ${CommonUtils.createStringFromDataObject(productDetail)}")

                        addProductDetailsToMap(productDetail)
                    }
//                    Timber.d("Product details fetched: ${productDetails.size}")
                }
            }

            else -> {
//                UiErrorHandler().handleError(
//                    RuntimeException("Product details: $responseCode $debugMessage"),
//                    R.string.error_purchase_error
//                )
            }
        }
    }

    private fun onProductPurchasedResponse(
        billingResult: BillingResult,
        productDetails: MutableList<Purchase>
    ) {

        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                countStateForInAppSubsFetch++
                if (productDetails.isEmpty()) {
                    Timber.d("Found empty product details")
                } else {
//                    Timber.d("==>onProductPurchasedHistoryResponse_184 ${CommonUtils.createStringFromDataObject(productDetails)}")


                    for (productDetail in productDetails) {
                        productDetail.products.forEach {
                            if (
                                it.equals(GoogleBillingConfig.LIFETIME_PLAN) ||
                                it.equals(GoogleBillingConfig.MONTHLY_PLAN) ||
                                it.equals(GoogleBillingConfig.YEARLY_PLAN)
                            ) {
                                countStateForInAppSubsFetch = Int.MIN_VALUE
                                activatePremium()
                            }
                        }
                    }
                }

//                Timber.d("==>onProductPurchasedResponse_186 $countStateForInAppSubsFetch")
                if(countStateForInAppSubsFetch == 2){
                    countStateForInAppSubsFetch = 0
                    deactivatePremium()
                }
            }

            else -> {
//                UiErrorHandler().handleError(
//                    RuntimeException("Product details: $responseCode $debugMessage"),
//                    R.string.error_purchase_error
//                )
            }
        }
    }


    override fun launchBillingFlow(productId: String) {
//        uiInfoService.showInfo(R.string.billing_starting_purchase)
//        Timber.d("==>launchBillingFlow_152 $productId")
        try {
//            Timber.d("==>launchBillingFlow_154 ${CommonUtils.createStringFromDataObject(productsDetails)}")
            val productDetails = productsDetails.first { it.productId == productId }
            val productDetailsParamsList = listOf(
                productDetails.let {
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(it)
                        .setOfferToken(it.subscriptionOfferDetails?.first()?.offerToken ?: "")
                        .build()
                }
            )
//            Timber.d("==>launchBillingFlow_162 ${CommonUtils.createStringFromDataObject(productDetailsParamsList)}")

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

//            Timber.d("==>launchBillingFlow_168 ${CommonUtils.createStringFromDataObject(billingFlowParams)}")

            billingClient.launchBillingFlow(activity, billingFlowParams)


        } catch (t: Exception) {
            Timber.d("==>launchBillingFlow_169 $t")
//            UiErrorHandler().handleError(t, R.string.error_purchase_error)
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {


        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        activatePremium()
        CommonUtils.restartApp()
    }

    private fun activatePremium() {
        PillsTrackerSharedPrefs.SUB_STATUS = true
    }

    private fun deactivatePremium() {
        PillsTrackerSharedPrefs.SUB_STATUS = false
    }


    override fun onDestroy(owner: LifecycleOwner) {
        billingClient.endConnection()
        Timber.d("onDestroy")
        super.onDestroy(owner)
    }
}