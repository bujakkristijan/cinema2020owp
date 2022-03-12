var userId = localStorage.getItem('userId');
var role = localStorage.getItem('role');

if(( role != "admin" && role != "member")){
	alert("Unsigned users can not access profile!")
	window.location = "index.html";	
}
else{
	$.post('UserServlet',
	{
		action: "profile",
		userId:userId
	},
	function(data){
		var obj = JSON.parse(data);
		
		if(obj.info == "success"){
			
			document.getElementById("usernameInput").value = obj.username;
			document.getElementById("dateInput").value = obj.date;
			document.getElementById("roleInput").value = obj.role;	
			
			for(i=0; i<obj.objListTicket.length;i++){
				var ticket = obj.objListTicket[i];
				if(ticket.deleted == 0){
					var text1 = "<tr><td>" +
										"<a href='ticketPage.html?id="+ticket.id+"'>"+ticket.date+"</a></td></tr>"
										$("#datas").append(text1);
				}
			}
			
			$("#ticketTableDate").click(); // kada ubacis karte klikni zaglavlje i sortiraj tableu
			
		}
		else{
			alert("Can't find user");
		}
	});
}

	
function changePassword(){
		
	var currentPassword = document.getElementById("currentPasswordInput").value;
	var newPassword = document.getElementById("newPasswordInput").value;
	var realCurrentPassword = localStorage.getItem('password');
	var username = localStorage.getItem('username');
	
	var letters = /^[A-Za-z0-9]+$/;
	
		
		if(currentPassword == '' || newPassword == '')
		{
			alert('Please insert password');
		}else if(!letters.test(currentPassword) || !letters.test(newPassword) )
		{
			alert('Password field required only alphanumeric characters');
		}
		else if(currentPassword != realCurrentPassword)
		{
			alert("Passwords don't  match");
		}
		
		else if(document.getElementById("currentPasswordInput").value.length < 6)
		{
			alert ('Password minimum length is 6');
		}
		else if(document.getElementById("newPasswordInput").value.length < 6)
		{
			alert ('Password minimum length is 6');
		}
		
		else{
			$.post('UserServlet',
			{
				action: "changePassword",
				username : username,
				newPassword : newPassword
			},
			function(data){
				var obj = JSON.parse(data);
				if(obj.info == "success"){
					alert('Successfuly changed password! Please log in again!');
					
					$.post('UserServlet',
						{
							action: "logout"
						},
						function(data){
							var obj = JSON.parse(data);
							
							
							if(obj.info == "success"){
								localStorage.setItem('role', "");
								localStorage.setItem('username', "");
								localStorage.setItem('userId', "");
								localStorage.setItem('deleted', "");
								localStorage.setItem('password', "");
							
							//zbog mozile ovakva vrsta brisanja
								localStorage.removeItem("role");
								localStorage.removeItem("username");
								localStorage.removeItem("userId");
								localStorage.removeItem("deleted");
								localStorage.removeItem("password");
								localStorage.clear();
								window.location = "login.html";
							
							}
							else if(obj.info == "errorNotAuthorized"){
								alert('Admins can not change user passwords!')
							}
							else{
							
								alert("Try again!");
							}
							
						});
					
				}
				else if(obj.info == "error"){
					alert('No user or no session');
						
					
				}
				
				else{
					alert('Try again.');
				}
				
			}
			);
		}
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