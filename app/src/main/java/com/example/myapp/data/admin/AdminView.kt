package com.example.myapp.data.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.houselocatapp.navigation.Views
import com.example.myapp.R
import com.example.myapp.data.repository.MaisonViewModel
import com.example.myapp.model.Maison
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminView(
    navController: NavController,
    onAddHouseClick: () -> Unit,
    onUpdateHouseClick: () -> Unit,
    maisonData: MaisonViewModel
){
    val listeMaison: List<Maison>
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "LocatHouse",
                color = Color.Black,
                fontWeight = FontWeight.Normal) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(Color(0xFFBCB1CF)),
                actions = {
                    Icon(imageVector = Icons.Default.ExitToApp,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        contentDescription = null)
                })
        }
    ){ padding ->
        Column(modifier = Modifier.padding(padding)) {



            Box (modifier = Modifier
                .fillMaxSize()
                .scrollable(orientation = Orientation.Horizontal, state = rememberScrollState())
                .padding(15.dp)){
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 70.dp)
                ) {
                    FirestoreCollectionScreen(maisonData,onUpdateHouseClick)
                }
                FloatingActionButton(onClick = { onAddHouseClick.invoke() }
                    ,modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }

            }


        }
    }
}

@Composable
fun FirestoreCollectionScreen(
    maisonData: MaisonViewModel,
    onUpdateHouseClick: () -> Unit,
) {
    val maiss : List<Maison>
    val documents = remember { mutableStateListOf<DocumentSnapshot>() }

    LaunchedEffect(Unit) {
        val collectionRef = Firebase.firestore.collection("maison")
        val snapshotListener = collectionRef.addSnapshotListener { snapshot, _ ->
            snapshot?.documents?.let { documentList ->
                documents.clear()
                documents.addAll(documentList)
            }
        }

    }
    Column {
        for (document in documents) {
            val typeM = document["typeM"] as? String
            val adresse = document["adresse"] as? String
            val etat = document["etat"] as? String
            val image = document["image"] as? String
            val superf = document["superficie"] as? String
            val prix = document["prix"] as? String
            ExpandableCard(image = image.toString(),
                typeM = typeM.toString(),
                adresse = adresse.toString(),
                etat = etat.toString(),
                superficie = superf.toString(),
                prix = prix.toString(),
                maisonDelete = maisonData,
                onUpdateHouseClick = onUpdateHouseClick,
                  maisonUpdate = maisonData
                )

        }
    }

}


@Composable
fun ExpandableCard(maisonDelete: MaisonViewModel,maisonUpdate: MaisonViewModel,onUpdateHouseClick: () -> Unit,image: String, typeM: String,adresse: String,etat: String, superficie: String,prix: String ) {
    var expanded by remember { mutableStateOf(false) }
    val navController: NavController
    val context = LocalContext.current
    Card(elevation = CardDefaults.elevatedCardElevation(10.dp),
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(10.dp)
    ) {
        Column(
            Modifier
                .padding(5.dp)
                .background(Color.White)
                .clip(RoundedCornerShape(10.dp))) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.weight(1f))
                FloatingActionButton(onClick = {
                                maisonUpdate.updateData(
                                    maisonID = typeM,
                                    context = context,
                                    updatedData = mapOf()
                                )
                               onUpdateHouseClick.invoke()
                }

                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                }
                FloatingActionButton(onClick = {
                                               maisonDelete.deleteData(
                                                   maisonID = typeM,
                                                   context = context
                                               )
                }, modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                }

            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Text(text = typeM)
                    Text(text = adresse)
                    Text(text = etat)
                    Text(text = superficie)
                    Text(text = prix)
                }
            }
        }
    }
}
@Preview
@Composable
fun MyScreen() {
    Column {
        //ExpandableCard(image = "", typeM = "fgdf", adresse = "dfgdf", etat = "dfg", superficie = "", prix = "")
        // Add more ExpandableCard instances as needed
    }
}