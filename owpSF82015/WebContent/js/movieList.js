$.post('MovieListServlet',
  {
    
    action:"movieList"
  },
  
  function(data){ 
	  var movieTable = document.getElementById('movieTable');
	  console.log(data);
	  if(data){
		  var obj = JSON.parse(data);
		  console.log(obj);
		  if(obj.info=="success"){
			  
			  for(i=0;i<obj.objList.length;i++){
				 
					var	movie = obj.objList[i];
					if(movie.deleted == 0){
					var text1 = "<tr><td class='profile'>" +
							"<a href='moviePage.html?id="+movie.id+"'>"+movie.name+"</a> </td><td>" +
									""+movie.genre+"</td><td>" +
											""+movie.duration+"</td><td>" +
													""+movie.distributor+"</td><td>" +
														""+movie.country+"</td><td>" +
															""+movie.year+"</td></tr>"
						$("#datas").append(text1);
					}
				}
			  
		  }else{
			  alert("No movies...");
		  }
	  }else{
		  alert("No movies...");
	  }
  });

function sortTable(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("movieTable");
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

function sortTableNumber(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("movieTable");
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
	        if (Number(x.innerHTML) > Number(y.innerHTML)) {
	          // If so, mark as a switch and break the loop:
	          shouldSwitch = true;
	          break;
	        }
	      } else if (dir == "desc") {
	        if (Number(x.innerHTML) > Number(y.innerHTML)) {
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

$('#searchBtn').click(function () { // on a click on the button with id 'one'
	 $('#nameSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#genreSearch').keyup(function () { // on a click on the button with id 'one'
	 $('#nameSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#distributorSearch').keyup(function () { // on a click on the button with id 'one'
	 $('#nameSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#countrySearch').keyup(function () { // on a click on the button with id 'one'
	 $('#nameSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#maxDurationSearch').keyup(function () { // on a click on the button with id 'one'
	 $('#nameSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#minDurationSearch').keyup(function () { // on a click on the button with id 'one'
	 $('#nameSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#maxYearSearch').keyup(function () { // on a click on the button with id 'one'
	 $('#nameSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#minYearSearch').keyup(function () { // on a click on the button with id 'one'
	 $('#nameSearch').trigger('keyup');// trigger the click on second, and go on 
});

$("#nameSearch").on("keyup", function(){
	if(document.getElementById(maxDurationSearch)){
		str = document.getElementById(maxDurationSearch).value;
		document.getElementById(maxDurationSearch).value = (str.replace(".",""));
	}
	if(document.getElementById(minDurationSearch)){
		str = document.getElementById(minDurationSearch).value;
		document.getElementById(minDurationSearch).value = (str.replace(".",""));
	}
	if(document.getElementById(minYearSearch)){
		str = document.getElementById(minYearSearch).value;
		document.getElementById(minYearSearch).value = (str.replace(".",""));
	}
	if(document.getElementById(maxYearSearch)){
		str = document.getElementById(maxYearSearch).value;
		document.getElementById(maxYearSearch).value = (str.replace(".",""));
	}
	$.post('MovieListServlet',
			{
				nameSearch: document.getElementById("nameSearch").value,
				genreSearch: document.getElementById("genreSearch").value,
				distributorSearch: document.getElementById("distributorSearch").value,
				countrySearch: document.getElementById("countrySearch").value,
				maxDurationSearch: document.getElementById("maxDurationSearch").value,
				minDurationSearch: document.getElementById("minDurationSearch").value,
				maxYearSearch: document.getElementById("maxYearSearch").value,
				minYearSearch: document.getElementById("minYearSearch").value,
				genreSearch: document.getElementById("genreSearch").value,
				genreSearch: document.getElementById("genreSearch").value,
				
				action :"search"
			},
			function(data) {
				  var movieTable = document.getElementById("movieTable");
				  console.log(data);
				  if(data){
					  var obj = JSON.parse(data);
					  console.log(obj);
					  if(obj.info=="success"){
						  $("#movieTable").find("tr:gt(0)").remove();
						  for(i=0;i<obj.objList.length;i++){
							 
								var	movie = obj.objList[i];
								if(movie.deleted == 0){
								var text1 = "<tr><td class='profile'>" +
										"<a href='moviePage.html?id="+movie.id+"'>"+movie.name+"</a> </td><td>" +
												""+movie.genre+"</td><td>" +
														""+movie.duration+"</td><td>" +
																""+movie.distributor+"</td><td>" +
																	""+movie.country+"</td><td>" +
																		""+movie.year+"</td></tr>"
									$("#datas").append(text1);
								}
							}
						  
					  }else{
						  //alert("No movies...");
							$("#movieTable").find("tr:gt(0)").remove();
						
					  }
				  }else{
					  //alert("No movies...");
				  }
			  });
});

function preventDot(id)
{
	str = document.getElementById(id).value;
	document.getElementById(id).value = (str.replace(".",""));
}
