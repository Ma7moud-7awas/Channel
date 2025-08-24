package com.m7.channel.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.m7.channel.ui.SectionsContainer
import com.m7.channel.ui.model.SectionsState
import com.m7.channel.ui.theme.ChannelTheme

@Composable
fun SearchScreen(
    searchSectionsState: SectionsState,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        var searchQuery by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            searchQuery,
            {
                searchQuery = it
                onSearch(it)
            },
            label = { Text("Search") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )

        SectionsContainer(searchSectionsState)
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    ChannelTheme {
        SearchScreen(searchSectionsState = SectionsState.Error("err"), {})
    }
}