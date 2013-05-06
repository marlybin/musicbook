<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String username = session.getAttribute("username").toString();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MusicBook</title>

	<link rel="stylesheet" type="text/css" href="css/style.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/code.js"></script>
</head>
<body>

<header id="menu">

	<div id="account">
		<span class="account_menu">
		
			<a href="">
				<img src="images/param.png" class="account_icon" id="param_icon">
			</a>
		
			<a href="Connection?mode=exit">
				<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfBQkAz1KEXUjMXuNhCKAzwgpeE4qtTy2-ak7UrrdBNp6VegdMJA" class="account_icon" >
			</a>
		</span>
		<img id="avatar">
		<span class="name"><%=username%></span>
	</div>


	<div id="player_controller">
		<button class="player_button" id="player_play">play</button>
		<button class="player_button" id="player_pause">pause</button>
		<button class="player_button" id="player_stop">stop</button>
		<button class="player_button" id="player_prev">previous</button>
		<button class="player_button" id="player_next">next</button>
		
		<progress max=100 id="music_progress_bar"></progress>

		<div id="player_soundControllers">
			<button id="player_sounddown">-</button>
			<progress max=100 id="sound_bar"></progress>
			<button id="player_soundup">+</button>
		</div>
		
		<div id="current_playlist"></div>

	</div>
	<div id="main_menu">
	
		<a href="Home">
			<span class="menu_item" id="menu_mymusic">Mes musiques</span>
		</a>
		<a href="Group">
			<span class="menu_item" id="menu_group">Communautés</span>
		</a>
		<a href="Discover">
			<span class="menu_item" id="menu_discover">Découverte</span>
		</a>
	
	</div>

</header>

<header id="action_menu">
	
	<div id="searchbar"><!-- 
		<input class="search">
		<input class="dosearch" type="submit" value="chercher"> -->
	</div>
	<div id="action_buttons">
	</div>
</header>

<div id="contain">

	<div id="presentation">
	
		<h1>Bienvenue sur musicbook !</h1>
		<div class="presentation_item">
			<img src="images/addthis.png"><br />
			Partage ta musique
		</div>
		
		<div class="presentation_item">
			<img src="images/sharethis.png"><br />
			Rejoint des communautés qui ont les mêmes goûts musicaux que toi!
		</div>
	
	</div>

</div>

</body>
</html>