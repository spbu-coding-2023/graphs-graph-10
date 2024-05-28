package graphs.abstracts

import graphs.primitives.Vertex

open class Vertex(
    override var element: Long,
    override var data: String?,
) : Vertex
