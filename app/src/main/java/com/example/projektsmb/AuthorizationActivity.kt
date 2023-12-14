package com.example.projektsmb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projektsmb.ui.theme.ProjektSMBTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class AuthorizationActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var registeredStatus = intent.extras?.getString("Registered")
        var loginVersion = if (registeredStatus == "Yes") "Sign-In" else "Sign-Up"
        var auth = FirebaseAuth.getInstance()
        setContent {
            ProjektSMBTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                            Text(text = loginVersion,
                                color = Color(android.graphics.Color.parseColor("#f76a54")),
                                fontSize = 70.sp,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )

                        Divider(
                            modifier = Modifier.padding(15.dp)
                        )

                        var username by remember{ mutableStateOf(TextFieldValue("")) }
                        OutlinedTextField(
                            value = username,
                            onValueChange = {username = it},
                            colors = TextFieldDefaults.textFieldColors(textColor = Color.Black, containerColor = Color.Transparent),
                            label = { Text(text = "Username")},
                            modifier = Modifier.padding(top = 20.dp)
                            )

                        var password by remember{ mutableStateOf(TextFieldValue("")) }
                        OutlinedTextField(
                            value = password,
                            onValueChange = {password = it},
                            colors = TextFieldDefaults.textFieldColors(textColor = Color.Black, containerColor = Color.Transparent),
                            label = { Text(text = "Password")},
                            modifier = Modifier.padding(top = 20.dp)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(all = 50.dp)
                        ) {}
                        
                        Button(onClick = {
                                         if(registeredStatus != "Yes")
                                         {
                                             auth.createUserWithEmailAndPassword(
                                                 username.text,
                                                 password.text
                                             ).addOnCompleteListener{
                                                 if(it.isSuccessful)
                                                 {
                                                     Toast.makeText(applicationContext,"Registered", Toast.LENGTH_SHORT).show()
                                                     var sharedPrefs = getSharedPreferences("def_prefs", MODE_PRIVATE)
                                                     val editor = sharedPrefs.edit()
                                                     var user = auth.currentUser
                                                     editor.putString("userName", username.text.split('@')[0])
                                                     editor.putString("userId", user!!.uid.toString())
                                                     editor.commit()
                                                     val intent = Intent(applicationContext, DashboardActivity::class.java)
                                                     startActivity(intent)
                                                 } else
                                                 {
                                                     Toast.makeText(applicationContext, "Not Registered", Toast.LENGTH_SHORT).show()
                                                 }
                                             }
                                         } else
                                         {
                                             auth.signInWithEmailAndPassword(
                                                 username.text,
                                                 password.text
                                             ).addOnCompleteListener{
                                                 if(it.isSuccessful)
                                                 {
                                                     Toast.makeText(applicationContext, "Logged", Toast.LENGTH_SHORT).show()
                                                     val intent = Intent(applicationContext, DashboardActivity::class.java)
                                                     startActivity(intent)
                                                 } else
                                                 {
                                                     Toast.makeText(applicationContext, "Not logged", Toast.LENGTH_SHORT).show()
                                                 }
                                             }
                                         }
                        },
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .align(Alignment.CenterHorizontally),
                            colors = ButtonDefaults.outlinedButtonColors(Color(android.graphics.Color.parseColor("#f76a54")))
                            ) {
                            Text(text = loginVersion, color = Color.White)
                        }


                    }
                }
            }
        }
    }
}