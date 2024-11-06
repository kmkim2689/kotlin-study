package study.collection

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class Transformations {
    @Test
    fun singleList_mapNotNull_nullElementFiltered() {
        // given
        val numbers = setOf(1, 2, 3)

        // when
        val result = numbers.mapNotNull { number ->
            if (number == 2) null else number * number
        }.toSet()

        // then
        assertThat(result).isEqualTo(setOf(1, 9))
    }

    @Test
    fun singleList_mapIndexedNotNull_nullElementFiltered() {
        // given
        val numbers = listOf(1, 2, 3)

        // when
        val result = numbers.mapIndexedNotNull { index, i ->
            if (index == 0) null else i
        }

        // then
        assertThat(result).isEqualTo(listOf(2, 3))
    }

    @Test
    fun singleMap_mapKeys_returnsTransformedKeys() {
        // given
        val map = mapOf(1 to "a", 2 to "b", 3 to "c")

        // when
        val result = map.mapKeys { item -> // item : key + value로 이뤄진 Map.Entry<K, V>
            "key${item.key}" // new key!
        }

        // then
        assertThat(result).isEqualTo(mapOf("key1" to "a", "key2" to "b", "key3" to "c"))
    }

    @Test
    fun singleMap_mapValues_returnsTransformedValues() {
        // given
        val map = mapOf(1 to "a", 2 to "b", 3 to "c")

        // when
        val result = map.mapValues { mapItem ->
            "value ${mapItem.value}"
        }

        // then
        assertThat(result).isEqualTo(mapOf(1 to "value a", 2 to "value b", 3 to "value c"))
    }

    /**
     * Zip : building pairs from elements with the same positions in both collections
     */
    @Test
    fun twoLists_zip_differentSizeCollections_returnsSmallerSize() {
        // given
        val list1 = listOf(1, 2, 3)
        val list2 = listOf(1, 2, 3, 4, 5)

        // when
        val result = list1.zip(list2) { elem1, elem2 -> // pair 형태
            elem1 + elem2
        }

        // then
        assertAll(
            { assertThat(result).isEqualTo(listOf(2, 4, 6)) },
            { assertThat(result.size).isEqualTo(3) },
        )
    }

    @Test
    fun twoLists_infixZip_returnsListOfPairs() {
        // given
        val list1 = listOf(1, 2, 3)
        val list2 = listOf(1, 2, 3, 4, 5)

        // when
        val result = list1 zip list2

        // then
        assertAll(
            { assertThat(result.first()).isInstanceOf(Pair::class.java) }
        )
    }

    @Test
    fun listOfPairs_unzip_returnsPairOfLists() {
        // given
        val pairs = listOf("one" to 1, "two" to 2, "three" to 3)

        // when
        val result = pairs.unzip()

        // then
        assertAll(
            {
                assertThat(result).isEqualTo(
                    listOf("one", "two", "three") to listOf(1, 2, 3)
                )
            },
            {
                assertThat(result).isInstanceOf(Pair::class.java)
            }
        )
    }

    /**
     * Associate : building maps from the collection elements
     * associate : just pair
     * associateBy : map key
     * associateWith : map value
     * associateTo : similar to associate, but add to "existing" map
     * -> `to` keyword in collection : adding to existing thing!
     */
    @Test
    fun singleList_associate_mappingUsingPairs_returnsMap() {
        // given
        val list = listOf(1, 2, 3)

        // when
        val result = list.associate { number ->
            "key$number" to "value$number"
        }

        // then
        assertThat(result).isEqualTo(
            mapOf(
                "key1" to "value1",
                "key2" to "value2",
                "key3" to "value3",
            )
        )
    }

    @Test
    fun singleList_associateBy_mappingElementToKey_returnsMap() {
        // given
        val list = listOf(1, 2, 3)

        // when
        val result = list.associateBy { keyNumber ->
            "key$keyNumber"
        }

        // then
        assertThat(result).isEqualTo(
            mapOf(
                "key1" to 1,
                "key2" to 2,
                "key3" to 3,
            )
        )
    }

    @Test
    fun singleList_associateWith_mappingElementToValue_returnsMap() {
        // given
        val list = listOf(1, 2, 3)

        // when
        val result = list.associateWith { valueNumber ->
            "value$valueNumber"
        }

        // then
        assertThat(result).isEqualTo(
            mapOf(
                1 to "value1",
                2 to "value2",
                3 to "value3",
            )
        )
    }

    @Test
    fun singleList_associateTo_mappingPairToMapEntry_returnsMap() {
        // given - MutableMap
        val existingMap = mutableMapOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3",
        )
        val list = listOf(4, 5, 6)

        // when
        val result = list.associateTo(existingMap) { number ->
            "key$number" to "value$number"
        }

        // then
        assertThat(result).isEqualTo(
            mapOf(
                "key1" to "value1",
                "key2" to "value2",
                "key3" to "value3",
                "key4" to "value4",
                "key5" to "value5",
                "key6" to "value6",
            )
        )
    }

    /**
     * String Representation : transform the collections to strings
     * joinToString : builds a single String from the collection elements based on the provided arguments
     * joinTo : joinToString + appends the result to the given Appendable object.
     */
    @Test
    fun singleList_joinToStringWithoutSeparator_returnsStringWithCommas() {
        // given
        val numbers = listOf("one", "two", "three", "four")

        // when
        val result = numbers.joinToString()

        // then
        assertThat(result).isEqualTo("one, two, three, four")
    }

    @Test
    fun singleList_joinToStringWithSeparator_returnsStringWithSeparatorSign() {
        // given
        val numbers = listOf("one", "two", "three", "four")

        // when
        val result = numbers.joinToString("*")

        // then
        assertThat(result).isEqualTo("one*two*three*four")
    }

    @Test
    fun singleList_joinToStringWithPrefixAndPostFix_returnsStringWithPrefixAndPostFix() {
        // given
        val numbers = listOf("one", "two", "three", "four")

        // when
        val result = numbers.joinToString(separator = "*", prefix = "start - ", postfix = " - end")

        // then
        assertThat(result).isEqualTo("start - one*two*three*four - end")
    }

    @Test
    fun singleList_joinToStringWithLimitedNumberAndTruncated_returnsTruncatedString() {
        // given
        val numbers = listOf("one", "two", "three", "four")

        // when
        val result = numbers.joinToString(limit = 3, truncated = "...")

        // then
        assertAll(
            { assertThat(result).isEqualTo("one, two, three, ...") },
            { assertThat(result).isNotEqualTo("one, two, three ...") },
        )
    }

//    flatMapTo
//    @Test
//    fun singleList_flatMapTo_returnsAddedList() {
//        // given
//        val numbers = listOf(
//            listOf("one", "two", "three", "four"),
//            listOf("five", "six", "seven", "eight"),
//            listOf("nine", "ten", "eleven", "twelve"),
//        )
//        val existingNumbers = mutableListOf("value zero")
//
//        // when
//        val result = numbers.flatMapTo(existingNumbers) { strValue ->
//            "value $strValue"
//        }
//    }
}
