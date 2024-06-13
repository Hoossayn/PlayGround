package com.example.core.test.rule

import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertIsDisplayed

/**
 * Asserts `IsDisplayed` for more than 2 items.
 *
 * @return
 */
fun SemanticsNodeInteractionCollection.assertAreDisplayed(): SemanticsNodeInteractionCollection {
    fetchSemanticsNodes().forEachIndexed { index, _ ->
        get(index).assertIsDisplayed()
    }
    return this
}
