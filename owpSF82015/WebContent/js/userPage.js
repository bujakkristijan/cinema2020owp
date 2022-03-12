var url = document.URL;
var id = url.substring(url.lastIndexOf('?')+4);
var role = localStorage.getItem('role');

if(( role != "admin")){
	alert("Can't access, only admin can access user page! ")
	window.location = "index.html";
	
}

else{
	
	$.post('AdminServlet',
		{
			action: "userPage",
			idUserPage: id
		},
		function(data){
			
			if(localStorage.getItem("role") == "member"){
				$("#deleteUserBtn, #changeUserBtn, #changeRole, #ticket ").hide();
				
			}else if(localStorage.getItem("role") == "admin"){
				if(localStorage.getItem("userId") == id){
					$("#deleteUserBtn").hide();
					$("#changeUserBtn, #changeRole, #ticket").show();
				}else{
					$("#deleteUserBtn, #changeUserBtn, #changeRole, #ticket").show();
				}
			}else{
				$("#deleteUserBtn, #changeUserBtn, #changeRole, #ticket").hide();
			}
			
			var obj = JSON.parse(data);
			
			if(obj.info == "success"){
				
				document.getElementById("usernameInput").value = obj.username;
				document.getElementById("dateInput").value = obj.date;
				document.getElementById("roleInput").value = obj.role;	
				document.getElementById("changeRole").value = obj.role;
				
				
				for(i=0; i<obj.objListTicket.length;i++){
					var ticket = obj.objListTicket[i];
					if(ticket.deleted == 0){
						var text1 = "<tr><td>" +
											"<a href='ticketPage.html?id="+ticket.id+"'>"+ticket.date+"</a></td></tr>"
											$("#datas").append(text1);
					}
				}
				
				$("#ticketTableDate").click();
				
				if(obj.deleted == 1){
					$("#deleteUserBtn").hide();				
				}
			}
			else{
				alert("Can't find user");
			}
		});
}	
function changeRole(){
		
	var newRole = document.getElementById("changeRole").value;

			$.post('AdminServlet',
			{
				action: "changeRole",
				newRole : newRole,
				userId : id,
				role: role
			},
			function(data){
				var obj = JSON.parse(data);
				if(obj.info == "success"){
					alert('Successfuly changed role!');
					if(localStorage.getItem("userId") == id){
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
							else{
							
								alert("Try again!");
							}
							
						});
					}
					else{
						location.reload();
					}
					
					
					
				}
				else if(obj.info == "error"){
					alert('No user or no session');
						
				}
				else if(obj.info == "errorNotAuthorized"){
					alert('Users are not allowed to change roles!');
									
				}
				
				else{
					alert('Try again.');
				}
				
			}
			);
		
	}
	
	function deleteUser(){
		
	
			$.post('AdminServlet',
			{
				action: "deleteUser",
				userId : id,
				role: role
			},
			function(data){
				var obj = JSON.parse(data);
				if(obj.info == "success"){
					alert('Successfuly deleted!');
					window.location= "userList.html";
					
				}
				else if(obj.info == "errorNotAuthorized"){
					alert('Members or unsigned users are not allowed to delete users! Only admins are allowed to');
									
				}
				else{
					alert("Can't find user.");
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