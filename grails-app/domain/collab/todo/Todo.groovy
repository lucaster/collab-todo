package collab.todo

import collab.todo.User
import collab.todo.Category

class Todo {

	static searchable = true

	static belongsTo = [User, Category]

	User owner
	String name
	String note
	Date createdDate
	Date dueDate
	Date completedDate
	Date lastModifiedDate
	//String priority
	Priorities priority
	//String status
	Statuses status
	Category category

	byte[] associatedFile
	String fileName
	String contentType

	static constraints = {
		owner(nullable:false)
		name(blank:false)
		createdDate(blank:false)
		dueDate(nullable:true)
		priority()
		status()
		completedDate(nullable:true)
		note(maxSize:1000, nullable:true)
		fileName(nullable:true)
		contentType(nullable:true)
		associatedFile(nullable:true)
	}

	String toString() {
		return "${owner?.userName} - $name"
	}
}

enum Priorities {Low, Medium, High }
enum Statuses {Incomplete, Complete}
