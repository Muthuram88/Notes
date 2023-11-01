package `in`.kumudan.notes.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InputTextBox(modifier: Modifier=Modifier,
                 text:String="",
                 label:String="Name or Number or Date Of Birth",
                 placeholder:String="",
                 maxLine:Int=1,
                 onTextChange:(String)->Unit,
                 onImeAction:()->Unit={},
                 singleLine:Boolean=true,
                 fontWeight: FontWeight=FontWeight.Normal
){

    val localKeyboardController=LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(text=label,
            style = MaterialTheme.typography.labelSmall) },
        maxLines = maxLine,
        textStyle = TextStyle(fontWeight = fontWeight),
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            localKeyboardController?.hide()
        }),
        placeholder={Text(placeholder,color=Color.Black)},
        singleLine = singleLine
    )

}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview(){
    InputTextBox(onTextChange = {}, label="title",placeholder = "Enter the Title")
}
