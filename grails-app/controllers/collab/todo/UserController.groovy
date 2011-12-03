package collab.todo

import groovy.text.SimpleTemplateEngine
import org.springframework.mail.MailException


class UserController {
	
	EMailAuthenticatedService eMailAuthenticatedService
	
	// Inject
	def authenticateService
	
	def index = { redirect(action:list,params:params) }

	// the delete, save and update actions only accept POST requests
	def allowedMethods = [delete:'POST', save:'POST', update:'POST']

	def list = {
		if(!params.max)params.max = 10
		[ userList: User.list( params ) ]
	}

	def show = {
		[ user : User.get( params.id ) ]
	}

	def delete = {

		//        if (session.user?.id as String != params.id) {
		//    		flash.message = "You can only delete yourself"
		//    		redirect(action:list)
		//    		return
		//        }

		def user = User.get( params.id )

		if(user) {
			user.delete()
			flash.message = "User ${params.id} deleted."
			redirect(action:list)
		}
		else {
			flash.message = "User not found with id ${params.id}"
			redirect(action:list)
		}
	}

	def edit = {
		//        if (session.user?.id as String != params.id) {
		//    		flash.message = "You can only edit yourself"
		//    		redirect(action:list)
		//    		return
		//        }

		def user = User.get( params.id )

		if(!user) {
			flash.message = "User not found with id ${params.id}"
			redirect(action:list)
		}
		else {
			return [ user : user ]
		}
	}

	def update = {

		//        if (params.id != session.user?.id as String ) {
		//    		flash.message = "You can only update yourself"
		//    		redirect(action:list)
		//    		return
		//        }

		def user = User.get( params.id )

		if(user) {
			user.properties = params
			if(user.save()) {
				flash.message = "User ${params.id} updated."
				redirect(action:show,id:user.id)
			}
			else {
				render(view:'edit',model:[user:user])
			}
		}
		else {
			flash.message = "User not found with id ${params.id}"
			redirect(action:edit,id:params.id)
		}
	}

	def create = {
		def user = new User()
		user.properties = params
		return ['user':user]
	}

	def save = {
		def user = new User()
		user.properties = params
		if(user.save()) {
			flash.message = "user.saved.message"
			flash.args = [
				user.firstName,
				user.lastName
			]
			flash.defaultMsg = "User Saved"
			// send a confirmation email
			sendAcknowledgment (user)
			redirect(action:show,id:user.id)
		}
		else {
			render(view:'create',model:[user:user])
		}
	}

	def login = {
	}

	def handleLogin = {
		//def user = User.findByUserName(params.userName)
		def user = User.findByUserName(authenticateService.principal().getUsername())
		if (!user) {
			flash.message = "User not found for userName: ${params.userName}"
			redirect(action:'login')
			return
		} else {
			session.user = user
			redirect(controller:'todo')
		}
	}

	def logout = {
		if(session.user) {
			session.user = null
			redirect(action:'login')
		}
	}

	private sendAcknowledgment = { user ->
		// Let's first design the email that we want to send
		def emailTpl = this.class.classloader.getResource("web-app/WEB-INF/templates/registrationEmail.gtpl")
		def binding = ["user": user]
		def engine = new SimpleTemplateEngine()
		def template = engine.createTemplate(emailTpl).make(binding)
		def body = template.toString()
		// Set up the email to send.
		def email = [
					to: [user.email],
					subject: "Your Collab-Todo Report",
					text: body
				]
		try {
			// Check if we "need" attachments
			eMailAuthenticatedService.sendEmail(email, [])
		} catch (MailException ex) {
			log.error("Failed to send emails", ex)
			return false
		}
		true
	}

	/**
	 * Used to retrieve user Todo for reports
	 * We might extend it to take some params
	 */
	def userTodo = {
		//def user = User.get(session.user.id)
		def user = User.findByUserName(authenticateService.principal().getUsername())
		return Todo.findAllByOwner(user)
	}

}