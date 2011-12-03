import collab.todo.User

class PutUserInSessionFilters {
	def filters = {


		interceptAcegiLoginAndPutUserInSession(uri: '/collab-todo/j_spring_security_check') {
			after = {
				println "j_username = " + params.j_username
				def u = User.getByUserName(params?.j_username) // sperando che j_username è ancora lì...
				println "filter" + u?.inspect()
				if (u==null) println "USERNULL!!"
				if (u) {
					session.user = u
				}
			}
		}
		
		
	}
}
