package com.hacklanta.mandrill

import net.liftweb._
  import common._
  import util.Helpers._
  import json._
    import JsonDSL._
    import Extraction._
  import util._

import dispatch._, Defaults._
import com.ning.http.client.{Request, RequestBuilder, Response}

case class MandrillTo(email: String, name: Option[String] = None, `type`: String = "to")
case class MandrillMessage(
  subject: String,
  from_email: String,
  to: List[MandrillTo],
  from_name: Option[String] = None,
  html: Option[String] = None,
  text: Option[String] = None
)

trait MandrillApiCall {
  def uri(requestBuilder: dispatch.Req): dispatch.Req
}
case class SendMandrillMessage(message: MandrillMessage, async: Boolean = false) extends MandrillApiCall {
  def uri(requestBuilder: dispatch.Req) = requestBuilder / "messages" / "send.json"
}
case class SendTemplateMandrillMessage(message: MandrillMessage, template_name: String, template_content: List[Map[String, String]], async: Boolean = false) extends MandrillApiCall {
  def uri(requestBuilder: dispatch.Req) = requestBuilder / "messages" / "send-template.json"
}

object Mandrill extends Loggable {
  implicit val formats = DefaultFormats

  private val key = Box.legacyNullTest(System.getenv("MANDRILL_KEY")) or
                    Box.legacyNullTest(System.getProperty("mandrill.apiKey")) or
                    Props.get("mandrill.apiKey") openOr ""
  private val endpointHost = Props.get("mandrill.endpointHost") openOr "mandrillapp.com"
  private val endpointVersion = Props.get("mandrill.endpointVersion") openOr "1.0"

  case class CodeResponse(code: Int)
  protected object AsCodeResponse extends (Response => CodeResponse) {
    def apply(r:Response) = {
      CodeResponse(
        r.getStatusCode()
      )
    }
  }

  case class MandrillResponse(status: String)

  def run(apiCall: MandrillApiCall) = {
    val postJson = decompose(apiCall) match {
      case obj:JObject =>
        obj ~
        ("key" -> key)

      case _ =>
        JObject(Nil)
    }

    val requestBody = compact(render(postJson))
    val request = (apiCall.uri(host(endpointHost) / "api" / endpointVersion)).secure <<
      requestBody

    val response = Http(request > AsCodeResponse).either

    response() match {
      case Right(CodeResponse(200)) => // All good.
      case Right(CodeResponse(code)) => logger.error("Mandrill returned code: " + code)
      case Left(dispatchError) => logger.error("Dispatch error: " + dispatchError)
    }
  }
}
