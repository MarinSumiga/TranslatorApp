@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.translatorapp

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.translatorapp.ui.theme.PurpleGrey40
import com.example.translatorapp.ui.theme.SpeechRecognizer


var baseLanguage =  mutableStateOf("")
var secondLanguage = mutableStateOf("")
val x = mutableStateOf("")

@Preview
@Composable fun TranslatorScreen()
{

    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
    ){
        ScreenTitle("Translator")

        DropDownLangSelector()
        DropDownLangSelector2()

         x.value = getTextInput()

        ButtonForTranslating(
            translation = TranslatorClass(
                baseLanguage.value,
                secondLanguage.value
            ),
            x.value
        )

        ButtonForSpeech(record = SpeechRecognizer())

        TranslatedTextBox()

    }
}



@Composable
fun ScreenTitle(
    title: String
) {
    Box(
        modifier = Modifier
            .padding(all = 14.dp)
            .background(color = Color.White)
            .fillMaxWidth()
    ){
        Text(
            text = title,
            style = TextStyle(color = Color.Black, fontSize = 26.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .align(Alignment.Center)

        )
    }
}

@Composable
fun getTextInput(
): String{
    val textInput = remember { mutableStateOf("") }
    TextField(
        value = textInput.value, onValueChange = {textInput.value = it},
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Enter your text here:")},
    )
    return textInput.value
}

@Composable
fun TranslatedTextBox(

) {
    Box(
        modifier = Modifier
            .background(color = PurpleGrey40)
            .fillMaxWidth()
    ) {
        Text(
            outputText.value,
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(Color.White)
        )
    }
}

@Composable
fun ButtonForTranslating(translation:TranslatorClass,text:String) {
    Button(
        onClick = {
            translation.onTranslateButtonClick(text,Activity())
        },
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            "Translate",
            style = TextStyle(Color.White, fontSize = 26.sp)
        )
    }
}
@Composable
fun ButtonForSpeech(
    record: SpeechRecognizer
) {
    val isListening by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Button(
        onClick = {
            if (!isListening) {
                if (record.isSpeechRecognitionAvailable(context)){
                    record.startVoiceRecognition(context)
                }
            }
        },
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
    ){
        Text(
            text = if (isListening) "Listening..." else "Press button to start listening",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}



@Composable
fun DropDownLangSelector(){

    var isExpanded = remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded.value,
        onExpandedChange = {isExpanded.value = it}
    ) {
        TextField(
            label = {
                Text(text = "Choose base language")
            },
            value =  baseLanguage.value,
            onValueChange ={},
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .width(200.dp)
        )

        ExposedDropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }
        ) {
                DropdownMenuItem(
                    text = { Text(text = "HR") },
                    onClick = {
                        baseLanguage.value = HR
                        isExpanded.value = false
                    }
                )
            DropdownMenuItem(
                text = { Text(text = "EN") },
                onClick = {
                    baseLanguage.value = EN
                    isExpanded.value = false
                }
            )
        }
    }

}
@Composable
fun DropDownLangSelector2(){

    var isExpanded = remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded.value,
        onExpandedChange = {isExpanded.value = it}
    ) {
        TextField(
            label = {
                    Text(text = "Choose second language")
            },
            value =  secondLanguage.value,
            onValueChange ={},
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .width(200.dp)
        )

        ExposedDropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = "HR") },
                onClick = {
                    secondLanguage.value = HR
                    isExpanded.value = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = "EN") },
                onClick = {
                    secondLanguage.value = EN
                    isExpanded.value = false
                }
            )
        }
    }
}


