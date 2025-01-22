package com.example.nyancat

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.room.util.query
import com.example.nyancat.data.CatRepository
import com.example.nyancat.data.local.entity.CatEntity
import com.example.nyancat.ui.common.Result
import com.example.nyancat.ui.component.CatList
import com.example.nyancat.ui.component.ErrorScreen
import com.example.nyancat.ui.component.LoadingScreen
import com.example.nyancat.ui.detail.DetailActivity
import com.example.nyancat.ui.profile.ProfileActivity


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NyanCatApp(
    modifier: Modifier = Modifier,
    viewModel: NyanCatViewModel,
    activity: ComponentActivity
) {
    val catsState by viewModel.cats.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    when(val result = catsState) {
        is Result.Loading -> {
            LoadingScreen(modifier)
        }
        is Result.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            SearchBar(
                                query = searchQuery,
                                onQueryChange = { searchQuery = it },
                                onSearch = { },
                                active = false,
                                onActiveChange = { },
                                placeholder = { Text(text = "Search cat...") },
                                modifier = Modifier.padding(16.dp)

                            ) { }
                        },

                        actions = {
                            IconButton(onClick = {navigateToProfile(activity)} ){
                                Icon(imageVector = Icons.Filled.Person, contentDescription = "about page")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                val filteredCats = result.data.filter { cat ->
                    cat.name.contains(searchQuery, ignoreCase = true) ||
                            cat.description.contains(searchQuery, ignoreCase = true)
                }
                CatList(
                    cats = filteredCats,
                    modifier = modifier.padding(innerPadding),
                    navigateToDetail = { cat ->
                        navigateToDetail(cat, activity)
                    }
                )
            }

        }
        is Result.Error -> {
            ErrorScreen(message = result.error, modifier)
        }
    }
}
fun navigateToDetail(cat: CatEntity, activity: ComponentActivity) {
    val intent = Intent(activity, DetailActivity::class.java)
    intent.putExtra(DetailActivity.CATS_DATA, cat)
    activity.startActivity(intent)
}

fun navigateToProfile(activity: ComponentActivity){
    val intent = Intent(activity, ProfileActivity::class.java)
    activity.startActivity(intent)
}

