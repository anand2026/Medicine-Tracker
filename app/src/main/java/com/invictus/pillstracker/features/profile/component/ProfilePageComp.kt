package com.invictus.pillstracker.features.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import com.invictus.pillstracker.R
import com.invictus.pillstracker.features.profile.data.ProfilePageDataItem
import com.invictus.pillstracker.utils.composeUtils.calender.clickable
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner10
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner100
import com.invictus.pillstracker.utils.composeUtils.theme.allCorner22
import com.invictus.pillstracker.utils.composeUtils.theme.itemShowColor
import com.invictus.pillstracker.utils.composeUtils.theme.primaryColor
import com.invictus.pillstracker.utils.composeUtils.theme.textColor
import com.invictus.pillstracker.utils.composeUtils.theme.typo12Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo14Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo15Bold
import com.invictus.pillstracker.utils.composeUtils.theme.typo18Bold
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP

@Composable
fun ProfilePageTopAppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(vertical = 16.DP)
    ) {
        Text(
            text = stringResource(R.string.profile),
            style = MaterialTheme.typography.typo18Bold,
            color = MaterialTheme.colors.textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun CameraImageWithBg(modifier: Modifier = Modifier, callback: () -> Unit) {
    Box(
        modifier = Modifier
            .then(modifier)
            .background(Color.White, MaterialTheme.shapes.allCorner100)
            .size(28.DP)
            .clickable { callback() }.semantics { this.contentDescription = "Camera Image" }
    ) {
        Image(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = "Camera With image Bg",
            modifier = Modifier
                .size(12.DP)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun BoxScope.ProfilePictureSelection(name: String, idName: String, image: Int, callback: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(117.DP)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(114.DP)
                    .clip(RoundedCornerShape(20.DP))
            )
//            CameraImageWithBg(Modifier.align(Alignment.TopEnd), callback)

        }

        Spacer(Modifier.height(10.DP))

        Text(
            text = idName,
            style = MaterialTheme.typography.typo15Bold,
            color = MaterialTheme.colors.textColor
        )
        Text(
            text = name,
            style = MaterialTheme.typography.typo12Bold,
            color = MaterialTheme.colors.textColor
        )
    }
}

@Composable
fun ProfilePictureWithBackgroundPicture(chooseProfilePic:()->Unit,chooseBackgroundPic:()->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.DP)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    primaryColor,
                    MaterialTheme.shapes.allCorner22
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_background_image),
                contentDescription = "prifile background image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(237.DP)
            )
        }

        ProfilePictureSelection("PillsTracker","",R.drawable.ic_logo_with_bg,chooseProfilePic)
//        CameraImageWithBg(
//            Modifier
//                .align(Alignment.TopEnd)
//                .padding(13.DP), chooseBackgroundPic)
    }
}

@Composable
fun ProfileItemComp(profilePageDataItem: ProfilePageDataItem,callback: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.DP)
            .clickable(onClick = callback)
            .background(MaterialTheme.colors.itemShowColor,MaterialTheme.shapes.allCorner10)
            .padding(12.DP),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            modifier = Modifier
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painterResource(id = profilePageDataItem.icon),
                contentDescription = "Profile Icon",
                modifier = Modifier.size(16.DP),
                tint = MaterialTheme.colors.textColor
            )
            
            Spacer(Modifier.width(20.DP))

            Text(
                text =profilePageDataItem.title,
                style = MaterialTheme.typography.typo14Bold,
                color = MaterialTheme.colors.textColor
            )
            
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            if(profilePageDataItem.isPremium){
                Icon(
                    imageVector = Icons.Outlined.WorkspacePremium,
                    contentDescription = "Premium Icon",
                    modifier = Modifier.size(16.DP),
                    tint = MaterialTheme.colors.textColor
                )

                Spacer(Modifier.width(10.DP))
            }

            if(profilePageDataItem.isForwardArrowVisible) {
                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = "Forward Icon",
                    modifier = Modifier.size(16.DP),
                    tint = MaterialTheme.colors.textColor
                )
            }
         }
     }
}