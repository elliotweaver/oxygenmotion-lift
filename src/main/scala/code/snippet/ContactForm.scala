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
    val ref = S.referer openOr "none"
    val ref_terms = {
      for {
	      queryString <- S.referer.toList
	      nameVal <- queryString.split("&").toList.map(_.trim).filter(_.length > 0)
	      (name, value) <- nameVal.split("=").toList match {
	        case Nil => Empty
	        case n :: v :: _ => Full((urlDecode(n), urlDecode(v)))
	        case n :: _ => Full((urlDecode(n), ""))
      	  }
	      if name == "q"
      } value.replace("+", " ")
    }
       
    // our process method returns a
    // JsCmd which will be sent back to the browser
    // as part of the response
    
    def process(): JsCmd= {
      
      // sleep for 400 millis to allow the user to
      // see the spinning icon
      Thread.sleep(400)
      
      var valid = true;
       
      // do the matching
      println("NAME: "+name)
      if (name.length == 0 || name == "Name*") {
        valid = false; S.error("Name: Please enter a name");
      } 
      if (email.length == 0 || email == "Email*") {
    	valid = false; S.error("Email: Please enter an email");
      }
      if (description.length == 0 || description == "Short Description*") {
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
    <ul>
      <h2>Search</h2>
      <li><strong>Referer: </strong>{ref}</li>
      <li><strong>Search Terms: </strong>{ref_terms}</li>
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
	      From("admin@oxygenproductions.com"),
	      Subject("Motion Website Quote: "+name),
	      To("janet@oxygenproductions.com"),
	      CC("ron@oxygenproductions.com"),
	      htmlBackend)
	      
	    Mailer.sendMail(
	      From("admin@oxygenproductions.com"),
	      Subject("Motion Website Quote: "+name),
	      To("patrick@oxygenproductions.com"),
	      htmlBackend)
	      
	    Mailer.sendMail(
	      From("admin@oxygenproductions.com"),
	      Subject("Motion Website Quote: "+name),
	      To("elliot@oxygenproductions.com"),
	      htmlBackend)
	      
	    Mailer.sendMail(
	      From("admin@oxygenproductions.com"),
	      Subject("Thank you for your request!"),
	      To(email),
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
        SetValById("contact_budget", budget) &
        SetHtml("adw-conversion", <noscript><div style="display:inline;"><img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/1071512688/?value=0&amp;label=hpzaCOLzugMQ8Pj3_gM&amp;guid=ON&amp;script=0"/></div></noscript>) &
        JsRaw("""
var google_conversion_id = 1071512688;
var google_conversion_language = "en";
var google_conversion_format = "2";
var google_conversion_color = "ffffff";
var google_conversion_label = "hpzaCOLzugMQ8Pj3_gM";
var google_conversion_value = 0;
            """).cmd
         
        
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