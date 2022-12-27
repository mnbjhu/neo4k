package uk.gibby.neo4k.returns

import uk.gibby.neo4k.core.QueryScope

sealed interface MultipleReturn

data class MultipleReturn2<a, A: ReturnValue<a>, b, B: ReturnValue<b>>(val first: A, val second: B): ReturnValue<Pair<a, b>>(), MultipleReturn {
    override fun getStructuredString(): String {
        return "${first.getString()}, ${second.getString()}"
    }
    override fun parse(value: Any?): Pair<a, b> {
        val list = value as List<*>
        return first.parse(list[0]) to second.parse(list[1])
    }

    override fun createReference(newRef: String): ReturnValue<Pair<a, b>> {
        TODO("Not yet implemented")
    }

    override fun createDummy(): ReturnValue<Pair<a, b>> {
        TODO("Not yet implemented")
    }

    override fun encode(value: Pair<a, b>): ReturnValue<Pair<a, b>> {
        TODO("Not yet implemented")
    }
}

data class MultipleReturn3<a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>(val first: A, val second: B, val third: C): ReturnValue<Triple<a, b, c>>(), MultipleReturn {
    override fun getStructuredString(): String {
        return "${first.getString()}, ${second.getString()}, ${third.getString()}"
    }
    override fun parse(value: Any?): Triple<a, b, c> {
        val list = value as List<*>
        return Triple(first.parse(list[0]), second.parse(list[1]), third.parse(list[2]))
    }

    override fun createReference(newRef: String): ReturnValue<Triple<a, b, c>> {
        TODO("Not yet implemented")
    }

    override fun createDummy(): ReturnValue<Triple<a, b, c>> {
        TODO("Not yet implemented")
    }

    override fun encode(value: Triple<a, b, c>): ReturnValue<Triple<a, b, c>> {
        TODO("Not yet implemented")
    }

}
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>>QueryScope.many(first: A, second: B) = MultipleReturn2(first, second)
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>QueryScope.many(first: A, second: B, third: C) =
    MultipleReturn3(first, second, third)
