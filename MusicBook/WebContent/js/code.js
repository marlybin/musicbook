$(document).ready(init);

// Constantes configurables.
var DISCOVERING_DURATE 	= 20000;
var FADIN_DURATE		= 1000;
var FADOUT_DURATE		= 1000;

// Diverses constantes
var NO			= 0;
var PAUSE		= 1;
var PLAY_LIST	= 2;
var DISCOVER	= 3;
var BLOCKED		= 4;

var player 			= null;
var current 		= null;
var sound_level 	= 50;
var current_fadout 	= null;

var playing_mode 	= NO;

// Page dans laquelle on est actuellement
var current_page = 0;

var list=[];

var current_playlist = [];

var discover_loop = null;
var player_ended_event;

/*
 * Fonction qui se lance au démarrage de la page
 */
function init(){

	// Initialisation des boutons du menu.
	$("#menu_mymusic")		.click( goto_mymusic );
	$("#menu_group")		.click( goto_grouplist );
	$("#menu_discover")		.click( goto_discover );
	$("#param_icon")		.click( goto_param );
	
	// Music controllers initialisation
	$("#player_play")		.click( player_play );
	$("#player_pause")		.click( player_pause );
	$("#player_stop")		.click( player_stop );
	$("#player_next")		.click( player_next );
	
	$("#player_sounddown")	.click( player_sounddown );
	$("#player_soundup")	.click( player_soundup );
	$("#music_progress_bar").click( player_goto );
	
	player = document.createElement("audio");
	player.src = null;
	
	$("#play_all")				.click(play_all);
	$("#sound_bar")				.val( sound_level );
	$("#music_progress_bar")	.val( 0 );
	
	$(document).keydown( shortcut );
	
	player.volume = sound_level/100;

	setInterval(function(){
		
		$("#music_progress_bar").val( player.currentTime * 100 / player.duration );
	}, 200);

	display_current_playlist();
}

// Navigation

/*
 * Raccourcis clavier
 */
function shortcut( key ){
	
	//alert(key.which)
	switch( key.which ){
	
	case 107:
		player_soundup( 10 );
		break;
		
	case 109:
		player_sounddown( 10 );
		break;

	case 78:
		player_next();
		break;
		
	case 80:
		player_pause();
		break;
	}
}

/*
 * Affiche la page "mes musiques".
 */
function goto_mymusic( e ){

	e.preventDefault();
	if( current_page != 1 ){
		current_page = 1;
		$("#contain").css("opacity",0);
		$("#action_buttons").css("opacity","0");
		$("#action_buttons").css("margin-top","-50px");
		setTimeout(function(){show_list();}, 500);
	}
}

/*
 * Affiche la liste des groupes.
 */
function goto_grouplist( e ){

	e.preventDefault();
	if( current_page != 2 ){
		current_page = 2;
		$("#contain").css("opacity",0);
		$("#action_buttons").css("opacity","0");
		$("#action_buttons").css("margin-top","-50px");
		setTimeout(function(){show_group();}, 500);
	}
}

/*
 * Affiche la page découverte
 */
function goto_discover(e){
	
	e.preventDefault();
	if( current_page != 3 ){
		current_page = 3;
		$("#contain").css("opacity",0);
		$("#action_buttons").css("opacity","0");
		$("#action_buttons").css("margin-top","-50px");
		setTimeout(function(){show_discover();}, 500);
	}
}

/*
 * Affiche la liste des groupes.
 */
function goto_param( e ){

	e.preventDefault();
	if( current_page != 4 ){
		current_page = 4;
		$("#contain").css("opacity",0);
		$("#action_buttons").css("opacity","0");
		$("#action_buttons").css("margin-top","-50px");
		setTimeout(function(){show_param();}, 500);
	}
}

// Player functions

function play_all( e ){
	
	current = null;
	playing_mode = PLAY_LIST;
	show_player();
	current_playlist = list;
	player_next();
	player.addEventListener('ended', player_next);
	display_current_playlist();
}
/*
 * Joue une musique de la liste.
 */
function play_one( i ){

	current = null;
	playing_mode = PLAY_LIST;
	show_player();
	current_playlist = new Array( list[i] );
	player_next();
	display_current_playlist();
}

/*
 * Affiche le player.
 */
function show_player( i ){
	
	$("#player_controller").css("opacity","1");
	$("#player_controller").css("height","320px");
	
}

/*
 * Actualise l'affichage de la playlist.
 */
function display_current_playlist(){
	
	$("#current_playlist").html("");
	
	$(current_playlist).each(function(i){
		if( i==current ){
			
			var item = $("<span class=\"item current\" id=\"current_item\">" + current_playlist[i].title + " - "+ current_playlist[i].artist + "</span>");
			$("#current_playlist").append(item);
			// play_one
		}
		else{
			var item = $("<span class=\"item\">" + current_playlist[i].title + " - "+ current_playlist[i].artist + "</span>");
			$("#current_playlist").append(item);
		}
	});
	
	// Hum ... beurk
	var scroll_playlist = $("#current_playlist").scrollTop() + ( $("#current_item").position().top - $("#current_playlist").position().top ) - 50;
	if( scroll_playlist>0 )
		$("#current_playlist").scrollTop( scroll_playlist );
}

/*
 * Musique suivante.
 */
function player_next(){

	player.pause();
	if( current == null ){
		current = 0;

		player.src=current_playlist[current].src;
		player_play();
		
		if( current_playlist.length>1 )
			player_ended_event = player.addEventListener('ended', player_next, false);
	}
	else{
		current++;
		if( current == current_playlist.length ){
			current=null;
			player_stop();
			player.removeEventListener( 'ended', player_ended_event );
			close_player();
		}
		else{

			player.src=current_playlist[current].src;
			player_play();
		}
	}
	// Actualise l'affichage de la playlist
	display_current_playlist();
}

/*
 * Joue la musique courante.
 */
function player_play( e ){

	playing_mode = PLAY_LIST;
	player.play();
}

/*
 * Stoppe la lecture en cours.
 */
function player_pause( e ){
	
	if( playing_mode==PAUSE ){
		player_play();
	}
	else{
		playing_mode = PAUSE;
		player.pause();
	}
}

/*
 * Stoppe tout et ferme le lecteur.
 */
function player_stop( e ){

	close_player();
	playing_mode=NO;
	player.pause();
	player.currentTime = 0;
}

/*
 * Effet fadin.
 */
function fadin( time ){
	
	player.volume += (( sound_level - ( player.volume*100 ) )/( time/200 ))/100;
	if( time>=200 ){
		setTimeout(function(){
			fadin( time-200 );
		},200);
	}
	else{
		player.volume = sound_level/100;
	}
}

/*
 * Effet fadout
 */
function fadout( time ){
	
	player.volume += (( 0 - ( player.volume*100 ) )/( time/200 ))/100;
	if( time>=200 ){
		setTimeout(function(){
			fadout( time-200 );
		},200);
	}
	else{
		player.volume = 0;
	}
}

/*
 * Saute dans la musique courante à l'endroit où l'utilisateur à cliqué.
 */
function player_goto( e ){
	
	var relative_x = e.pageX - $("#music_progress_bar").offset().left;
	var music_time = relative_x * player.duration / $("#music_progress_bar").width();
		
	player.currentTime = music_time;
	
}

/*
 * Mode découverte, joue 'DISCOVERING_DURATE' secondes de musiques à faire découvrir à l'utilisateur.
 */
function start_discover( e ){
	
	if( playing_mode == PLAY_LIST){
		alert("cela va stopper la lecture en cour !");
	}
	
	playing_mode = DISCOVER;
	
	$("#begin_discover_button").html	("Stop");
	$("#begin_discover_button").unbind	("click");
	$("#begin_discover_button").click	(stop_discover);
	
	// Effet de fadin au début de la musique.
	player.volume = 0;
	fadin( FADIN_DURATE );
	current_fadout = setTimeout(function(){ fadout( FADOUT_DURATE ); }, DISCOVERING_DURATE-FADOUT_DURATE);
	
	player_next();

	$("#discover_info")		.css("height","150px");
	$("#discover_info")		.css("opacity",1);
	$("#discover_title")	.html( current_playlist[current].title );
	$("#discover_artist")	.html( current_playlist[current].artist );
	
	player.addEventListener('canplaythrough', startLater, false);
	
	// Boucle de lecture du mode découverte.
	discover_loop = setInterval( function(){

		$("#discover_info").css("opacity",0);
		
		// Effet de fadout à la fin de la chanson.
		current_fadout = setTimeout(function(){ 
			$("#discover_title")	.html( current_playlist[current].title );
			$("#discover_artist")	.html( current_playlist[current].artist );
			$("#discover_info")		.css("opacity",1);
		}, 500);
		player.volume = 0;
		
		// Effet de fadin au début de la nouvelle chanson.
		fadin(FADIN_DURATE);
		setTimeout(
			function(){ fadout( FADOUT_DURATE ); },
			DISCOVERING_DURATE - FADOUT_DURATE
		);
		
		player_next();
		
	},DISCOVERING_DURATE );
}

/*
 * Démarre la musque un peu après le début pour le mode découvert.
 */
function startLater(){
	player.currentTime = 20;
}

/*
 * Enregistre un like sur la musique en cours.
 */
function like_discover(){
	
	alert( "j'aime "+current_playlist[current].id+" !!" );
}

/*
 * Arrête de mode découverte.
 */
function stop_discover( e ){

	$("#discover_info").css("height",0);
	$("#discover_info").css("opacity",0);
	player.removeEventListener( 'canplaythrough',startLater,false );
	player.pause();
	current++;
	playing_mode = NO;
	clearTimeout(current_fadout);
	clearInterval(discover_loop);
	$("#begin_discover_button").html("Commencer");
	$("#begin_discover_button").unbind("click");
	$("#begin_discover_button").click(start_discover);
}

/*
 * Passe à la musique suivante.
 */
function switch_discover( e ){
	
	clearTimeout(current_fadout);
	current_fadout = null;
	stop_discover(e);
	start_discover(e);
}

/*
 * Ferme le player.
 */
function close_player(){

	$("#player_controller").css("opacity","0");
	$("#player_controller").css("height","0");
}

function player_soundup( e ){
	
	sound_level += 10;
	
	if( sound_level < 0 ){
		sound_level = 0;
	}
	$("#sound_bar").val( sound_level );
	player.volume = sound_level/100;
}

function player_sounddown( e ){

	sound_level -= 10;
	
	if( sound_level > 100 ){
		sound_level = 100;
	}
	$("#sound_bar").val( sound_level );
	player.volume = sound_level/100;
}

/*
 * Affiche la page des musiques.
 */
function show_list(){

	// Requête au serveur la liste des musiques qui concernent l'utilisateur.
	$.ajax({
		
        url			: "Playlist",
        data   		: "",
        cache		: false,
        dataType	: "json",
        error		: function(request, error) {},
		success		: function(data) {
			
			list = data;
			
			// Ecriture de la barre d'action en haut			
			$("#action_buttons").html	("");
			$("#action_buttons").append	("<button>Envoyer une musique</button>");
			
			var readall_button = $("<button>Tout lire</button>");
			readall_button.click(play_all);
			$("#action_buttons").append	(readall_button);

			$("#action_buttons").css	("margin-top","0");
			$("#action_buttons").css	("opacity","1");
			
			// Ecriture du contenu
			$("#contain").html("");
			$("#contain").append("<h1>Mes musiques</h1>");
			$("#contain").append("<ul id=\"contain_list\"></ul>");
			$("#contain").css("opacity","1");
			
			var ul = $("#contain_list");
			ul.html("");
			$(list).each( function( i ){

				var li = $('<li><span class="user">tony</span>'+
						'<span class="title">'	+ list[i].title + '</span>' +
						'<span class="artist">'	+ list[i].artist + '</span>' +
						'<span class="reputation"></span>'+
						'<span class="comments">Pas de commentaires</span></li>');
				li.click( function(){ play_one(i); } );
				ul.append(li);
			} );
		}
	});
}

/*
 * Affiche la page des groupes.
 */
function show_group(){

	// Ecriture de la barre d'action en haut
	$("#action_buttons").html("");
	$("#action_buttons").append("<button>Créer un groupe</button>");
	$("#action_buttons").css("margin-top","0");
	$("#action_buttons").css("opacity","1");
	
	// Ecriture du contenu
	$("#contain").html("");
	$("#contain").css("opacity","1");
	var grouplist = $("<div class=\"group_list\"></div>");
	grouplist.append("<h1>Mes groupes</h1>");
	
	grouplist.append("<span class=\"group_item\"><img src=\"http://www.tribune.com.ng/sat/images/stories/feb2010/jay%20martins.jpg\" /><span> Jay martins </span></span>");
	grouplist.append("<span class=\"group_item\"><img src=\"http://www.gbheld.com/upload/aa5e985a.png\" /><span> R'n'b </span></span>");
	
	$("#contain").append( grouplist );
}

/*
 * Affiche la page de découverte.
 */
function show_discover(){
	
	// Requête au serveur la liste des musiques à découvrir.
	$.ajax({
		
        url			: "Playlist?mode=discover",
        data   		: "",
        cache		: false,
        dataType	: "json",
		success		: function(data) {
			
			list 				= data;
			current				= null;
			current_playlist 	= list;
			
			// Ecriture de la barre d'action en haut
			$("#action_buttons").html("");
			$("#action_buttons").css("margin-top","0");
			$("#action_buttons").css("opacity","1");
			
			// Ecriture du contenu
			$("#contain").html("");
			$("#contain").css("opacity","1");
			$("#contain").append( "<h1>Découverte</h1>" );
			
			var begin		= $( "<button id=\"begin_discover_button\">Commencer !</button>" );
			var like_but	= $( "<button id=\"discover_like\">J'aime !</button>");
			var switch_but	= $( "<button id=\"discover_switch\">Switcher</button>");
			
			// Action
			begin		.click(start_discover);
			like_but	.click(like_discover);
			switch_but	.click(switch_discover);
			
			var discover_info_block = $("<div id=\"discover_info\"></div>");
			discover_info_block.append("<div id=\"discover_title\"></div><div id=\"discover_artist\"></div>");
			discover_info_block.append( like_but );
			discover_info_block.append( switch_but );

			$("#contain").append( discover_info_block );
			$("#contain").append( begin );
		}
	});
}

/*
 * Affiche la page des paramètres.
 */
function show_param(){

	$("#contain").html("");
	$("#contain").css("opacity","1");
	$("#contain").html("<h1>Paramètres</h1>");
}
