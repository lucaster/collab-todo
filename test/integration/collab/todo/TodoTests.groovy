package collab.todo

import grails.test.*

class TodoTests extends GroovyTestCase { 

  void setUp() { 
    Todo.list()*.delete() 
  } 

  void testPersist() {
    User user = new User(userName:"jshingler", firstName:"Jim", lastName:"Shingler")
    user.save()
    Category cat = new Category(name:"testCat", description:"test category", user:user)
    cat.save()

    Todo t = new Todo(name: "1", createdDate:new Date(), priority: "", status:"", owner:user, category:cat)
    t.save()
    if (t.hasErrors() ) {
          t.errors.each { println it}
    }
    new Todo(name: "2", createdDate:new Date(), priority: "", status:"", owner:user, category:cat).save()
    new Todo(name: "3", createdDate:new Date(), priority: "", status:"", owner:user, category:cat).save()
    new Todo(name: "4", createdDate:new Date(), priority: "", status:"", owner:user, category:cat).save()
    new Todo(name: "5", createdDate:new Date(), priority: "", status:"", owner:user, category:cat).save()


    assert 5 == Todo.count() 
    def actualTodo = Todo.findByName('1') 
    assert actualTodo 
    assert '1' == actualTodo.name 
  } 
 
  void testToString() {
    User user = new User(userName:"jshingler", firstName:"Jim", lastName:"Shingler")
    def todo = new Todo(name: "Pickup laundry", owner:user)
    assertToString(todo, "jshingler - Pickup laundry")
  } 
} 
