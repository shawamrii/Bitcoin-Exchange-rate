import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.idling.CountingIdlingResource

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val idlingResource = CountingIdlingResource("DataLoader")

    @Test
    fun testCurrencyChangeUpdatesRecyclerView() {
        // Assuming MainActivity registers an IdlingResource that tracks ongoing network requests
        IdlingRegistry.getInstance().register(idlingResource)

        // Select the spinner and change currency
        onView(withId(R.id.currencySpinner)).perform(click())
        onView(withText("USD")).perform(click())

        // Check for a specific item in the RecyclerView that should be present after data is loaded
        onView(withId(R.id.recyclerView))
            .check(matches(hasDescendant(withText("$"))))

        // Clean up
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}
