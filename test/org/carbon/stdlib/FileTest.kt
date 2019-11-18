package org.carbon.stdlib

import org.carbon.evaluate
import org.carbon.intrepreter.wrap
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.test.assertEquals

class FileTest {
    companion object {
        private const val testDir = "test/filetest"

        @JvmStatic
        @BeforeAll
        fun setupDirectory() {
            Files.createDirectory(Path.of(testDir))
        }

        @JvmStatic
        @AfterAll
        fun teardownDirectory() {
            Files.list(Path.of(testDir)).forEach { Files.delete(it) }
            Files.delete(Path.of(testDir))
        }
    }

    @Test
    fun readTest() {
        Files.createFile(Path.of(testDir, "read"))
        Files.writeString(Path.of(testDir, "read"), "Test Text")

        val result = evaluate(
        """
            Content = File("$testDir/read").Contents
        """
        )
        assertEquals(wrap("Test Text"), result.getMember("Content"))
    }
}