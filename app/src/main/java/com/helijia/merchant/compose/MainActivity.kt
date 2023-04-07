package com.helijia.merchant.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helijia.merchant.compose.api.NetRepository
import com.helijia.merchant.compose.api.VersionInfo
import com.helijia.merchant.compose.ui.theme.MerchantcomposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MerchantcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val isLoading = remember { mutableStateOf(true) }
    val versionList = remember { mutableStateOf(emptyList<VersionInfo>()) }

    LaunchedEffect(true) {
        launch {
            val data = NetRepository.getVersionList()
            if (data.data?.widgets != null) {
                val list = data.data.widgets.map { it.data }
                versionList.value = list
            }
            isLoading.value = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("版本记录", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }, backgroundColor = Color.White)
        },
        content = { padding ->
            if (isLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else{
                VersionList(padding, versionList.value)
            }
        }
    )
}

@Composable
fun VersionList(padding: PaddingValues, list: List<VersionInfo>) {
    LazyColumn(modifier = Modifier.padding(padding), ) {
        items(list.size) {
            VersionItemView(list[it])
            Divider(color = Color(0xffcccccc), thickness = 0.5.dp)
        }
    }
}

@Composable
fun VersionItemView(item: VersionInfo) {
    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = item.version, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = item.date, color = Color.Gray, fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = item.content.replace("\\n", "\n"), color = Color.Black, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MerchantcomposeTheme {
        MainScreen()
    }
}
