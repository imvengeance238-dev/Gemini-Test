package com.example.phonecomparison

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF4F46E5),
                    secondary = Color(0xFF0D9488),
                    background = Color(0xFFF8FAFC),
                    surface = Color.White
                )
            ) {
                PhoneExplorerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneExplorerApp() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSegment by remember { mutableStateOf(PhoneSegment.ALL) }
    var selectedBrand by remember { mutableStateOf("সব ব্র্যান্ড") }
    var showFavoritesOnly by remember { mutableStateOf(false) }

    val favoriteIds = remember { mutableStateListOf<String>() }
    val compareIds = remember { mutableStateListOf<String>() }

    var detailPhone by remember { mutableStateOf<Phone?>(null) }
    var showCompareSheet by remember { mutableStateOf(false) }

    val brands = remember {
        listOf("সব ব্র্যান্ড") + masterPhonesList.map { it.brand }.distinct()
    }

    val filteredPhones by remember {
        derivedStateOf {
            masterPhonesList.filter { phone ->
                val matchSegment = selectedSegment == PhoneSegment.ALL || phone.segment == selectedSegment
                val matchBrand = selectedBrand == "সব ব্র্যান্ড" || phone.brand == selectedBrand
                val matchFav = !showFavoritesOnly || favoriteIds.contains(phone.id)
                val q = searchQuery.trim()
                val matchQuery = q.isEmpty() ||
                        phone.name.contains(q, ignoreCase = true) ||
                        phone.brand.contains(q, ignoreCase = true) ||
                        phone.processor.contains(q, ignoreCase = true) ||
                        phone.mainCamera.contains(q, ignoreCase = true) ||
                        phone.batteryCapacity.contains(q, ignoreCase = true)

                matchSegment && matchBrand && matchFav && matchQuery
            }
        }
    }

    val comparedPhones by remember {
        derivedStateOf {
            compareIds.mapNotNull { id -> masterPhonesList.find { it.id == id } }
        }
    }

    Scaffold(
        containerColor = Color(0xFFF1F5F9),
        topBar = {
            HeaderSection(
                totalCount = filteredPhones.size,
                favCount = favoriteIds.size,
                showFavoritesOnly = showFavoritesOnly,
                onToggleFavorites = { showFavoritesOnly = !showFavoritesOnly }
            )
        },
        bottomBar = {
            if (comparedPhones.isNotEmpty()) {
                CompareBottomBar(
                    selectedPhones = comparedPhones,
                    onClear = { compareIds.clear() },
                    onCompareNow = { showCompareSheet = true }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "controls") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                "মডেল, প্রসেসর, ক্যামেরা বা ব্যাটারি খুঁজুন...",
                                fontSize = 13.sp,
                                color = Color(0xFF94A3B8)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = Color(0xFF64748B)
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF4F46E5),
                            unfocusedBorderColor = Color(0xFFE2E8F0),
                            focusedContainerColor = Color(0xFFF8FAFC),
                            unfocusedContainerColor = Color(0xFFF8FAFC)
                        )
                    )

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(PhoneSegment.entries) { segment ->
                            val isSelected = selectedSegment == segment
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedSegment = segment },
                                label = {
                                    Text(
                                        segment.label,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF4F46E5),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(brands) { brand ->
                            val isSelected = selectedBrand == brand
                            Surface(
                                onClick = { selectedBrand = brand },
                                shape = CircleShape,
                                color = if (isSelected) Color(0xFF0F172A) else Color(0xFFF1F5F9),
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) Color(0xFF0F172A) else Color(0xFFE2E8F0)
                                )
                            ) {
                                Text(
                                    text = brand,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (isSelected) Color.White else Color(0xFF475569)
                                )
                            }
                        }
                    }
                }
            }

            if (filteredPhones.isEmpty()) {
                item(key = "empty") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "কোনো ফোন পাওয়া যায়নি। ফিল্টার পরিবর্তন করে দেখুন।",
                            color = Color(0xFF64748B),
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(
                    items = filteredPhones,
                    key = { it.id },
                    contentType = { "phone_card" }
                ) { phone ->
                    val isFav = favoriteIds.contains(phone.id)
                    val isCompared = compareIds.contains(phone.id)

                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ModernPhoneCard(
                            phone = phone,
                            isFavorite = isFav,
                            isCompared = isCompared,
                            onCardClick = { detailPhone = phone },
                            onFavoriteToggle = {
                                if (isFav) favoriteIds.remove(phone.id) else favoriteIds.add(phone.id)
                            },
                            onCompareToggle = {
                                if (isCompared) {
                                    compareIds.remove(phone.id)
                                } else if (compareIds.size < 2) {
                                    compareIds.add(phone.id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    detailPhone?.let { phone ->
        ModalBottomSheet(
            onDismissRequest = { detailPhone = null },
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            PhoneFullDetailSheet(
                phone = phone,
                isFavorite = favoriteIds.contains(phone.id),
                onToggleFavorite = {
                    if (favoriteIds.contains(phone.id)) favoriteIds.remove(phone.id)
                    else favoriteIds.add(phone.id)
                }
            )
        }
    }

    if (showCompareSheet && comparedPhones.size == 2) {
        ModalBottomSheet(
            onDismissRequest = { showCompareSheet = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            SideBySideComparisonSheet(
                phone1 = comparedPhones[0],
                phone2 = comparedPhones[1],
                onClose = { showCompareSheet = false }
            )
        }
    }
}

@Composable
fun HeaderSection(
    totalCount: Int,
    favCount: Int,
    showFavoritesOnly: Boolean,
    onToggleFavorites: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF1E1B4B), Color(0xFF4338CA), Color(0xFF0D9488))
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "স্মার্টফোন মাস্টার গাইড ও তুলনা",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "বাজেট থেকে ফ্ল্যাগশিপ • $totalCount টি মডেল",
                    color = Color(0xFFCBD5E1),
                    fontSize = 12.sp
                )
            }

            Surface(
                onClick = onToggleFavorites,
                shape = RoundedCornerShape(12.dp),
                color = if (showFavoritesOnly) Color(0xFFEF4444) else Color.White.copy(alpha = 0.16f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = if (showFavoritesOnly) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "ফেভারিট ($favCount)",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun ModernPhoneCard(
    phone: Phone,
    isFavorite: Boolean,
    isCompared: Boolean,
    onCardClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onCompareToggle: () -> Unit
) {
    val accent = Color(phone.accentColorHex)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            width = if (isCompared) 2.dp else 1.dp,
            color = if (isCompared) Color(0xFF0D9488) else Color(0xFFE2E8F0)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(accent)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = accent.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = phone.brand.uppercase(),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = accent
                            )
                        }
                        Text(
                            text = phone.segment.label,
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF0F172A)
                    ) {
                        Text(
                            text = phone.priceFormatted,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = phone.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = phone.highlight,
                    fontSize = 12.sp,
                    color = Color(0xFF0D9488),
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpecMiniBox(
                        title = "ডিসপ্লে",
                        value = "${phone.displaySize} • ${phone.refreshRate}",
                        modifier = Modifier.weight(1f)
                    )
                    SpecMiniBox(
                        title = "প্রসেসর",
                        value = phone.processor,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpecMiniBox(
                        title = "ব্যাটারি ও চার্জিং",
                        value = "${phone.batteryCapacity} (${phone.chargingSpeed.substringBefore(" ")})",
                        modifier = Modifier.weight(1f)
                    )
                    SpecMiniBox(
                        title = "মেইন ও সেলফি ক্যামেরা",
                        value = "${phone.mainCamera.substringBefore("+").trim()} / ${phone.selfieCamera.substringBefore("(").trim()}",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "বিস্তারিত স্পেকস দেখুন →",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = accent
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            onClick = onCompareToggle,
                            shape = RoundedCornerShape(8.dp),
                            color = if (isCompared) Color(0xFF0D9488) else Color(0xFFF1F5F9)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                if (isCompared) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Text(
                                    text = if (isCompared) "তুলনায় যুক্ত" else "+ তুলনা",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isCompared) Color.White else Color(0xFF334155)
                                )
                            }
                        }

                        Surface(
                            onClick = onFavoriteToggle,
                            shape = CircleShape,
                            color = if (isFavorite) Color(0xFFFEE2E2) else Color(0xFFF1F5F9)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color(0xFFDC2626) else Color(0xFF64748B),
                                modifier = Modifier
                                    .padding(7.dp)
                                    .size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpecMiniBox(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 10.sp,
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1E293B),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun CompareBottomBar(
    selectedPhones: List<Phone>,
    onClear: () -> Unit,
    onCompareNow: () -> Unit
) {
    Surface(
        color = Color(0xFF0F172A),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "তুলনার জন্য নির্বাচিত (${selectedPhones.size}/2)",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp
                )
                Text(
                    text = selectedPhones.joinToString(" vs ") { it.name },
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onClear) {
                    Text("মুছুন", color = Color(0xFFCBD5E1), fontSize = 12.sp)
                }
                Button(
                    onClick = onCompareNow,
                    enabled = selectedPhones.size == 2,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D9488),
                        disabledContainerColor = Color(0xFF334155)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = if (selectedPhones.size == 2) "তুলনা দেখুন" else "আরও ১টি নিন",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun PhoneFullDetailSheet(
    phone: Phone,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${phone.brand} • ${phone.segment.label}",
                    fontSize = 12.sp,
                    color = Color(phone.accentColorHex),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = phone.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(phone.accentColorHex)
            ) {
                Text(
                    text = phone.priceFormatted,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF0FDFA)
        ) {
            Text(
                text = "✨ ${phone.highlight}",
                modifier = Modifier.padding(12.dp),
                fontSize = 13.sp,
                color = Color(0xFF0F766E),
                fontWeight = FontWeight.Medium
            )
        }

        DetailRow("কোম্পানি / ব্র্যান্ড", phone.brand)
        DetailRow("ডিসপ্লে সাইজ", phone.displaySize)
        DetailRow("ডিসপ্লে প্রযুক্তি ও রিফ্রেশ রেট", "${phone.displayType} (${phone.refreshRate})")
        DetailRow("প্রসেসর / চিপসেট", phone.processor)
        DetailRow("র‍্যাম ও স্টোরেজ", phone.ramStorage)
        DetailRow("ব্যাটারি ক্যাপাসিটি", phone.batteryCapacity)
        DetailRow("চার্জিং সিস্টেম", phone.chargingSpeed)
        DetailRow("অপারেটিং সিস্টেম (OS)", phone.osVersion)
        DetailRow("সফটওয়্যার / ইউআই", phone.uiSoftware)
        DetailRow("মেইন ক্যামেরা (Rear)", phone.mainCamera)
        DetailRow("সেলফি ক্যামেরা (Front)", phone.selfieCamera)
        DetailRow("বিল্ড ও ওজন", phone.weightAndBuild)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onToggleFavorite,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFavorite) Color(0xFFDC2626) else Color(0xFF0F172A)
            )
        ) {
            Text(
                text = if (isFavorite) "❤️ ফেভারিট লিস্ট থেকে সরান" else "🤍 ফেভারিট লিস্টে যুক্ত করুন",
                modifier = Modifier.padding(vertical = 6.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(text = label, fontSize = 11.sp, color = Color(0xFF64748B))
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
    }
}

@Composable
fun SideBySideComparisonSheet(
    phone1: Phone,
    phone2: Phone,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "পাশাপাশি বিস্তারিত তুলনা",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CompareHeaderCard(phone = phone1, modifier = Modifier.weight(1f))
            CompareHeaderCard(phone = phone2, modifier = Modifier.weight(1f))
        }

        CompareSpecBlock("কোম্পানি ও রেঞ্জ", "${phone1.brand} (${phone1.segment.label})", "${phone2.brand} (${phone2.segment.label})")
        CompareSpecBlock("ডিসপ্লে সাইজ ও টাইপ", "${phone1.displaySize} • ${phone1.displayType}", "${phone2.displaySize} • ${phone2.displayType}")
        CompareSpecBlock("রিফ্রেশ রেট", phone1.refreshRate, phone2.refreshRate)
        CompareSpecBlock("প্রসেসর", phone1.processor, phone2.processor)
        CompareSpecBlock("র‍্যাম ও স্টোরেজ", phone1.ramStorage, phone2.ramStorage)
        CompareSpecBlock("ব্যাটারি সাইজ", phone1.batteryCapacity, phone2.batteryCapacity)
        CompareSpecBlock("চার্জিং সিস্টেম", phone1.chargingSpeed, phone2.chargingSpeed)
        CompareSpecBlock("অপারেটিং সিস্টেম ও সফটওয়্যার", "${phone1.osVersion} (${phone1.uiSoftware})", "${phone2.osVersion} (${phone2.uiSoftware})")
        CompareSpecBlock("মেইন ক্যামেরা", phone1.mainCamera, phone2.mainCamera)
        CompareSpecBlock("সেলফি ক্যামেরা", phone1.selfieCamera, phone2.selfieCamera)
        CompareSpecBlock("বিল্ড ও ওজন", phone1.weightAndBuild, phone2.weightAndBuild)

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("তুলনা বন্ধ করুন")
        }
    }
}

@Composable
fun CompareHeaderCard(phone: Phone, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(phone.accentColorHex).copy(alpha = 0.1f), RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Text(
            text = phone.brand,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(phone.accentColorHex)
        )
        Text(
            text = phone.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = phone.priceFormatted,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(phone.accentColorHex)
        )
    }
}

@Composable
fun CompareSpecBlock(label: String, val1: String, val2: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = val1,
                fontSize = 12.sp,
                color = Color(0xFF0F172A),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = val2,
                fontSize = 12.sp,
                color = Color(0xFF0F172A),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
