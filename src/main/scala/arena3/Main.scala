package arena3

import scala.io.StdIn.readLine
object Main extends App:
  val program = for
    _ <- write("Input:")
    input <- read
    _ <- write("Output:")
    _ <- write(input)
  yield input
  // 此行以上没有任何Effect

  program.run
  // Console输出: Input:
  // Console等待输入
  // Console输出: Output:
  // Console输出: 上一行输入结果

  println("=============")
  program.loopWhile(_.nonEmpty).run
// 进行上述行为的循环直到输入空值

sealed trait ConsoleEffect[A]:
  def flatMap[B](f: A => ConsoleEffect[B]): ConsoleEffect[B] = ChainEffect(this, f)
  def map[B](f: A => B): ConsoleEffect[B] = this.flatMap(a => NoEffect(f(a)))
  def loopWhile(p: A => Boolean): ConsoleEffect[A] = this.flatMap(a => if (p(a)) loopWhile(p) else NoEffect(a))

  final def run: A = this match
    case Read        => readLine()
    case Write(v)    => println(v)
    case NoEffect(a) => a
    case ChainEffect(fa, f) =>
      fa match
        case Read                 => f(readLine()).run
        case Write(v)             => f(println(v)).run
        case NoEffect(a)          => f(a).run
        case ChainEffect(fa1, f1) => fa1.flatMap(a => f1(a).flatMap(f)).run
case object Read extends ConsoleEffect[String]
case class Write(v: String) extends ConsoleEffect[Unit]
case class NoEffect[A](a: A) extends ConsoleEffect[A]
case class ChainEffect[A, B](fa: ConsoleEffect[A], f: A => ConsoleEffect[B]) extends ConsoleEffect[B]

def read = Read
def write(v: String) = Write(v)
