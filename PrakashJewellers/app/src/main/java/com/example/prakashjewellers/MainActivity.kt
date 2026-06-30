package com.example.prakashjewellers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prakashjewellers.model.JewelryItem
import com.example.prakashjewellers.model.LocalDataSource
import com.example.prakashjewellers.ui.theme.DetailScreen
import com.example.prakashjewellers.ui.theme.GoldPrimary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Changed to lightColorScheme for a White background theme
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = GoldPrimary,
                    background = Color.White,
                    surface = Color.White
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JewelryApp()
                }
            }
        }
    }
}

@Composable
fun JewelryApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            val item = LocalDataSource.items.find { it.id == itemId }
            item?.let { DetailScreen(it, navController) }
        }
        // Payment Route has been removed
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val selectedCategory = remember { mutableStateOf("All") }

    val filteredItems = remember(selectedCategory.value) {
        if (selectedCategory.value == "All") {
            LocalDataSource.items
        } else {
            LocalDataSource.items.filter { it.category == selectedCategory.value }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "PRAKASH JEWELLERS",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = Color.Black // Dark text for light top bar
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White // White TopBar
                )
            )
        },
        containerColor = Color.White // Ensures Scaffold background is White
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CategoryTabs(
                selectedCategory = selectedCategory.value,
                onCategorySelected = { category -> selectedCategory.value = category }
            )

            ProductGrid(items = filteredItems) { selectedItem ->
                navController.navigate(Screen.Details.createRoute(selectedItem.id))
            }
        }
    }
}

@Composable
fun CategoryTabs(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("All", "Gold", "Diamond", "Silver")
    val selectedIndex = categories.indexOf(selectedCategory)

    PrimaryScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 16.dp,
        divider = {},
        containerColor = Color.White,
        contentColor = GoldPrimary
    ) {
        categories.forEachIndexed { index, title ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onCategorySelected(title) },
                text = {
                    Text(
                        text = title,
                        color = if (selectedIndex == index) GoldPrimary else Color.DarkGray,
                        fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}

@Composable
fun ProductGrid(items: List<JewelryItem>, onProductClick: (JewelryItem) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            ProductCard(item = item, onClick = { onProductClick(item) })
        }
    }
}

@Composable
fun ProductCard(item: JewelryItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)), // Light Grey for subtle contrast
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = item.price,
                    color = GoldPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("View Detail", fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{itemId}") {
        fun createRoute(itemId: Int) = "details/$itemId"
    }
}