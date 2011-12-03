  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Todo</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Todo List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Todo</g:link></span>
        </div>
        <div class="body">
            <h1>Show Todo</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            
                            <td valign="top" class="value">${todo.name}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Created Date:</td>
                            
                            <td valign="top" class="value">${todo.createdDate}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Due Date:</td>
                            
                            <td valign="top" class="value">${todo.dueDate}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Priority:</td>
                            
                            <td valign="top" class="value">${todo.priority}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Status:</td>
                            
                            <td valign="top" class="value">${todo.status}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Completed Date:</td>
                            
                            <td valign="top" class="value">${todo.completedDate}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Note:</td>
                            
                            <td valign="top" class="value">${todo.note}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Category:</td>
                            
                            <td valign="top" class="value"><g:link controller="category" action="show" id="${todo?.category?.id}">${todo?.category}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                        	<td>
	                        	<g:if test="${todo.fileName?.length() > 0}">
							        <g:link action="downloadFile" id="${todo.id}">Download Attachment</g:link>
						    	</g:if>
					    	</td>
					    </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form controller="todo">
                    <input type="hidden" name="id" value="${todo?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
