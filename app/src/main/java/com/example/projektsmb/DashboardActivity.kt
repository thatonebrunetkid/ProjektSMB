package com.example.projektsmb


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projektsmb.Data.Parent
import com.example.projektsmb.ui.theme.ProjektSMBTheme
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Plus
import compose.icons.fontawesomeicons.solid.PlusCircle
import kotlinx.coroutines.flow.Flow

class DashboardActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                SetSystemBarColor(color = Color(android.graphics.Color.parseColor("#200036")))
                val viewModel by viewModels<ViewModel>()
                val parents by viewModel.parents.collectAsState(emptyMap<String, Parent>())
                var sharedPrefs = getSharedPreferences("def_prefs", MODE_PRIVATE)
                var fontDefault = sharedPrefs.getFloat("fontDefault", 1f)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(android.graphics.Color.parseColor("#200036"))
                ) {
                    Column(
                        modifier = Modifier
                            .background(color = Color(android.graphics.Color.parseColor("#200036")))
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 30.dp)
                        ) {
                            Text(text = "Welcome ",
                                color = Color.White,
                                fontSize = (45 * fontDefault).sp,
                                style = MaterialTheme.typography.bodyLarge
                                )

                            var name = sharedPrefs.getString("userName", "")
                                if (name != null) {
                                Text(text = name,
                                    color = Color(android.graphics.Color.parseColor("#f76a54")),
                                    fontSize = (45 * fontDefault).sp,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                        Text(text = "Open existing or create new shopping list",
                            color = Color.White,
                            fontSize = (20 * fontDefault).sp,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 30.dp)
                            )


                        Column(
                            modifier = Modifier.padding(all = 50.dp)
                        ){}

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp))
                                .background(Color.White)
                        ){
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Row(
                                    modifier = Modifier.padding(top = 30.dp, start = 15.dp, end = 15.dp)
                                )
                                {
                                    Button(onClick = {
                                     val intent = Intent(applicationContext, AddParent::class.java)
                                      startActivity(intent)
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
                                        shape = CircleShape,
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(80.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Add new",
                                            tint = Color(android.graphics.Color.parseColor("#f76a54"))
                                            )
                                    }

                                    Row(
                                        modifier = Modifier.padding(15.dp)
                                    ){}

                                    Button(onClick = {
                                        intent = Intent(applicationContext, SettingsActivity::class.java)
                                        startActivity(intent)
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
                                        shape = CircleShape,
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(80.dp)
                                        ) {
                                        Icon(
                                            Icons.Default.Settings,
                                            contentDescription = "Settings",
                                            tint = Color(android.graphics.Color.parseColor("#f76a54"))
                                        )
                                    }

                                    Row(
                                        modifier = Modifier.padding(15.dp)
                                    ){}

                                    Button(onClick = {
                                                     val intent = Intent(applicationContext, AccountSettingsActivity::class.java)
                                        startActivity(intent)
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
                                        shape = CircleShape,
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(80.dp)
                                        ) {
                                        Icon(
                                            Icons.Default.Person,
                                            contentDescription = "Account information",
                                            tint = Color(android.graphics.Color.parseColor("#f76a54"))
                                        )
                                    }
                                }

                                Divider(
                                    thickness = 1.dp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(all = 20.dp)
                                )

                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    items(parents.toList()){parent ->
                                        Row {
                                            Button(onClick = {
                                                val intent = Intent(applicationContext, ChildActivity::class.java)
                                                intent.putExtra("id", parent.second.id)
                                                intent.putExtra("name", parent.second.ListName)
                                                startActivity(intent)
                                            },
                                                colors = ButtonDefaults.outlinedButtonColors(Color.Transparent),
                                                modifier = Modifier
                                                    .padding(end = 10.dp)
                                            ) {
                                                Text(text = parent.second.ListName!!, fontSize = (25 * fontDefault).sp, color = Color.Black)
                                            }
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                horizontalAlignment = Alignment.End
                                            ) {
                                                Button(onClick = {
                                                    viewModel.deleteParent(parent.second)
                                                },
                                                    colors = ButtonDefaults.outlinedButtonColors(Color.Transparent),
                                                    modifier = Modifier
                                                        .align(Alignment.End)
                                                ) {
                                                    Icon(
                                                        Icons.Default.Close,
                                                        contentDescription = "Delete",
                                                        tint = Color.Red,
                                                        modifier = Modifier.padding(start = 30.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



