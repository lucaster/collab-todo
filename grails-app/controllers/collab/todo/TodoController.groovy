package collab.todo

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import collab.todo.*

class TodoController {

	// Inject
	def authenticateService

	def index = { redirect(action:list,params:params) }

	// the delete, save and update actions only accept POST requests
	def allowedMethods = [delete:'POST', save:'POST', update:'POST']

	def beforeInterceptor = [action:this.&beforeAudit,except:['list']]
	def afterInterceptor = [action:{model ->this.&afterAudit(model)},except:['list']]

	def beforeAudit = {
		log.trace("${session?.user?.userName} Start action ${controllerName}Controller.${actionName}() : parameters $params")
	}

	def afterAudit = { model ->
		log.trace("${session?.user?.userName} End action ${controllerName}Controller.${actionName}() : returns $model")
	}

	def list = {
		if(!params.max)params.max = 10
		def user = User.findByUserName(authenticateService.principal().getUsername())
		[ todoList: Todo.findAllByOwner(user, params) ]
	}

	def show = {
		def todo = Todo.get( params.id )
		if (User.findByUserName(authenticateService.principal().getUsername()).userName != todo.owner.userName) {
			flash.message = "You can only display your own Todo"
			redirect(action:list)
			return
		}
		return [ todo : todo ]
	}

	def delete = {
		def todo = Todo.get( params.id )
		if (User.findByUserName(authenticateService.principal().getUsername()).userName != todo.owner.userName) {
			flash.message = "You can only delete your own Todo"
			redirect(action:list)
			return
		}

		if(todo) {
			todo.delete()
			flash.message = "Todo ${params.id} deleted."
			redirect(action:list)
		}
		else {
			flash.message = "Todo not found with id ${params.id}"
			redirect(action:list)
		}
	}

	def edit = {
		def todo = Todo.get( params.id )
		if (User.findByUserName(authenticateService.principal().getUsername()).userName != todo.owner.userName) {
			flash.message = "You can only edit your own Todo"
			redirect(action:list)
			return
		}

		if(!todo) {
			flash.message = "Todo not found with id ${params.id}"
			redirect(action:list)
		}
		else {
			return [ todo : todo ]
		}
	}

	def update = {
		def todo = Todo.get( params.id )
		if (User.findByUserName(authenticateService.principal().getUsername()).userName != todo.owner.userName) {
			flash.message = "You can only update your own Todo"
			redirect(action:list)
			return
		}

		if(todo) {
			todo.properties = params
			todo.lastModifiedDate = new Date()
			uploadFileData(todo)
			if(todo.save()) {
				flash.message = "Todo ${params.id} updated."
				redirect(action:show,id:todo.id)
			}
			else {
				render(view:'edit',model:[todo:todo])
			}
		}
		else {
			flash.message = "Todo not found with id ${params.id}"
			redirect(action:edit,id:params.id)
		}
	}

	def create = {
		def todo = new Todo()
		todo.properties = params
		def owner = User.findByUserName(authenticateService.principal().getUsername())
		todo.owner = owner
		return ['todo':todo]
	}

	def save = {
		def todo = new Todo()
		todo.properties = params
		println params.inspect()
		def owner = User.findByUserName(authenticateService.principal().getUsername())
		todo.owner = owner
		println owner.inspect()
		todo.lastModifiedDate = new Date()
		uploadFileData(todo)
		println todo.fileName
		if(todo.save()) {
			flash.message = "Todo ${todo.id} created."
			//redirect(action:show,id:todo.id)
			render(template:'/common/detail',  var: 'todo', collection:listByOwner())
			//redirect(action: list)
		}
		else {
			//render(view:'create',model:[todo:todo])
			todo.validate()
			//render(view:'list', model:[todo:todo, todoList:listByOwner()])
			//render(template:'/common/detail',  var: 'todo', collection:listByOwner())
			redirect(action: list)
		}
	}

	def uploadFileData = { todo ->
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file =	(CommonsMultipartFile) multiRequest.getFile("associatedFile");
			// Save the object items.
			todo.fileName = file.originalFilename
			todo.contentType = file.contentType
			todo.associatedFile = file.bytes
		}
	}

	def downloadFile = {
		def todo = Todo.get( params.id )
		response.setHeader("Content-disposition", "attachment; filename=${todo.fileName}")
		response.contentType = todo.contentType
		response.outputStream << todo.associatedFile
	}

	/**
	 * This completes the task.
	 * It will save the completed date and re render that section of the page.
	 */
	def completeTask = {
		log.info "Complete Task"
		Todo t = Todo.get( params['id'] )
		// update the date now
		t.completedDate = new Date()
		t.lastModifiedDate = new Date()
		// model is for a specific name .... bean is for "it"
		render(template:'detail', model:[todo:t])
	}

	/**
	 * Used to show thet note information.
	 */
	def showNotes = {
		def note = Todo.executeQuery("select t.note From Todo as t Where t.id = ${params['id']}")
		// why am i having to do an array?
		render note[0]
	}

	/**
	 * Used to remove the task from the page
	 * We won't want to render anything on the page after.
	 */
	def removeTask = {
		Todo t = Todo.get( params['id'] )
		log.info "Delete - $t"
		t.delete();
		// now lets just eliminate our item from the page.
		render ''
	}

	/**
	 * This is used if you want to refresh the list.
	 * via an ajax'ish way
	 */
	private def listByOwner = {
		log.info("List")
		if(!params.max)params.max = 100
		def owner = User.findByUserName(authenticateService.principal().getUsername())
		//Todo.findAllByOwnerOrderByLastModifiedDate(owner, params)
		//Todo.findAllByOwner(owner, params, order: 'lastModifiedDate')
		// this needs to have an order by
		def list = Todo.findAllByOwner(owner, params)
		println list.inspect()
		list
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