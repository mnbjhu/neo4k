package uk.gibby.neo4k.clauses


import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance

/**
 * Delete
 *
 * Represents the 'SET' claus from the CypherQL
 *
 * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/set/)
 *
 * @property map A map from attributes to the values they should be set to
 * @constructor Creates a 'SET' claus
 */
class Set(private val map: SetMap): Claus() {
    override fun getString(): String {
        return map.params.entries.joinToString(prefix = "SET ") { "${it.key.getString()} = ${it.value.getString()}" }
    }
    companion object{
        /**
         * Set
         *
         * Used to set attributes of nodes or relations
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/set/)
         *
         * @param setScope A lambda with access to set attributes ([SetMap.to])
         * @receiver The scope of the query
         * @return An empty return so queries can end with this claus
         * @sample [e2e.clauses.Set.setNodeValue]
         * @sample [e2e.clauses.Set.setRelationValue]
         * @sample [e2e.clauses.Set.setNodeValueToReference]
         * @see [QueryScope]
         */
        fun QueryScope.set(setScope: SetMap.() -> Unit): EmptyReturnInstance {
            val map = SetMap()
            map.setScope()
            addStatement(Set(map))
            return EmptyReturnInstance
        }
    }
    class SetMap(internal val params: MutableMap<ReturnValue<*>, ReturnValue<*>> = mutableMapOf()){
        infix fun <T, U: ReturnValue<T>>U.to(value: T) { params[this] = encode(value) }
        infix fun <T, U: ReturnValue<T>>U.to(value: U) { params[this] = value }
    }
}
