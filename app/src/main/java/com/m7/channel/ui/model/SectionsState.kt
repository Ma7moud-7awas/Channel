package com.m7.channel.ui.model

import com.m7.channel.data.model.Section

sealed class SectionsState {
    data object Loading : SectionsState()
    data class Success(val sections: List<UISection>) : SectionsState()
    data class Error(val message: String?) : SectionsState()
}
