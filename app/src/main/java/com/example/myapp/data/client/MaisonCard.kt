package com.example.myapp.data.client

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapp.R
import com.example.myapp.data.repository.MaisonViewModel
import com.example.myapp.model.Maison
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MaisonCard(
    maiss: Maison,
    maisonData: MaisonViewModel,
    onNavigateToMaisonScreen : (Maison)->Unit
){
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(text = "Propriété Disponible",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold)
        LazyColumn(){
            items(documents.chunked(2)){ pair->
                Row {
                    pair.forEach { m ->
                        Column(modifier = Modifier
                            .weight(1f)
                            .padding(3.dp)) {
                            val typeM = m["typeM"] as? String
                            val image = m["image"] as? String
                            val prix = m["prix"] as? String
                            val adresse = m["adresse"] as? String
                            val etat = m["etat"] as? String
                            val superficie = m["superficie"] as? String
                            val chambre = m["chambre"] as? Int
                            val chambres = (m["chambre"] as? Int) ?: maiss.chambre
                            val douche = m["douche"] as? Int
                            val salon = m["salon"] as? Int
                            val buanderie = m["buanderie"] as? Int
                            val cuisine = m["cuisine"] as? Int
                            val veranda = m["veranda"] as? Int
                            val piscine = m["piscine"] as? Int
                            val garage = m["garage"] as? Int
                            val bureau = m["bureau"] as? Int
                            val jardin = m["jardin"] as? Int

                            house(
                                maiss = maiss,
                                maisonData = maisonData,
                                image = image.toString(),
                                typeM = typeM.toString(),
                                prix = prix.toString(),
                                onNavigateToMaisonScreen = {
                                    onNavigateToMaisonScreen(maiss)
                                },
                                adresse = adresse.toString(),
                                etat = etat.toString(),
                                superficie = superficie.toString(),
                                chambre = chambres,
                                douche = 3,
                                salon = 0,
                                buanderie = 0,
                                cuisine = 0,
                                veranda = 0,
                                piscine = 0,
                                garage = 0,
                                bureau = 0,
                                jardin = 0,

                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun house(
    maiss: Maison,
    maisonData: MaisonViewModel,
    image: String, typeM: String, prix: String,
    adresse: String,
    etat: String,
    superficie: String,
    chambre: Int,
    douche: Int,
    salon: Int,
    buanderie: Int,
    cuisine: Int,
    veranda: Int,
    piscine: Int,
    garage: Int,
    bureau: Int,
    jardin: Int,
    onNavigateToMaisonScreen: ()->Unit,
){
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    Card (
        modifier = Modifier
            .width(147.dp)
            .clickable {  }
            .border(
                width = 1.dp,
                color = Color.Black.copy(0.4f),
                shape = RoundedCornerShape(bottomEnd = 15.dp)
            ),
        colors = CardDefaults.cardColors(Color.White.copy(0.9f)),
        shape = RoundedCornerShape( bottomEnd = 15.dp)
    ){
        AsyncImage(
            model =  image,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
        )

        Column(modifier = Modifier.padding(4.dp).fillMaxWidth()) {
            Text(text = typeM,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 13.dp)
                ) {
                Text(text = prix + "$",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { onNavigateToMaisonScreen() }) {
                    Text(text = "Voir")
                }


            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Text(text = typeM)
                    Text(text = adresse)
                    Text(text = chambre.toString())
                    Text(text = superficie)
                    Text(text = douche.toString())
                }
            }



        }

    }
}


@Preview(showBackground = true)
@Composable
fun MaisonViewPreview(){
    //MaisonView()
}
