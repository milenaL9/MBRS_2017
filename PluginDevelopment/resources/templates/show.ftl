${r"#{extends"} 'standardForm.html' /}
${r"#{set"} title:'${class.label}' /}

<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">

<script>
	function sync(item){
		if ("${r"${session.mode}"}" == "edit"){
			<#list class.propertiesNoZoom as property>
			${property.name} = item.find(".${property.name}").html()  		    	
	    	</#list>
	    	id = item.find(".id").html()   	
	    	<#list class.classProperties as property>
	    	$("#${class.name?uncap_first}_${property.name}").val(${property.name});	 
	    	</#list>
	    	$("#${class.name?uncap_first}_id").val(id);
	    	
	    	<#list class.propertiesManyToOne as property>	    	
	    	var options${property_index} = document.getElementById("${property.name}").children;
			for (i = 0; i < options${property_index}.length; i++) { 
    			if(options${property_index}[i].label == ${property.name}){
    				document.getElementById("${property.name}").selectedIndex = i;
    				break;
    			}
			}
			
			</#list>
    	}
	}
	
	function remove(id){
		if (id == null){
			alert("Oznacite red u tabeli!");
		} else {
			var r  = confirm("Da li zelite da obrisete slog?");
			if(r == true) {
	    		${r"$(location)"}.attr('href', '/${class.controllerName}/delete?id='+id);
			}else{
				txt = "You pressed Cancel"
			}
		}
	}
</script>

<div class="container-fluid">
	<div class="row">
		<div class="icon-bar">
		
			<#if class.showMenu>
			<a href="#" onclick="openSideNav()">
				<img width="16px" height="16px" src="@{'/public/images/menu_icon.png'}" />
			</a>
			</#if>
			
			<a href="@{${class.controllerName}.show("edit")}" id = "refresh">
				<img src="@{'/public/images/refresh.gif'}"/>
			</a>
			
			<a id = "first">
				<img src="@{'/public/images/first.gif'}"/>
			</a>
			<a id = "prev">
				<img src="@{'/public/images/prev.gif'}"/>
			</a>
			<a id = "next">
				<img src="@{'/public/images/next.gif'}"/>
			</a>
			<a id = "last">
				<img src="@{'/public/images/last.gif'}"/>
			</a>
			
			<#if class.createSF>
			<a href="@{${class.controllerName}.show("add")}" id = "add">
				<img src="@{'/public/images/add.gif'}"/>
			</a>
			</#if>
			
			<#if class.deleteSF>
			<a href="#" id = "remove">
				<img src="@{'/public/images/remove.gif'}"/>
			</a>
			</#if>
			
			<#if class.saveStavke>
			<a href="@{${class.controllerName}.saveStavke}" id="done"> 
				<img src="@{'/public/images/stavke_fakture_done.png'}" width="22px" height="22px"/>
			</a>
			</#if>
		</div>
			
		<div class="container-fluid">
			<h1>${class.label}</h1>
			<table>
				<tr class = "header">
					<#list class.propertiesNoZoom as property>
	   				<th>${property.label}</th>
	   				</#list>
				</tr>
				
				${r"#{list"} items:${class.controllerName?uncap_first}, as:'${class.name?uncap_first}'}
				<tr ${r"#{if"} ${class.name?uncap_first}.id == idd} class = "highlighted" ${r"#{/if}"} class="tableRow">
					<#list class.classProperties as property>
					<td class = "${property.name}">${r"${"}${class.name?uncap_first}.${property.name}}</td>
					</#list>
					<#list class.propertiesManyToOne as property>
					<td class = "${property.name}">${r"${"}${class.name?uncap_first}.${property.name}.${property.lookupName}${r"}"}</td>
					</#list>					
					<td style = "visibility: hidden; max-width: 0px" class = "id">${r"${"}${class.name?uncap_first}.id}</td>
				</tr>				
				${r"#{/list}"}
			</table>
			
			${r"#{set"} 'action'}
			${r"#{if"} mode == 'add'}
    			@{${class.controllerName}.create()}
			${r"#{/if}"}
			${r"#{else"} mode == 'edit'}
   				@{${class.controllerName}.edit()}
			${r"#{/else}"}
    		${r"#{/set}"}
    	
    		<#if class.editSF>
			<div class="panel">
				<form action=${r"${action}"} method="post">
					<p>
						${r"#{field"} '${class.name?uncap_first}.id'}
							<input type = "hidden" name = "${r"${field.name}"}" id = "${r"${field.id}"}" value="${r"${session.idVPDVa}"}"/>
						${r"#{/}"}
					</p>
					
					<#list class.classProperties as property>
						<#if !property.readonly>
					<p>
						${r"#{field"} '${class.name?uncap_first}.${property.name}'}
							<label for = "${r"${field.id}"}">${property.label}</label>
							<input type = "text" name = "${r"${field.name}"}" id = "${r"${field.id}"}" maxlength="40" size="40"/>
						${r"#{/}"}
					</p>
						</#if>					
					</#list>	
					
					<#list class.propertiesManyToOne as property>
					<p>
						${r"#{field"} '${property.name}'} <label for="${r"${field.id}"}">${property.label}</label>
						&nbsp &nbsp &nbsp <select name="${r"${field.name}"}" id="${r"${field.id}"}">
							${r"#{list"} items:${property.controllerName?uncap_first}, as:'${property.name}'}
							<option value="${r"$"}{${property.name}.id}">${r"${"}${property.name}.${property.lookupName}${r"}"}</option>
							${r"#{/list}"}
						</select>
						
						${r"#{/}"}
					</p>
					</#list>	
					
					<div id = "formActions">
						<input type = "submit" value = "Potvrdi" style="margin-left:6em" class="btn btn-success"/>
						<a href = "@{${class.controllerName}.show("edit")}" style="margin-left:1em">
							<button type="button" class="btn btn-danger">Otkazi</button>
						</a>
					</div>				
				</form>
			</div>
			</#if>
		</div>
	</div>
</div>

<p class="status_line">Trenutni režim: ${r"${session.mode}"}</p>

<button onclick="topFunction()" id="myBtn" title="Idi na vrh">
	<img src="@{'/public/images/back_top.png'}" alt=""
		class="rounded-circle" width="40" height="40">
</button>
