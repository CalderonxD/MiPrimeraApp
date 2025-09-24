package com.example.miprimeraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotoTaxiApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotoTaxiApp() {
    var currentPage by remember { mutableStateOf("Inicio") }

    // 🔹 Datos de perfil como estados (para que se actualicen en tiempo real)
    var nombre by remember { mutableStateOf("Juan Motero") }
    var moto by remember { mutableStateOf("Honda XR 150") }
    var telefono by remember { mutableStateOf("300 123 4567") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MotoTaxiApp") })
        },
        bottomBar = {
            BottomNavigationBar(
                currentPage = currentPage,
                onPageChange = { currentPage = it }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentPage) {
                "Inicio" -> InicioScreen()
                "Viajes" -> ViajesScreen()
                "Perfil" -> PerfilScreen(
                    nombre = nombre,
                    moto = moto,
                    telefono = telefono,
                    onEditClick = { currentPage = "EditarPerfil" }
                )
                "EditarPerfil" -> EditarPerfilScreen(
                    nombre = nombre,
                    moto = moto,
                    telefono = telefono,
                    onGuardar = { nuevoNombre, nuevaMoto, nuevoTelefono ->
                        nombre = nuevoNombre
                        moto = nuevaMoto
                        telefono = nuevoTelefono
                        currentPage = "Perfil" // volver a Perfil después de guardar
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(currentPage: String, onPageChange: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = currentPage == "Inicio",
            onClick = { onPageChange("Inicio") },
            label = { Text("Inicio") },
            icon = { }
        )
        NavigationBarItem(
            selected = currentPage == "Viajes",
            onClick = { onPageChange("Viajes") },
            label = { Text("Viajes") },
            icon = { }
        )
        NavigationBarItem(
            selected = currentPage == "Perfil",
            onClick = { onPageChange("Perfil") },
            label = { Text("Perfil") },
            icon = { }
        )
    }
}

@Composable
fun InicioScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🚖 Bienvenido a MotoTaxiApp",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Conecta pasajeros con mototaxistas de manera rápida y segura.")
    }
}

@Composable
fun ViajesScreen() {
    val viajes = listOf("Centro → Terminal", "Parque → Estadio", "Universidad → Mall")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        viajes.forEach { viaje ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = viaje,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun PerfilScreen(
    nombre: String,
    moto: String,
    telefono: String,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Quita la imagen si aún no tienes un recurso válido
        Text("Foto de perfil aquí (pendiente)", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("👤 Nombre: $nombre", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("🏍️ Moto: $moto")
        Spacer(modifier = Modifier.height(8.dp))
        Text("📞 Teléfono: $telefono")
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onEditClick) {
            Text("✏️ Editar Perfil")
        }
    }
}

@Composable
fun EditarPerfilScreen(
    nombre: String,
    moto: String,
    telefono: String,
    onGuardar: (String, String, String) -> Unit
) {
    var nuevoNombre by remember { mutableStateOf(nombre) }
    var nuevaMoto by remember { mutableStateOf(moto) }
    var nuevoTelefono by remember { mutableStateOf(telefono) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nuevoNombre,
            onValueChange = { nuevoNombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nuevaMoto,
            onValueChange = { nuevaMoto = it },
            label = { Text("Moto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nuevoTelefono,
            onValueChange = { nuevoTelefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onGuardar(nuevoNombre, nuevaMoto, nuevoTelefono)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("💾 Guardar")
        }
    }
}
