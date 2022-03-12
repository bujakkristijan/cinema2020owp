var role = localStorage.getItem('role');

if(( role != "admin")){
	alert("Can't access, only admin can access user page! ")
	window.location = "index.html";
	
}
else{
	$.post('AdminServlet',
	  {
	    
	    action:"userList"
	  },
	  
	  function(data){ 
		  var userTable = document.getElementById('userTable');
		  console.log(data);
		  if(data){// ako nema korisnika da ne baci gresku
			  var obj = JSON.parse(data);
			  console.log(obj);
			  if(obj.info=="success"){
				  
				  for(i=0;i<obj.objList.length;i++){
					 
						var user = obj.objList[i];
						if(user.deleted == 0){
						var text1 = "<tr><td class='profile'>" +
								"<a href='userPage.html?id="+user.id+"'>"+user.username+"</a> </td><td>" +
										""+user.date+"</td><td>" +
												""+user.role+"</td><td>" +
														""+user.deleted+"</td></tr>"
							$("#datas").append(text1);
						}
					}
				  
			  }else{
				  alert("No users...");
			  }
		  }else{
			  alert("No users...");
		  }
	  });
}



function sortTable(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("userTable");
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

function sortTableCombo(role) {
		var n = 2;// polje u tabeli za ulogu
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("userTable");
	  switching = true;
	  // Set the sorting direction to ascending:
	  dir = role;
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
	      if (dir == "admin") {
	        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	          // If so, mark as a switch and break the loop:
	          shouldSwitch = true;
	          break;
	        }
	      } else if (dir == "member") {
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
	      if (switchcount == 0 && dir == "admin") {
	        dir = "member";
	        switching = true;
	      }
	    }
	  }
	}

$('#searchBtn').click(function () { // on a click on the button with id 'one'
	 $('#usernameSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#rolesSearch').change(function () { // on a click on the button with id 'one'
	 $('#usernameSearch').trigger('keyup');// trigger the click on second, and go on 
});

$("#usernameSearch").on("keyup", function(){
	
	$.post('AdminServlet',
			{
				rolesSearch: document.getElementById("rolesSearch").value,
				usernameSerach: document.getElementById("usernameSearch").value,
				action :"usernameSerach"
			},
			function(data) { 
				var userTable = document.getElementById('userTable');
				  
				var obj = JSON.parse(data);
				if(obj.info == "success"){
				$("#userTable").find("tr:gt(0)").remove(); // selektuj tr red  kog po redu indeksa
						  for(i=0;i<obj.objList.length;i++){
							 
								var user = obj.objList[i];
								if(user.deleted == 0){
								var text1 = "<tr><td class='profile'>" +
										"<a href='userPage.html?id="+user.id+"'>"+user.username+"</a> </td><td>" +
												""+user.date+"</td><td>" +
														""+user.role+"</td><td>" +
																""+user.deleted+"</td></tr>"
									$("#datas").append(text1);
								}
							}
			
				}
				else if(obj.info =="error"){
					alert('No user with that username. ');			
				}
				else{
					alert('Refresh and try again.');	
				}
			});
});
