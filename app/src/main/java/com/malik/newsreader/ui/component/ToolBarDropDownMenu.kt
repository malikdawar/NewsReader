package com.malik.newsreader.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import com.malik.newsreader.R

/**
 * The [ToolBarDropDownMenu].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBarDropDownMenu(
    label: String = "News Articles",
    options: List<String>,
    onOptionSelectionEvent: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    TopAppBar(
        title = { Text(label) },
        actions = {
            Box(
                modifier = Modifier.focusRequester(focusRequester)
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sort),
                        contentDescription = "Sort"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option: String ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onOptionSelectionEvent.invoke(option)
                            }
                        )
                    }
                }
            }
        }
    )
}