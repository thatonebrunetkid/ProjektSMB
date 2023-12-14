package com.example.projektsmb

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.ListFormatter.Width
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.projektsmb.ui.theme.ProjektSMBTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    lottieEntranceAnimation(Modifier.size(300.dp, 300.dp))
                    WelcomeScreenText()
                    LoadingAnim(Modifier.padding(100.dp))

                    var sharedPrefs = getSharedPreferences("def_prefs", MODE_PRIVATE)
                    if(sharedPrefs.getString("userName", "") == "")
                    {
                        var editor = sharedPrefs.edit()
                        editor.putFloat("fontDefault", 1f)
                        editor.commit()
                        Handler().postDelayed({
                            val intent = Intent(applicationContext, AuthorizationActivity::class.java)
                            intent.putExtra("Registered", "No")
                            startActivity(intent)
                        }, 4000)
                    }else
                    {
                        Handler().postDelayed({
                            val intent = Intent(applicationContext, AuthorizationActivity::class.java)
                            intent.putExtra("Registered", "Yes")
                            startActivity(intent)
                        }, 4000)
                    }
                }
            }
        }
    }
}

@Composable
fun lottieEntranceAnimation(modifier: Modifier = Modifier)
{
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url("https://lottie.host/729b2cae-7fc9-4cd8-8e22-79bc201d6e71/cW04XsY0i5.json"))
    LottieAnimation(composition = composition, modifier)
}

@Composable
fun WelcomeScreenText(modifier: Modifier = Modifier)
{
    Text(text = "Shoppper",
        color = Color(android.graphics.Color.parseColor("#f76a54")),
        fontSize = 30.sp,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LoadingAnim(modifier: Modifier = Modifier)
{
    LinearProgressIndicator(color = Color(android.graphics.Color.parseColor("#f76a54")))
}



