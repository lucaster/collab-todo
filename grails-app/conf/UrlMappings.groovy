class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
		
		"/$rest/$domain/$id?"{
			constraints {
				rest(inList:["rest","json"])
			}
			controller = "rest"
			//domain = "todo"
			action = [GET:"show", PUT:"create", POST:"update", DELETE:"delete"]
		}
	}
}
