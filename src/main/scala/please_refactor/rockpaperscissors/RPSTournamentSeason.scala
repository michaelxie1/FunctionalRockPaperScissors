package benjamingarrett.rockpaperscissorstools

import benjamingarrett.rockpaperscissorstools.RPSTournamentSeason.{RPSGamesPlayed, RPSPoints, RPSWinRate}

trait RPSTournamentSeason {
  def handleTournamentSeason(players: List[RPSPlayer])(tournaments: List[RPSTournament]): Map[RPSPlayer,(RPSPoints,RPSGamesPlayed,RPSWinRate)]
}

object RPSTournamentSeason {
  type RPSPoints = Int
  type RPSGamesPlayed = Int
  type RPSWinRate = Double
}
