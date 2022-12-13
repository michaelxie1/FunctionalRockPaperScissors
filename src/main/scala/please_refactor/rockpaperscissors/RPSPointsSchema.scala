package benjamingarrett.rockpaperscissorstools

object RPSPointsSchema {
  def pointsAwarded(tournamentRanking: Int): Int =
    tournamentRanking match {
      case 1 => 2000
      case 2 => 1200
      case 3 => 720
      case 4 => 360
      case _ => 0
    }
}
