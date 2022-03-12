$.post('ProjectionListServlet',
  {
    
    action:"projectionList"
  },
  
  function(data){ 
	  var projectionTable = document.getElementById('projectionTable');
	  console.log(data);
	  if(data){
		  var obj = JSON.parse(data);
		  console.log(obj);
		  if(obj.info=="success"){
			  
			  for(i=0;i<obj.objList.length;i++){
				 
					var	projection = obj.objList[i];
					if(projection.deleted == 0){
					var text1 = "<tr><td class='profile'>" +
							"<a href='moviePage.html?id="+projection.movieId+"'>"+projection.movieName+"</a> </td><td>" +
								"<a href='projectionPage.html?id="+projection.id+"'>"+projection.date+"</a> </td><td>"+
									""+projection.hallName+"</td><td>" +
											""+projection.projectionTypeName+"</td><td>" +
													""+projection.price+"</td></tr>" 
														
						$("#datas").append(text1);
					}
				}
			  sortTable(0);
		  }else{
			  alert("No projections...");
		  }
	  }else{
		  alert("No projections...");
	  }
  });

function sortTable(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("projectionTable");
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
	  table = document.getElementById("projectionTable");
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


$('#typeSearch').change(function () { // on a click on the button with id 'one'
	 $('#movieSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#hallSearch').change(function () { // on a click on the button with id 'one'
	 $('#movieSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#maxPriceSearch').keyup(function () { // on a click on the button with id 'one'
	 $('#movieSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#minPriceSearch').keyup(function () { // on a click on the button with id 'one'
	 $('#movieSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#maxDateSearch').change(function () { // on a click on the button with id 'one'
	 $('#movieSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#minDateSearch').change(function () { // on a click on the button with id 'one'
	 $('#movieSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#minTimeSearch').change(function () { // on a click on the button with id 'one'
	 $('#movieSearch').trigger('keyup');// trigger the click on second, and go on 
});
$('#maxTimeSearch').change(function () { // on a click on the button with id 'one'
	 $('#movieSearch').trigger('keyup');// trigger the click on second, and go on 
});

$("#movieSearch").on("keyup", function(){
	if(document.getElementById(maxPriceSearch)){
		str = document.getElementById(maxPriceSearch).value;
		document.getElementById(maxPriceSearch).value = (str.replace(".","")); //ako ima tacku zamenice sa praznim stringom
	}
	if(document.getElementById(minPriceSearch)){
		str = document.getElementById(minPriceSearch).value;
		document.getElementById(minPriceSearch).value = (str.replace(".",""));
	}
	$.post('ProjectionListServlet',
			{
				movieSearch: document.getElementById("movieSearch").value,
				typeSearch: document.getElementById("typeSearch").value,
				hallSearch: document.getElementById("hallSearch").value,
				maxPriceSearch: document.getElementById("maxPriceSearch").value,
				minPriceSearch: document.getElementById("minPriceSearch").value,
				maxDateSearch: document.getElementById("maxDateSearch").value,
				minDateSearch: document.getElementById("minDateSearch").value,
				minTimeSearch: document.getElementById("minTimeSearch").value,
				maxTimeSearch: document.getElementById("maxTimeSearch").value,
				
				action :"search"
			},
			function(data) {
				  var projectionTable = document.getElementById('projectionTable');
				  console.log(data);
				  if(data){
					  var obj = JSON.parse(data);
					  console.log(obj);
					  if(obj.info=="success"){
						  $("#projectionTable").find("tr:gt(0)").remove(); //zaobidji prvi red odnosno zaglavlje i obrisi stare redove
						  for(i=0;i<obj.objList.length;i++){
							 
								var	projection = obj.objList[i];
								if(projection.deleted == 0){
								var text1 = "<tr><td class='profile'>" +
										"<a href='moviePage.html?id="+projection.movieId+"'>"+projection.movieName+"</a> </td><td>" +
											"<a href='projectionPage.html?id="+projection.id+"'>"+projection.date+"</a> </td><td>"+
												""+projection.hallName+"</td><td>" +
														""+projection.projectionTypeName+"</td><td>" +
																""+projection.price+"</td></tr>" 
																	
									$("#datas").append(text1);
								}
							}
						  sortTable(0);
					  }else{
						  $("#projectionTable").find("tr:gt(0)").remove();
					  }
				  }else{
					  alert("No projections...");
				  }
						  });
});

function preventDot(id)
{
	str = document.getElementById(id).value;
	document.getElementById(id).value = (str.replace(".",""));
}
