package com.example.translatorapp

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage.CROATIAN
import com.google.mlkit.nl.translate.TranslateLanguage.ENGLISH
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions



val outputText = mutableStateOf("")
const val HR = CROATIAN
const val EN = ENGLISH

class TranslatorClass(
    baseLanguage:String,
    secondLanguage: String
){
    private val translatedText: String = ""
    private val options =
            TranslatorOptions.Builder()
            .setSourceLanguage(baseLanguage)
            .setTargetLanguage(secondLanguage)
            .build()


    private val languageTranslator = Translation.getClient(options)
    
    fun onTranslateButtonClick(
        textForTranslating: String,
        context: Context
    ){

        languageTranslator.translate(textForTranslating)
            .addOnSuccessListener {
                translatedText -> outputText.value = translatedText
            }
            .addOnFailureListener{
                Toast.makeText(
                    context,
                    "Downloading started...",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun downloadModelIfNotDownloaded(
        context: Context
    )   {
        val conditions = DownloadConditions
          .Builder()
          .requireWifi()
          .build()
        languageTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Downloaded model successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener{
                Toast.makeText(
                    context,
                    "Error occurred while downloading language model",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
