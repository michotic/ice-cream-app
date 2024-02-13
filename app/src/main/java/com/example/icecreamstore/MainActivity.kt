package com.example.icecreamstore

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecreamstore.ui.theme.IceCreamStoreTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IceCreamStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AppLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeMenu(typeSelected: String, selectionMade: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    val bowlType: String = stringResource(id = R.string.bowl_type)
    val coneType: String = stringResource(id = R.string.cone_type)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        ExposedDropdownMenuBox(expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }) {

            TextField(
                value = typeSelected,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor(),
                label = {
                    Text(text = "Cone or Bowl?")
                }
            )

            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                DropdownMenuItem(text = { Text(text = bowlType) }, onClick = {
                    selectionMade(bowlType)
                    isExpanded = false
                })
                DropdownMenuItem(text = { Text(text = coneType) }, onClick = {
                    selectionMade(coneType)
                    isExpanded = false
                })
            }
        }
    }
}

@Composable
fun ChooseQuantity(curQuantity: Int, updateQuantity: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {updateQuantity(-1)}) {
           Text(text = "-")
        }
        Text(text = curQuantity.toString(),
            fontSize = 24.sp)
        Button(onClick = { updateQuantity(1) }) {
            Text(text = "+")
        }
    }
}

@Composable
fun DisplayPrice(quantitySelected: Int, typeSelected: String) {
    var unitPrice: Float = 3.69F
    if (typeSelected == stringResource(id = R.string.bowl_type)) {
        unitPrice = 3.39F
    }
    var formattedTotal = (unitPrice * quantitySelected).toString()

    Row() {
        Text(text = "Total: ${"$%.2f".format(unitPrice * quantitySelected)}",
            fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AppLayout() {
    val initialType = stringResource(id = R.string.cone_type)
    var typeSelected by remember {
        mutableStateOf(initialType)
    }
    var quantitySelected by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = stringResource(id = R.string.app_name),
            fontSize = 30.sp,
            fontWeight = FontWeight.Black
        )

        TypeMenu(typeSelected = typeSelected, selectionMade = { typeSelected = it })

        Image(painter = painterResource(id = R.drawable.icecream), contentDescription = "")

        ChooseQuantity(curQuantity = quantitySelected,
            updateQuantity = {
                quantitySelected += it
                if (quantitySelected < 0) {
                    quantitySelected = 0
                }
            }
        )
        DisplayPrice(quantitySelected = quantitySelected, typeSelected = typeSelected)
    }

}