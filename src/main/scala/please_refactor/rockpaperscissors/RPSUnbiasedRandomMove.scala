package benjamingarrett.rockpaperscissorstools

trait RPSUnbiasedRandomMove {
  def unbiasedRandomMove: (RPSMove, RPSUnbiasedRandomMove)
  def nextDouble: (Double, RPSUnbiasedRandomMove)
}

object RPSUnbiasedRandomMove {
  case class SimpleRPSUnbiasedRandomMove(seed: Long) extends RPSUnbiasedRandomMove {
    override def unbiasedRandomMove: (RPSMove, RPSUnbiasedRandomMove) = {
      val (n, nextRNG) = nonNegativeLessThan(3)(this)
      (n match {
        case 0 => Rock
        case 1 => Paper
        case 2 => Scissors
      }, nextRNG)
    }

    def nextDouble: (Double, RPSUnbiasedRandomMove) = {
      val (i, r) = nonNegativeInt(this)
      (i / (Int.MaxValue.toDouble + 1), r)
    }

    private def nextInt: (Int, SimpleRPSUnbiasedRandomMove) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
      val nextRNG = SimpleRPSUnbiasedRandomMove(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRNG)
    }

    private def nonNegativeInt(rng: SimpleRPSUnbiasedRandomMove): (Int, SimpleRPSUnbiasedRandomMove) = {
      val (i, r) = rng.nextInt
      (if (i < 0) -(i + 1) else i, r)
    }

    private def nonNegativeLessThan(n: Int): RandMove[Int] =
      flatMap(nonNegativeInt) { (i: Int) =>
        val mod = i % n
        if (i + (n - 1) - mod >= 0) unit(mod) else nonNegativeLessThan(n)
      }
  }

  type RandMove[+A] = SimpleRPSUnbiasedRandomMove => (A, SimpleRPSUnbiasedRandomMove)

  def unit[A](a: A): RandMove[A] = rng => (a, rng)

  def flatMap[A, B](f: RandMove[A])(g: A => RandMove[B]): RandMove[B] =
    rng => {
      val (a, r1) = f(rng)
      g(a)(r1) // We pass the new state along
    }
}

