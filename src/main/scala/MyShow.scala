trait MyShow[A]:
  def myShow(a: A): String
  extension (a: A)
    def anotherShow: String = myShow(a)

object MyShow:
  given MyShow[String] with
    override def myShow(a: String): String = "show: "+ a

object Main extends App:
  import MyShow.given
  val result = "hh".anotherShow
  println(result)
