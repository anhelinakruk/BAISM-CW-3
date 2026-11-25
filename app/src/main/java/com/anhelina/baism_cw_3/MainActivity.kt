package com.anhelina.baism_cw_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    RickAndMortyScreen(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun RickAndMortyScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    var characters by remember { mutableStateOf<List<Character>>(emptyList()) }
    var single by remember { mutableStateOf<Character?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var idText by remember { mutableStateOf("1") }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Rick & Morty API", style = MaterialTheme.typography.titleLarge)

        Button(onClick = {
            scope.launch {
                try {
                    error = null; single = null
                    characters = Api.service.getCharacters().results
                } catch (e: Exception) { error = e.message }
            }
        }) { Text("Pobierz liste postaci") }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = idText,
                onValueChange = { idText = it },
                label = { Text("ID") },
                modifier = Modifier.width(110.dp)
            )
            Button(onClick = {
                scope.launch {
                    try {
                        error = null; characters = emptyList()
                        single = Api.service.getCharacter(idText.toIntOrNull() ?: 1)
                    } catch (e: Exception) { error = e.message }
                }
            }) { Text("Postac po ID") }
        }

        error?.let { Text("Blad: $it", color = MaterialTheme.colorScheme.error) }

        single?.let { c ->
            Text("#${c.id} ${c.name}  -  ${c.species} - ${c.status}")
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(characters) { c ->
                Text("#${c.id} ${c.name} - ${c.species} (${c.status})")
            }
        }
    }
}