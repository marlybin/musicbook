<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<link rel="stylesheet" type="text/css" href="css/style.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/script.js"></script>
</head>
<body>

	<div id="connection_panel">
		
		<form action="Connection" method="post">
			
			<fieldset>
			<legend>Connection</legend>
			
				<% if( request.getParameter("error")!=null ){ out.println("<span class=\"error_message\">Erreur d'identifiant</span>"); } %>
				<label for="connection_login"> Login : </label>
				<input name="login" type="text" id="connection_login"><br />
				<label for="connection_password"> Password : </label>
				<input name="password" type="password" id="connection_password"><br />
				<input type="submit" value="envoyer">
			</fieldset>
		</form>

		<a href="Subscribe">Créer un compte</a>
	</div>

</body>
</html>

<script>
	$("#connection_login").focus()
</script>