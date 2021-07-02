package com.example.vocabularyapp.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.vocabularyapp.data.Injection
import com.example.vocabularyapp.data.WordDao
import java.lang.Exception
import java.time.LocalDateTime
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)

class InsertWordTest {
    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wordDao: WordDao


}