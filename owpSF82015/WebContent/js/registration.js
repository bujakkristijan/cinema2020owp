var role = localStorage.getItem('role');
function registration()
{
	var username = document.getElementById("usernameInput").value;
	var password = document.getElementById("passwordInput").value;
	var confirmPassword = document.getElementById("confirmPasswordInput").value;
	
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
		else if(confirmPassword =='')
		{
			alert('Please confirm password');
		}
		else if(!letters.test(confirmPassword))
		{
			alert('Confirm password field required only alphanumeric characters');
		}
		else if (password != confirmPassword)
		{
			alert('Password not matched');
		}
		else if(document.getElementById("passwordInput").value.length < 6)
		{
			alert ('Password minimum length is 6');
		}
		else{
			console.log("ime :" + username);
			$.post('UserServlet',
			{
				action: "registration",
				username : username,
				password : password,
				role: role
			},
			function(data){
				var obj = JSON.parse(data);
				if(obj.info == "success"){
					alert('Thank you for registration! You are redirecting to Login page');
					window.location = "login.html";
					
				}
				else if(obj.info == "errorUsernamePassword"){
					alert('Username or password error, try again');
						
				}
				else if(obj.info == "errorUserExist"){
					alert('Username already exist, try another one');			
				}
				else if(obj.info == "errorNotAuthorized"){
					alert('Admins and members can not register!');			
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