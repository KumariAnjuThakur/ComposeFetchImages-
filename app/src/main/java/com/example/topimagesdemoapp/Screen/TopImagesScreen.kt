package com.example.topimagesdemoapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.topimagesdemoapp.R
import com.example.topimagesdemoapp.component.ShowSnackBar
import com.example.topimagesdemoapp.model.DataModel
import com.example.topimagesdemoapp.retrofit.ApiState
import com.example.topimagesdemoapp.retrofit.NetworkConnection
import com.example.topimagesdemoapp.ui.theme.Montserrat
import com.example.topimagesdemoapp.ui.theme.Neutral0
import com.example.topimagesdemoapp.ui.theme.Neutral5
import com.example.topimagesdemoapp.viewModel.TopImagesViewModel

@Composable
fun TopImageScreen(navHostController: NavHostController) {
    val topImagesVM: TopImagesViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = "Images of the Week", textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            color = Neutral0
                        )
                    )
                }
            )
        })
    { it ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val searchText by topImagesVM.searchText.collectAsState()
            val viewType = topImagesVM.isViewTypeListState.value
            val context = LocalContext.current
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(searchText = searchText, onSearchChange = {
                    topImagesVM.onSearchTextChange(it)
                }, modifier = Modifier.weight(1f, true),
                    onSearch = {
                        topImagesVM.postSearchQuery(NetworkConnection.isOnline(context))
                    })
                DisplayListGridToggle(
                    isListType = viewType, onToggleUpdate = {
                        topImagesVM.isViewTypeListState.value = it
                    }, modifier = Modifier
                        .padding(5.dp)
                )
            }
            ApiStateUi(topImagesVM.response.value, topImagesVM)
        }

    }
}

@Composable
fun DisplayListGridToggle(
    isListType: Boolean,
    onToggleUpdate: (Boolean) -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = {
            onToggleUpdate(!isListType)
        },
        modifier = modifier
    ) {
        Icon(
            painter = if (isListType
            ) painterResource(R.drawable.ic_list) else painterResource(
                R.drawable.ic_grid
            ), contentDescription = "",
            Modifier.size(15.dp)
        )
    }

}

@Composable
fun DisplayList(list: List<DataModel>, isListView: Boolean) {
    if (isListView) {
        ListViewUi(list = list)
    } else {
        GridViewUi(list)
    }
}

@Composable
fun ListViewUi(list: List<DataModel>) {
    LazyColumn() {
        items(list) {
            ListCard(it)
        }
    }
}

@Composable
fun GridViewUi(list: List<DataModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(list) {
            GridCard(dataModel = it)
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchChange: (String) -> Unit,
    modifier: Modifier,
    onSearch: () -> Unit
) {
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                onSearchChange("")
            },
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = searchText,
        modifier = modifier,
        onValueChange = {
            onSearchChange(it)
        },
        shape = RoundedCornerShape(10.dp),
        label = {
            Text(
                "Search here", style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = Neutral5
                )
            )
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = if (searchText.isNotEmpty()) trailingIconView else null,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            onSearch()
            keyboardController?.hide()
        }
    )
}

@Composable
fun ApiStateUi(apiState: ApiState, topImagesVM: TopImagesViewModel) {
    val context = LocalContext.current
    when (apiState) {
        is ApiState.Success -> {
            val searchList = topImagesVM.searchResultsState.collectAsState().value
            val viewType = topImagesVM.isViewTypeListState.value
            if (searchList.isEmpty()) {
                EmptyScreen(
                    "No images found! The query is either invalid or doesn't contains images",
                    true,
                    onRetry = {
                        topImagesVM.postSearchQuery(NetworkConnection.isOnline(context))
                    })
            } else
                DisplayList(list = searchList, isListView = viewType)
        }

        is ApiState.Failure -> {
            val message = if (NetworkConnection.isOnline(context)) {
                "Unable to fetch response from server"
            } else {
                "Internet connection is not available. Please check your connection and try again"
            }
//            ShowSnackBar(message)
            EmptyScreen(message, true) {
                topImagesVM.postSearchQuery(NetworkConnection.isOnline(context))
            }

        }

        ApiState.Loading -> {
            FeaturedCircularProgressIndicator()
        }

        ApiState.Empty -> {
            EmptyScreen("Enter Search Query to display relevant results", false) {}
        }
    }
}

@Composable
fun EmptyScreen(message: String, isButton: Boolean, onRetry: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val (button, text) = createRefs()

        Text(
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            textAlign = TextAlign.Center,
            text = message,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                color = Neutral5
            )
        )
        if (isButton) {
            Button(onClick = { onRetry() },
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(text.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    textAlign = TextAlign.Center,
                    text = "Retry",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        color = Neutral0
                    )
                )
            }
        }


    }

}

@Composable
fun FeaturedCircularProgressIndicator() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            progress = 0.8f,
            modifier = Modifier.padding(8.dp),
            color = Color.Blue,
            strokeWidth = 2.dp
        )
    }

}
