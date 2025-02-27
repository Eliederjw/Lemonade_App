package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme { LemonadePreview() }
        }
    }
}

@Composable
fun LemonadeApp(modifier: Modifier = Modifier){
    Scaffold (
        topBar = { TopBar() },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                content = { ImageAndText(modifier) }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = { TopBarText() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.top_bar_collor))
    )
}
@Composable
fun TopBarText() {
    Text(
        text = stringResource(R.string.top_bar_text),
        fontWeight = FontWeight.Bold)
}

@Composable
fun ImageAndText(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf(1) }
    var squeezeCount = (2..4).random()
    val currentImage = when(currentScreen) {
        1 -> painterResource(R.drawable.lemon_tree)
        2 -> painterResource(R.drawable.lemon_squeeze)
        3 -> painterResource(R.drawable.lemon_drink)
        else -> painterResource(R.drawable.lemon_restart)
    }
    val currentText = when(currentScreen) {
        1 -> stringResource(R.string.tap_the_lemon_tree)
        2 -> stringResource(R.string.keep_taping)
        3 -> stringResource(R.string.tap_the_lemonade)
        else -> stringResource(R.string.tap_the_empty)
    }

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_collor)),
            onClick = {
                if (currentScreen == 2) squeezeCount-- else currentScreen = changeCurrentImage(currentScreen)
                if (squeezeCount == 0) currentScreen = changeCurrentImage(currentScreen)
        }) {
            Image (
                painter = currentImage,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = currentText,
            fontSize = 18.sp
        )
    }
}

fun changeCurrentImage(currentScreen: Int) : Int {
    return if(currentScreen < 4) currentScreen + 1 else 1
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    LemonadeTheme {
        LemonadeApp(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}