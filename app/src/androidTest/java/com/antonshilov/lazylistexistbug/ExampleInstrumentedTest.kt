package com.antonshilov.lazylistexistbug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.tooling.preview.Preview
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

class ComposeDoesNotExistTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun GIVEN_LazyColumn_WHEN_click_THEN_does_not_exist() {
        rule.setContent { LazyColumnScreen() }
        rule.onNodeWithText("ITEM_TO_REMOVE").assertExists()
        rule.onNodeWithText("CLICK").performClick()
        //failed
        rule.onNodeWithText("ITEM_TO_REMOVE").assertDoesNotExist()
    }

    @Test
    fun GIVEN_Column_WHEN_click_THEN_does_not_exist() {
        rule.setContent { ColumnScreen() }
        rule.onNodeWithText("ITEM_TO_REMOVE").assertExists()
        rule.onNodeWithText("CLICK").performClick()
        //passed
        rule.onNodeWithText("ITEM_TO_REMOVE").assertDoesNotExist()
    }
}

@Preview
@Composable
internal fun LazyColumnScreen() {
    val isVisible = remember {
        mutableStateOf(true)
    }

    LazyColumn() {
        if (isVisible.value) {
            item("0") {
                Text(text = "ITEM_TO_REMOVE")
            }
        }
        item("1") {
            Button(onClick = { isVisible.value = !isVisible.value }) {
                Text(text = "CLICK")
            }
        }
    }
}

@Preview
@Composable
internal fun ColumnScreen() {
    val isVisible = remember {
        mutableStateOf(true)
    }

    Column() {
        if (isVisible.value) {
            Text(text = "ITEM_TO_REMOVE")
        }
        Button(onClick = { isVisible.value = !isVisible.value }) {
            Text(text = "CLICK")
        }

    }
}