package com.example.topimagesdemoapp.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.topimagesdemoapp.R
import com.example.topimagesdemoapp.Utils.convertTimestampToDate
import com.example.topimagesdemoapp.model.DataModel
import com.example.topimagesdemoapp.model.ImageModel
import com.example.topimagesdemoapp.ui.theme.Montserrat
import com.example.topimagesdemoapp.ui.theme.Neutral8
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ListCard(dataModel: DataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val modifier = Modifier
                .size(130.dp)
                .padding(5.dp)
            LoadImage(modifier = modifier, dataModel.images)
            ImageDetails(dataModel)
        }

    }
}

@Composable
fun ImageDetails(dataModel: DataModel) {
    val imageCount = dataModel.images?.size
    Column(Modifier.padding(8.dp)) {
        val timestamp = convertTimestampToDate(dataModel.datetime)
        Text(
            text = dataModel.title ?: "",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                color = Neutral8
            )
        )
        Text(
            text = "Date : $timestamp",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                color = Neutral8
            )
        )
        Text(
            text = "Images : $imageCount",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                color = Neutral8
            )
        )

    }

}

@Composable
fun GridCard(dataModel: DataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
            LoadImage(modifier = modifier, dataModel.images)
            ImageDetails(dataModel)
        }
    }
}

@Composable
fun LoadImage(modifier: Modifier, images: List<ImageModel>?) {
    if (images.isNullOrEmpty()) {
        // No Images Available
        NoImage(modifier)
    } else {
        // Images are available, Hence
        val link = images[0].link ?: ""
        if (link.isEmpty()) {
            NoImage(modifier)
        } else {
            Log.i("Image Link ", link)
            GlideImage(
                imageModel = { link },
                modifier = modifier,
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                ),
                // shows a progress indicator when loading an image.
                loading = {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val indicator = createRef()
                        CircularProgressIndicator(
                            modifier = Modifier.constrainAs(indicator) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        )
                    }
                },
                // shows an error text message when request failed.
                failure = {
                    Log.i("", "Image loading failed")
                    NoImage(modifier)
                })

        }

    }
}

@Composable
fun NoImage(modifier: Modifier) {
    Image(
        painter = painterResource(
            R.drawable.ic_default
        ),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
