package com.videoview

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.videoview.presentation.PagingListScreen
import com.videoview.ui.theme.VideoViewTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VideoViewTheme {
                val viewModelStore = ViewModelStore()
                val navController = rememberNavController()
                navController.setViewModelStore(viewModelStore)

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") { Login(navController) }
                    composable(
                        route = "tab?value={value}",
                        arguments = listOf(
                            navArgument("value") {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        TabScreen(
                            backStackEntry.arguments?.getString("value") ?: ""
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun TabScreen(
    userAvatar: String?,
    navController: NavHostController = rememberNavController()
) {
    val tabs = listOf("Films", "Favorites")
    val tabIndex = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors())
                .padding(16.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            AsyncImage(
                model = userAvatar,
                contentDescription = "user avatar",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            )
        }

        TabRow(
            selectedTabIndex = tabIndex.value
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.background(Colors()),
                    text = { Text(title) },
                    selected = tabIndex.value == index,
                    onClick = {
                        tabIndex.value = index
                        navController.navigate(if (index == 0) "Films" else "Favorites")
                    }
                )
            }
        }

        NavHost(
            navController = navController,
            startDestination = "Films"
        ) {
            composable("Films") { PagingListScreen(isFavorite = false) }
            composable("Favorites") { PagingListScreen(isFavorite = true) }
        }
    }
}

@Composable
fun Login(navController: NavHostController) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
        },
        onAuthError = {
            user = null
        }
    )
    val token = stringResource(id = R.string.default_web_client_id)
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user == null) {
            Text("Not logged in")
            Button(onClick = {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }) {
                Text("Sign in via Google")
            }
        } else {
            navController.navigate("tab?value=${user?.photoUrl}")
        }
    }
}

@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            Timber.d("-> account token ${account.idToken}")
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                Timber.d("-> authResult$authResult")
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            Timber.d("-> ${e.toString()}")
            onAuthError(e)
        }
    }
}

@Composable
fun Colors(): Color {
    val isDarkTheme = isSystemInDarkTheme()
    val darkTextColor = Color.White
    val lightTextColor = Color.Black
    return if (!isDarkTheme) darkTextColor else lightTextColor
}
