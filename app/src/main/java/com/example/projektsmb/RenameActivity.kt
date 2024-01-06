package com.example.projektsmb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.projektsmb.ui.theme.ui.theme.ProjektSMBTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.NonDisposableHandle.parent

class InputNameActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                SetSystemBarColor(color = Color(android.graphics.Color.parseColor("#200036")))
                var sharedPrefs = getSharedPreferences("def_prefs", MODE_PRIVATE)
                var fontDefault = sharedPrefs.getFloat("fontDefault", 1f)
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Box(modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                    )
                    {
                        Box(modifier = Modifier
                            .height(400.dp)
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(0.dp, 0.dp, 50.dp, 0.dp))
                            .background(Color(android.graphics.Color.parseColor("#200036")))
                        )
                        {

                            Column (modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally){
                                LottieUserAnim(Modifier.size(150.dp, 150.dp))
                                Text(text = "Create Account",
                                    color = Color.White,
                                    fontSize = (30 * fontDefault).sp,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(top = 20.dp)
                                )
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .size(500.dp)
                        .fillMaxWidth()
                        .background(Color(android.graphics.Color.parseColor("#200036")))

                    )
                    {
                        Box(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(50.dp, 0.dp, 0.dp, 0.dp))
                            .background(Color.White)
                        ){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                var text by remember { mutableStateOf(TextFieldValue("")) }
                                OutlinedTextField(
                                    value = text,
                                    colors = TextFieldDefaults.colors(focusedTextColor = Color.Black, focusedContainerColor = Color.Transparent),
                                    onValueChange = {
                                        text = it
                                    },
                                    label = {Text(text = "Name")},
                                    modifier = Modifier.padding(top = 20.dp)
                                )

                                Button(onClick = {

                                         if(text.text != "")
                                         {
                                             var sharedPrefs = getSharedPreferences("def_prefs", MODE_PRIVATE)
                                             val editor = sharedPrefs.edit()
                                             editor.putString("userName", text.text.toString())
                                             editor.commit()
                                             Log.v("APPKA SP", sharedPrefs.getString("userName", "").toString())
                                             val intent = Intent(applicationContext, DashboardActivity::class.java)
                                             startActivity(intent)
                                         } else
                                         {
                                             Toast.makeText(applicationContext, "Input name", Toast.LENGTH_SHORT).show()
                                         }
                                },
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .align(Alignment.CenterHorizontally),
                                    colors = ButtonDefaults.outlinedButtonColors(Color(android.graphics.Color.parseColor("#f76a54")))
                                ) {
                                    Text(text = "Confirm", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LottieUserAnim(modifier: Modifier = Modifier)
{
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url("https://lottie.host/50b6bc8e-4dd9-4d74-9e1e-88d63e79f7ff/5zoTpQ9dXN.json"))
    LottieAnimation(composition = composition, modifier, iterations = LottieConstants.IterateForever)
}

@Composable
fun SetSystemBarColor(color: Color)
{
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color)
    }
}





