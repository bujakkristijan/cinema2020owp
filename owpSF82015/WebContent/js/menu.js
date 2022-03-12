
$.post('UserServlet',
	{
		action: "session"
	},
	function(data){
		var obj = JSON.parse(data);
		localStorage.setItem('role', obj.role);
		localStorage.setItem('username', obj.username);
		localStorage.setItem('password', obj.password);
		localStorage.setItem('userId', obj.userId);
		localStorage.setItem('deleted', obj.deleted);
		if(obj.role == "member"){
			$("#login_btn, #registration_btn,#userList_btn, #movieInsert_btn, #projectionInsert_btn").hide();
			$("#logout_btn, #profile_btn, #movieList_btn").show();
			
		}else if(obj.role == "admin"){
			$("#login_btn, #registration_btn").hide();
			$("#logout_btn, #profile_btn,#userList_btn, #movieList_btn, #movieInsert_btn, #projectionInsert_btn").show();
		}
		else{
			$("#login_btn, #registration_btn, #movieList_btn").show();
			$("#logout_btn, #profile_btn,#userList_btn, #movieInsert_btn, #projectionInsert_btn").hide();
		}
		
	});
	
	
	
	