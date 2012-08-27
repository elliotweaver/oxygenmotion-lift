package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._

import code.db._
import code.model._

import auth._

import com.mongodb.{Mongo, MongoOptions, ServerAddress}
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable{
  def boot {
	
    // where to search snippet
    LiftRules.addToPackages("code")
    
	val srvr = new ServerAddress("127.0.0.1", 27017)
	val mo = new MongoOptions
	mo.socketTimeout = 10
	MongoDB.defineDb(DefaultMongoIdentifier, new Mongo(srvr, mo), "oxygenmotion")
	
	val roles = AuthRole("admin", "user")
	
    LiftRules.httpAuthProtectedResource.prepend {
       case (Req("user" :: Nil, _, _)) => Full(AuthRole("admin"))
     }

     LiftRules.authentication = HttpBasicAuthentication("Oxygen Motion") {
       case ("oxygen", "0xygenPro", req) => {
         logger.info("You are now authenticated !")
         userRoles(AuthRole("admin"))
         true
       }
     }

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu
      Menu.i("Traveler") / "traveler", // the simple way to declare a menu
      Menu.i("User") / "user", // the simple way to declare a menu

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Static Content")))
	  
    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    // Use jQuery 1.4
    LiftRules.jsArtifacts = net.liftweb.http.js.jquery.JQuery14Artifacts

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    // Use HTML5 for rendering
	LiftRules.htmlProperties.default.set((r: Req) =>
	  new Html5Properties(r.userAgent))  
	  
	//LiftRules.noticesAutoFadeOut.default.set((noticeType: NoticeType.Value) => Full((1 seconds, 2 seconds)))
  }
}
