package com.example.prakashjewellers.ui.theme

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prakashjewellers.model.JewelryItem

@Composable
fun DetailScreen(item: JewelryItem, navController: NavController) {
    val context = LocalContext.current

    // Changed background to White
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // --- Image Header ---
        Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { navController.popBackStack() },
                // Subtle dark background for the icon to ensure it's visible over images
                modifier = Modifier.padding(16.dp).background(Color.Black.copy(0.2f), RoundedCornerShape(50))
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {
            Text(item.category.uppercase(), color = GoldPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(item.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black) // Text to Black
            Text(item.price, fontSize = 20.sp, color = GoldPrimary, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray) // Lighter divider for white theme
            Spacer(modifier = Modifier.height(16.dp))

            Text("PRODUCT DETAILS", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(item.description, color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.weight(1f))

            // --- TWO BUTTONS ---
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                // Inquiry Button
                OutlinedButton(
                    onClick = {
                        try {
                            val url = "https://api.whatsapp.com/send?phone=919152493233&text=${Uri.encode("Inquiry: ${item.name}")}"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        } catch (e: Exception) { Log.e("WA", "Error", e) }
                    },
                    modifier = Modifier.weight(1f).height(50.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("WHATSAPP", color = GoldPrimary)
                }
            }
        }
    }
}