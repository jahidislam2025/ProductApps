package com.qsoft.feed_presentation.surveyui

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview

private val PinkPrimary = Color(0xFFF72D96)
private val SoftPinkBorder = Color(0xFFF3C6D8)
private val ScreenBg = Color(0xFFF3F3F3)
private val TopBg = Color(0xFFF1EAEA)
private val FieldBg = Color.White
private val LabelColor = Color(0xFF111111)
private val HintColor = Color(0xFF6E6E6E)
private val IconGray = Color(0xFF666666)
private val RequiredColor = Color(0xFFFF6A00)

@Composable
fun SurveyFormScreen() {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var productType by remember { mutableStateOf("Select") }

    var address1 by remember { mutableStateOf("") }
    var address2 by remember { mutableStateOf("") }
    var address3 by remember { mutableStateOf("") }

    var expectedLoan by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Select") }
    var followUpDate by remember { mutableStateOf("2026-05-15") }
    var comment by remember { mutableStateOf("") }

    val productOptions = listOf("Select", "Trade", "Agri", "Migration","Nirvorota","Remittance","Goti")
    val statusOptions = listOf("Select", "Potential", "Non-Potential")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ScreenBg,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { SurveyFormTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ScreenBg)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 18.dp)
                .navigationBarsPadding()
        ) {
            FormRow(label = "Name", required = true) {
                SurveyTextField(
                    value = name,
                    onValueChange = { name = it }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            FormRow(label = "Phone number", required = true) {
                SurveyTextField(
                    value = phone,
                    onValueChange = { phone = it }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            FormRow(label = "Product Type", required = true) {
                SurveyDropdownField(
                    selectedValue = productType,
                    options = productOptions,
                    onValueSelected = { productType = it }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            FormRow(label = "Business /\nWorkplace\naddress", required = true) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SurveyTextField(
                        value = address1,
                        onValueChange = { address1 = it },
                        placeholder = "House/Village"
                    )
                    SurveyTextField(
                        value = address2,
                        onValueChange = { address2 = it },
                        placeholder = "Road No/Ward No"
                    )
                    SurveyTextField(
                        value = address3,
                        onValueChange = { address3 = it },
                        placeholder = "Union/Pourosabha"
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            FormRow(label = "Expected Loan\nAmount", required = false) {
                SurveyTextField(
                    value = expectedLoan,
                    onValueChange = { expectedLoan = it }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            FormRow(label = "Status", required = true) {
                SurveyDropdownField(
                    selectedValue = status,
                    options = statusOptions,
                    onValueSelected = { status = it }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            FormRow(label = "Follow-up Date", required = true) {
                SurveyDateField(
                    text = followUpDate,
                    onDateSelected = { followUpDate = it }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            FormRow(label = "Comment", required = true) {
                SurveyTextField(
                    value = comment,
                    onValueChange = { comment = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ActionButton(
                    text = "DRAFT",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )

                ActionButton(
                    text = "SUBMIT",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurveyFormTopBar() {
    Surface(
        color = TopBg,
        shadowElevation = 2.dp
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = TopBg
            ),
            title = {
                Text(
                    text = "Survey Form",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            },
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

@Composable
private fun FormRow(
    label: String,
    required: Boolean,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp, top = 6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily.Serif,
                    color = LabelColor
                )

                if (required) {
                    Text(
                        text = " *",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Serif,
                        color = RequiredColor
                    )
                }
            }
        }

        Box(
            modifier = Modifier.weight(1.5f)
        ) {
            content()
        }
    }
}

@Composable
private fun SurveyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = ""
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(
                    text = placeholder,
                    color = HintColor,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .border(1.dp, SoftPinkBorder, RoundedCornerShape(4.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = FieldBg,
            unfocusedContainerColor = FieldBg,
            disabledContainerColor = FieldBg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Black
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurveyDropdownField(
    selectedValue: String,
    options: List<String>,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(FieldBg, RoundedCornerShape(4.dp))
                .border(1.dp, SoftPinkBorder, RoundedCornerShape(4.dp))
                .clickable { expanded = true }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedValue,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            fontFamily = FontFamily.Serif
                        )
                    },
                    onClick = {
                        onValueSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun SurveyDateField(
    text: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(FieldBg, RoundedCornerShape(4.dp))
            .border(1.dp, SoftPinkBorder, RoundedCornerShape(4.dp))
            .clickable {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val picked = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth, 0, 0, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        onDateSelected(formatDate(picked.timeInMillis))
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Black
        )
    }
}

@Composable
private fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(34.dp),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PinkPrimary,
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal
        )
    }
}

private fun formatDate(millis: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(millis))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SurveyFormScreenPreview() {
    MaterialTheme {
        SurveyFormScreen()
    }
}

