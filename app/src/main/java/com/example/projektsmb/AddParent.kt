package com.example.projektsmb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projektsmb.Data.Parent
import com.example.projektsmb.ui.theme.ProjektSMBTheme

class AddParent : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                SetSystemBarColor(color = Color(android.graphics.Color.parseColor("#200036")))
                var sharedPrefs = getSharedPreferences("def_prefs", MODE_PRIVATE)
                var fontDefault = sharedPrefs.getFloat("fontDefault", 1f)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                           .background(Color(android.graphics.Color.parseColor("#200036"))),
                       horizontalAlignment = Alignment.CenterHorizontally
                   ) {
                       Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 30.dp)
                       ){
                           Text(text = "Create new list",
                               color = Color.White,
                               fontSize = (45 * fontDefault).sp,
                               style = MaterialTheme.typography.bodyLarge,
                           )
                       }

                       Column(
                           modifier = Modifier.padding(all = 50.dp)
                       ){}

                       Box(modifier = Modifier
                           .fillMaxHeight()
                           .fillMaxWidth()
                           .clip(shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp))
                           .background(Color.White)){

                           Column(
                               modifier = Modifier.fillMaxSize(),
                               horizontalAlignment = Alignment.CenterHorizontally
                           )
                           {
                               var name by remember { mutableStateOf(TextFieldValue("")) }
                               OutlinedTextField(
                                   value = name,
                                   onValueChange = {name = it},
                                   colors = TextFieldDefaults.colors(focusedTextColor = Color.Black, focusedContainerColor = Color.Transparent),
                                   label = { Text(text = "Name")},
                                   modifier = Modifier.padding(top = 20.dp))

                               Button(onClick = {
                                   val parent = Parent(id = "0", ListName = name.text)
                                   val viewModel by viewModels<ViewModel>()
                                   viewModel.addParent(parent)
                                   val intent = Intent(applicationContext, DashboardActivity::class.java)
                                   startActivity(intent)
                               },
                                        modifier = Modifier
                                            .padding(top = 20.dp)
                                            .align(Alignment.CenterHorizontally),
                                   colors = ButtonDefaults.outlinedButtonColors(Color(android.graphics.Color.parseColor("#f76a54")))
                                   ) {
                                    Text(text = "Create", color = Color.White)
                               }

                           }
                       }
                   }
                }
            }
        }
    }
}