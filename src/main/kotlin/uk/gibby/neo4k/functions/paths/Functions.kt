package uk.gibby.neo4k.functions.paths

// fun <T, U: Node<T>>nodes(path2: Path2<U, *, U>) = ReturnValue.createReference(path2.getNodeType(), "nodes(${path2.getString()})") as ArrayReturn<T, U>