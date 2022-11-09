package me.jim.wx.awesomebasicpractice.compose

import android.content.Context
import android.util.AttributeSet
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.appcompattheme.AppCompatTheme

class CallToActionViewButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {

    var text by mutableStateOf<String>("Hello Compose")
    var onClick by mutableStateOf<() -> Unit>({

    })

    @Composable
    override fun Content() {
        AppCompatTheme {
            CallToActionButton(text, onClick)
        }
    }

    @Composable
    fun CallToActionButton(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            ),
            onClick = onClick,
            modifier = modifier,
        ) {
            Text(text)
        }
    }
}