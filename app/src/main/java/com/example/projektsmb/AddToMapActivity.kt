package com.example.projektsmb
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projektsmb.ui.theme.ui.theme.ProjektSMBTheme

class AddToMapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                // A surface container using the 'background' color from the theme
                SetSystemBarColor(color = Color(android.graphics.Color.parseColor("#200036")))
                var shopName = intent.extras?.getString("name")
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color(android.graphics.Color.parseColor("#200036")))
                            .fillMaxSize()
                            .padding(top = 30.dp)
                    ) {
                        Text(text = "Add to fav",
                            fontSize = 54.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                            )

                        Column (modifier = Modifier.padding(all = 50.dp)){}

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp))
                            .background(Color.White))
                        {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Text(text = "Map",
                                    fontSize = 32.sp,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                        .padding(top = 15.dp)
                                    )

                                Spacer(modifier = Modifier.padding(15.dp))




                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Map()
    {

    }



}

