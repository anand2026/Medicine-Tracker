package com.invictus.pillstracker.features.homePage

import androidx.compose.ui.graphics.Color
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.invictus.pillstracker.features.homePage.data.PeriodOvulationData
import com.invictus.pillstracker.features.homePage.data.PeriodStateIdentifier

class HomePageViewModel(
    initialState: HomePageState,
    val repo: HomePageRepository,
) : MavericksViewModel<HomePageState>(initialState) {

    init {
        initEmojiList()
    }

    private fun initEmojiList() = setState { copy(moodEmojiData = repo.getMoodEmojiData()) }
    fun periodOvulationDataIntro(periodStateIdentifier: PeriodStateIdentifier,str:String="") = setState { copy(periodAndOvulationData = repo.getPeriodAndOvulationData(periodStateIdentifier,str)) }



    companion object : MavericksViewModelFactory<HomePageViewModel, HomePageState> {
        override fun create(viewModelContext: ViewModelContext, state: HomePageState): HomePageViewModel {
            val repository = HomePageRepository()
            return HomePageViewModel(state, repository)
        }
    }

}

data class HomePageState(
    val moodEmojiData: List<Pair<String, Pair<Int, Color>>> = emptyList(),
    val periodAndOvulationData: PeriodOvulationData? = null
) : MavericksState
