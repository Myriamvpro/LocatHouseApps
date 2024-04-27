package com.example.myapp.data.admin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapp.R
import com.example.myapp.data.repository.MaisonViewModel
import com.example.myapp.model.Maison

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMaison(
    maison: MaisonViewModel,
    onBackPressed : ()->Unit,
    navController: NavController
)

{
    Box(modifier = Modifier
        .fillMaxSize()
        .scrollable(orientation = Orientation.Horizontal, state = rememberScrollState())
    ){
        val context = LocalContext.current
        var IdTxt by remember {
            mutableStateOf(  "")
        }
        var typeTxt by remember {
            mutableStateOf(  "")
        }
        var adresseTxt by remember {
            mutableStateOf( "")
        }
        var etatTxt by remember {
            mutableStateOf(  "")
        }
        var superficieTxt by remember {
            mutableStateOf( "")
        }
        var superficieInt by remember {
            mutableStateOf( 0)
        }
        var prixTxt by remember {
            mutableStateOf( "")
        }
        var prixInt by remember {
            mutableStateOf( 0)
        }
        var imageTxt by remember {
            mutableStateOf(  "")
        }
        var piscineTxt by remember {
            mutableStateOf(  0)
        }
        var chambreTxt by remember {
            mutableStateOf(  0)
        }
        var doucheTxt by remember {
            mutableStateOf(  0)
        }
        var salonTxt by remember {
            mutableStateOf(  0)
        }
        var buanderieTxt by remember {
            mutableStateOf(  0)
        }
        var cuisineTxt by remember {
            mutableStateOf(  0)
        }
        var verandaTxt by remember {
            mutableStateOf(  0)
        }
        var garageTxt by remember {
            mutableStateOf(  0)
        }
        var bureauTxt by remember {
            mutableStateOf(  0)
        }
        var jardinTxt by remember {
            mutableStateOf(  0)
        }
        val pictureLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {uri ->
                imageTxt = uri.toString()
            }
        )
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(bottom = 70.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    pictureLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            ){
                if(imageTxt.isEmpty()){
                    Card(modifier = Modifier
                        .fillMaxSize(0.98f)
                        .align(Alignment.Center),
                        shape = RectangleShape,
                    ){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = null)
                            Text(text = "Cliquer ici pour sélectionner l'image")
                        }
                    }
                }else{
                    AsyncImage(
                        model = imageTxt,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.placeholder),
                        error = painterResource(id = R.drawable.placeholder),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                ){
                    IconButton(onClick = onBackPressed) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            }
            Column(modifier = Modifier.padding(10.dp)) {
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = typeTxt, onValueChange = { typeTxt = it }, label = { Text(text = "Type maison")})
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = adresseTxt, onValueChange = { adresseTxt = it }, label = { Text(text = "Adresse")})
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = etatTxt, onValueChange = {  etatTxt = it}, label = { Text(text = "Etat")})
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(modifier = Modifier.width(200.dp),value = superficieTxt, onValueChange = { superficieTxt = it}, label = { Text(text = "Superficie")})
                    OutlinedTextField(value = prixTxt, onValueChange = {  prixTxt = it }, label = { Text(text ="Prix")})
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Chambre",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { chambreTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${chambreTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { chambreTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Douches",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { doucheTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${doucheTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { doucheTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Buanderie",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { buanderieTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${buanderieTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { buanderieTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Salon",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { salonTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${salonTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { salonTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Bureau",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { bureauTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${bureauTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { bureauTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Cuisine",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { cuisineTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${cuisineTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { cuisineTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Veranda",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { verandaTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${verandaTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { verandaTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Garage",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { garageTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${garageTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { garageTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Piscine",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { piscineTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${piscineTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { piscineTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Jardin",
                        color = Color.Black
                    )
                    Box(modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp))) {
                        Row(modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            SmallFloatingActionButton(onClick = { jardinTxt++ },
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.Add,
                                    contentDescription =null )
                            }
                            Text(text = "${jardinTxt.toString()}",
                                color = Color.Black
                            )
                            SmallFloatingActionButton(onClick = { jardinTxt--},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription =null)
                            }
                        }
                    }
                }



            }
        }

        FloatingActionButton(onClick = {
            val maissDate = Maison(
                typeM = typeTxt,
                adresse = adresseTxt,
                etat = etatTxt,
                superficie = superficieTxt,
                prix = prixTxt,
                image = imageTxt,
                chambre = chambreTxt,
                douche = doucheTxt,
                salon = salonTxt,
                buanderie = buanderieTxt,
                cuisine = cuisineTxt,
                veranda = verandaTxt,
                piscine = piscineTxt,
                garage = garageTxt,
                bureau = bureauTxt,
                jardin = jardinTxt
            )

            maison.saveData(maisonData = maissDate, context = context)
            navController.popBackStack()

        }
            ,modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPreview(){

}