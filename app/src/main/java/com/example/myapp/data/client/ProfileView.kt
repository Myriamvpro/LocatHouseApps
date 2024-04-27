package com.example.myapp.data.client

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapp.R
import com.example.myapp.data.admin.ExpandableCard
import com.example.myapp.data.client.MaisonCard
import com.example.myapp.data.repository.MaisonViewModel
import com.example.myapp.data.signIn.UserData
import com.example.myapp.model.Maison
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    userData: UserData?,
    isLoading: Boolean,
    mm: List<Maison>,
    maison: Maison,
    onSignOut: () -> Unit,
    maisonData: MaisonViewModel,
    onNavigateToSigninScreen: ()->Unit,
    onNavigateToMaisonScreen: (Maison) -> Unit,
) {
    var isDropDownMenuExpanded by remember {
        mutableStateOf(false)
    }

    var query by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = userData){
        if(userData==null){
            onNavigateToSigninScreen()
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "LocatHouse", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
            },
            actions = {
                AsyncImage(
                    model = userData?.profilePictureUrl,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.accunt),
                    error = painterResource(id = R.drawable.accunt),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            isDropDownMenuExpanded = !isDropDownMenuExpanded
                        }
                )
                DropdownMenu(
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "${userData?.username}")
                            Text(text = "Déconnexion")
                        } },
                        onClick = {
                            onSignOut()
                            isDropDownMenuExpanded = false
                        }
                    )
                }
            }
        )
    },
        )
    {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(7.dp)
                .padding(paddingValues)
        ) {
            if(isLoading){
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            OutlinedTextField(
                value = query ,
                onValueChange = { text->
                    query = text

                },
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                placeholder = { Text(text = "Rechercher")},
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                },
                trailingIcon = {
                    AnimatedVisibility(visible = query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = null)
                    }
                }
            )
            if(query.isEmpty()){

                MaisonCard(maiss = maison, maisonData = maisonData, onNavigateToMaisonScreen = {
                    onNavigateToMaisonScreen(maison)
                })
            }else{
                val db = Firebase.firestore
                db.collection("maison")
                    .whereEqualTo("typeM", query)
                    .get()
                    .addOnSuccessListener { querySnapshot ->

                    }
                    .addOnFailureListener { exception ->

                    }
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(bottom = 202.dp)) {

                        Text(
                            text = "Aucune maison disponnible",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }


            }


        }

    }
}

