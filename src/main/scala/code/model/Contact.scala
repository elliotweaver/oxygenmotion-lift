package code
package model

import net.liftweb.mongodb.record._ 
import net.liftweb.mongodb.record.field._ 
import net.liftweb.record.field._ 
import net.liftweb.record._ 

class Contact private () extends MongoRecord[Contact] with ObjectIdPk[Contact] {
	def meta = Contact
	
	object name extends StringField(this, "")
	object email extends EmailField(this, 100)
	object url extends StringField(this, "")
	object description extends TextareaField(this, 200)
	object company extends StringField(this, "")
	object phone extends StringField(this, "")
	object deadline extends StringField(this, "")
	object budget extends StringField(this, "")
	object project extends StringField(this, "")

}
object Contact extends Contact with MongoMetaRecord[Contact]