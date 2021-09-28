package com.jtampinco.githubsearch

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jtampinco.githubsearch.ui.SearchRepoScreen
import com.jtampinco.githubsearch.ui.ViewRepoScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent(navController = rememberNavController())
        }
    }
}

@Composable
fun MainContent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "SearchRepoScreen"
    ) {
        composable("SearchRepoScreen") {
            SearchRepoScreen(viewModel = hiltViewModel(), navController = navController)
        }
        composable("ViewRepoScreen") {
            ViewRepoScreen(viewModel = hiltViewModel())
        }
    }
}
