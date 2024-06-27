package com.reciipiie.app.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.reciipiie.app.R
import com.reciipiie.app.common.ui.ReciipiieLoading
import com.reciipiie.app.common.ui.SearchTextField
import com.reciipiie.app.common.utils.UiState
import com.reciipiie.app.data.model.Recipe
import com.reciipiie.app.presantation.viewmodel.AuthenticationViewModel
import com.reciipiie.app.presantation.viewmodel.RecipeViewModel
import com.reciipiie.app.ui.theme.Gray_0
import com.reciipiie.app.ui.theme.Gray_10
import com.reciipiie.app.ui.theme.Gray_2
import com.reciipiie.app.ui.theme.Gray_6
import com.reciipiie.app.ui.theme.Gray_9
import com.reciipiie.app.ui.theme.InterFontFamily
import com.reciipiie.app.ui.theme.White
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthenticationViewModel,
    recipeViewModel: RecipeViewModel
) {
    val userName by authViewModel.userName.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var randomRecipes by remember { mutableStateOf(emptyList<Recipe>()) }
    val popularRecipes by recipeViewModel.popularRecipesFlow.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        recipeViewModel.getRandomRecipe(100, null, null)
        recipeViewModel.randomRecipeFlow.collectLatest { state ->
            when (state) {
                is UiState.Error -> {
                    isLoading = false
                    snackBarHostState.showSnackbar(state.message)
                }

                UiState.Idle -> {}
                UiState.Loading -> {
                    isLoading = true
                }

                is UiState.Success -> {
                    isLoading = false
                    randomRecipes = state.data
                    Log.i("TAG", "HomeScreen: $randomRecipes")
                }
            }

        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) {
        if (isLoading) {
            ReciipiieLoading()
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column {

                        Text(
                            text = "\uD83D\uDC4B Hey $userName",
                            fontFamily = InterFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Gray_9
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Discover tasty and healthy recipes",
                            fontFamily = InterFontFamily,
                            fontSize = 14.sp,
                            color = Gray_6
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        SearchTextField(
                            string = searchText,
                            onValueChange = { searchText = it },
                            hintInternalText = "Search recipes",
                            modifier = Modifier.clickable { active = true },
                            shape = RoundedCornerShape(12.dp),
                        )
                    }
                    Column {
                        Text(
                            text = "Popular Recipes",
                            fontFamily = InterFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gray_10
                        )
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(popularRecipes) { recipe ->
                                PopularItem(recipe = recipe)
                            }
                        }
                    }
                    Column {
                        Text(
                            text = "All Recipes",
                            fontFamily = InterFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gray_10
                        )
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(randomRecipes) { recipe ->
                                RecipeCard(recipe = recipe)
                            }
                        }

                    }


                }
                // search bar active State
                AnimatedVisibility(
                    visible = active,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Dialog(
                        onDismissRequest = { active = false },
                        properties = DialogProperties(usePlatformDefaultWidth = false)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(White)
                                .padding(16.dp),
                        ) {
                            stickyHeader {
                                SearchTextField(
                                    string = searchText,
                                    onValueChange = {
                                        searchText = it
                                    },
                                    hintInternalText = "Search any recipe",
                                    icon = R.drawable.ic_back,
                                    onBackClick = {
                                        active = false
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                )
                            }
                            item {

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PopularItem(modifier: Modifier = Modifier, recipe: Recipe) {
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(14.dp))
            .padding(8.dp)
    ) {
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(14.dp)),
            contentScale = ContentScale.FillBounds,

            )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            verticalArrangement = Arrangement.spacedBy(5.dp)

        ) {
            Text(
                text = recipe.title,
                fontFamily = InterFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Gray_0,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = " Ready in ${recipe.readyInMinutes} Min",
                fontFamily = InterFontFamily,
                fontSize = 12.sp,
                color = Gray_0,
            )
        }
    }
}

@Composable
fun RecipeCard(modifier: Modifier = Modifier, recipe: Recipe) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Gray_2,
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)),
            contentScale = ContentScale.FillBounds,)
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = recipe.title,
                fontFamily = InterFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Gray_0,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = " Ready in ${recipe.readyInMinutes} Min",
                fontFamily = InterFontFamily,
                fontSize = 14.sp,
                color = Gray_6,
            )
        }
    }

}
