package com.invictus.pillstracker.features.premiumPage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.mainActivityPage.MainActivityPageViewModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import com.invictus.pillstracker.features.premiumPage.PremiumState
import com.invictus.pillstracker.features.premiumPage.PremiumViewModel
import com.invictus.pillstracker.utils.composeUtils.theme.FF153843
import com.invictus.pillstracker.utils.composeUtils.theme.FF7A7A7A
import com.invictus.pillstracker.utils.composeUtils.theme.typo15Normal
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP
import com.invictus.pillstracker.utils.googleBilling.GoogleBillingConfig
import com.invictus.pillstracker.utils.googleBilling.GoogleBillingSdkOperation
import com.invictus.pillstracker.utils.googleBilling.PurchasePlanClass

@Composable
fun PremiumPagePopUp(mainActivityPageViewModel: MainActivityPageViewModel) {

    val viewModel: PremiumViewModel = mavericksViewModel()
    val productPlanList by viewModel.collectAsState(PremiumState::productPlanList)
    val availablePlanList = remember { mutableStateListOf<PurchasePlanClass>() }
    val selectedPlanIdentifier = remember { mutableStateOf(productPlanList.firstOrNull()?.productId) }

    LaunchedEffect(key1 = productPlanList){
        availablePlanList.clear()
//        val monthly = productPlanList.firstOrNull { it.productId == GoogleBillingConfig.MONTHLY_PLAN }?.let { availablePlanList.add(it) }
        productPlanList.firstOrNull { it.productId == GoogleBillingConfig.YEARLY_PLAN }?.let { availablePlanList.add(it) }
        productPlanList.firstOrNull { it.productId == GoogleBillingConfig.LIFETIME_PLAN }?.let { availablePlanList.add(it) }
        selectedPlanIdentifier.value = availablePlanList.firstOrNull()?.productId
    }




    Surface(
        color = Color.White
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.DP, horizontal = 8.DP)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_close_24),
                        contentDescription = null,
                        tint = FF7A7A7A,
                        modifier = Modifier
                            .size(32.DP)
                            .align(Alignment.CenterStart)
                            .clickable {
                                mainActivityPageViewModel.setSelectedGlobalNavItem(BottomNavItemsIdentifiers.NONE)
                            }
                    )
                }
            },
            bottomBar = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PlanSelectionToggle(availablePlanList, selectedPlanIdentifier)

                    Text(
                        text = productPlanList.firstOrNull { it.productId == selectedPlanIdentifier.value }?.planInfoDesc?:"",
                        style = MaterialTheme.typography.typo15Normal,
                        color = Color.Black
                    )

                    OnBoardingFilledButton(
                        text = stringResource(id = R.string.continue_tag),
                        outerModifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.DP),
                        cornerRadius = 100.DP
                    ) {
                        selectedPlanIdentifier.value?.let { GoogleBillingSdkOperation.launchBillingFlow(it) }
                    }
                }
            },
            backgroundColor = Color.Transparent,
            modifier = Modifier.padding(horizontal = 16.DP)
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {

                    Spacer(Modifier.height(25.DP))

                    Text(
                        text = stringResource(id = R.string.choose_your_plan),
                        style = MaterialTheme.typography.h5.copy(
                            fontSize = 28.SP,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = FF153843
                        )
                    )

                    Spacer(Modifier.height(70.DP))
                }

                item {

                    Image(
                        painter = painterResource(id = R.drawable.five_stars),
                        contentDescription = null,
                        modifier = Modifier
                            .width(165.DP)
                            .height(25.DP)
                    )

                    Spacer(Modifier.height(20.DP))
                }


                item {
                    Text(
                        text = stringResource(id = R.string.user_feedback_on_premium_page),
                        style = MaterialTheme.typography.h5.copy(
                            fontSize = 15.SP,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                }

                item {
                    Text(
                        text = "- Julianne Taylor",
                        style = MaterialTheme.typography.h5.copy(
                            fontSize = 11.SP,
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.SemiBold,
                            color = FF7A7A7A
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}