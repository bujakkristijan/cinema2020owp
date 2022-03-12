var url = document.URL;
var stage = url.substring(url.lastIndexOf('?')+4, url.lastIndexOf(';'));
var movieId = url.substring(url.lastIndexOf(';')+4, url.lastIndexOf(":"));
var userId = localStorage.getItem('userId');
var projectionIdGlobal = 0;
var priceOneTicket = 0;
var listChecked = [];
var role = localStorage.getItem('role');

var role = localStorage.getItem('role');

if(( role != "member")){
	alert("Can't buy ticket! Only members can buy tickets!")
	window.location = "index.html";
	
}
else{
	$.post('ProjectionListServlet',
  {
    movieId: movieId,
    action:"projectionListBuyTicket"
  },
  
  function(data){ 
		$.post('MovieServlet',
			{
				action:"getMovie",
				movieId:movieId
			},
			function(data){
				if(data){
					var obj = JSON.parse(data);
					if(obj.info=="success"){
						document.getElementById("movie").value = obj.movieName;
					}else{
						alert("No movie...");
					}
				}
		});
				
	  if(stage == "1"){
			  $("#stage3").hide();
			  $("#stage2").hide();
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
									
										"<a href='projectionPage.html?id="+projection.id+"'>"+projection.date+"</a> </td><td>"+
											""+projection.hallName+"</td><td>" +
												""+projection.projectionTypeName+"</td><td>" +
													""+projection.price+"</td>" +
														""+"<td onclick=\"nextStage("+projection.id+")\"> Take it!"+ 
															""	+"</td></tr>"
															// "" se stavljaju da bi se polje tumacilo kao string
																 
																
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
	}else{
		var projectionId = url.substring(url.lastIndexOf(':')+1); // ako je dosao sa stranice projekcije pokrece se faza 2 
		nextStage(projectionId); 
	}
	  
  });
}



function nextStage(projectionId){ // pokrece se faza 2 
	projectionIdGlobal = projectionId; // zato sto je u else definisan a koristi se i u drugim funkcijama
	$("#projectionTable").hide();
	$("#stage2").show();
	$("#stage3").hide();
	
	$.post('ProjectionServlet',
			{
				action: "getProjection",
				projectionId: projectionId
			},
			function(data){
				if(data){
					var obj = JSON.parse(data);
					if(obj.info == "success"){
						document.getElementById("dateS2").value = obj.date;
						document.getElementById("typeS2").value = obj.projectionTypeName;
						document.getElementById("hallS2").value = "Hall: " + obj.hallName;
						document.getElementById("priceS2").value = "$ "+ obj.price;
						priceOneTicket = obj.price;
						var seatArray = [];
						var ticketArray = [];
						for(var num in obj.objListSeat){
							seatArray.push(obj.objListSeat[num].seatNumber);
						}
						for(var num in obj.objListTicket){
							ticketArray.push(obj.objListTicket[num].ticketSeatNumber);
						}
						
						for(var seat in seatArray){
							var pair = seatArray[seat]; // bice id i value od checkbox-a
							var label = document.createElement("label");
							var description = document.createTextNode(pair); // sta ce pisati pored checkbox-a
							var checkbox = document.createElement("input");
							
							checkbox.type = "checkbox"; // make the element a checkbox
							checkbox.name = "checkboxSeat"; // give it a name we can check on the server side
							checkbox.value = pair;
							checkbox.id = pair; // make its value "pair"
							checkbox.setAttribute("onclick", "seatClicked("+pair+")");
							label.appendChild(checkbox);
							label.appendChild(description);
							
							document.getElementById('takeSeat').appendChild(label); // na div u htmlu nakaci checkboxove 
							
							// <label><input type="checkbox" name="checkboxSeat" value="2" id="2" onclick="seatClicked(2)">2</label>
							
						}
						// prodate karte cekiramo i zabranjujemo 
						for(var num in obj.objListTicket){
							for(var num2 in obj.objListSeat){
								if(obj.objListTicket[num].ticketSeatNumber == obj.objListSeat[num2].seatNumber){
									
									$("#"+obj.objListTicket[num].ticketSeatNumber+"").prop('checked', true);
									$("#"+obj.objListTicket[num].ticketSeatNumber+"").prop('disabled', true);
									// $("#2").prop('disabled', true);  npr ako sediste 2 ima kupljenu kartu disablovace se 
									if(obj.objListTicket[num].deleted == 1 ){
										$("#"+obj.objListTicket[num].ticketSeatNumber+"").prop('checked', false);
										$("#"+obj.objListTicket[num].ticketSeatNumber+"").prop('disabled', false);
									}
								}
							}
						}
					}
					else{
						alert("No projections...")
					}
				}else{
					alert("No datas...");
				}
			}
			);
	
	
}

function seatClicked(number){
	
	var checkbox = document.getElementById(""+number+"");
	var checked = $("#"+number+"").is(':checked'); // u zavisnosti koji checkbox je stisnut (number), proverimo da li je cekiran
	if(checked){
		listChecked.push(number);
	}
	if(!checked){ // ako je decekiran izbacujemo ga iz liste
		var index = listChecked.indexOf(number);
		listChecked.splice(index, 1); // izbaci jedan element sa pozicije index 
		
	}
	//alert(listChecked);
	
}

function nextStage3(){
	
	if(!Array.isArray(listChecked) || !listChecked.length){ // proveramo da li postoji lista i da li je prazna
		alert("Must check at least one seat!");
		
	}
	else{
		$("#projectionTable").hide();
		$("#hideAfterStage2").hide(); //sakriva checkboxove i next dugme
		$("#stage3").show();
		var finalPrice = 0;
		finalPrice = priceOneTicket *  listChecked.length;
		document.getElementById("seatS3").value = "Seat/s: " + listChecked;
		document.getElementById("finalPriceS3").value = "Final price: $"+ finalPrice;
	}
}

function buyTicket(){
	
	$.post('TicketServlet',
		{
			action:"buyTicket",
			movieId: movieId,
			projectionId: projectionIdGlobal,
			listChecked: listChecked.toString(), 
			userId: userId,
			role: role
		},
		function(data){
			if(data){
				var obj = JSON.parse(data);
				//console.log(obj.info);
				if(obj.info == "success"){
					alert("Thank you!")
					window.location = "index.html";
					
				}else if(obj.info == "errorNotAuthorized"){
					alert("Admins and unsigned users can not buy tickets");
				}else if(obj.info == "errorDateTime"){
					alert("You can not buy ticket for projections in past!");
				}
				else{
					alert("Try again!");
				}
			}
		}
		)
}

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


function preventDot(id)
{
	str = document.getElementById(id).value;
	document.getElementById(id).value = (str.replace(".",""));
}

function moviePage(){
	window.location = "moviePage.html?id=" + movieId ;
}

function projectionPage(){
	var projectionId = url.substring(url.lastIndexOf(':') +1);
	projectionIdGlobal = projectionId;
	window.location = "projectionPage.html?id="+projectionId;
}
