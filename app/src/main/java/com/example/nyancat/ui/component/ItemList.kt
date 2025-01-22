package com.example.nyancat.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nyancat.data.local.entity.CatEntity

@Composable
fun CatList(
    cats: List<CatEntity>,
    modifier: Modifier = Modifier,
    navigateToDetail: (CatEntity) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(cats) { cat ->
            CatListItem(cat = cat, modifier = Modifier.clickable {
                navigateToDetail(cat)
            })
        }
    }
}

@Composable
fun CatListItem(cat: CatEntity, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cat.imageUrl,
            contentDescription = "Image of ${cat.name}",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = cat.name, style = MaterialTheme.typography.titleMedium)
            Text(text = cat.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
        }
    }
}


