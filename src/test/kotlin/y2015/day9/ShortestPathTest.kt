package y2015.day9

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith


@RunWith(JUnitPlatform::class)
class ShortestPathTest : Spek({

    /**
     * London to Dublin = 464
     * London to Belfast = 518
     * Dublin to Belfast = 141
     */
    test("sample") {
        assertThat(shortestPath(listOf(
                "London to Dublin = 464",
                "London to Belfast = 518",
                "Dublin to Belfast = 141"
        ))).isEqualTo(605)

    }
})

