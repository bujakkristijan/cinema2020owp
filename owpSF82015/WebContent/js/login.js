var role = localStorage.getItem('role');

if(( role == "admin" || role == "member")){
	alert("You are already logged in! ")
	window.location = "index.html";
}

function login()
{
	var username = document.getElementById("usernameInput").value;
	var password = document.getElementById("passwordInput").value;
	
	
		var letters = /^[A-Za-z0-9]+$/;
		console.log(username);
		console.log("asddqwdq");
		if(username == "")
		{
			alert('Please insert username');
		}else if(!letters.test(username))
		{
			alert('Username123 field required only alphanumeric characters');
		}
		else if(password =='')
		{
			alert('Please insert password');
		}
		else if(!letters.test(password))
		{
			alert('Password field required only alphanumeric characters');
		}
		else if(document.getElementById("passwordInput").value.length < 6)
		{
			alert ('Password minimum length is 6');
		}
		else{
			$.post('UserServlet',
			{
				action: "login",
				username : username,
				password : password
			},
			function(data){
				var obj = JSON.parse(data);
				if(obj.info == "success"){
					alert('Successful login! You are redirecting to welcome page');
					window.location = "index.html";
					
				}
				else if(obj.info == "errorUsernamePassword"){
					alert('Username or password error, try again');
						
					
				}
				else if(obj.info == "errorPasswordFalse"){
					alert('Wrong password, try again');
				}
				else if(obj.info == "errorUserDeleted"){
					alert('Can not log in! User deleted!');
				}
				else{
					alert('Try again.');
				}
				
			}
			);
		}
	}
	
	function clearForm()
	{
	document.getElementById("usernameInput").value =  "";
	document.getElementById("passwordInput").value = "";
	document.getElementById("confirmPasswordInput").value = "";
	
	}