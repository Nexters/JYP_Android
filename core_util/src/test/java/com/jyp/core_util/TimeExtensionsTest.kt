package com.jyp.core_util

import com.google.common.truth.Truth.assertThat
import com.jyp.core_util.extensions.secondToDate
import org.junit.Test

class TimeExtensionsTest {
    @Test
    fun `toDate_원하는_날짜로_잘_변환한다`() {
        val seconds = 1676184951L

        val actualDate = seconds.secondToDate("MM월dd일")
        val expectedDate = "02월12일"

        assertThat(actualDate).isEqualTo(expectedDate)
    }
}
