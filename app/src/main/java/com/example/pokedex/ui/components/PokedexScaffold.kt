package com.example.pokedex.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedex.extensions.addDefaultBack
import com.example.pokedex.ui.theme.Black
import androidx.compose.material3.Text as MaterialText
import com.example.pokedex.ui.theme.MdRed800
import com.example.pokedex.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexScaffold(
    modifier: Modifier = Modifier,
    title: String,
    hasNavigationIcon: Boolean = false,
    navController: NavController? = null,
    containerColor: Color = White,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = containerColor,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    if (hasNavigationIcon) addDefaultBack(navController)
                },
                colors = TopAppBarColors(
                    containerColor = MdRed800,
                    titleContentColor = White,
                    scrolledContainerColor = White,
                    navigationIconContentColor = White,
                    actionIconContentColor = White
                ),
                title = { MaterialText (title) }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                        ),

                    thickness = 2.dp,
                    color = Black
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    content(innerPadding)
                }
            }
        }
    )
}

