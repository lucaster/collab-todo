<div id="menu">
	<nobr>
		<g:isLoggedIn>
			<b>
				<g:loggedInUsername/>
			</b> |
			<g:link uri="/logout">
				<g:message code="topbar.logout" />
			</g:link>
		</g:isLoggedIn>
		<g:isNotLoggedIn>
			<g:link uri="/login">
				<g:message code="topbar.login" />
			</g:link>
		</g:isNotLoggedIn>
	</nobr>
</div>