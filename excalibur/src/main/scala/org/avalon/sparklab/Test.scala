package org.avalon.sparklab

object Test {

  def main(args: Array[String]): Unit = {
    val someNumbers = List(-11, -10, -5, 0, 5, 10)

    someNumbers.foreach(f => println(f))

    println(".....................")

    def gameResults(): Seq[(String, Int)] =
      ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil
    def hallOfFame = for {
      result <- gameResults()
      (name, score) = result
      if (score > 5000)
    } yield name

    println(hallOfFame)

    val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil
    for {
      list @ head :: _ <- lists
    } yield list.size

    println(lists)

    println(".....................")

    val songTitles = List("The White Hare", "Childe the Hunter", "Take no Rogues")

    songTitles.map(t => t.toLowerCase)

    songTitles.map(_.toLowerCase()).foreach(x => println(x))

    val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) :: ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil
   /* def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
      wordFrequencies.filter(wf => wf._2 > 3 && wf._2 < 25).map(_._1)*/
    

    def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
      wordFrequencies.filter { case (_, f) => f > 3 && f < 25 } map {
        case (w, _) => w
      }
      
    wordsWithoutOutliers(wordFrequencies).foreach(x => println(x)) // List("habitual", "homely", "society")

    println(".....................")

  }

  case class User(firstName: String, lastName: String, score: Int)

  def advance(xs: List[User]) = xs match {
    case User(_, _, score1) :: User(_, _, score2) :: _ => score1 - score2
    case _ => 0
  }

}