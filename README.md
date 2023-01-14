![alt text](https://github.com/mnbjhu/neo4k/blob/master/neo4k_logo_text.png?raw=true)

![tests](https://github.com/mnbjhu/KRG2/actions/workflows/gradle.yml/badge.svg)
[![](https://jitpack.io/v/mnbjhu/neo4k.svg)](https://jitpack.io/#mnbjhu/neo4k)
[![stability-alpha](https://img.shields.io/badge/stability-alpha-f4d03f.svg)](https://github.com/mkenney/software-guides/blob/master/STABILITY-BADGES.md#alpha)

Inspired by Kotlin Exposed, this library aims to provide a type-safe cypher DSL for interacting with Neo4j.
### Features
* User defined graph schemas
* Cypher CRUD operations
* Allows you to construct complex queries
* Returned data is automatically deserialized into the implied type

### Example

```kotlin

val findBestRatedMovies = query {
    val (movie, review) = match(::Movie `←-o` ::Reviewed `←-o` ::User)
    many(movie.title, avg(review.rating), count(review))
}.with { (title, averageRating, numberOfReviews) ->
    where(numberOfReviews greaterThan 100)
    orderByDesc(averageRating)
    limit(25)
    many(title, averageRating)
}.build()
graph.findBestRatedMovies().forEach { println(it) /* it: Pair<String, Double> */ }
```

## Setup
You will need a Neo4j database setup. For instructions on installing Neo4j desktop see [here](https://neo4j.com/docs/operations-manual/current/installation/).

Note: Some features will only be available for the enterprise edition.
#### Gradle
Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```groovy
dependencies {
    implementation 'com.github.mnbjhu:neo4k:0.0.1'
}
```

#### Gradle Kotlin DSL
Step 1. Add the JitPack repository to your build file
```kotlin
allprojects {
    repositories {
        ...
        maven("https://jitpack.io")
    }
}
```

Step 2. Add the dependency
```kotlin
dependencies {
    implementation("com.github.mnbjhu:neo4k:0.0.1")
}
```

# Connect to Neo4j
To connect to your database, you create an instance of 'Graph':
```kotlin
val graph = Graph(
    name = "neo4j",
    username = "Username",
    password = "Password123!",
    host = "localhost"
)
```

# Build a schema
To take advantage of Kotlin's type safety, neo4k requires you to build a schema.

You can define nodes by extending either the UnitNode or the Node class and by defining its attributes as values in the primary constructor.
These values should all be instances of ReturnValue<T>:  

## Nodes
#### UnitNode

```kotlin
class Movie(
    val title: StringReturn,
    val releaseYear: LongReturn
): UnitNode()
```
#### Node\<T>
A UnitNode is just a node which can't be returned directly from a query. 
```kotlin
data class MovieData(val title: String, val releaseYear: Long)

class Movie( 
    val title: StringReturn,
    val releaseYear: LongReturn 
): Node<MovieData>(){ 
    override fun ReturnScope.decode() = MovieData( 
        ::title.result(), 
        ::releaseYear.result()
    )
}
```
Currently supported types are:

| Name     | Class                                 | Type Descriptor               | Return Type    |
|----------|---------------------------------------|-------------------------------|----------------|
| Long     | LongReturn                            | ::LongReturn                  | kotlin.Long    |
| Boolean  | BooleanReturn                         | ::BooleanReturn               | kotlin.Boolean |
| String   | StringReturn                          | ::StringReturn                | kotlin.String  |
| Double   | DoubleReturn                          | ::DoubleReturn                | kotlin.Double  |
| Nullable | NullableReturn<T, U: ReturnValue\<T>> | nullable{ /* Inner */ }       | T?             |
| Arrays   | ArrayReturn<T, U: ReturnValue\<T>>    | array{ /* Inner */ }          | List\<T>       |
| Structs  | StructReturn<T, U: ReturnValue\<T>>   | ::MyStruct (see StructReturn) | T              |  

## Relationships

You can create relationships by extending the DirectionalRelationship or NonDirectionalRelationship classes or their Unit equivalent.
### Directional Relationships
Directional relationships take two type arguments: the 'from' type and the 'to' type.
```kotlin
class ActedIn(val role: StringReturn): UnitDirectionalRelationship<Actor, Movie>()
```

### Non-Directional Relationships
On the other hand non-directional only require one type argument 
```kotlin
class FriendsWith: UnitNonDirectionalRelationship<User>()
```

# Construct and execute queries
### Paths
Once you have built a schema it can be used to query your graph.
You can describe your nodes and relationships using their constructor reference:

With no constraints:
`::Movie`,`::ActedIn`

With constrains:
```kotlin
import uk.gibby.neo4k.core.invoke // THIS WILL NOT IMPORT AUTOMATICALLY

::Movie{ it[title] = "Pulp Fiction" }
```
You can then use the arrow functions: \`o-→\`, \`←-o\` \`-o-\` ('o' was added for autocomplete) to describe paths:

```kotlin
::Actor `o-→` ::ActedIn `o-→` ::Movie{ it[title] = "Pulp Fiction" }
```
Path are currently supported upto a length of ten (not including ranged relation matches).

### Queries
#### QueryScope
Queries are written within a lambda with the QueryScope class as the receiver.
This provides you with a set of functions which correspond to the clauses from neo4j.
The currently supported clauses are:
* CREATE
* DELETE
* LIMIT
* MATCH
* ORDER BY
* SET
* SKIP
* UNWIND
* WHERE
* WITH (See below)

Queries can either be executed directly by calling the 'query' function:
```kotlin
graph.query { // this: QueryScope
    val movie = create(::Movie{ it[title] = "Star Wars: Episode V - The Empire Strikes Back"; it[releaseYear] = 1980 })
    create(::Actor{ it[name] = "Mark Hamill" } `o-→` ::ActedIn{ it[role] = "Luke Skywalker" } `o-→` movie)
    create(::Actor{ it[name] = "Carrie Fisher" } `o-→` ::ActedIn{ it[role] = "Princess Leia" } `o-→` movie)
    create(::Actor{ it[name] = "Harrison Ford" } `o-→` ::ActedIn{ it[role] = "Han Solo" } `o-→` movie)
}
```
or by first constructing a query and then executing it on the graph:

```kotlin
val findActorsInMovie = query(::StringReturn) { searchString -> 
    val (actor, _, movie) = match(::Actor `o-→` ::ActedIn `o-→` ::Movie)
    where(movie.name contains searchString)
    actor.name
}.build()
val actorNames = graph.findActorsInMovie("Star Wars") 
```
The latter creates the query string and the relevant serializers when 'build' is called: making the query much more efficient.
In this example we have a parameter of 'searchString'. Parameterizing queries (rather than constructing them at execution time) makes it easier for neo4j to cache the execution plan, which also makes it faster 🔥.  
```kotlin
val findBestRatedMovies = query {
    val (movie, review) = match(::Movie `←-o` ::Reviewed `←-o` ::User)
    many(movie.title, avg(review.rating), count(review))
}.with { (title, averageRating, numberOfReviews) ->
    where(numberOfReviews greaterThan 100)
    orderByDesc(averageRating)
    limit(25)
    many(title, averageRating)
}.build()
graph.findBestRatedMovies() // List<Pair<String, Double>>
```

You can chain queries together using the 'with' function which passes on the return of the previous query. 

