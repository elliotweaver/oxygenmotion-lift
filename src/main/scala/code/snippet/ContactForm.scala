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

import code.model._

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
    val budgetMap = List( ("-none-", "- Budget* -"),
    					("8k-10k", "8k-10k"),
    					("10k-25k", "10k-25k"),
    					("25k-50k", "25k-50k"),
    					("50k-100k", "50k-100k"),
    					("100k+", "100k+") )
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

      if (valid == true) {
        
	    Contact.createRecord
	        	.name(name)
	        	.email(email)
	        	.url(url)
	        	.description(description)
	        	.company(company)
	        	.phone(phone)
	        	.deadline(deadline)
	        	.budget(budget)
	        	.save
        
        val htmlBackend = <html>
   <head>
     <title>Motion Website Quote: {name}</title>
   </head>
   <body>
    <h1>Motion Website Quote: {name}</h1>
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
	      Subject("Motion Website Quote: "+name),
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

        SetValById("contact_name", name) & 
        SetValById("contact_email", email) &
        SetValById("contact_url", url) &
        SetValById("contact_description", description) &
        SetValById("contact_company", company) &
        SetValById("contact_phone", phone) &
        SetValById("contact_deadline", deadline) &
        SetValById("contact_budget", budget)
        
      }
      
    }
	
    
    // binding looks normal
    "name=name" #> SHtml.text(name, name = _) &
    "name=email" #> SHtml.text(email, email = _) &
    "name=urllink" #> SHtml.text(url, url = _) &
    "name=description" #> SHtml.textarea(description, description = _, "cols" -> "10", "rows" -> "5") &
    "name=company" #> SHtml.text(company, company = _) &
    "name=phone" #> SHtml.text(phone, phone = _) &
    "name=deadline" #> SHtml.text(deadline, deadline = _) &
    "name=budget" #> (SHtml.select(budgetMap, Empty, budget = _)  ++ SHtml.hidden(process))
  }
  
}