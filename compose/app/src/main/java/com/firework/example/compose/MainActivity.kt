package com.firework.example.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.firework.example.compose.storyblock.ComposeLazyVerticalGridStoryActivity
import com.firework.example.compose.storyblock.ComposeStoryActivity
import com.firework.example.compose.videofeed.VideoFeedActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Scaffold(
                    ) { innerPadding ->
                        NavigationButtons(innerPadding)
                    }
                }
            }
        }
    }
}

@Composable
private fun NavigationButtons(padding: PaddingValues) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(padding).padding(horizontal = 20.dp, vertical = 30.dp).fillMaxWidth()) {
        Button(modifier = Modifier.fillMaxWidth().padding(30.dp), onClick = {
            context.startActivity(Intent(context, VideoFeedActivity::class.java))
        }) {
            Text("Video Feed")
        }
        Button(modifier = Modifier.fillMaxWidth().padding(30.dp), onClick = {
            context.startActivity(Intent(context, ComposeStoryActivity::class.java))
        }) {
            Text("Single StoryBlock")
        }
        Button(modifier = Modifier.fillMaxWidth().padding(30.dp), onClick = {
            context.startActivity(Intent(context, ComposeLazyVerticalGridStoryActivity::class.java))
        }) {
            Text("StoryBlock in Grid")
        }
    }
}
