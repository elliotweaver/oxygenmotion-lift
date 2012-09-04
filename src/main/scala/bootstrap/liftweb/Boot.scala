package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._

import code.model._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable{
  def boot {
	
    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    val entries = List(
      //Menu.i("Home") / "index" >> User.AddUserMenusAfter, // Simple menu form
      Menu.i("Home") / "index"
      //Menu.i("Admin") / "admin" / "index" >> If(() => User.loggedIn_?, "You must be logged in")
      //Menu.i("Admin") / "admin" / "index" >> LocGroup("TopBarGroup")
    )
	  
    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))
    
    //LiftRules.setSiteMapFunc(() => User.sitemapMutator(SiteMap(entries:_*)))

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
    
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)
    
    // Use HTML5 for rendering
	LiftRules.htmlProperties.default.set((r: Req) =>
	  new Html5Properties(r.userAgent))  
	  
	//LiftRules.noticesAutoFadeOut.default.set((noticeType: NoticeType.Value) => Full((1 seconds, 2 seconds)))
	  
	MongoConfig.init
	
  }
}
