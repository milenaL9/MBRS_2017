${r"#{"}extends 'main.html'/}

<script>
	$(document).ready(function() {
		$("tr").click(function() {
			//ne reagujemo na klik na header tabele, samo obicne redove
			//this sadrzi red na koji se kliknulo
			if (!$(this).hasClass("header")) {
				//klasa highlighted postavlja pozadinu na zutu
				//njenim dodavanjem ili uklanjanjem oznacavamo da neki red
				//dobija, odnosno gubi selekciju
				//uklanjamo sa trenutno selektovanog
				$(".highlighted").removeClass("highlighted");
				//dodajemo na novi selektovani
				$(this).addClass("highlighted");
				//pozivamo sinhronizaciju, prosledjujemo dati red
				sync($(this));
			}
		});

		$("#remove").click(function() {
			highlighted = $(".highlighted");
			id = highlighted.find(".id").html();
			remove(id);
		});

		$("#nextform").click(function() {
			highlighted = $(".highlighted");
			id = highlighted.find(".id").html();
			nextform_func(id);
		});
		
		$("#kupovinaProdaja").click(function() {
			highlighted = $(".highlighted");
			id = highlighted.find(".id").html();
			kupovinaProdaja_func(id);
		});
		
		$("#faktura").click(function() {
			highlighted = $(".highlighted");
			id = highlighted.find(".id").html();
			faktura_func(id);
		});
		
		$("#copy").click(function() {
			highlighted = $(".highlighted");
			id = highlighted.find(".id").html();
			copy_func(id);
		});

		$("#first").click(function() {
			//indeksi pocinju od 1
			//prvi red je header, zato se trazi drugo dete
			item = $("table tr:nth-child(2)");
			$(".highlighted").removeClass("highlighted");
			item.addClass("highlighted");
			sync(item);
		});

		$("#last").click(function() {
			//indeksi pocinju od 1
			var rowsCount = $('.tableRow').length + 1;

			item = $("tr:nth-child(" + rowsCount + ")");
			$(".highlighted").removeClass("highlighted");
			item.addClass("highlighted");
			sync(item);
		});
		
		$("#xml").click(function() {
			highlighted = $(".highlighted");
			id = highlighted.find(".id").html();
			xml_func(id);
		});
		
		$("#narudzbaFaktura").click(function(){
			highlighted = $(".highlighted");
			id = highlighted.find(".id").html();
			narudzbaFaktura_func(id);
		});

		$("#next").click(function() {
			highlighted = $(".highlighted");
			//nalazi poziciju trazanog u okviru selektovane selekcije
			//indeksi pocinju od 0
			var count = $('.tableRow').length + 1;
			if (count == 0)
				return;
			index = $("tr").index(highlighted);
			if (index < 0)
				return;
			//ako smo na poslednjem, predji na prvi (odnosno drugi red, preskacuci header)
			selectChild = 2;
			//inace
			if (index < count - 1)
				selectChild = index + 2; //povecavamo za 1, i jos dodajemo 1 jer nth child pocinje od 1, indeksi od 0
			item = $("tr:nth-child(" + selectChild + ")");
			$(".highlighted").removeClass("highlighted");
			item.addClass("highlighted");
			sync(item);
		});

		$("#prev").click(function() {
			highlighted = $(".highlighted");
			//nalazi poziciju trazanog u okviru selektovane selekcije
			//indeksi pocinju od 0
			var count = $('.tableRow').length + 1;
			if (count == 0)
				return;
			index = $("tr").index(highlighted);
			if (index < 0)
				return;
			selectChild = count - 1;

			if (index > 1)
				selectChild = index; //povecavamo za 1, i jos dodajemo 1 jer nth child pocinje od 1, indeksi od 0
			else
				// ako si na prvom predji na poslednji
				selectChild = count;
			item = $("tr:nth-child(" + selectChild + ")");
			$(".highlighted").removeClass("highlighted");
			item.addClass("highlighted");
			sync(item);
		});
		
		
	});

	function myFunction() {
		var x = document.getElementById("myTopnav");
		if (x.className === "topnav") {
			x.className += " responsive";
		} else {
			x.className = "topnav";
		}
	}

	$(document).ready(function() {
		$('.container').addClass('container-loaded');
	});

	//When the user scrolls down 20px from the top of the document, show the button
	window.onscroll = function() {
		scrollFunction()
	};

	function scrollFunction() {
		if (document.body.scrollTop > 20
				|| document.documentElement.scrollTop > 20) {
			document.getElementById("myBtn").style.display = "block";
		} else {
			document.getElementById("myBtn").style.display = "none";
		}
	}

	// When the user clicks on the button, scroll to the top of the document
	function topFunction() {
		document.body.scrollTop = 0;
		document.documentElement.scrollTop = 0;

		var body = $("body");
		var top = body.scrollTop();// Get position of the body
		if (top != 0) {
			body.animate({
				scrollTop : 0
			}, '500');
		}
	}

	function openSideNav() {
    	document.getElementById("sidenav").style.width = "250px";
	}

	function closeSideNav() {
    	document.getElementById("sidenav").style.width = "0";
	}
	
	
	
	
</script>

<div class="topnav" id="myTopnav">
	<a class="navbar-brand" href="@{Application.index()}"> <img
		src="@{'/public/images/logo.png'}" width="170" height="70"
		class="d-inline-block align-top" alt="">
	</a> 
	<a href="@{Application.index()}" style="margin-top: 40px;">Početna</a> 
	<a href="@{Preduzeca.show()}" style="margin-top: 40px;">Servis</a> 
	<a href="@{Application.about()}" style="margin-top: 40px;">O nama</a> 
	<a href="@{Application.contact()}" style="margin-top: 40px;">Kontakt</a> 
	<a href="javascript:void(0);" class="icon" onclick="myFunction()">&#9776;</a>
</div>

<div id="sidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeSideNav()">&times;</a>
  <#list classes as item>
  <a href="@{${item.controllerName}.show("edit")}">${item.label}</a>
  </#list>
</div>
${r"#{"}doLayout/}



