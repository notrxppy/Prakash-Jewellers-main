package com.example.prakashjewellers.model

import com.example.prakashjewellers.R


data class JewelryItem(
    val id: Int,
    val name: String,
    val category: String,
    val price: String,
    val description: String,
    val imageRes: Int // Using local drawables
)

object LocalDataSource {
    val items = listOf(
        JewelryItem(1, "Bridal Gold Necklace", "Gold", "₹2,00,000", "A timeless symbol of luxury, this necklace features a lustrous 22K gold chain that adds a warm, elegant glow to any ensemble.", R.drawable.gold),
        JewelryItem(2, "Diamond Necklace", "Diamond", "₹2,85,000", "This exquisite piece showcases a string of brilliant-cut diamonds set in white or yellow gold, designed to capture and reflect light from every angle.", R.drawable.diamond),
        JewelryItem(3, "Silver Ganpati Necklace", "Silver", "5,000", "This spiritual accessory features a finely detailed sterling silver Ganesha pendant (dollar) on a sleek silver chain, symbolizing wisdom and prosperity.", R.drawable.silver),
        JewelryItem(4, "Gold Necklace", "Gold", "3,96,000", "A timeless symbol of luxury, this necklace features a lustrous 23K gold chain that adds a warm, elegant glow to any ensemble.", R.drawable.gold1),
        JewelryItem(5, "Diamond Bracelet", "Diamond", "85,000", "A sophisticated wrist accessory, this bracelet features precision-set diamonds that offer a seamless blend of modern style and classic sparkle.", R.drawable.diamond1),
        JewelryItem(6, "Silver Ring (Pair)", "Silver", "4,500", "This matching pair of sterling silver rings features a minimalist polished finish, making them perfect for couple bands or versatile everyday wear.", R.drawable.silver1)

    )
}