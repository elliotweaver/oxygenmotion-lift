package code
package snippet

import net.liftweb._
import http._
import common._
import util.Helpers._
import js._
import JsCmds._
import JE._
import scala.xml.NodeSeq
import util.Mailer
import Mailer._

class ContactForm {
 
  def render = {
    // state
    var name = ""
    var email = ""
    var url = ""
    var description = ""
    var company = ""
    var phone = ""
    var deadline = ""
    var budget = ""
    var project = ""
    val budgetMap = List( ("-none-", "- Budget* -"),
    					("8k-10k", "8k-10k"),
    					("10k-25k", "10k-25k"),
    					("25k-50k", "25k-50k"),
    					("50k-100k", "50k-100k"),
    					("100k+", "100k+") )
    val projectMap = List( ("-none-", "- Project Type* -"),
    					("Motion Graphics", "Motion Graphics"),
    					("Flash Presentations", "Flash Presentations"),
    					("Video Production", "Video Production"),
    					("Web Development", "Web Development"),
    					("Web Design", "Web Design") )
    val whence = S.referer openOr "/"
    
    // our process method returns a
    // JsCmd which will be sent back to the browser
    // as part of the response
    def process(): JsCmd= {
      
      // sleep for 400 millis to allow the user to
      // see the spinning icon
      Thread.sleep(400)
      
      var valid = true;
       
      // do the matching
      if (name.length == 0) {
        valid = false; S.error("Name: Please enter a name");
      } 
      if (email.length== 0) {
    	valid = false; S.error("Email: Please enter an email");
      }
      if (description.length == 0) {
    	valid = false; S.error("Description: Please enter a short description");
      }
      if (budget == "-none-"){
    	valid = false; S.error("Budget: Please select a budget");
      }
      if (project == "-none-") {
    	valid = false; S.error("Project Type: Please select a project type");
      }

      if (valid == true) {
        
        val htmlBackend = <html>
   <head>
     <title>Website Quote: {project}</title>
   </head>
   <body>
    <h1>Website Quote: {project}</h1>
    <ul>
      <h2>Contact Details</h2>
      <li><strong>Name: </strong>{name}</li>
      <li><strong>Email: </strong>{email}</li>
      <li><strong>URL: </strong>{url}</li>
      <li><strong>Company: </strong>{company}</li>
      <li><strong>Phone: </strong>{phone}</li>
    </ul>
    <ul>
      <h2>Project Details</h2>
      <li><strong>Projects Type: </strong>{project}</li>
      <li><strong>Budget: </strong>{budget}</li>
      <li><strong>Deadline: </strong>{deadline}</li>
      <li><strong>Description: </strong>{description}</li>
    </ul>
   </body>
  </html>
      
      val htmlUser = <html>
   <head>
     <title>Thank you for your request!</title>
   </head>
   <body>
    <h1>Thank you for your request!</h1>
    <p>We have recieved your request and we will be in touch with you shortly.</p>
   </body>
  </html>

        Mailer.sendMail(
	      From("elliot@oxygenproductions.com"),
	      Subject("Website Quote: "+project),
	      To("elliot.weaver@gmail.com"),
	      htmlBackend)
	      
	    Mailer.sendMail(
	      From("elliot@oxygenproductions.com"),
	      Subject("Thank you for your request!"),
	      To("elliot.weaver@gmail.com"),
	      htmlUser)
	    
        S.notice("Thank You! We'll be in contact with you shortly.");

        name = ""
	    email = ""
	    url = ""
	    description = ""
	    company = ""
	    phone = ""
	    deadline = ""
	    budget = "-none-"
	    project = "-none-"

        SetValById("contact_name", name) & 
        SetValById("contact_email", email) &
        SetValById("contact_url", url) &
        SetValById("contact_description", description) &
        SetValById("contact_company", company) &
        SetValById("contact_phone", phone) &
        SetValById("contact_deadline", deadline) &
        SetValById("contact_budget", budget) &
        SetValById("contact_project", project)
        
      }
      
    }
	
    
    // binding looks normal
    "name=name" #> SHtml.text(name, name = _) &
    "name=email" #> SHtml.text(email, email = _) &
    "name=url" #> SHtml.text(url, url = _) &
    "name=description" #> SHtml.textarea(description, description = _, "cols" -> "10", "rows" -> "5") &
    "name=company" #> SHtml.text(company, company = _) &
    "name=phone" #> SHtml.text(phone, phone = _) &
    "name=deadline" #> SHtml.text(deadline, deadline = _) &
    "name=budget" #> SHtml.select(budgetMap, Empty, budget = _) &
    "name=project" #> (SHtml.select(projectMap, Empty, project = _, "id" -> "contact_project") ++ SHtml.hidden(process))
  }
  
}