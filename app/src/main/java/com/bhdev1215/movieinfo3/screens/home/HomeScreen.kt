package com.bhdev1215.movieinfo3.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bhdev1215.movieinfo3.R
import com.bhdev1215.movieinfo3.navigation.NavigationObject
import com.bhdev1215.movieinfo3.screens.components.CommonAppBar
import com.bhdev1215.movieinfo3.screens.components.MovieItem
import com.bhdev1215.movieinfo3.screens.components.drawer.NavigationDrawer
import com.bhdev1215.movieinfo3.ui.theme.primaryGray
import com.bhdev1215.movieinfo3.util.Constants.IMAGE_BASE_URL
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    currentScreen: String,
) {

    val trendingMovieList = viewModel.trendingMovieList.value.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var showAlertDialog by remember { mutableStateOf(false) }

    Timber.d("current : $currentScreen")
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                CommonAppBar(
                    title = {
                        Text(text = currentScreen, color = Color.White, fontSize = 18.sp)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    showBackArrow = false,
                    navActions = {
                        IconButton(onClick = { /*TODO SEARCH*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                tint = primaryGray
                            )
                        }
                    },
                    navController = navController,
                    coroutineScope = coroutineScope,
                    scaffoldState = scaffoldState
                )
            },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                NavigationDrawer(currentScreen = currentScreen) {
                    Timber.d("it : $it")
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(it)
                }
            }
        ) {
            if (currentScreen == NavigationObject.TV) {
                LazyColumn {
                    item {
                        Text(text = "this is tv screen")
                    }
                }
            } else {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, end = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "????????? ?????????", color = Color.White, fontSize = 18.sp)
                            ClickableText(
                                text = AnnotatedString("?????????"),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 18.sp
                                ),
                                onClick = {
                                    navController.navigate(NavigationObject.MORE)
                                })
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(215.dp)
                        ) {
                            LazyRow(content = {
                                items(trendingMovieList) { it ->
                                    MovieItem(
                                        modifier = Modifier
                                            .width(150.dp)
                                            .clickable {
                                                navController.navigate(
                                                    NavigationObject.Detail.MOVIE_DETAIL.plus(
                                                        "/${it?.id}"
                                                    )
                                                )
                                            },
                                        imageUrl = "$IMAGE_BASE_URL/${it?.posterPath}",
                                        title = null,
                                        release = null,
                                        rating = null
                                    )
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    if (showAlertDialog) {
        OnBackDialog(
            onDismissRequest = { showAlertDialog = false },
            onConfirmClick = {
                //TODO finish() or navigateUp()
            }
        )
    }

    BackHandler {
        coroutineScope.launch {
            scaffoldState.drawerState.close()
            if (scaffoldState.drawerState.isOpen) {
                showAlertDialog = true
            }
        }
    }
}

@Composable
fun OnBackDialog(onConfirmClick: () -> Unit, onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "?????????????????????????", fontSize = 16.sp, color = Color.White)
        },
        text = {
            Text(text = "????????? ????????? ?????? ???????????????.", fontSize = 16.sp, color = Color.White)

        },
        confirmButton = {
            TextButton(onClick = { onConfirmClick() }) {
                Text(text = "??????", fontSize = 16.sp, color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "??????", fontSize = 16.sp, color = Color.White)
            }
        }
    )
}