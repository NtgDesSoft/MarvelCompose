package com.app.marvel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.app.marvel.data.CharacterInfo
import com.app.marvel.ui.theme.MarvelTheme
import com.app.marvel.viewModel.APITask
import com.app.marvel.viewModel.MainFactory
import com.app.marvel.viewModel.MainViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory= MainFactory(Repository())
        viewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
        viewModel.getAll()

        setContent {
            MarvelTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: MainViewModel) {
    Column {

        TopAppBar {
            Text(
                text = "Marvel",
                modifier = Modifier
                    .padding(start = 10.dp),
                style = MaterialTheme.typography.h6
            )
        }

        Column(
            Modifier
                .padding(5.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val apiState by viewModel.APIState.collectAsState()

            when (apiState) {
                is APITask.Loading -> Text(text = "Loading")
                is APITask.Response.Ok -> (apiState as APITask.Response.Ok).payload.forEach {
                    Divider(startIndent = 50.dp)
                    CharItem(it)
                }
                is APITask.Response.Error -> Text(text = (apiState as APITask.Response.Error).error)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharItem(characterInfo: CharacterInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        characterInfo.imageUrl?.let {

            GlideImage(
                model = it, contentDescription = null, modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxWidth(.25f),
                contentScale = ContentScale.FillWidth
            )
        }
        Column {
            Text(
                text = characterInfo.Name, style = MaterialTheme.typography.h6

            )
            characterInfo.Details?.let {
                Text(
                    text = it, style = MaterialTheme.typography.body2
                )
            }
        }
    }
}