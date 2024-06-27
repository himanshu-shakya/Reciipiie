package com.reciipiie.app.presantation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reciipiie.app.R
import com.reciipiie.app.common.ui.SearchTextField
import com.reciipiie.app.ui.theme.White

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    var searchText by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTextField(
                string = searchText,
                onValueChange = {
                    searchText = it
                },
                hintInternalText = "Search any recipe",
                icon = R.drawable.ic_back,
                modifier = Modifier.clickable {

                }

            )
        }
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
                        shape = RoundedCornerShape(12.dp),
                    )
                }
                item {

                }
            }

    }
}