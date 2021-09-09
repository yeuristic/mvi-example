package com.yeuristic.mymvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.yeuristic.mymvi.ui.theme.Gray200
import com.yeuristic.mymvi.ui.theme.Mocker
import com.yeuristic.mymvi.ui.theme.MyMviTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()

        setContent {
            MyMviTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Gray200) {
                    MainScreen(mainViewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val mainModel by mainViewModel.model.observeAsState(Mocker.mockMainModel(true))

    Column {
        Button(onClick = { mainViewModel.intent(MainViewModel.Intent.FetchData) }) {

        }
        Button(onClick = { mainViewModel.intent(MainViewModel.Intent.FetchUser) }) {

        }
        if (mainModel.isLoading) {
            LoadingScreen()
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                UserSection(userData = mainModel.userData)
                Spacer(modifier = Modifier.height(8.dp))
                ListItem(itemList = mainModel.items)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun LoadingScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        for (i in 1..5) {
            Text(
                text = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .placeholder(
                        visible = true,
                        color = Color.Gray,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White,
                        ),
                    )
            )
        }
    }
}

@Composable
fun UserSection(userData: UserData) {
    Row {
        Image(
            painter = rememberImagePainter(userData.photoUrl),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Column {
            Text(
                text = userData.name
            )
            Text(text = "Age: ${userData.age}")
        }
    }
}

@Composable
fun ListItem(itemList: List<Item>) {
    LazyColumn {
        items(itemList) {
            ItemCard(item = it, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.h5)
            Text(text = item.description, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyMviTheme {
        Surface(color = Gray200) {
            MainScreen(mainViewModel = MainViewModel())
        }
    }
}