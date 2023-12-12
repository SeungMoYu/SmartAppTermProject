package kr.ac.kumoh.ce20180727.smartAppTermProject

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.compose.AsyncImage


enum class CompletedItemScreen {
    List,
    Detail
}

@Composable //리스트 화면
fun CompletedItemApp(completedItemList: List<CompletedItem>) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CompletedItemScreen.List.name,
    ) {
        composable(route = CompletedItemScreen.List.name) {
            CompletedItemList(navController, completedItemList)
        }
        composable(
            route = CompletedItemScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt("index") ?: -1
            if (index >= 0)
                CompletedItemDetail(completedItemList[index]) // 완성 아이템의 세부 정보 페이지로 이동
        }
    }
}


@Composable
fun CompletedItemList(navController: NavController, list: List<CompletedItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        items(list.size) {
            CompletedItemIndex(navController, list, it)
        }
    }
}

@Composable
fun CompletedItemIndex(navController: NavController, completedItemList: List<CompletedItem>, index: Int){
// 리스트 화면에 출력 될 인덱스들
    Card(
        modifier = Modifier.clickable {
            navController.navigate(CompletedItemScreen.Detail.name + "/$index")
        },
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(16.dp)

        ) {
            AsyncImage(
                model = "${completedItemList[index].image}",
                contentDescription = "완성 아이템 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextTitle(completedItemList[index].name)
                ComponentItem(completedItemList[index])
            }
        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}

@Composable
fun ComponentItem(completedItem: CompletedItem) {
    Row {
        Text("조합 아이템 : ")
        Spacer(modifier = Modifier.width(5.dp))
        AsyncImage(
            model = "${completedItem.componentItem1Image}",
            contentDescription = "조합 아이템1 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(percent = 10))
        )
        Spacer(modifier = Modifier.width(10.dp))
        AsyncImage(
            model = "${completedItem.componentItem2Image}",
            contentDescription = "조합 아이템2 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(percent = 10))
        )
    }
}

@Composable
fun CompletedItemDetail(completedItem: CompletedItem) {
// 완성아이템의 세부 정보 페이지. completedItem을 통해서 서버에서 정보를 얻음
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = "${completedItem.image}",
            contentDescription = "완성 아이템 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(220.dp)
                .border(5.dp, Color.Black)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "${completedItem.name}",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Text(
            "조합 아이템",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "${completedItem.componentItem1Image}",
                contentDescription = "조합 아이템1 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(percent = 10)),
            )
            Spacer(modifier = Modifier.width(20.dp))
            AsyncImage(
                model = "${completedItem.componentItem2Image}",
                contentDescription = "조합 아이템2 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(percent = 10)),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        completedItem.effect?.let {
            Text(
                it,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
        }

    }
}