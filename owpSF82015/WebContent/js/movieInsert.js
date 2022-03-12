var role = localStorage.getItem('role');
if(( role != "admin")){
	alert("Can't access, only admin can insert movies! ")
	window.location = "index.html";
	
}
	
function movieAdd()
{
	var name = document.getElementById("nameInput").value;
	var director = document.getElementById("directorInput").value;
	var actors = document.getElementById("actorsInput").value;
	var genres = document.getElementById("genresInput").value;
	var duration = document.getElementById("durationInput").value;
	var distributor = document.getElementById("distributorInput").value;
	var country = document.getElementById("countryInput").value;
	var year = document.getElementById("yearInput").value;
	var description = document.getElementById("descriptionInput").value;
	

	
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
				action: "movieAdd",
				name : name,
				director : director,
				actors : actors,
				genres : genres,
				duration : duration,
				distributor : distributor, 
				country : country,
				year : year,
				description : description,
				role: role
			},
			function(data){
				var obj = JSON.parse(data);
				if(obj.info == "success"){
					alert('Movie is successfully added! ');
					window.location = "movieList.html";
					
				}
				else if(obj.info == "error"){
					alert("Can't add movie");
						
				}
				else if(obj.info == "errorNotAuthorized"){
					alert("Member or unsigned users can't insert movies! Only admins are allowed to!")
				}
					
				else{
					alert('Try again.');
				}
				
			}
			);
		}
	}
	
function preventDot(id)
	{
		str = document.getElementById(id).value;
		document.getElementById(id).value = (str.replace(".",""));
	}
function clearForm()
	{
	document.getElementById("nameInput").value =  "";
	document.getElementById("actorsInput").value = "";
	document.getElementById("directorInput").value = "";
	document.getElementById("genresInput").value = "";
	document.getElementById("durationInput").value = "";
	document.getElementById("distributorInput").value = "";
	document.getElementById("countryInput").value = "";
	document.getElementById("yearInput").value = "";
	document.getElementById("descriptionInput").value = "";
	
	}