package com.pratham.project.fileio

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest{

    val activityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Before
    fun setup(){

    }

    @Test
    fun testView(){
        Espresso.onView(ViewMatchers.withId(R.id.webView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}