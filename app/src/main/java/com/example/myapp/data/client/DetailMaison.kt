package com.example.myapp.data.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMaison(
    typeM: String?,
    adresse: String?,
    etat: String?,
    superficie: String?,
    prix: String?,
    image: String?,
    chambre: Int?,
    douche: Int?,
    salon: Int?,
    buanderie: Int?,
    cuisine: Int?,
    veranda: Int?,
    piscine: Int?,
    garage: Int?,
    bureau: Int?,
    jardin: Int?,
    onBackPressed : ()->Unit,
    onEditClicked : ()->Unit,
    onDeleteClicked : ()->Unit,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = { Text(text = "Details Maison")},
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        ){
            Card(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxSize(0.94f)
                    .align(Alignment.Center)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Type de maison : $typeM",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Prix : $prix",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Adresse : $adresse",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Etat : $etat",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Superficie : $superficie",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Douche : $douche",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Chambre : $chambre",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Buanderie : $buanderie",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cuisine : $cuisine",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Salon : $salon",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bureau : $bureau",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Jardin : $jardin",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Garage : $garage",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Piscine : $piscine",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Veranda : $veranda",
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(10.dp)){
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Prendre Rendez-vous pour une visiter")
            }
        }

    }

}