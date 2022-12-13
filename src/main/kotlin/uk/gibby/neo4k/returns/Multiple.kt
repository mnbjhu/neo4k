package uk.gibby.neo4k.returns

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