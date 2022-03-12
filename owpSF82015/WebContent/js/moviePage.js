var url = document.URL;
var id = url.substring(url.lastIndexOf('?')+4);
var role = localStorage.getItem('role');

// http://localhost:8080/owpSF82015/moviePage.html?id=2
$.post('MovieServlet',
	{
		action: "moviePage",
		movieId: id
	},
	function(data){
		
		if(localStorage.getItem("role") == "member"){
			$("#deleteMovieBtn, #editMovieBtn, #confirmBtn").hide();
			$("#buyTicketBtn").show();
			
		}else if(localStorage.getItem("role") == "admin"){
			
				$("#deleteMovieBtn, #editMovieBtn, #confirmBtn").show();
				$("#buyTicketBtn").hide();
				document.getElementById("confirmBtn").disabled = true;
				
			
			
		}else{
			$("#deleteMovieBtn, #editMovieBtn, #confirmBtn, #buyTicketBtn").hide();
		}
		
		
		var obj = JSON.parse(data);
		
		if(obj.info == "success"){
			
			document.getElementById("name").value = obj.name;
			document.getElementById("actors").value = obj.actors;
			document.getElementById("director").value = obj.director;
			document.getElementById("genres").value = obj.genres;
			document.getElementById("duration").value = obj.duration;
			document.getElementById("distributor").value = obj.distributor;
			document.getElementById("country").value = obj.country;
			document.getElementById("year").value = obj.year;
			document.getElementById("description").value = obj.description;
			
			if(obj.deleted == 1){
				$("#deleteMovieBtn").hide();				
			}
		}
		else{
			alert("Can't find movie");
		}
	});
	
function editMovie(){
		
	document.getElementById("confirmBtn").disabled = false;
	
	document.getElementById("name").disabled = false;
	document.getElementById("name").style.backgroundColor= "white";
	document.getElementById("name").style.color= "black";
	document.getElementById("director").disabled = false;
	document.getElementById("director").style.backgroundColor= "white";
	document.getElementById("director").style.color= "black";
	document.getElementById("actors").disabled = false;
	document.getElementById("actors").style.backgroundColor= "white";
	document.getElementById("actors").style.color= "black";
	document.getElementById("genres").disabled = false;
	document.getElementById("genres").style.backgroundColor= "white";
	document.getElementById("genres").style.color= "black";
	document.getElementById("duration").disabled = false;
	document.getElementById("duration").style.backgroundColor= "white";
	document.getElementById("duration").style.color= "black";
	document.getElementById("distributor").disabled = false;
	document.getElementById("distributor").style.backgroundColor= "white";
	document.getElementById("distributor").style.color= "black";
	document.getElementById("country").disabled = false;
	document.getElementById("country").style.backgroundColor= "white";
	document.getElementById("country").style.color= "black";
	document.getElementById("year").disabled = false;
	document.getElementById("year").style.backgroundColor= "white";
	document.getElementById("year").style.color= "black";
	document.getElementById("description").disabled = false;
	document.getElementById("description").style.backgroundColor= "white";
	document.getElementById("description").style.color= "black";

}
			
function confirmMovieChanges(){
	
	var name = document.getElementById("name").value;
	var director = document.getElementById("director").value;
	var actors = document.getElementById("actors").value;
	var genres = document.getElementById("genres").value;
	var duration = document.getElementById("duration").value;
	var distributor = document.getElementById("distributor").value;
	var country = document.getElementById("country").value;
	var year = document.getElementById("year").value;
	var description = document.getElementById("description").value;
	
	var letters = /^[A-Za-z0-9]+$/;
	
	if(name == "")
		{
			alert('Please insert name');
		}else if(!letters.test(country))
		{
			alert('Country field required only alphanumeric characters');
		}else if(duration == "")
		{
			alert('Please insert duration');
		}
		else if(distributor == "")
		{
			alert('Please insert distributor');
		}
		else if(country == "")
		{
			alert('Please insert country');
		}
		else if(year == "")
		{
			alert('Please insert year');
		}
		
		else{
			$.post('MovieServlet',
				{
					action:"movieUpdate",
					movieId:id,
					name : document.getElementById("name").value,
					actors : document.getElementById("actors").value,
					director : document.getElementById("director").value,
					genres : document.getElementById("genres").value,
					duration : document.getElementById("duration").value,
					distributor : document.getElementById("distributor").value,
					country : document.getElementById("country").value,
					year : document.getElementById("year").value,
					description : document.getElementById("description").value,
				},
				function(data){
					var obj = JSON.parse(data);
					
					if(obj.info=="success"){
						location.reload();
					}
					else{
						alert("Can't find movie.")
					}
				});
		}
	
}		
	
	
function deleteMovie(){
	

		$.post('MovieServlet',
		{
			action: "deleteMovie",
			movieId : id,
			role: role
		},
		function(data){
			var obj = JSON.parse(data);
			if(obj.info == "success"){
				alert('Successfuly deleted!');
				window.location= "movieList.html";
				
			}
			else if(obj.info == "errorNotAuthorized"){
					alert('Users are not allowed to delete movies!');
									
				}
			else{
				alert("Can't find movie.");
			}
			
		}
		);
	
	}

function preventDot(id)
	{
		str = document.getElementById(id).value;
		document.getElementById(id).value = (str.replace(".",""));
	}
	
function buyTicket(){
	window.location = "ticketBuy.html?st=1;id="+id+":"; // st oznacava stage, iza ; oznacava id filma, iza : ce biti id projekcije
}

