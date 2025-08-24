package com.m7.channel.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m7.channel.ui.component.SearchScreen
import com.m7.channel.ui.component.SectionsHeader
import com.m7.channel.ui.component.SectionsList
import com.m7.channel.ui.model.SectionsState
import com.m7.channel.ui.theme.ChannelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChannelTheme {

                val mainViewModel: MainViewModel = hiltViewModel()
                var selectedItem by rememberSaveable { mutableIntStateOf(0) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { HomeAppBar(selectedItem) { selectedItem = it } }
                ) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        when (selectedItem) {
                            1 -> {
                                SearchScreen(
                                    mainViewModel.searchSections.collectAsStateWithLifecycle().value,
                                    {
                                        mainViewModel.searchAsSections(it)
                                    }
                                )
                            }

                            else -> {
                                SectionsContainer(
                                    mainViewModel.homeSections.collectAsStateWithLifecycle().value
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeAppBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {

    val items = listOf("Home" to Icons.Filled.Home, "Search" to Icons.Filled.Search)

    BottomAppBar(modifier = Modifier.height(80.dp)) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.second,
                        contentDescription = item.first,
                        Modifier.size(32.dp)
                    )
                },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeAppBarPreview() {
    ChannelTheme {
        HomeAppBar(0, {})
    }
}

@Composable
fun SectionsContainer(
    sectionsState: SectionsState,
) {
    when (sectionsState) {
        SectionsState.Loading -> {
            Loading()
        }

        is SectionsState.Error -> {
            Error(sectionsState.message)
        }

        is SectionsState.Success -> {
            Column(Modifier.fillMaxSize()) {
                var selectedSectionIndex by rememberSaveable { mutableIntStateOf(0) }

                sectionsState.sections.also { sections ->
                    SectionsHeader(
                        sections
                            .map { it.contentType }
                            .toSet() // Convert to a set to remove duplicates
                            .toList(),
                        onSectionSelected = {
                            selectedSectionIndex = it
                        },
                        selectedIndex = selectedSectionIndex
                    )

                    SectionsList(sections.filter { it.contentType == sections[selectedSectionIndex].contentType })
                }
            }
        }
    }
}

@Composable
fun Loading() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoadingPreview() {
    ChannelTheme {
        Loading()
    }
}

@Composable
fun Error(message: String?) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = message ?: "Unknown error",
            modifier = Modifier.padding(16.dp)
        )
    }
}