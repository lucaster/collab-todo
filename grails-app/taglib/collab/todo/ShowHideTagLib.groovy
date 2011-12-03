package collab.todo

class ShowHideTagLib {

	def showHide = { attrs, body ->
		def divId = attrs['update']
		out << """<a href="javascript:showHide('$divId');">${body()}</a>"""
	}

	def preLoadShowHide = { attrs, body ->
		out << """
			<script language="javascript">			
				function showHide(layer_ref) {
					// Let's get the state.
					var state = document.getElementById(layer_ref).style.display;
					if (state == 'block' || !state) {
						state = 'none';
					} else {
						state = 'block';
					}
					if (document.all) { //IS IE 4 or 5 (or 6 beta)
						eval( "document.all." + layer_ref + ".style.display = state");
					}
					if (document.layers) { //IS NETSCAPE 4 or below
						document.layers[layer_ref].display = state;
					}
					if (document.getElementById &&!document.all) {
						hza = document.getElementById(layer_ref);
					hza.style.display = state;
					}
				}
			</script>
		"""
	}
}
