package com.example.myapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date
import java.util.UUID

data class Maison(
    //var maisonID: UUID,
    val typeM: String = "",
    val adresse: String = "",
    val etat: String = "",
    val superficie: String = "",
    var prix: String = "",
    val image: String = "",
    val chambre: Int = 0,
    val douche: Int = 0,
    val salon: Int = 0,
    val buanderie: Int = 0,
    val cuisine: Int = 0,
    val veranda: Int = 0,
    val piscine: Int = 0,
    val garage: Int = 0,
    val bureau: Int = 0,
    val jardin: Int = 0
)
/*
"blog_details?typeM={typeM}?adresse={adresse}?buanderie={buanderie}" +
"?bureau={bureau}?chambre={chambre}?cuisine={cuisine}?douche={douche}?image={image}"+
"?etat={etat}?garage={garage}?jardin={jardin}?piscine={piscine}?prix={prix}"+
"?salon={salon}?superficie={superficie}?veranda={veranda}}",

 */