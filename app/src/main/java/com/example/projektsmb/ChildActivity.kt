package com.example.projektsmb
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projektsmb.Data.Child
import com.example.projektsmb.ui.theme.ProjektSMBTheme
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToLong

class ChildActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                SetSystemBarColor(color = Color(android.graphics.Color.parseColor("#200036")))
                val viewModel by viewModels<ViewModel>()
                var listName = intent.extras?.getString("name")
                var id = intent.extras?.getString("id")
                //val children by viewModel.getAllChilds(id!!).collectAsState(emptyList())
                var sharedPrefs = getSharedPreferences("def_prefs", MODE_PRIVATE)
                var fontDefault = sharedPrefs.getFloat("fontDefault", 1f)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color(android.graphics.Color.parseColor("#200036")))
                            .fillMaxSize()
                    ){
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally,
                           modifier = Modifier
                               .fillMaxSize()
                               .padding(top = 30.dp)
                       ) {
                           Text(text = listName.toString(),
                               fontSize = (54 * fontDefault).sp,
                               style = MaterialTheme.typography.bodyLarge,
                               color = Color.White
                           )

                           Column(
                               modifier = Modifier.padding(all = 50.dp)
                           ){}

                           Box(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .fillMaxHeight()
                                   .clip(shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp))
                                   .background(Color.White)
                           )
                           {
                               LazyColumn(
                                   modifier = Modifier.fillMaxSize(),
                                   horizontalAlignment = Alignment.CenterHorizontally
                               )
                               {
                                   /*
                                 items(children){
                                     children ->
                                     Column {
                                        Row {
                                            CustomizePresentationRow(children = children, viewModel, fontDefault)
                                        }
                                     }
                                 }
                                    */
                               }

                               Column(modifier = Modifier.padding(all = 50.dp)) {
                               }

                               Column(
                                   verticalArrangement = Arrangement.Bottom,
                                   modifier = Modifier
                                       .fillMaxSize()
                                       .padding(bottom = 30.dp)
                               ) {
                                    Button(onClick = {
                                        intent = Intent(applicationContext, AddChildActivity::class.java)
                                        intent.putExtra("parentId", id)
                                        intent.putExtra("name", listName)
                                        startActivity(intent)
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(Color(android.graphics.Color.parseColor("#200036"))),
                                        shape = CircleShape,
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier
                                            .size(80.dp)
                                            .align(Alignment.End)
                                            .padding(all = 10.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Add,
                                                contentDescription = "AddNewChild",
                                                tint = Color(android.graphics.Color.parseColor("#f76a54"))
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

@Composable
fun CustomizePresentationRow(children : Child, viewModel: ViewModel, fontDefault : Float)
{
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ){
        Icon(
            Icons.Rounded.ShoppingCart,
            contentDescription = "productRow",
            tint = Color(android.graphics.Color.parseColor("#f76a54")),
            modifier = Modifier.padding(start = 30.dp)
        )
        Column {
            Row {
                Text(text = children.productName,
                    fontSize = (25 * fontDefault).sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 30.dp)
                )
                Text(text = " x " + children.quantity.toString(),
                    fontSize = (25 * fontDefault).sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Row {
                Text(text = children.price.toString() + " PLN",
                    fontSize = (18 * fontDefault).sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 30.dp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ){
           Row(
               horizontalArrangement = Arrangement.End
           ) {
               var checkboxValue by remember { mutableStateOf(children.bought) }
               Checkbox(checked = checkboxValue, onCheckedChange = {
                   checkboxValue = it
                   children.bought = checkboxValue
                   //viewModel.updateChild(children)
               }
               )

               Row(
                  horizontalArrangement = Arrangement.End
               ) {
                   Button(onClick = {
                       //viewModel.deleteChild(children)
                   },
                       colors = ButtonDefaults.outlinedButtonColors(Color.Transparent)
                   ) {
                       Icon(
                           Icons.Default.Close,
                           contentDescription = "delete child",
                           tint = Color.Red
                       )
                   }
               }
           }
        }




    }
}