package com.example.projektsmb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projektsmb.Data.Child
import com.example.projektsmb.ui.theme.ProjektSMBTheme

class AddChildActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                // A surface container using the 'background' color from the theme
                SetSystemBarColor(color = Color(android.graphics.Color.parseColor("#200036")))
                var sharedPrefs = getSharedPreferences("def_prefs", MODE_PRIVATE)
                var fontDefault = sharedPrefs.getFloat("fontDefault", 1f)
                var id = intent.extras?.getString("parentId")
                val viewModel by viewModels<ChildViewModel>()
                var listName = intent.extras?.getString("name")
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color(android.graphics.Color.parseColor("#200036")))
                            .fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 30.dp)
                        ) {
                            Text(text = "Add position",
                                fontSize = (54 * fontDefault).sp,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                                )
                            Column(
                                modifier = Modifier.padding(all = 50.dp)
                            ) {}

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .clip(shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp))
                                    .background(Color.White)
                            )
                            {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 30.dp)
                                ) {
                                    var productName by remember { mutableStateOf(TextFieldValue("")) }
                                    OutlinedTextField(
                                        value = productName,
                                        onValueChange = {productName = it},
                                        colors = TextFieldDefaults.textFieldColors(textColor = Color.Black, containerColor = Color.Transparent),
                                        label = { Text(text = "Product Name")},
                                        modifier = Modifier.padding(top = 20.dp)
                                    )

                                    var price by remember{ mutableStateOf(TextFieldValue("")) }
                                    OutlinedTextField(value = price,
                                        onValueChange = {price = it},
                                        colors = TextFieldDefaults.textFieldColors(textColor = Color.Black, containerColor = Color.Transparent),
                                        label = { Text(text = "Price")},
                                        modifier = Modifier.padding(top = 20.dp),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                                    )

                                    var quantity by remember{ mutableStateOf(TextFieldValue("")) }
                                    OutlinedTextField(value = quantity,
                                        onValueChange = {quantity = it},
                                        colors = TextFieldDefaults.textFieldColors(textColor = Color.Black, containerColor = Color.Transparent),
                                        label = { Text(text = "Quantity")},
                                        modifier = Modifier.padding(top = 20.dp),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(all = 50.dp)
                                    ) {}

                                    Button(onClick = {
                                        var children = Child(id = "0",productName = productName.text, price = price.text.toDouble(), quantity = quantity.text, bought = false, parentId = id!!)
                                        viewModel.addChild(children)
                                        val intent = Intent(applicationContext, ChildActivity::class.java)
                                        intent.putExtra("parentId", id)
                                        intent.putExtra("name", listName)

                                        val notification = Intent()
                                        notification.putExtra("product", productName.text)
                                        notification.setAction("com.example.projektsmbreceiver")
                                        notification.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                                        applicationContext.sendBroadcast(notification)
                                        onBackPressed()
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
}