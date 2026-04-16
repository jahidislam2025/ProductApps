package com.qsoft.feed_presentation.surveyui

// Android এর built-in date picker dialog
import android.app.DatePickerDialog

// Compose foundation UI imports
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape

// Material icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Search

// Material3 UI components
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

// Compose state
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// UI helper imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Navigation controller
import androidx.navigation.NavController

// Date formatting imports
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing

// ------------------------------------
// এখানে screen-এ যেসব custom color বারবার use হবে
// সেগুলো আগে variable হিসেবে define করা হয়েছে
// এতে code clean থাকে
// ------------------------------------
private val PinkPrimary = Color(0xFFFF2D96)
private val BorderPink = Color(0xFFF3B8CF)
private val ScreenBg = Color(0xFFF4F4F4)
private val TopBg = Color(0xFFF1EBEE)
private val UnselectedTab = Color(0xFFE7C8D5)
private val SearchBorder = Color(0xFFD9E0E7)
private val IconGray = Color(0xFF777777)

// ------------------------------------
// এটা main screen composable
// navController nullable দেওয়া হয়েছে যাতে Preview তে crash না করে
// ------------------------------------
@Composable
fun SurveyScreen(navController: NavController? = null) {

    // কোন tab selected আছে সেটা রাখে
    // 0 = SURVEY LIST
    // 1 = DRAFT
    var selectedTab by remember { mutableStateOf(0) }

    // search box এ user কী লিখেছে
    var searchText by remember { mutableStateOf("") }

    // from date এর initial value
    var fromDateMillis by remember { mutableStateOf(parseDateToMillis("2026-04-01")) }

    // to date এর initial value
    var toDateMillis by remember { mutableStateOf(parseDateToMillis("2026-04-15")) }

    // dropdown এর options
    val statusOptions = listOf("All", "Potential", "Non-Potential")

    // currently selected dropdown value
    var selectedStatus by remember { mutableStateOf("All") }

    // Scaffold = screen-এর basic layout structure
    // এতে topBar, floatingActionButton, content body রাখা হয়
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ScreenBg,

        contentWindowInsets = WindowInsets(0, 0, 0, 0),

        // screen-এর top bar
        topBar = { SurveyTopBar() },

        // floating button শুধু Survey List tab এ show করবে
        floatingActionButton = {
            if (selectedTab == 0) {
                NewSurveyFab(navController)
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) { paddingValues ->

        // Column দিয়ে সব item vertical ভাবে সাজানো হয়েছে
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ScreenBg)
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Top এর দুইটা tab
            SurveyTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // যদি প্রথম tab selected থাকে
            if (selectedTab == 0) {

                // search section show হবে
                SearchSection(
                    value = searchText,
                    onValueChange = { searchText = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // filter section show হবে
                FilterSection(
                    fromDateMillis = fromDateMillis,
                    toDateMillis = toDateMillis,
                    selectedStatus = selectedStatus,
                    statusOptions = statusOptions,
                    onFromDateSelected = { fromDateMillis = it },
                    onToDateSelected = { toDateMillis = it },
                    onStatusSelected = { selectedStatus = it }
                )
            } else {
                // যদি DRAFT tab selected থাকে
                // empty state দেখাবে
                DraftEmptyScreen()
            }
        }
    }
}

// ------------------------------------
// Top bar composable
// ------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurveyTopBar() {

    // Surface background + shadow দেওয়ার জন্য
    Surface(
        //modifier = Modifier,
        modifier = Modifier.statusBarsPadding(),
        color = TopBg,
        shadowElevation = 2.dp
    ) {
        CenterAlignedTopAppBar(
            //modifier = Modifier.statusBarsPadding(),
            // এটি স্ট্যাটাস বারের জায়গা অটোমেটিক নিয়ে নেবে
            windowInsets = TopAppBarDefaults.windowInsets,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = TopBg
            ),

            // title text
            title = {
                Text(
                    text = "Survey Form",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            },

            // left side menu icon
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = IconGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },

            // right side action icons
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = PinkPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "More",
                        tint = IconGray,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        )
    }
}

// ------------------------------------
// দুইটা tab show করার জন্য function
// ------------------------------------
@Composable
private fun SurveyTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTab,
        containerColor = ScreenBg,
        divider = {},

        // selected tab এর নিচে pink indicator line
        indicator = { positions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(positions[selectedTab])
                    .height(2.dp)
                    .background(PinkPrimary)
            )
        }
    ) {
        // প্রথম tab
        SurveyTabItem(
            selected = selectedTab == 0,
            text = "SURVEY LIST",
            icon = Icons.Outlined.Article,
            onClick = { onTabSelected(0) }
        )

        // দ্বিতীয় tab
        SurveyTabItem(
            selected = selectedTab == 1,
            text = "DRAFT",
            icon = Icons.Outlined.PushPin,
            onClick = { onTabSelected(1) }
        )
    }
}

// ------------------------------------
// একটি single tab item UI
// ------------------------------------
@Composable
private fun SurveyTabItem(
    selected: Boolean,
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Tab(
        selected = selected,
        onClick = onClick,
        selectedContentColor = PinkPrimary,
        unselectedContentColor = UnselectedTab
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            // tab icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // tab text
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

// ------------------------------------
// Search field composable
// ------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchSection(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        // বর্তমান search text
        value = value,

        // user লিখলে parent function এ data পাঠাবে
        onValueChange = onValueChange,

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp),

        // field empty থাকলে placeholder text
        placeholder = {
            Text(
                text = "Search...",
                color = Color.Gray,
                fontSize = 16.sp
            )
        },

        // ডান পাশে search icon
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(34.dp)
            )
        },

        singleLine = true,
        shape = RoundedCornerShape(20.dp),

        // border ও background colors
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SearchBorder,
            unfocusedBorderColor = SearchBorder,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

// ------------------------------------
// Filter row: From date, To date, Status dropdown
// ------------------------------------
@Composable
private fun FilterSection(
    fromDateMillis: Long,
    toDateMillis: Long,
    selectedStatus: String,
    statusOptions: List<String>,
    onFromDateSelected: (Long) -> Unit,
    onToDateSelected: (Long) -> Unit,
    onStatusSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Top
    ) {

        // From date section
        Column(modifier = Modifier.weight(1f)) {
            FilterLabel("From")
            DateField(
                text = "Date: ${formatMillisToDate(fromDateMillis)}",
                onDateSelected = onFromDateSelected
            )
        }

        // To date section
        Column(modifier = Modifier.weight(1f)) {
            FilterLabel("To")
            DateField(
                text = "Date: ${formatMillisToDate(toDateMillis)}",
                onDateSelected = onToDateSelected
            )
        }

        // Status dropdown section
        Column(modifier = Modifier.weight(0.72f)) {
            FilterLabel("")
            StatusDropdown(
                selectedValue = selectedStatus,
                options = statusOptions,
                onValueSelected = onStatusSelected
            )
        }
    }
}

// ------------------------------------
// ছোট label text যেমন From / To
// ------------------------------------
@Composable
private fun FilterLabel(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF333333),
        modifier = Modifier.padding(start = 2.dp, bottom = 2.dp)
    )
}

// ------------------------------------
// Date picker field composable
// click করলে date picker open হবে
// ------------------------------------
@Composable
private fun DateField(
    text: String,
    onDateSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(1.dp, BorderPink, RoundedCornerShape(4.dp))
            .clickable {
                // click করলে Android date picker dialog open হবে
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val picked = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth, 0, 0, 0)
                            set(Calendar.MILLISECOND, 0)
                        }

                        // selected date millis আকারে parent এ পাঠানো হচ্ছে
                        onDateSelected(picked.timeInMillis)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}

// ------------------------------------
// Status dropdown composable
// ------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatusDropdown(
    selectedValue: String,
    options: List<String>,
    onValueSelected: (String) -> Unit
) {
    // dropdown open/close state
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // custom dropdown field UI
        Box(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(40.dp)
                .border(1.dp, BorderPink, RoundedCornerShape(4.dp))
                .clickable { expanded = true }
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // currently selected item
                Text(
                    text = selectedValue,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                // arrow icon
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    tint = Color.Gray
                )
            }
        }

        // dropdown menu items
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        // selected value parent এ পাঠাবে
                        onValueSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

// ------------------------------------
// DRAFT tab empty হলে এই screen দেখাবে
// ------------------------------------
@Composable
private fun DraftEmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.Article,
                contentDescription = "No Draft",
                tint = Color.LightGray,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "No Draft Found",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }
    }
}

// ------------------------------------
// Floating Action Button
// click করলে survey_form route এ যাবে
// ------------------------------------
@Composable
private fun NewSurveyFab(navController: NavController? = null) {
    FloatingActionButton(
        onClick = {
            // navController null না হলে navigate করবে
            navController?.navigate("survey_form")
        },
        modifier = Modifier
            //.navigationBarsPadding()
            //.padding(end = 10.dp)
            .padding(end = 10.dp, bottom = 0.dp)
            .height(50.dp),
        shape = RoundedCornerShape(40.dp),
        containerColor = PinkPrimary,
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 28.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "New Survey",
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = "New Survey",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

// ------------------------------------
// String date -> millis convert করে
// যেমন "2026-04-01" -> Long time
// ------------------------------------
private fun parseDateToMillis(date: String): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.parse(date)?.time ?: Date().time
}

// ------------------------------------
// millis -> readable date string
// যেমন 1710000000000 -> "2026-04-01"
// ------------------------------------
private fun formatMillisToDate(millis: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(millis))
}

// ------------------------------------
// Android Studio Preview
// এখানে app run না করেও UI preview দেখা যায়
// ------------------------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SurveyScreenPreview() {
    MaterialTheme {
        SurveyScreen()
    }
}