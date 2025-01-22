package com.example.nyancat.ui.detail

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nyancat.NyanCatViewModel
import com.example.nyancat.R
import com.example.nyancat.ViewModelFactory
import com.example.nyancat.data.local.entity.CatEntity

class DetailActivity : ComponentActivity() {
    private lateinit var catsDetail: CatEntity
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: NyanCatViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        catsDetail = IntentCompat.getParcelableExtra(intent, CATS_DATA, CatEntity::class.java) as CatEntity

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailScreen(
                        catsDetail = catsDetail,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
    companion object {
        const val CATS_DATA = "cats_data"
    }
}

@Composable
fun DetailScreen(
    catsDetail: CatEntity,
    viewModel: NyanCatViewModel
) {
    viewModel.setCatData(catsDetail)
    DetailContent(
        catsDetail.name,
        catsDetail.url.toString()
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    name: String,
    url: String,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                modifier = modifier,
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            AndroidView(
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                    }
                },
                update = {
                    it.loadUrl(url)
                }
            )
        }

    }
}
