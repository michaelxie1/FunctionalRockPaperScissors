package benjamingarrett.rockpaperscissorstools

trait RPSMatch {
  def playMatch(rounds: Int)(firstPlayer: RPSPlayer)(secondPlayer: RPSPlayer): List[RPSOutcome]
}
