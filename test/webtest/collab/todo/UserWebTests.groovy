package collab.todo



class UserWebTests extends grails.util.WebTest {


	def testUserListNewDelete() {
		webtest('User basic operations: view list, create new entry, view, edit, delete, view') {

			invoke      'user'
			verifyText  'Home'

			verifyListSize 0

			clickLink   'New User'
			verifyText  'Create User'
			setInputField(name:'userName', 'User1')
			setInputField(name:'firstName', 'User1FN')
			setInputField(name:'lastName', 'User1LN')
			clickButton 'Create'
			verifyText  'Show User', description:'Detail page'
			clickLink   'List', description:'Back to list view'

			verifyListSize 1

			// We added UserFilter so need to login
			// Login
			clickLink   'Login'
			setSelectField name:'userName', value:'User1'
			clickButton 'Login'
			clickLink 'Home'
			invoke 'user'


			group(description:'edit the one element') {
				showFirstElementDetails()
				clickButton 'Edit'
				verifyText  'Edit User'
				clickButton 'Update'
				verifyText  'Show User'
				clickLink   'List', description:'Back to list view'
			}

			verifyListSize 1

			group(description:'delete the only element') {
				showFirstElementDetails()
				clickButton 'Delete'
				verifyXPath xpath:  "//div[@class='message']",
						text:   /.*User.*deleted.*/,
						regex:  true
			}

			verifyListSize 0
		}
	}

	String ROW_COUNT_XPATH = "count(//div[@class='list']//tbody/tr)"

	def verifyListSize(int size) {
		ant.group(description:"verify User list view with $size row(s)") {
			verifyText  'User List'
			verifyXPath xpath:      ROW_COUNT_XPATH,
					text:       size,
					description:"$size row(s) of data expected"
		}
	}

	def showFirstElementDetails() {
		ant.clickLink   'User1', description:'go to detail view'
	}

}