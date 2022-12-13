package benjamingarrett.rockpaperscissorstools

trait RPSDecider {
  def beats(firstMove: RPSMove)(secondMove: RPSMove): RPSOutcome
}
