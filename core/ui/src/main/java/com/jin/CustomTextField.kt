package com.jin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit

@Composable
fun HoneyTextField(
    keyword: String,
    hintText: String,
    hintTextColor: Color,
    fontSize: TextUnit,
    isSingleLine: Boolean,
    focusRequester: FocusRequester,
    onValueChange: (value: String) -> Unit,
    onFocusChanged: (value: Boolean) -> Unit,
) {
    BasicTextField(
        value = keyword,
        onValueChange = { onValueChange(it) },
        singleLine = isSingleLine,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused) },
        decorationBox = { innerTextField ->
            if (keyword.isEmpty()) {
                Text(
                    text = hintText,
                    color = hintTextColor,
                    fontSize = fontSize
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun HoneyNumberField(
    keyword: String,
    hintText: String,
    hintTextColor: Color,
    fontSize: TextUnit,
    isSingleLine: Boolean,
    focusRequester: FocusRequester,
    onValueChange: (value: String) -> Unit,
    onFocusChanged: (value: Boolean) -> Unit,
) {
    BasicTextField(
        value = keyword,
        onValueChange = { newText ->
            if (newText.all { it.isDigit() }) {
                onValueChange(newText)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        singleLine = isSingleLine,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused) },
        decorationBox = { innerTextField ->
            if (keyword.isEmpty()) {
                Text(
                    text = hintText,
                    color = hintTextColor,
                    fontSize = fontSize
                )
            }
            innerTextField()
        }
    )
}
