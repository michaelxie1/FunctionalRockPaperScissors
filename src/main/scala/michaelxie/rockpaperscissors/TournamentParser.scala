package michaelxie.rockpaperscissors

import fpinscala.parsing.JSON.{JArray, JNumber, JObject, JString}
import fpinscala.parsing.{JSON, Location, ParseError}

import scala.::
import scala.collection.IterableOnce.iterableOnceExtensionMethods

object TournamentParser {
  /*
  * {
    "Company name" : "Microsoft Corporation",
    "Ticker"  : "MSFT",
    "Active"  : true,
    "Price"   : 30.66,
    "Shares outstanding" : 8.38e9,
    "Related companies" : [ "HPQ", "IBM", "YHOO", "DELL", "GOOG" ]
  }
  * case class SampleDTO(
      companyName: String,
      ticker: String,
      isActive: Boolean,
      price: Double,
      sharesOutstanding: Double,
      relatedCompanies: List[String])
  *
  * */

  val jsonTxt =
"""
      {"tournaments": 1000}
      """
   /* ""//json objects keys are always strings
       {
      "tournaments": 1000,
      "roundsPerMatch": 1000,
      "randomSeed": 12345,
      "players": [
        {
          "name": "Last Losing 1",
          "type": "LastLosingMovePlayer"
        },
        {
          "name": "Last Losing 2",
          "type": "LastLosingMovePlayer",
        },
        {
          "name": "Last Winning 1",
          "type": "LastWinningMovePlayer",
        },
        {
          "name": "Majority Losing 1",
          "type": "MajorityLosingMovePlayer"
        },
        {
          "name": "Majority Winning 1",
          "type": "MajorityWinningMovePlayer"
        },
        {
          "name": "Unbiased Random 1",
          "type": "RandomMovePlayer"
        },
        {
          "name": "Biased Random 1",
          "type": "BiasedRandomMovePlayer",
          "weights": {
            "rock": 0.5,
            "paper": 0.25,
            "scissors": 0.25
          }
        },
        {
          "name": "Biased Random 2",
          "type": "BiasedRandomMovePlayer",
          "weights": {
            "rock": 0.8,
            "paper": 0.1,
            "scissors": 0.1
          }
        }
      ]
    }

    """*/

  def go: Unit = {
    val P = fpinscala.parsing.Reference
    import fpinscala.parsing.ReferenceTypes.Parser
    val json: Parser[JSON] = JSON.jsonParser(P)
    val resultOfParsing = P.run(json)(jsonTxt) // this parses JSON input into a JSON object
    //resultOfParsing.flatMap(j => unpack(j)).map(dto => println(dto)).map(_ => ())
    resultOfParsing.flatMap(j => unpack(j)).map(dto => println(dto)).map(_ => ())
    //resultOfParsing.flatMap(j => betterUnpackUsingFlatMap(j)).map(dto => println(dto)).map(_ => ())
  }
  //paste the JSON File for now with triple quotes
  case class SampleTournament(
      tournaments: Int /*,
      roundsPerMatch: Int,
      randomSeed: Int,
      players: List[String]*/)
/*
  def unpack(json: JSON): Either[ParseError,SampleTournament] = {
    val res = json match {
      case jObject: JObject =>
        for {
          tournaments <- jObject.get("tournament") match {
            case jNumber: JNumber => Right(jNumber.get)
            case _ => Left(ParseError(List((Location("Could not unpack tournament"),"tournament"))))
          }
          roundsPerMatch <- jObject.get("roundsPerMatch") match {
            case jString: JString => Right(jString.get)
            case _ => Left(ParseError(List((Location("Could not unpack rounds per match"),"roundsPerMatch"))))
          }
          randomSeed <- jObject.get("randomSeed") match {
            case jInt: JNumber => Right(jInt.get)
            case _ => Left(ParseError(List((Location("Could not unpack random seed"),"randomSeed"))))
          }
          players <- jObject.get("players") match {
            case jArray: JArray => Right(jArray.get)
            case _ => Left(ParseError(List((Location("Could not unpack related"),"players"))))
          }
          players <- unpackList(players.toList, Right(List.empty))
        } yield SampleTournament(tournaments, roundsPerMatch, randomSeed, players)
      case _ => Left(ParseError(List((Location("Could not unpack JSON contents"),"Could not unpack JSON contents"))))
    }
    res
  }
*/
  def unpackList(c: List[JSON], r: Either[ParseError,List[String]]): Either[ParseError,List[String]] =
    c match {
      case ::(head, next) => head match {
        case JString(v) => unpackList(next, r.flatMap(list => Right(v :: list)))
        case p: ParseError => Left(p)
      }
      case Nil => r
    }

  def unpack(json: JSON): Either[ParseError,SampleTournament] =
    json match {
      case jObject: JObject =>
        for {
          tournaments <- unpackNumber(jObject, "tournaments")
         /* roundsPerMatch <- unpackNumber(jObject, "Rounds Per Match")
          randomSeed <- unpackNumber(jObject, "Random Seed")
          players <- unpackArray(jObject, "Players")*/
        } yield SampleTournament(tournaments /*, roundsPerMatch, randomSeed, players*/)
      case _ => Left(ParseError(List((Location("Could not unpack JSON contents"),"Could not unpack JSON contents"))))
    }
/*
  def betterUnpackUsingFlatMap(json: JSON): Either[ParseError, SampleTournament] = {
    json match {
      case jObject: JObject =>
        unpackString(jObject, "Company name")
          .flatMap(companyName => unpackString(jObject, "Ticker")
          //.flatMap(ticker => unpackBoolean(jObject, "Active")
          .flatMap(isActive => unpackNumber(jObject, "Price")
          .flatMap(price => unpackNumber(jObject, "Shares outstanding")
          .flatMap(shares => unpackArray(jObject, "Related companies")
          .map(related => SampleTournament(companyName, isActive, price, shares, related))))))//)
      case _ => Left(ParseError(List((Location("Could not unpack JSON contents"), "Could not unpack JSON contents"))))
    }
  }
*/
  def unpackString(jObject: JObject, key: String): Either[ParseError,String] = jObject.get(key) match {
    case jString: JString => Right(jString.get)
    case _ => Left(ParseError(List((Location("Could not unpack ticker"), "ticker"))))
  }

  /*def unpackBoolean(jObject: JObject, key: String): Either[ParseError, Boolean] = jObject.get(key) match {
    case jBool: JBool => Right(jBool.get)
    case _ => Left(ParseError(List((Location("Could not unpack ticker"), "ticker"))))
  }*/

  def unpackNumber(jObject: JObject, key: String): Either[ParseError, Int] = jObject.get(key) match {
    case jNumber: JNumber => Right(jNumber.get.toInt)
    case _ => Left(ParseError(List((Location("Could not unpack number"), "ticker"))))
  }

  def unpackArray(jObject: JObject, key: String): Either[ParseError, List[String]] = {
    for {
      relatedPacked <- jObject.get(key) match {
        case jArray: JArray => Right(jArray.get)
        case _ => Left(ParseError(List((Location("Could not unpack players"), "players"))))
      }
      related <- unpackList(relatedPacked.toList, Right(List.empty))
    } yield related
  }

  
}
