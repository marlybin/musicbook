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
	<script type="text/javascript" src="js/groupe.js"></script>
</head>
<body>

<header id="menu">

	<div id="account">
		Bienvenue <span class="name"><%=username%></span> !
		<span class="account_menu">
			
			<a href="Connection?mode=exit">Déconnection</a>
		</span>
	</div>


	<div id="player_controller">
		<button class="player_button" id="player_play">play</button>
		<button class="player_button" id="player_pause">pause</button>
		<button class="player_button" id="player_stop">stop</button>
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
			<span class="menu_item">Mon mur</span>
		</a>
		<a href="Group">
			<span class="menu_item">Groupes</span>
		</a>
		<a href="">
			<span class="menu_item">Envoyer</span>
		</a>
		<a href="Home">
			<span class="menu_item">Mes playlists</span>
		</a>
	
	</div>
	
	<div id="file_holder"><span>Envoyer des fichiers</span></div> 

</header>

<div id="contain">
	
	<header id="action_menu">
	
		<div id="searchbar">
			<input>
		</div>
		
		<button id="play_all">Créer un groupe</button>
		<button id="play_all">Rechercher un groupe</button>
		
	</header>
	
	<div class="group_list">
	
		<h1>Mes groupes</h1>
		
		<span class="group_item">
			<img src="" />
			<span> Groupe 1 </span>
		</span>
		<span class="group_item">
			<img src="" />
			<span> Groupe 2 </span>
		</span>
		<span class="group_item">
			<img src="" />
			<span> Groupe 3 </span>
		</span>
		<span class="group_item">
			<img src="" />
			<span> Groupe 4 </span>
		</span>
		<span class="group_item">
			<img src="" />
			<span> Groupe 5 </span>
		</span>
		<span class="group_item">
			<img src="" />
			<span> Groupe 6 </span>
		</span>
		
	</div>
	
	<div class="group_list">
	
		<h1>Vous allez peut-être aimer ...</h1>
		
		<span class="group_item">
			<img src="" />
			<span> Groupe 1 </span>
		</span>
		<span class="group_item">
			<img src="" />
			<span> Groupe 2 </span>
		</span>
		<span class="group_item">
			<img src="" />
			<span> Groupe 3 </span>
		</span>
		
	</div>
	
</div>

</body>
</html>