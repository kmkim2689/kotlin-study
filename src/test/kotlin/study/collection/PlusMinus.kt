package study.collection

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlusMinus {
    @Test
    fun singleListAsFirstOperand_plus_elementAsSecondOperand_returnsReadOnlyListWithTheElement() {
        // given
        val numbers = listOf("one", "two")

        // when
        val result = numbers + "three"

        // then
        assertThat(result).isEqualTo(listOf("one", "two", "three"))
    }

    @Test
    fun singleList_plus_anotherList_returnsReadOnlyListWithAnotherListElements() {
        // given
        val numbers = listOf("one", "two", "three")

        // when
        val result = numbers + listOf("three", "four")

        // then
        assertThat(result).isEqualTo(listOf("one", "two", "three", "three", "four"))
    }

    @Test
    fun singleList_minus_elementAsSecondOperand_returnsReadOnlyList_onlyFirstOccurrenceRemoved() {
        // given
        val numbers = listOf("one", "two", "three", "three")

        // when
        val result = numbers - "three"

        // then
        assertThat(result).isEqualTo(listOf("one", "two", "three"))
    }

    @Test
    fun singleList_minus_anotherListAsSecondOperand_returnsReadOnlyList_allOccurrencesRemoved() {
        // given
        val numbers = listOf("one", "two", "three", "three", "three", "four")

        // when
        val result = numbers - listOf("three", "four")

        // then
        assertThat(result).isEqualTo(listOf("one", "two"))
    }
}
