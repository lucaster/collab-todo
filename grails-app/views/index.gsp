<html>
<head>
<title>Welcome to Grails</title>
<meta name="layout" content="main" />
<style type="text/css" media="screen">
#nav {
	margin-top: 20px;
	margin-left: 30px;
	width: 228px;
	float: left;
}

.homePagePanel * {
	margin: 0px;
}

.homePagePanel .panelBody ul {
	list-style-type: none;
	margin-bottom: 10px;
}

.homePagePanel .panelBody h1 {
	text-transform: uppercase;
	font-size: 1.1em;
	margin-bottom: 10px;
}

.homePagePanel .panelBody {
	background: url(images/leftnav_midstretch.png) repeat-y top;
	margin: 0px;
	padding: 15px;
}

.homePagePanel .panelBtm {
	background: url(images/leftnav_btm.png) no-repeat top;
	height: 20px;
	margin: 0px;
}

.homePagePanel .panelTop {
	background: url(images/leftnav_top.png) no-repeat top;
	height: 11px;
	margin: 0px;
}

h2 {
	margin-top: 15px;
	margin-bottom: 15px;
	font-size: 1.2em;
}

#pageBody {
	margin-left: 280px;
	margin-right: 20px;
}
</style>
</head>
<body>
	<h1 style="margin-left: 20px;">Welcome to Collab-Todo</h1>
	<p style="margin-left: 20px; width: 80%">Welcome to the Collab-Todo
		application. This application was built as part of the Apress Book,
		"Beginning Groovy and Grails." Functionally, the application is a
		collaborative "To-Do" list that allows users and their buddies to
		jointly manage "To-Do" tasks.</p>
	<br />
	<p style="margin-left: 20px; width: 80%">Building the Collab-Todo
		application is used to walk the user through using Grails 1.0 to build
		an application. Below is a list of controllers that are currently
		deployed in this application. Click on each to execute its default
		action:</p>
	<br />
	<div class="dialog" style="margin-left: 20px; width: 60%;">
		<ul>
			<g:each var="c" in="${grailsApplication.controllerClasses}">
				<li class="controller"><a href="${c.logicalPropertyName}">
						${c.fullName}
				</a></li>
			</g:each>
		</ul>
	</div>
</body>
</html>
