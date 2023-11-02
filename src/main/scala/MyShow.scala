
trait MyShow[A]:
  def myShow(a: A): String

object MyShow:
  given MyShow[String] with
    override def myShow(a: String): String = "show: "+ a

object Main extends App:
  val result = summon[MyShow[String]].myShow("hh")
  println(result)
