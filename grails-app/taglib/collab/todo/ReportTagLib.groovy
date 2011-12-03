package collab.todo

class ReportTagLib {
	
	/* 
	 *=== EXAMPLE === 
 	 *<g:report id="todoReport" controller="TodoController" action="userTodo" report="userTodo" format="PDF,HTML,CSV,XLS,RTF,TXT,XML">
	 *	<input type="hidden" name="userName" value="${todoList[0]?.owner}" />
	 *</g:report>
	 *
	 *=== ATTRIBUTES ===
	 *controller: the controller that provides the collection
	 *action: the closure of the controller that provides the collection
	 *report: the name of the compiled .jasper file, without extension
	 *format: a list of formats we want to make available to the user. Each format will become a button
	 */
		
	def report = { attrs, body ->

		validateAttributes(attrs)
		
		def appPath = grailsAttributes.getApplicationUri(request)

		out << """
		<form id="${attrs['id']}" name="${attrs['report']}" action="${appPath}/report">
			<input type="hidden" name="format"/>
			<input type="hidden" name="file" value="${attrs['report']}"/>
			<input type="hidden" name="_controller" value="${attrs['controller']}"/>
			<input type="hidden" name="_action" value="${attrs['action']}"/>
		"""
		// Create a button for each format
		TreeSet formats = attrs['format'].split(",")
		formats.each{  out << """
			<a href="#${attrs['report']}Report" onClick="document.getElementById('${attrs['id']}').format.value='${it}'; document.getElementById('${attrs['id']}').submit()">
				<img width="16px" height="16px" border="0" src="${appPath}/images/icons/${it}.gif" />
		 	</a>
		 	"""  }
		
		// Add the hidden "userName" input field
		out << body()
		
		out << """
		</form>"""
	}

	private void validateAttributes(attrs) {
		//Verify the 'id' attribute
		if(attrs.id == null) {
			throw new Exception("The 'id' attribute in 'report' tag mustn't be 'null'")
		}

		//Verify the 'format' attribute
		def availableFormats = ["CSV","HTML","RTF","XLS","PDF","TXT","XML"]
		
		attrs.format.toUpperCase().split(",").each{
			if(!availableFormats.contains(it)){
				throw new Exception("""Value ${it} is a invalid format attribute. Only ${availableFormats} are permitted""")
			}
		}
	}
}