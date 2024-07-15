package com.invictus.pillstracker.features.premiumPage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.composeUtils.theme.FF7E7E7E
import com.invictus.pillstracker.utils.composeUtils.theme.FFF30B57
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner10
import com.invictus.pillstracker.utils.composeUtils.theme.primaryColor
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.SP
import com.invictus.pillstracker.utils.googleBilling.PurchasePlanClass


@Composable
fun PlanSelectionToggle(premiumPlans: List<PurchasePlanClass>, selectedPlanIdentifier: MutableState<String?>) {


    val selectedPlan = premiumPlans.firstOrNull { it.productId == selectedPlanIdentifier.value } ?: PurchasePlanClass()

    LaunchedEffect(key1 = premiumPlans ){
        if(selectedPlanIdentifier.value.isNullOrEmpty()){
            selectedPlanIdentifier.value = premiumPlans.firstOrNull()?.productId
        }
    }

    Spacer(Modifier.height(14.DP))


    premiumPlans.forEach { plan ->

       if(selectedPlanIdentifier.value == plan.productId){
           PlanSelectedComponent(
               planName = selectedPlan.planName,
               planDiscount = "Save ${selectedPlan.offPercentage}",
               perMonthcutPrice = selectedPlan.perMonthCut,
               perMonthPrice = selectedPlan.perMonthPrice,
               planDurationDetail = buildAnnotatedString {
                   append("${selectedPlan.timeAbbreviation}  ")
                   withStyle(SpanStyle(color = FF7E7E7E, textDecoration = TextDecoration.LineThrough)) {
                       append(selectedPlan.planPriceCut)
                   }
                   append("  ${selectedPlan.planPrice}")

               },
               saleText = "",
           )
       }else {

           NonSelectedPlan(
               planName = plan.planName,
               originalPrice = plan.planPrice,
               cutPrice = plan.planPriceCut,
           ) {
               selectedPlanIdentifier.value = plan.productId
           }
       }
        Spacer(Modifier.height(14.DP))

    }
}


@Composable
fun NonSelectedPlan(planName: String, cutPrice: String, originalPrice: String, callback: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.DP, primaryColor, MaterialTheme.shapes.allCorner10)
            .padding(vertical = 8.DP, horizontal = 16.DP),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { callback() }
                .padding(vertical = 0.DP, horizontal = 0.DP),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = planName,
                style = MaterialTheme.typography.h5.copy(
                    fontSize = 15.SP,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    color = FF7E7E7E
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cutPrice,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 14.SP,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.LineThrough,
                        color = FF7E7E7E
                    )
                )

                Spacer(Modifier.width(4.DP))

                Text(
                    text = originalPrice,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 17.SP,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )
            }
        }
    }


}


@Composable
fun PlanSelectedComponent(
    planName: String,
    planDiscount: String,
    planDurationDetail: AnnotatedString? = null,
    perMonthPrice: String,
    perMonthcutPrice: String,
    saleText: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (planDiscount.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = FFF30B57,
                        RoundedCornerShape(topStart = 10.DP, topEnd = 10.DP)
                    )
            ) {
                Text(
                    text = planDiscount,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 13.SP,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.white)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.white), RoundedCornerShape(bottomEnd = 10.DP, bottomStart = 10.DP))
                .border(2.DP, FFF30B57, RoundedCornerShape(bottomEnd = 10.DP, bottomStart = 10.DP))
                .padding(horizontal = 10.DP, vertical = 10.DP),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = planName,
                        style = MaterialTheme.typography.h5.copy(
                            fontSize = 24.SP,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )

                    Spacer(Modifier.width(13.DP))

                    if (saleText.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = primaryColor,
                                    RoundedCornerShape(4.DP)
                                )
                                .padding(horizontal = 6.DP, vertical = 4.DP)
                        ) {
                            Text(
                                text = saleText,
                                style = MaterialTheme.typography.h5.copy(
                                    fontSize = 10.SP,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(R.color.white)
                                )
                            )
                        }
                    }

                }

                if (planDurationDetail != null) {

                    Text(
                        text = planDurationDetail,
                        style = MaterialTheme.typography.h5.copy(
                            fontSize = 15.SP,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = perMonthPrice,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 15.SP,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )
                Text(
                    text = perMonthcutPrice,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 12.SP,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    )
                )
            }
        }


    }
}

@Composable
fun OnBoardingFilledButton(
    text: String,
    cornerRadius: Dp = 10.DP,
    bgColor: Color = FFF30B57,
    textColor: Color = colorResource(id = R.color.white),
    outerModifier: Modifier = Modifier
        .fillMaxWidth().padding(vertical = 16.DP),
    innerPadding: Modifier = Modifier
        .padding(16.DP),
    callback: () -> Unit
) {
    Box(
        modifier = Modifier
            .then(outerModifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = bgColor, RoundedCornerShape(cornerRadius))
                .clickable {
                    callback()
                }then (innerPadding)
                .semantics { testTag = "continue" }
        ) {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 17.SP,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            )
        }
    }
}