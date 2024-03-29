package collab.todo

import collab.todo.Todo

class User {
  String userName 
  String firstName 
  String lastName 
  String email
  
  static hasMany = [todos: Todo, categories: Category]
	
  static constraints = { 
    userName(blank:false,unique:true) 
    firstName(blank:false) 
    lastName(blank:false) 
  }

  String  toString () { 
    "$lastName, $firstName" 
  } 

}