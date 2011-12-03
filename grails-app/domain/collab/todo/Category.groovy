package collab.todo

import collab.todo.User

class Category {
    String name
    String description
    User user
    
    static belongsTo = User
    static hasMany = [todos: Todo]

    static constraints = {
        name(blank: false)
    }

    String toString() {
        name
    }

}
