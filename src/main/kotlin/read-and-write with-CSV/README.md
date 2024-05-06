# read-and-write-with-CSV
This **`read-and-write-with-CSV`** library helps to read a [**graph**](https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)) from a [**CSV**](https://en.wikipedia.org/wiki/Comma-separated_values) file and write the graph in the form of tables to a [**CSV**](https://en.wikipedia.org/wiki/Comma-separated_values) file
___
# How to write?
Creating a **graph** and adding the necessary vertices to it
```kotlin
val sampleGraph: Graph<String, Long> = UndirectedGraph<String, Long>().apply {
    addVertex("A")
    addVertex("B")
    addVertex("C")
    addVertex("D")
    addVertex("E")
    addVertex("F")
    addVertex("G")
    addVertex("H")
    addVertex("I")
    addVertex("J")
    addVertex("K")
    addVertex("L")
    addVertex("M")
    addVertex("N")
```
We create the **edges** of the **graph** and assign a unique key to each
``` kotlin
    addEdge("A", "B", 1)
    addEdge("A", "C", 2)
    addEdge("A", "D", 3)
    addEdge("A", "E", 4)
    addEdge("A", "F", 5)
    addEdge("A", "G", 6)

   
    addEdge("A", "H", 0)
    addEdge("H", "I", 7)
    addEdge("H", "J", 8)
    addEdge("H", "K", 9)
    addEdge("H", "L", 10)
    addEdge("H", "M", 11)
    addEdge("H", "N", 12)
}
```
After all the work done, we call the **`.writing()`** method and we create a file called **`graph.csv`**

![2024-05-06 18-44-21 (1)](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/36ad7cbb-60ac-450d-9c73-d78ca5a94498)


Thus, our file was recorded in **`.CSV`**
___
# How to read?

Our **`csv file`** from which we want to read should look **like this**:
```
#EXAMPLE 1                           
;;A;B;C;D;E;F;G;H;I;J;K;L;M;N                                  
A;0;1;1;1;1;1;1;1;0;0;0;0;0;0
B;1;0;0;0;0;0;0;0;0;0;0;0;0;0
C;1;0;0;0;0;0;0;0;0;0;0;0;0;0
D;1;0;0;0;0;0;0;0;0;0;0;0;0;0
E;1;0;0;0;0;0;0;0;0;0;0;0;0;0
F;1;0;0;0;0;0;0;0;0;0;0;0;0;0
G;1;0;0;0;0;0;0;0;0;0;0;0;0;0
H;1;0;0;0;0;0;0;0;1;1;1;1;1;1
I;0;0;0;0;0;0;0;1;0;0;0;0;0;0
J;0;0;0;0;0;0;0;1;0;0;0;0;0;0
K;0;0;0;0;0;0;0;1;0;0;0;0;0;0
L;0;0;0;0;0;0;0;1;0;0;0;0;0;0
M;0;0;0;0;0;0;0;1;0;0;0;0;0;0
N;0;0;0;0;0;0;0;1;0;0;0;0;0;0
```


```
#EXAMPLE 2                       
;;A;B;C;D;E;F;G
A;0;1;1;1;1;1;1
B;1;0;0;0;0;0;0
C;1;0;0;0;0;0;0
D;1;0;0;0;0;0;0
E;1;0;0;0;0;0;0
F;1;0;0;0;0;0;0
G;1;0;0;0;0;0;0
```

That is, all vertices and values(0 or 1) **must be** separated **`;`**  (At the beginning of the first line should also be **`;;`**)

A value of **`1`** indicates that there is a path between the vertices, and **`0`** indicates that there is no path

Before reading, you need to create an object of **`class Graph`**
```kotlin
val graph: Graph<String, Long> = UndirectedGraph<String, Long>()
```

Next, you need to call the **`.reading<String, Long>("path to the file")`** method and specify the path to the file
```kotlin
graph.reading<String, Long>("graph.csv")
```
When reading the graph, all vertices (even those that do not have edges) will be written to the  field **`.vertices`** 

And in the **`edges`** field, *only* the vertices between which there is a path


For the csv file 
```
;;A;B;C;D;E;F;G
A;0;1;1;1;1;1;0
B;1;0;0;0;0;0;0
C;1;0;0;0;0;0;0
D;1;0;0;0;0;0;0
E;1;0;0;0;0;0;0
F;1;0;0;0;0;0;0
G;0;0;0;0;0;0;0
```
 we will get the result:
 ``` kotlin
    //println(graph.edges)
    //println(graph.vertices)
    [UndirectedEdge(element=1, first=UndirectedVertex(element=A), second=UndirectedVertex(element=B)), UndirectedEdge(element=2, first=UndirectedVertex(element=A), second=UndirectedVertex(element=C)), UndirectedEdge(element=3, first=UndirectedVertex(element=A), second=UndirectedVertex(element=D)), UndirectedEdge(element=4, first=UndirectedVertex(element=A), second=UndirectedVertex(element=E)), UndirectedEdge(element=5, first=UndirectedVertex(element=A), second=UndirectedVertex(element=F))]
    [UndirectedVertex(element=A), UndirectedVertex(element=B), UndirectedVertex(element=C), UndirectedVertex(element=D), UndirectedVertex(element=E), UndirectedVertex(element=F), UndirectedVertex(element=G)]

Process finished with exit code 0

```
