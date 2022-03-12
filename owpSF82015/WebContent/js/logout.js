$("#logout_btn").on("click", function(){
	
	
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
			location.reload();
			window.location = "login.html";
		
		}
		else{
		
			alert("Try again!");
		}
		
	});
	
	
});