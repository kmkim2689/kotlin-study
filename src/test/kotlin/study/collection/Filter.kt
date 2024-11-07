package study.collection

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class Filter {
    /**
     * Leave the original collection `unchanged`!
     * -> Available both for `mutable` and `read-only` collections.
     */
    @Test
    fun singleListWithMultipleTypes_filterIsInstance_returnsNewListWithSpecificTypeElements() {
        // given
        val numbers = listOf(null, 1, "two", 3.0f, 5)

        // when
        val result = numbers.filterIsInstance<Int>()

        // then
        assertThat(result).isEqualTo(listOf(1, 5))
    }

    @Test
    fun singleList_partition_returnsTwoListsByCondition() {
        // given
        val numbers = listOf(1, 2, 3, 4, 5)

        // when - predicates to be true is the first list
        val result: Pair<List<Int>, List<Int>> = numbers.partition { it % 2 == 0 }
        val (even, odd) = result

        // then
        assertAll(
            { assertThat(result.first).isEqualTo(listOf(2, 4)) },
            { assertThat(result.second).isEqualTo(listOf(1, 3, 5)) },
            { assertThat(even).isEqualTo(listOf(2, 4)) },
            { assertThat(odd).isEqualTo(listOf(1, 3, 5)) },
        )
    }

    @Test
    fun singleEmptyList_any_withoutAPredicate_returnsWhetherListIsNotEmpty() {
        // given
        val emptyList = listOf<Int>()
        val numbers = listOf(1)

        // when - whether collection has any(at least one) element
        val emptyListResult = emptyList.any()
        val numbersListResult = numbers.any()

        // then
        assertAll(
            { assertThat(emptyListResult).isFalse() },
            { assertThat(numbersListResult).isTrue() },
        )
    }

    @Test
    fun singleEmptyList_none_withoutAPredicate_returnsWhetherListIsEmpty() {
        // given
        val emptyList = listOf<Int>()

        // when - whether collection has none of elements(is empty)
        val result = emptyList.none()

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun singleEmptyList_all_withAPredicate_returnsTrue() {
        // given
        val emptyList = emptyList<Int>()

        // when
        val result = emptyList.all { it > 5 }

        // then - always return true
        assertThat(result).isTrue()
    }
}
