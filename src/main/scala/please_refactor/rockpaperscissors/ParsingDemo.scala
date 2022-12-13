/*import fpinscala.parsing.JSON.{JArray, JBool, JNumber, JObject, JString}
import fpinscala.parsing.{JSON, Location, ParseError}

import scala.::
import scala.collection.IterableOnce.iterableOnceExtensionMethods

object ParsingDemo {

  val jsonTxt =
    """
{
  "Company name" : "Microsoft Corporation",
  "Ticker"  : "MSFT",
  "Active"  : true,
  "Price"   : 30.66,
  "Shares outstanding" : 8.38e9,
  "Related companies" : [ "HPQ", "IBM", "YHOO", "DELL", "GOOG" ]
}
"""

  def go = {
    val P = fpinscala.parsing.Reference
    import fpinscala.parsing.ReferenceTypes.Parser
    val json: Parser[JSON] = JSON.jsonParser(P)
    val resultOfParsing = P.run(json)(jsonTxt) // this parses JSON input into a JSON object
    resultOfParsing.flatMap(j => unpack(j)).map(dto => println(dto)).map(_ => ())
    resultOfParsing.flatMap(j => betterUnpackUsingForComprehension(j)).map(dto => println(dto)).map(_ => ())
    resultOfParsing.flatMap(j => betterUnpackUsingFlatMap(j)).map(dto => println(dto)).map(_ => ())
  }

  case class SampleDTO(
      companyName: String,
      ticker: String,
      isActive: Boolean,
      price: Double,
      sharesOutstanding: Double,
      relatedCompanies: List[String])

  def unpack(json: JSON): Either[ParseError,SampleDTO] = {
    val res = json match {
      case jObject: JObject =>
        for {
          companyName <- jObject.get("Company name") match {
            case jString: JString => Right(jString.get)
            case _ => Left(ParseError(List((Location("Could not unpack companyName"),"companyName"))))
          }
          ticker <- jObject.get("Ticker") match {
            case jString: JString => Right(jString.get)
            case _ => Left(ParseError(List((Location("Could not unpack ticker"),"ticker"))))
          }
          isActive <- jObject.get("Active") match {
            case jBool: JBool => Right(jBool.get)
            case _ => Left(ParseError(List((Location("Could not unpack isActive"),"isActive"))))
          }
          price <- jObject.get("Price") match {
            case jNumber: JNumber => Right(jNumber.get)
            case _ => Left(ParseError(List((Location("Could not unpack price"),"price"))))
          }
          shares <- jObject.get("Shares outstanding") match {
            case jNumber: JNumber => Right(jNumber.get)
            case _ => Left(ParseError(List((Location("Could not unpack shares"),"shares"))))
          }
          relatedPacked <- jObject.get("Related companies") match {
            case jArray: JArray => Right(jArray.get)
            case _ => Left(ParseError(List((Location("Could not unpack related"),"related"))))
          }
          related <- unpackList(relatedPacked.toList, Right(List.empty))
        } yield SampleDTO(companyName,ticker,isActive,price,shares,related)
      case _ => Left(ParseError(List((Location("Could not unpack JSON contents"),"Could not unpack JSON contents"))))
    }
    res
  }

  def unpackList(c: List[JSON], r: Either[ParseError,List[String]]): Either[ParseError,List[String]] =
    c match {
      case ::(head, next) => head match {
        case JString(v) => unpackList(next, r.flatMap(list => Right(v :: list)))
        case p: ParseError => Left(p)
      }
      case Nil => r
    }

  def betterUnpackUsingForComprehension(json: JSON): Either[ParseError,SampleDTO] =
    json match {
      case jObject: JObject =>
        for {
          companyName <- unpackString(jObject, "Company name")
          ticker <- unpackString(jObject, "Ticker")
          isActive <- unpackBoolean(jObject, "Active")
          price <- unpackNumber(jObject, "Price")
          shares <- unpackNumber(jObject, "Shares outstanding")
          related <- unpackArray(jObject, "Related companies")
        } yield SampleDTO(companyName, ticker, isActive, price, shares, related)
      case _ => Left(ParseError(List((Location("Could not unpack JSON contents"),"Could not unpack JSON contents"))))
    }

  def betterUnpackUsingFlatMap(json: JSON): Either[ParseError, SampleDTO] = {
    json match {
      case jObject: JObject =>
        unpackString(jObject, "Company name")
          .flatMap(companyName => unpackString(jObject, "Ticker")
          .flatMap(ticker => unpackBoolean(jObject, "Active")
          .flatMap(isActive => unpackNumber(jObject, "Price")
          .flatMap(price => unpackNumber(jObject, "Shares outstanding")
          .flatMap(shares => unpackArray(jObject, "Related companies")
          .map(related => SampleDTO(companyName, ticker, isActive, price, shares, related)))))))
      case _ => Left(ParseError(List((Location("Could not unpack JSON contents"), "Could not unpack JSON contents"))))
    }
  }

  def unpackString(jObject: JObject, key: String): Either[ParseError,String] = jObject.get(key) match {
    case jString: JString => Right(jString.get)
    case _ => Left(ParseError(List((Location("Could not unpack ticker"), "ticker"))))
  }

  def unpackBoolean(jObject: JObject, key: String): Either[ParseError, Boolean] = jObject.get(key) match {
    case jBool: JBool => Right(jBool.get)
    case _ => Left(ParseError(List((Location("Could not unpack ticker"), "ticker"))))
  }

  def unpackNumber(jObject: JObject, key: String): Either[ParseError, Double] = jObject.get(key) match {
    case jNumber: JNumber => Right(jNumber.get)
    case _ => Left(ParseError(List((Location("Could not unpack ticker"), "ticker"))))
  }

  def unpackArray(jObject: JObject, key: String): Either[ParseError, List[String]] = {
    for {
      relatedPacked <- jObject.get(key) match {
        case jArray: JArray => Right(jArray.get)
        case _ => Left(ParseError(List((Location("Could not unpack related"), "related"))))
      }
      related <- unpackList(relatedPacked.toList, Right(List.empty))
    } yield related
  }
}
*/