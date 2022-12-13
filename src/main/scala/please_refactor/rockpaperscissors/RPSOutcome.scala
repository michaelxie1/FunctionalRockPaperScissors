package benjamingarrett.rockpaperscissorstools

sealed trait RPSOutcome
case object AWins extends RPSOutcome
case object BWins extends RPSOutcome
case object Tie extends RPSOutcome
