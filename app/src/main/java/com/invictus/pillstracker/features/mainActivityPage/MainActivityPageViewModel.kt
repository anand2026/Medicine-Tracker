package com.invictus.pillstracker.features.mainActivityPage

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemDataModel
import com.invictus.pillstracker.features.mainActivityPage.data.BottomNavItemsIdentifiers
import com.invictus.pillstracker.features.turnNetOnPage.utils.TurnNetOnPageUtils
import com.invictus.pillstracker.utils.broadcastReceivers.InternetOnOffReceiver
import com.invictus.pillstracker.utils.netUtil.ApiController
import com.invictus.pillstracker.utils.netUtil.ApiWithParamsCalls

class MainActivityPageViewModel(
    initialState: MainActivityPageState,
    private val apiWithParamsCalls: ApiWithParamsCalls,
    private val repo: MainActivityRepository,
) : MavericksViewModel<MainActivityPageState>(initialState) {

    init {
        netWorkChangeCallBack()
        initBottomNav()
    }

    fun setSelectedNavItem(selectedNav: BottomNavItemsIdentifiers) = setState { copy(selectedNavItem = selectedNav) }
    fun setSelectedGlobalNavItem(selectedNav: BottomNavItemsIdentifiers) = setState { copy(globalNavItem = selectedNav) }

    private fun netWorkChangeCallBack() {
        InternetOnOffReceiver.callback = {
            runCatching {
                setState { copy(isDeviceInternetOn = TurnNetOnPageUtils.isOnline()) }
            }
        }
    }

    private fun initBottomNav(){
        setState { copy(bottomNavItem = repo.getBottomNavItemList()) }
    }


    companion object : MavericksViewModelFactory<MainActivityPageViewModel, MainActivityPageState> {
        override fun create(viewModelContext: ViewModelContext, state: MainActivityPageState): MainActivityPageViewModel {
            val apiWithParamsCalls: ApiWithParamsCalls = ApiController.mApiInterface
            return MainActivityPageViewModel(state, apiWithParamsCalls,MainActivityRepository())
        }
    }

}

data class MainActivityPageState(
    val isDeviceInternetOn : Boolean  = true,
    val bottomNavItem: List<BottomNavItemDataModel> = emptyList(),
    val selectedNavItem: BottomNavItemsIdentifiers = BottomNavItemsIdentifiers.HOME,
    val globalNavItem: BottomNavItemsIdentifiers = BottomNavItemsIdentifiers.HOME,
) : MavericksState
