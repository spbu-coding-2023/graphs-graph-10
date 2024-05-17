package database.connectedEntity

class EdgeDBFormat<E, V>(val element: E, val weight: E, val color: ULong,
                           val firstVertex: V, val secondVertex: V)