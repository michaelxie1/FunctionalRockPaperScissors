package benjamingarrett.rockpaperscissorstools

sealed trait RPSPlayer {
  val playerInfo: String
}

trait RPSRandomPlayer extends RPSPlayer {
  def playMove(random: RPSUnbiasedRandomMove): (RPSMove, RPSUnbiasedRandomMove)
}
trait RPSHistoryBasedPlayer extends RPSPlayer {
  def playMove(history: List[(RPSMove,RPSOutcome)]): RPSMove
}

trait RPSHistoryBasedRandomPlayer extends RPSPlayer {
  def playMove(history: List[(RPSMove, RPSOutcome)], random: RPSUnbiasedRandomMove): (RPSMove, RPSUnbiasedRandomMove)
}