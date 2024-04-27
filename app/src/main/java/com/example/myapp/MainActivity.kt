package com.example.myapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.example.houselocatapp.navigation.Views
import com.example.myapp.data.admin.AddMaison
import com.example.myapp.data.admin.AdminView
import com.example.myapp.data.admin.UpdateMaison
import com.example.myapp.data.client.DetailMaison
import com.example.myapp.data.client.ProfileView
import com.example.myapp.data.repository.MaisonViewModel
import com.example.myapp.data.signIn.GoogleAuthUiClient
import com.example.myapp.data.signIn.SignInView
import com.example.myapp.data.signIn.SignInViewModel
import com.example.myapp.model.Maison
import com.example.myapp.ui.theme.MyAppTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okio.ByteString.Companion.encode
import java.net.URLEncoder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    private val maison: MaisonViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val listMaison: Maison
                    val viewModel : MaisonViewModel
                    val onNavigateToMaisonScreen: (Maison) -> Unit

                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if(googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate(Views.ProfileView.views)
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if(result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if(state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(Views.ProfileView.views)
                                    viewModel.resetState()
                                }
                            }


                            SignInView(
                                state = state,
                                navController = navController,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },
                                onAdminClick = {
                                    navController.navigate(Views.AdminView.views)
                                }
                            )
                        }
                        composable(Views.ProfileView.views) {
                            ProfileView(
                                userData = googleAuthUiClient.getSignedInUser(),
                                isLoading = false,
                                maisonData = maison,
                                maison = Maison(),
                                onNavigateToSigninScreen = {},
                                onNavigateToMaisonScreen = { m ->
                                    val encodedUrl = URLEncoder.encode(m.image,"UTF-8")
                                    navController.navigate(
                                        "maison_view?typeM=${m.typeM}?adresse=${m.adresse}" +
                                                "?buanderie=${m.buanderie}" +
                                                "?bureau=${m.bureau}" +
                                                "?chambre=${m.chambre}" +
                                                "?cuisine=${m.cuisine}" +
                                                "?douche=${m.douche}" +
                                                "?etat=${m.etat}" +
                                                "?garage=${m.garage}" +
                                                "?jardin=${m.jardin}" +
                                                "?piscine=${m.piscine}" +
                                                "?prix=${m.prix}" +
                                                "?salon=${m.salon}" +
                                                "?superficie=${m.superficie}" +
                                                "?veranda=${m.veranda}" +
                                                "?image=$encodedUrl"
                                    )

                                },
                                mm = listOf(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.popBackStack()
                                    }
                                }
                            )
                        }




                        composable(
                            route = "maison_view?typeM={typeM}?adresse={adresse}?buanderie={buanderie}?bureau={bureau}?chambre={chambre}?cuisine={cuisine}?douche={douche}?etat={etat}?garage={garage}?jardin={jardin}?piscine={piscine}?prix={prix}?salon={salon}?superficie={superficie}?veranda={veranda}?image={image}",
                            arguments = listOf(
                                navArgument("typjeM") { nullable = true },
                                navArgument("adresse") { nullable = true },
                                navArgument("buanderie") { nullable = true },
                                navArgument("bureau") { nullable = true },
                                navArgument("chambre") { nullable = true },
                                navArgument("cuisine") { nullable = true },
                                navArgument("douche") { nullable = true },
                                navArgument("etat") { nullable = true },
                                navArgument("garage") { nullable = true },
                                navArgument("jardin") { nullable = true },
                                navArgument("piscine") { nullable = true },
                                navArgument("prix") { nullable = true },
                                navArgument("salon") { nullable = true },
                                navArgument("superficie") { nullable = true },
                                navArgument("veranda") { nullable = true },
                                navArgument("image") { nullable = true }
                            )
                        ) { backStackEntry ->
                            val typeM = backStackEntry.arguments?.getString("typeM")
                            val adresse = backStackEntry.arguments?.getString("adresse")
                            val etat = backStackEntry.arguments?.getString("etat")
                            val superficie = backStackEntry.arguments?.getString("superficie")
                            val prix = backStackEntry.arguments?.getString("prix")
                            val chambre = backStackEntry.arguments?.getInt("chambre")
                            val douche = backStackEntry.arguments?.getInt("douche")
                            val salon = backStackEntry.arguments?.getInt("salon")
                            val buanderie = backStackEntry.arguments?.getInt("buanderie")
                            val cuisine = backStackEntry.arguments?.getInt("cuisine")
                            val veranda = backStackEntry.arguments?.getInt("veranda")
                            val piscine = backStackEntry.arguments?.getInt("piscine")
                            val garage = backStackEntry.arguments?.getInt("garage")
                            val bureau = backStackEntry.arguments?.getInt("bureau")
                            val jardin = backStackEntry.arguments?.getInt("jardin")
                            val image = backStackEntry.arguments?.getString("image")

                            val encodedUrl = URLEncoder.encode(image, "UTF-8")



                            DetailMaison(
                                typeM = typeM!!,
                                adresse = adresse,
                                etat = etat,
                                superficie = superficie,
                                prix = prix,
                                chambre = chambre,
                                douche = douche,
                                salon = salon,
                                buanderie = buanderie,
                                cuisine = cuisine,
                                veranda = veranda,
                                piscine = piscine,
                                garage = garage,
                                bureau = bureau,
                                jardin = jardin,
                                image = image,
                                onBackPressed = { navController.popBackStack() },
                                onEditClicked = {
                                    navController.navigate("maison_view?typeM=$typeM&adresse=$adresse" +
                                            "&etat=$etat&image=$encodedUrl")
                                },
                                navController = navController,
                                onDeleteClicked = {
                                    //viewModel.deleteBlog(id!!)
                                    //navController.popBackStack()
                                }
                            )
                        }


                        composable(Views.AdminView.views){
                            AdminView(navController = navController,
                                onAddHouseClick = {
                                    navController.navigate(Views.AddMaison.views)
                                },
                                onUpdateHouseClick = {
                                    navController.navigate(Views.UpdateMaison.views)
                                },
                                maisonData = maison)
                        }
                        composable(Views.AddMaison.views){
                            AddMaison(maison = maison,
                                onBackPressed = { navController.navigate(Views.AdminView.views) },
                                navController = navController)
                        }
                        composable(Views.UpdateMaison.views){
                            UpdateMaison(
                                maison = maison,
                                onBackPressed = { navController.navigate(Views.AdminView.views) },
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
