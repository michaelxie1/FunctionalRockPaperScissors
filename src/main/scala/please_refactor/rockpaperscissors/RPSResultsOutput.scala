package benjamingarrett.rockpaperscissorstools

trait RPSResultsOutput {
  def reportResults(summaries: List[RPSPlayerSeasonSummary])
}

case class RPSPlayerSeasonSummary(playerName: String, seasonRank: Int, points: Int, winRate: Double)