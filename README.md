![alt text](https://github.com/mnbjhu/neo4k/blob/master/neo4k_logo_text.png?raw=true)

![tests](https://github.com/mnbjhu/KRG2/actions/workflows/gradle.yml/badge.svg)
[![](https://jitpack.io/v/mnbjhu/KRG.svg)](https://jitpack.io/#mnbjhu/KRG)
[![stability-alpha](https://img.shields.io/badge/stability-alpha-f4d03f.svg)](https://github.com/mkenney/software-guides/blob/master/STABILITY-BADGES.md#alpha)

Inspired by Kotlin Exposed, this library aims to provide a type-safe cypher DSL for interacting with Neo4j.
### Features
* User defined graph schemas
* Cypher CRUD operations
* Allows you to construct complex queries
* Returned data is automatically deserialized into the implied type

### Example

```kotlin
val myQuery = query {
    val (movie, userRating) = match(::Movie `←-o` ::Rated `←-o` ::User)
    many(movie.title, avg(userRating.rating), count(userRating))
}.with { (title, averageRating, numberOfRatings) ->
    where(numberOfRatings greaterThan 100)
    orderByDesc(averageRating)
    limit(25)
    many(title, averageRating)
}.build()
graph.myQuery().forEach { println(it) }
```
