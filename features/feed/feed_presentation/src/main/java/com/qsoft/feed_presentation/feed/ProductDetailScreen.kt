package com.qsoft.feed_presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.qsoft.feed_domain.model.ProductModel
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications

@Composable
fun ProductDetailScreen(
    state: ProductDetailState,
    onBackClick: () -> Unit = {},
    onFavoriteClick: (Int, Boolean) -> Unit = { _, _ -> }
) {
    val product = state.product

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (state.error.isNotBlank()) state.error else "No product found"
            )
        }
        return
    }

    val isInPreview = LocalInspectionMode.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(14.dp)
                    .padding(bottom = 16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .clip(RoundedCornerShape(26.dp))
                ) {
                    if (isInPreview) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Preview Image",
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        AsyncImage(
                            model = product.image,
                            contentDescription = product.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .width(110.dp)
                            .height(26.dp)
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 18.dp,
                                    bottomEnd = 18.dp
                                )
                            )
                            .background(Color.White)
                    )

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.95f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color(0xFF5E5E5E),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            onFavoriteClick(product.id, !product.isFavorite)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.95f))
                    ) {
                        Icon(
                            imageVector = if (product.isFavorite) {
                                Icons.Default.Favorite
                                //Icons.Filled.Notifications

                            } else {
                                Icons.Default.FavoriteBorder
                                //Icons.Outlined.NotificationsNon
                            },
                            contentDescription = "Favorite",
                            //contentDescription = "Notification",
                            tint = if (product.isFavorite) Color(0xFFF57C5C) else Color(0xFF8A8A8A),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DotIndicator(active = false)
                        Spacer(modifier = Modifier.width(4.dp))
                        DotIndicator(active = true)
                        Spacer(modifier = Modifier.width(4.dp))
                        DotIndicator(active = false)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = product.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2F2F2F)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "by ${product.brand ?: "Unknown Brand"}",
                    fontSize = 12.sp,
                    color = Color(0xFF8F8F8F)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${String.format("%.2f", product.price)}",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF57C5C)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    val oldPrice =
                        product.price + ((product.price * product.discountPercentage) / 100.0)

                    Text(
                        text = "$${String.format("%.0f", oldPrice)}",
                        fontSize = 13.sp,
                        color = Color(0xFFB5B5B5),
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Description",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3A3A3A)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.description,
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF8B8B8B)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Category: ${product.category}",
                    fontSize = 12.sp,
                    color = Color(0xFF7A7A7A)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Rating: ${product.rating}",
                    fontSize = 12.sp,
                    color = Color(0xFF7A7A7A)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Stock: ${product.stock}",
                    fontSize = 12.sp,
                    color = Color(0xFF7A7A7A)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF57C5C)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Add To Cart",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun DotIndicator(active: Boolean) {
    Box(
        modifier = Modifier
            .size(if (active) 7.dp else 5.dp)
            .clip(CircleShape)
            .background(
                if (active) Color(0xFFF57C5C)
                else Color(0xFFE4C8C1)
            )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        state = ProductDetailState(
            isLoading = false,
            error = "",
            product = ProductModel(
                id = 1,
                title = "Powder Canister",
                description = "The Powder Canister is a finely milled setting powder designed to set makeup and control shine. With a lightweight and translucent formula, it provides a smooth and matte finish.",
                image = "",
                isFavorite = true,
                brand = "Beauty Brand",
                price = 249.99,
                discountPercentage = 12.0,
                category = "Beauty",
                rating = 4.8,
                stock = 25
            )
        )
    )
}