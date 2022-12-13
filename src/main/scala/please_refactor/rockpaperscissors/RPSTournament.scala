package benjamingarrett.rockpaperscissorstools

trait RPSTournament {
  def playTournament(entrants: List[RPSPlayer]): Map[(RPSPlayer,RPSPlayer),List[RPSOutcome]]
}
