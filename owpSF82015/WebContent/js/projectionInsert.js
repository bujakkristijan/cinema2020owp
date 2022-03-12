var role = localStorage.getItem('role');
document.getElementById("projectionType").disabled = true;

if(( role != "admin")){
	alert("Can't access, only admin can insert movies! ")
	window.location = "index.html";
	
}
else{
	$.post('ProjectionServlet',
	{
		action:"projectionInsertPrepare"

	},
	function(data){
		var obj = JSON.parse(data);
		
		//postavlja minimalan datum i vreme 
		
		var today = new Date();
		var dd = String(today.getDate()).padStart(2,'0'); //mora da ima dve cifre, ako ima 2 ne dira ga ako ima jednu dodaje nulu
		var mm = String(today.getMonth() + 1).padStart(2,'0'); //Januar je 0! 
		var yyyy = today.getFullYear();
			todayStr = yyyy + '-' + mm + '-' + dd;
		
		document.getElementById("date").min = todayStr; // stavlja minimalan datum na htmlu na danasnji datum
		var minutes, hours = "";
		if(parseInt (today.getMinutes())<10){
			minutes = "0" + today.getMinutes(); // radi isto sto i padStart, stavlja 0 ispred broja ako je manji od 10 	
		}else{
			minutes = today.getMinutes();
		}
		if(parseInt (today.getHours())<10){
			hours = "0" + today.getHours(); // radi isto sto i padStart, stavlja 0 ispred broja ako je manji od 10 	
		}else{
			hours = today.getHours();
		}
		
		time = hours + ":" +minutes;
		
		document.getElementById("time").min = time;
		
		if(obj.info == "success"){
			//ubacivanje filmova u combobox
			var selectMovie = document.getElementById('movie');
			for(movie of obj.objListMovie){
				if(movie.deleted==0){
					var option = document.createElement("option");
					option.value = movie.movieId;
					option.text = movie.movieName;
					selectMovie.appendChild(option);
				}
			}
			var selectHall = document.getElementById('hall');
			for(hall of obj.objListHall){
					var option = document.createElement("option"); 
					option.value = hall.hallId; 
					option.text = hall.hallName;
					selectHall.appendChild(option);
			}
			var selectType = document.getElementById('projectionType');
			for(type of obj.objListType){
					var option = document.createElement("option");
					option.value = type.projectionTypeId;
					option.text = type.projectionTypeName;
					selectType.appendChild(option);
			}
		}
		else{
			alert("No data for movie, hall or type");
		}
	});
}


function projectionAdd()
{
	var movie = document.getElementById("movie").value;
	var hall = document.getElementById("hall").value;
	var projectionType = document.getElementById("projectionType").value;
	var price = document.getElementById("price").value;
	var date = document.getElementById("date").value;
	var time = document.getElementById("time").value;

		var letters = /^[A-Za-z0-9]+$/;
			
		if(price == "")
		{
			alert('Please insert price');
		}
		else if(document.getElementById("projectionType").disabled)
		{
			alert('Select hall');
		}
		else if(date == "")
		{
			alert('Please insert date');
		}
		else if(time == "")
		{
			alert('Please insert time');
		}
		else if(parseFloat(price)  <0)
		{
			alert('Enter price bigger then 0');
		}
	
		else{
			
			$.post('ProjectionServlet',
			{
				action: "insertProjection",
				movie : movie,
				hall : hall,
				projectionType : projectionType,
				price : price,
				date : date,
				time : time,
				userId: localStorage.getItem('userId'),
				role: role
				
			},
			function(data){
				var obj = JSON.parse(data);
				if(obj.info == "success"){
					alert('Projection is successfully added! ');
					window.location = "index.html";
					
				}
				else if(obj.info == "error"){
					alert("Can't add projection. Validation failed");
						
				}
				else if(obj.info == "errorNotAvailable"){
					alert('Set other date and time! Already exist projection in that hall on that date and time or projection is in the past!')
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
	
	$("#hall").on('change', function(data)
	{
		 document.getElementById("projectionType").disabled = false;
		 var hall =  document.getElementById("hall");
		 var hallId = hall.options[hall.selectedIndex].value;
	
		$.post('ProjectionServlet',
		{
			action:"checkHall",
			hallId: hallId
		},
		function(data){
			var obj = JSON.parse(data);
			
			if(obj.info == "success"){
				var selectType = document.getElementById('projectionType');
				
				var i;
				var len = selectType.options.length-1;
				for(i = len ; i >=0; i--){
					selectType.remove(i);
				}
				for(type of obj.objListType){
						var option = document.createElement("option");
						option.value = type.projectionTypeId;
						option.text = type.projectionTypeName;
						selectType.appendChild(option);
				}
			}
			else if(obj.info =="error"){
				alert("Error try other one.");
			}else{
				alert("Try again.");
			}
		});	
	
	
	});
function preventDot(id)
	{
		str = document.getElementById(id).value;
		document.getElementById(id).value = (str.replace(".",""));
	}
function clearForm()
	{
	document.getElementById("price").value =  "";
	document.getElementById("date").value = "";
	document.getElementById("time").value = "";
	
	
	}