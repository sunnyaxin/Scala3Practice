package arena3

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
