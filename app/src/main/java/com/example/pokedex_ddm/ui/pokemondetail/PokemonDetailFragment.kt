package com.example.pokedex_ddm.ui.pokemondetail
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex_android.util.Resource
import com.example.pokedex_ddm.data.remote.responses.Pokemon

@Composable
fun PokemonDetailFragment(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 325.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value
    val pokemonInfo2 = "Teste Renan"
    Box(modifier = Modifier
        .fillMaxSize()
        .background(dominantColor)
        .padding(bottom = 16.dp)
    ){
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        PokemonDetailStateWrapper(
            pokemonInfo = pokemonInfo2,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
        Box(contentAlignment = Alignment.TopCenter,
            modifier = Modifier
            .fillMaxSize()
        ) {
            if(pokemonInfo is Resource.Success) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemonInfo.data?.sprites?.front_default)
                        .crossfade(true)
                        .build(),
                    contentDescription = pokemonInfo.data?.name,
                    modifier = Modifier
                        .size(pokemonImageSize)
                        .offset()
                )
            }
        }
    }
}

@Composable
fun PokemonDetailTopSection(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier.background(
            Brush.verticalGradient(
                listOf(Color.Black,
                       Color.Transparent)
            )
        )
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo : String,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    Text(
        text = pokemonInfo,
        color = Color.Green,
        modifier = modifier
    )
//    when(pokemonInfo) {
//        is Resource.Success -> {
//
//        }
//        is Resource.Error -> {
//            Text(
//                text = pokemonInfo.message!!,
//                color = Color.Red,
//                modifier = modifier
//            )
//        }
//        is Resource.Loading -> {
//            CircularProgressIndicator(
//                color = MaterialTheme.colors.primary,
//                modifier = loadingModifier
//            )
//        }
    }