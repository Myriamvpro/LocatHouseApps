package com.example.myapp.data.signIn

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.parseAsHtml
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.houselocatapp.navigation.Views
import com.example.myapp.R
import com.example.myapp.data.signIn.SignInState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInView(
    state: SignInState,
    navController: NavController,
    onSignInClick: () -> Unit,
    onAdminClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .background(Color.White)
    ) {
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        Image(
            painter = painterResource(id = R.drawable.logo),
            modifier = Modifier.size(150.dp),
            contentDescription = ""
        )
        OutlinedTextField(
            value = email,
            onValueChange = { text ->
                email = text
            },
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            placeholder = { Text(text = "Email Adresse") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = null)
            }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { text ->
                password = text
            },
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            placeholder = { Text(text = "Password") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Password, contentDescription = null)
            }
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                if (email == "admin" || password == "admin") {
                    onAdminClick.invoke()

                } else {
                    email = ""
                    password = ""
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Log In")
            }
        }
        Text(text = "Vous n'avez pas de compte ? Inscrivez-vous ici",
            fontSize = 13.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable {
                val url = "https://support.google.com/accounts/answer/27441?hl=fr"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivities(arrayOf(intent))
            }
        )
        Row(modifier = Modifier.padding(top = 30.dp, bottom = 30.dp)) {
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .width(160.dp)
            )
            Text(text = "OR")
            Divider(modifier = Modifier.height(1.dp))
        }
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSignInClick.invoke() }
                .height(50.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black.copy(0.5f),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.google), contentDescription = "")
                Text(
                    text = "Connectez-vous avec Google",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}