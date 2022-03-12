var url = document.URL;
var id = url.substring(url.lastIndexOf('?')+4);
var role = localStorage.getItem('role');
var loggedUserId = localStorage.getItem('userId');
var movieId;
var projectionId;
var buyerId;

if(( role != "admin" && role != "member")){
	alert("Can't access, only admin and member can access ticket page! ")
	window.location = "index.html";
	
}
else{
	$.post('TicketServlet',
	{
		action: "ticketPage",
		ticketId: id
	},
	function(data){
		
		if(localStorage.getItem("role") == "member"){
			$("#deleteTicketBtn, #buyerLabel, #buyer").hide();
			
		}else if(localStorage.getItem("role") == "admin"){
		
			$("#deleteTicketBtn, #buyerLabel, #buyer").show();
			
		}else{
			$("#deleteTicketBtn, #buyerLabel, #buyer").hide();
		}
		
		var obj = JSON.parse(data);
		
		if(obj.info == "success"){
			
			document.getElementById("movie").value = obj.movieName;
			document.getElementById("type").value = obj.projectionTypeName;
			document.getElementById("date").value = obj.date;	
			document.getElementById("hall").value = obj.hallName;	
			document.getElementById("price").value = obj.price;	
			document.getElementById("seat").value = obj.seat;	
			document.getElementById("buyer").value = obj.username;	
			
			movieId = obj.movieId;
			projectionId = obj.projectionId;
			buyerId = obj.userId;
			
			if(loggedUserId != buyerId && role != "admin"){// ako nije tvoja karta(ako si neprijavljen nece biti tvoja karta) i ako nisi admin (znaci da si meber) onda ne mozes pristupiti
				alert("Members can see only their tickets!")
				window.location = "index.html";
			}

			
			if(obj.deleted == 1){
				$("#deleteTicketBtn").hide();				
			}
		}
		else{
			alert("There is no ticket!");
		}
	});
}




	

	
	function deleteTicket(){
		
	
			$.post('TicketServlet',
			{
				action: "deleteTicket",
				ticketId : id,
				role: role
			},
			function(data){
				var obj = JSON.parse(data);
				if(obj.info == "success"){
					alert('Successfuly deleted!');
					window.location= "index.html";
					
				}
				else if(obj.info == "errorNotAuthorized"){
					alert('Users are not allowed to delete tickets!');
									
				}else if(obj.info == "errorProjectionInPast"){
					alert("Can not ticket for projection in past");
				}
				
				else{
					alert("Can't find ticket'.");
				}
				
			}
			);
		
	}
	
	function preventDot(id)
	{
		str = document.getElementById(id).value;
		document.getElementById(id).value = (str.replace(".",""));
	}
	
	function moviePage(){
		window.location = "moviePage.html?id="+movieId;
	}
	function projectionPage(){
		window.location = "projectionPage.html?id="+projectionId;
	}
	function userPage(){
		window.location = "userPage.html?id="+buyerId;
	}