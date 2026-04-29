package com.example.appmaneiro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import kotlinx.coroutines.delay
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var telaAtual by remember { mutableStateOf("loading") }
            LaunchedEffect(Unit) {
                delay(5000)
                telaAtual = "segunda"
            }
            when (telaAtual) {
                "loading" -> {
                    TelaDeLoading()
                }
                "segunda" -> {
                    SegundaTela(onProximoClick = { telaAtual = "terceira" })
                }
                "terceira" -> {
                    HubPrincipal()
                }
            }
        }
    }
}

@Composable
fun TelaDeLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bikologo),
            contentDescription = "Biko",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SegundaTela(onProximoClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isCliente by remember { mutableStateOf(true) }

    val blueHeader = Color(0xFF255EE6)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F5FA))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Biko App",
            fontSize = 36.sp,
            color = blueHeader,
            fontWeight = FontWeight.Bold,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bem-vindo de volta!",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Toggle Cliente / Prestador
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFE0E7FF))
                .padding(4.dp)
        ) {
            Button(
                onClick = { isCliente = true; username = ""; password = ""; errorMessage = "" },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = if (isCliente) blueHeader else Color.Transparent
                ),
                elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(0.dp),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text("Cliente", color = if (isCliente) Color.White else Color.Gray, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { isCliente = false; username = ""; password = ""; errorMessage = "" },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = if (!isCliente) blueHeader else Color.Transparent
                ),
                elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(0.dp),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text("Prestador", color = if (!isCliente) Color.White else Color.Gray, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuário") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            singleLine = true,
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = errorMessage, color = Color.Red, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                val correctUser = if (isCliente) "PedroLouco" else "RobertoLegal"
                val correctPass = if (isCliente) "Doido" else "Legal"
                when {
                    username.isBlank() || password.isBlank() -> {
                        errorMessage = "Por favor, preencha todos os campos."
                    }
                    username == correctUser && password == correctPass -> {
                        errorMessage = ""
                        onProximoClick()
                    }
                    else -> {
                        errorMessage = "Usuário ou senha incorretos."
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = blueHeader)
        ) {
            Text("Entrar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HubPrincipal() {
    val blueHeader = Color(0xFF255EE6)
    val background = Color(0xFFF2F5FA)
    val orangeBanner = Color(0xFFF09A00)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        // Header Azul
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(blueHeader)
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Olá,  \uD83D\uDC4B",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "O que você precisa hoje?",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configurações",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Barra de pesquisa
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Cyan,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "O que precisas?",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        // Conteúdo com Scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Banner Destaques
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(orangeBanner)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "\uD83C\uDF89 Destaques da semana",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Ofertas especiais para você",
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
                Button(
                    onClick = {},
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = blueHeader),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("VER", fontSize = 12.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryPill("Todos", true)
                CategoryPill("Cupons", false)
                CategoryPill("Últimos", false)
                CategoryPill("Favoritos", false)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Serviços disponíveis",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Serviços
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ServiceListItem("Jardineiro", "Poda, limpeza, paisagismo", "🌿")
                ServiceListItem("Encanador", "Reparos, instalações", "🔧")
                ServiceListItem("Eletricista", "Elétrica residencial", "⚡")
                ServiceListItem("Barbeiro", "Corte, barba, acabamento", "✂️")
            }
        }
    }
}
@Composable
fun CategoryPill(label: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) Color(0xFF255EE6) else Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Transparent else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else Color(0xFF255EE6),
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
@Composable
fun ServiceListItem(title: String, subtitle: String, emoji: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF2F5FA)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 24.sp)
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
        
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFFF2F5FA)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "›", fontSize = 18.sp, color = Color(0xFF255EE6))
        }
    }
}
