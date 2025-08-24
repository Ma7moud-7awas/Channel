package com.m7.channel.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.m7.channel.R
import com.m7.channel.ui.model.ContentType
import com.m7.channel.ui.model.UIContent
import com.m7.channel.ui.model.UISection
import com.m7.channel.ui.theme.BlueBlack
import com.m7.channel.ui.theme.ChannelTheme
import com.m7.channel.ui.theme.Red

@Composable
fun SectionsHeader(
    sections: List<String>,
    onSectionSelected: (Int) -> Unit,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier) {
        itemsIndexed(sections, key = { i, _ -> i }) { index, sectionTitle ->
            Text(
                text = ContentType.resFromValue(sectionTitle)
                    ?.let { stringResource(it) }
                    ?: sectionTitle,
                color = Color.White,
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(if (selectedIndex == index) Red else BlueBlack)
                    .clickable { onSectionSelected(index) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(locale = "ar", showBackground = true)
@Composable
fun SectionsHeaderPreview() {
    ChannelTheme {
        SectionsHeader(
            sections = listOf("podcast", "episode", "audio_book"),
            onSectionSelected = {},
            0
        )
    }
}

@Composable
fun SectionsList(sections: List<UISection>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(sections, key = { it.name }) {
            when (it.type) {
                "2_lines_grid" -> {
                    GridList(section = it)
                }

                "big_square" -> {
                    BigSquareList(section = it)
                }

                "queue" -> {
                    QueueList(section = it)
                }

                else -> {
                    SquareList(section = it)
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "en")
@Preview(showBackground = true, backgroundColor = 0xFF262939, locale = "ar")
@Composable
fun SectionsListPreview() {
    ChannelTheme {
        SectionsList(
            sections = listOf(
                UISection(
                    name = "Square Section",
                    type = "square",
                    contentType = "podcast",
                    order = 0,
                    content = listOf(
                        UIContent(
                            name = "Content 1 one two three four five",
                            avatarUrl = "https://example.com/avatar1.jpg",
                            duration = 120,
                            releaseDate = "2023-07-01"
                        ),
                        UIContent(
                            name = "Content 2",
                            avatarUrl = "https://example.com/avatar2.jpg",
                            duration = 223344,
                            releaseDate = "2023-07-15"
                        )
                    )
                ),
                UISection(
                    name = "Grid Section",
                    type = "2_lines_grid",
                    contentType = "podcast",
                    order = 1,
                    content = listOf(
                        UIContent(
                            name = "Content 3",
                            avatarUrl = "https://example.com/avatar3.jpg",
                        ),
                        UIContent(
                            name = "Content 4",
                            avatarUrl = "https://example.com/avatar4.jpg",
                        )
                    )
                ),
                UISection(
                    name = "Big Square Section",
                    type = "big_square",
                    contentType = "podcast",
                    order = 2,
                    content = listOf(
                        UIContent(
                            name = "Content 5",
                            episodeCount = 10,
                            avatarUrl = "https://example.com/avatar3.jpg",
                        ),
                        UIContent(
                            name = "Content 6",
                            episodeCount = 12,
                            avatarUrl = "https://example.com/avatar4.jpg",
                        )
                    )
                ),
                UISection(
                    name = "Queue Section",
                    type = "queue",
                    contentType = "podcast",
                    order = 3,
                    content = listOf(
                        UIContent(
                            name = "Content 7",
                            releaseDate = "2023-07-01",
                            duration = 234455,
                            episodeCount = 5,
                            avatarUrl = "https://example.com/avatar3.jpg",
                        ),
                        UIContent(
                            name = "Content 8",
                            avatarUrl = "https://example.com/avatar4.jpg",
                        )
                    )
                )
            )
        )
    }
}

@Composable
fun SquareList(section: UISection) {
    SectionTitleTailed(section.name)

    LazyRow(Modifier.fillMaxWidth()) {
        itemsIndexed(section.content, key = { i, _ -> i }) { index, content ->
            Column(
                Modifier
                    .width(180.dp)
                    .padding(10.dp)
            ) {
                AsyncImage(
                    model = content.avatarUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = content.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(20.dp))
                )

                Title(
                    content.name.toString(),
                    maxLines = 1,
                    modifier = Modifier.padding(vertical = 5.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Duration(
                        content.getFormatedDuration(
                            stringResource(R.string.hour_symbol),
                            stringResource(R.string.minute_symbol)
                        )
                    )

                    content.getFormatedDate()?.also {
                        Date(
                            stringResource(it.first, it.second),
                            Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GridList(section: UISection) {
    SectionTitleTailed(section.name)

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        itemsIndexed(section.content, key = { i, _ -> i }) { index, content ->
            Row(
                Modifier
                    .requiredWidth(360.dp)
                    .padding(10.dp)
            ) {
                AsyncImage(
                    model = content.avatarUrl,
                    contentDescription = content.name,
                    Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(10.dp))
                )

                Column(
                    Modifier.padding(horizontal = 10.dp)
                ) {
                    content.getFormatedDate()?.also {
                        Date(
                            stringResource(it.first, it.second),
                        )
                    }

                    // title
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Title(content.name.toString())
                    }

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Duration(
                            content.getFormatedDuration(
                                stringResource(R.string.hour_symbol),
                                stringResource(R.string.minute_symbol)
                            )
                        )

                        Spacer(Modifier.weight(1f))

                        // options
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "more options"
                        )

                        // add to
                        Icon(
                            painter = painterResource(R.drawable.playlist_add),
                            contentDescription = "add to playlist",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BigSquareList(section: UISection) {
    SectionTitleTailed(section.name)

    LazyRow(Modifier.fillMaxWidth()) {
        itemsIndexed(section.content, key = { i, _ -> i }) { index, content ->
            Box(
                Modifier
                    .size(width = 270.dp, height = 180.dp)
                    .padding(10.dp)
            ) {
                AsyncImage(
                    model = content.avatarUrl,
                    contentDescription = content.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                )

                Column(
                    Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Title(
                        content.name.toString(),
                        maxLines = 1,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )

                    content.episodeCount?.also { EpisodeCounter(it.toString()) }
                }
            }
        }
    }
}

@Composable
fun QueueList(section: UISection) {
    section.content.firstOrNull()?.also {
        // header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            SectionTitle(section.name, Modifier.weight(1f))
            it.episodeCount?.also { EpisodeCounter(it.toString()) }
            it.getFormatedDuration(
                stringResource(R.string.hour_symbol),
                stringResource(R.string.minute_symbol)
            )
                .also {
                    Text(". $it", color = Color.Gray)
                }
        }

        // body
        Box(
            Modifier
                .padding(15.dp)
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(BlueBlack)
        ) {
            Row(
                Modifier.padding(15.dp)
            ) {
                // stacked images
                LazyRow(
                    userScrollEnabled = false,
                    modifier = Modifier.fillMaxWidth(.5f)
                ) {
                    itemsIndexed(
                        items = section.content,
                        key = { i, _ -> i }) { index, content ->
                        AsyncImage(
                            model = content.avatarUrl,
                            contentDescription = content.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(140.dp)
                                .offset((index * -120).dp)
                                .zIndex(index.toFloat())
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }
                }

                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Title(it.name.toString())

                    // duration & date
                    Row(Modifier.padding(vertical = 5.dp)) {
                        Text(
                            it.getFormatedDuration(
                                stringResource(R.string.hour_symbol),
                                stringResource(R.string.minute_symbol)
                            ),
                            fontSize = 13.sp,
                            color = Red
                        )

                        it.getFormatedDate()?.also {
                            Date(
                                stringResource(it.first, it.second),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
            }

            // play queue
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = "play queue",
                tint = BlueBlack,
                modifier = Modifier
                    .padding(15.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.BottomEnd)
                    .padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF262939, locale = "ar")
@Composable
fun QueueListPreview() {
    ChannelTheme {
        QueueList(
            section = UISection(
                name = "Queue Section",
                type = "queue",
                contentType = "podcast",
                order = 0,
                content = listOf(
                    UIContent(
                        name = "Content 7",
                        releaseDate = "2023-11-30T20:30:51.000Z",
                        duration = 234455,
                        episodeCount = 5,
                        avatarUrl = "https://example.com/avatar3.jpg",
                    ),
                    UIContent(
                        name = "Content 8",
                        releaseDate = "2023-11-30T20:30:51.000Z",
                        duration = 234455,
                        episodeCount = 5,
                        avatarUrl = "https://example.com/avatar4.jpg",
                    )
                )
            )
        )
    }
}

@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SectionTitleTailed(text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )

        Icon(Icons.AutoMirrored.Default.KeyboardArrowRight, "open section")
    }
}

@Composable
fun Title(title: String, modifier: Modifier = Modifier, maxLines: Int = 2) {
    Text(
        text = title,
        color = Color.White,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
fun Duration(duration: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(BlueBlack)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Icon(Icons.Default.PlayArrow, contentDescription = "play", tint = Color.White)
        Text(
            text = duration,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun Date(date: String, modifier: Modifier = Modifier) {
    Text(
        text = date,
        fontSize = 13.sp,
        color = Color.Gray,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
fun EpisodeCounter(count: String, modifier: Modifier = Modifier) {
    Text(
        text = "$count ${stringResource(R.string.episodes)}",
        color = Color.Gray,
        maxLines = 1,
        modifier = modifier
    )
}
