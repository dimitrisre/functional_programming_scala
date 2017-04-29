import funsets.FunSets._

val x = singletonSet(1)
val y = singletonSet(2)
val z = singletonSet(4)
val j = singletonSet(3)

val e = union(x, y)
val i = union(z, j)

val g = union(e, i)

contains(e, 4)
contains(g, 2)

val d = diff(i, y)
contains(d, 3)

def p(x: Int): Boolean = {
  x >= 1
}

forall(g, (x: Int) => x>=7)

object Main extends App {
}