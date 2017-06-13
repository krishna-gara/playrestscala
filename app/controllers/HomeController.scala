package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.text.SimpleDateFormat
import java.util.Calendar
import java.io.File
import scala.io.Source
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.Logger

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def results = Action(parse.json) { request =>

    val cal = Calendar.getInstance.getTime
    val format1 = new SimpleDateFormat("yyyy-MM-dd")

    var dateinputtemp = (request.body \ "start_date").as[String]
    var dateinputtemp_end = (request.body \ "end_date").as[String]
    var errorTypetmp = (request.body \ "errorTypetmp").as[String]
    var errorNametmp = (request.body \ "errorNametmp").as[String]

    var start_date = if(dateinputtemp == "") format1.format(cal) else dateinputtemp
    var end_date = if(dateinputtemp_end == "") format1.format(cal) else dateinputtemp_end
    var errorType = if(errorTypetmp == "") "" else errorTypetmp;
    var errorName   = if(errorNametmp == "") "" else errorNametmp;

    println("Hello : " + dateinputtemp + " errorType : " + errorTypetmp + " errName : " + errorNametmp)
    var valueList:List[String] = Nil
    var original_date="";
    for (file <- new File("C://Users/kgara/Desktop/new/").listFiles) {
      //println(file);
      val datepattern = """(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})""".r
      val date_match = """(\d{4})-(\d{2})-(\d{2})""".r

      try {
        var errorStart: Boolean = false;
        for (line <- Source.fromFile(file).getLines()) {//datepattern.
          if(!datepattern.findFirstIn(line).isEmpty){

            var date_match(original_date,text) = line

            println("start : " + original_date + " end " + start_date )
            //println("line contains ----------> "+line.contains(dateinput));println("\n");
            if( (original_date > start_date && original_date < end_date) && line.contains(errorType) && line.contains(errorName) ){
              errorStart = true;
            }else{
              errorStart = false;
            }
            if(errorStart){
              valueList = valueList ::: List(line)
            }
          }
        }
      } catch {
        case ex: Exception => println("Bummer, an exception happened.")
      }
    }

    //if(valueList.count > 0){
      //var status = "success"
    //}

    Ok(Json.obj("status"-> "success","results"->original_date))

  }
}
