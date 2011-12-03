import org.apache.commons.codec.digest.DigestUtils as DU
import collab.todo.Category
import collab.todo.Todo
import collab.todo.User
import collab.todo.Priorities
import collab.todo.Statuses

class BootStrap {

	def pass = DU.md5Hex("pass")

	def init = { servletContext ->
		def person = new Person(username: "user", userRealName: "Joseph Nusairat",
				email: "jnusairat@integrallis.com", description: "Joseph's Account",
				passwd: pass, enabled: true).save()
		def admin = new Person(username: "admin", userRealName: "Administrator",
				email: "cjudd@juddsolutions.com", description: "Our admin", passwd: pass,
				enabled: true).save()
		println "Persons: " + Person.count()
		def adm = new User(userName: "admin", firstName: "Administrator", 
			lastName: "Administrator", email: "cjudd@juddsolutions.com").save()
		def user = new User(userName: "user", firstName: "Joseph", 
			lastName: "Nusairat", email: "jnusairat@integrallis.com").save()
		println "Users: " + User.count()

		def userAuth =
				new Authority(authority:"ROLE_USER", description: "Authenticated User").save()
		def su =
				new Authority(authority:"ROLE_ADMIN", description: "Administrator Role").save()
		println "Authorities: " + Authority.count()
		
		userAuth.addToPeople(person)
		su.addToPeople(admin)

		new Requestmap(url:"/**",configAttribute:"IS_AUTHENTICATED_ANONYMOUSLY").save()
		new Requestmap(url:"/todo/**",configAttribute:"IS_AUTHENTICATED_FULLY").save()
		new Requestmap(url:"/category/**",configAttribute:"IS_AUTHENTICATED_FULLY").save()
		new Requestmap(url:"/user/**",configAttribute:"IS_AUTHENTICATED_FULLY").save()
		new Requestmap(url:"/user/list/**",configAttribute:"ROLE_ADMIN").save()

		def Category cat1 = new Category(name: "cat1", description: "cat1", user: adm).save()
		println "Categories: " + Category.count()

		def todo =	new Todo(
				owner: adm, 
				category: cat1, 
				name: 'Our First Task',
				createdDate: new Date(), 
				priority: Priorities.Low,
				status: Statuses.Incomplete, 
				dueDate: new Date() + 7, 
				lastModifiedDate: new Date(),
				note: 'A note about our __task__.').save()
		println "Todos: " + Todo.count()
	}
	def destroy = {
	}
}
