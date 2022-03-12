var url = document.URL;
var id = url.substring(url.lastIndexOf('?')+4);
var movieId = 0; // vrednost se menja cim se preuzme projekcija
// http://localhost:8080/owpSF82015/moviePage.html?id=2
var role = localStorage.getItem('role');

$.post('ProjectionServlet',
	{
		action: "projectionPage",
		projectionId: id
	},
	function(data){
		
		
		
		if(localStorage.getItem("role") == "member"){
			$("#deleteProjectionBtn, #ticket").hide();
			$("#buyTicketBtn").show();
			
		}else if(localStorage.getItem("role") == "admin"){
			
				$("#deleteProjectionBtn, #ticket").show();
				$("#buyTicketBtn").hide();
			
			
		}else{
			$("#deleteProjectionBtn, #ticket, #buyTicketBtn").hide();
		}
		
		var obj = JSON.parse(data);
		
		
		
		if(obj.info == "success"){
			
			var currentDateTime = new Date();
			var projectionDateTime = new Date(obj.date);
			console.log(currentDateTime); 
			console.log(projectionDateTime);   
			document.getElementById("movie").value = obj.movieName;
			document.getElementById("hall").value = obj.hallName;
			document.getElementById("date").value = obj.date;
			document.getElementById("price").value = obj.price;
			document.getElementById("type").value = obj.projectionTypeName;
			document.getElementById("unsoldSeat").value = obj.unsoldSeat;
			movieId = obj.movieId;
			
			if(currentDateTime>projectionDateTime && role == "member"){
				$("#buyTicketBtn").hide();
				alert('Projection is in past, you can not buy ticket!');
			}
			
			for(i=0; i<obj.objListTicket.length;i++){
				var ticket = obj.objListTicket[i];
				if(ticket.deleted == 0){
					var text1 = "<tr><td>" +
										"<a href='ticketPage.html?id="+ticket.id+"'>"+ticket.date+"</a></td><td>" +
										"<a href='userPage.html?id="+ticket.ticketUserId+"'>"+ticket.ticketUsername+"</a></td>"+
										"</tr>"
										$("#datas").append(text1);
				}
			}
			
			// $("#ticketTableDate").click(); ne radi sortiranje
			
			
			if(obj.deleted == 1){
				$("#deleteProjectionBtn").hide();				
			}
		}
		else{
			alert("Can't find projection");
		}
	});
	

	
	
function deleteProjection(){
	

		$.post('ProjectionServlet',
		{
			action: "deleteProjection",
			projectionId : id, 
			role: role
		},
		function(data){
			var obj = JSON.parse(data);
			if(obj.info == "success"){
				alert('Successfuly deleted!');
				window.location= "index.html";
				
			}
			else if(obj.info == "errorNotAuthorized"){
				alert('Members or unsigned users can not delete projections, only admins are allowed to do that!');
			}
			else{
				alert("Can't find projection.");
			}
			
		}
		);
	
	}
	
function sortTable(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("ticketTable");
	  switching = true;
	  // Set the sorting direction to ascending:
	  dir = "asc";
	  /* Make a loop that will continue until
	  no switching has been done: */
	  while (switching) {
	    // Start by saying: no switching is done:
	    switching = false;
	    rows = table.rows;
	    /* Loop through all table rows (except the
	    first, which contains table headers): */
	    for (i = 1; i < (rows.length - 1); i++) {
	      // Start by saying there should be no switching:
	      shouldSwitch = false;
	      /* Get the two elements you want to compare,
	      one from current row and one from the next: */
	      x = rows[i].getElementsByTagName("TD")[n];
	      y = rows[i + 1].getElementsByTagName("TD")[n];
	      /* Check if the two rows should switch place,
	      based on the direction, asc or desc: */
	      if (dir == "asc") {
	        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	          // If so, mark as a switch and break the loop:
	          shouldSwitch = true;
	          break;
	        }
	      } else if (dir == "desc") {
	        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
	          // If so, mark as a switch and break the loop:
	          shouldSwitch = true;
	          break;
	        }
	      }
	    }
	    if (shouldSwitch) {
	      /* If a switch has been marked, make the switch
	      and mark that a switch has been done: */
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	      // Each time a switch is done, increase this count by 1:
	      switchcount ++;
	    } else {
	      /* If no switching has been done AND the direction is "asc",
	      set the direction to "desc" and run the while loop again. */
	      if (switchcount == 0 && dir == "asc") {
	        dir = "desc";
	        switching = true;
	      }
	    }
	  }
	}
 
function moviePage(){
	window.location = "moviePage.html?id="+movieId;
}

function preventDot(id)
	{
		str = document.getElementById(id).value;
		document.getElementById(id).value = (str.replace(".",""));
	}
	
function buyTicket(){
	window.location = "ticketBuy.html?st=2;id="+movieId+":"+id; // st oznacava stage, iza ; oznacava id filma, iza : ce biti id projekcije
}