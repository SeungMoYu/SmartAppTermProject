package kr.ac.kumoh.ce20180727.smartAppTermProject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import kr.ac.kumoh.ce20180727.smartAppTermProject.ui.theme.SmartAppTermProjectTheme

class MainActivity : ComponentActivity() {
    private val viewModel: CompletedItemViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: CompletedItemViewModel) {
    val completedItemList by viewModel.completedItemList.observeAsState(emptyList())

    SmartAppTermProjectTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CompletedItemApp(completedItemList)
        }
    }
}
