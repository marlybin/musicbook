$(document).ready(init)

var player 	= null
var current = null
var sound_level = 50

var list= [
		{"artist":"Ne-yo","title":"So sick", "src":"userData/so sick.mp3"},
		{"artist":"Falvour","title":"Nwa Baby", "src":"userData/flavour.mp3"}
	]

var current_playlist = [
	]

/*
 * Fonction qui se lance au démarrage de la page
 */
function init(){

	// Controllers initialisation
	$("#player_play").click( player_play )
	$("#player_pause").click( player_pause )
	$("#player_stop").click( player_stop )
	$("#player_next").click( player_next )
	
	$("#player_sounddown").click( player_sounddown )
	$("#player_soundup").click( player_soundup )
	
	player = document.createElement("audio")
	player.src = null
	
	$("#play_all").click(play_all)
	$("#sound_bar").val( sound_level )
	$("#music_progress_bar").val( 0 )
	player.volume = sound_level/100

	show_list()

	setInterval(function(){
		$("#music_progress_bar").val( player.currentTime *100 / player.duration )
		
		// Si on est à la fin de la chanson.
		if( player.currentTime == player.duration ){
			player_next()
		}
	},
	200)

	display_current_playlist()
}

// Player functions

function play_all( e ){
	
	current = null
	show_player()
	current_playlist = list
	player_next()
	display_current_playlist()
}
/*
 * Joue une musique de la liste.
 */
function play_one( i ){

	current = null
	show_player()
	current_playlist = new Array( list[i] )
	player_next()
	display_current_playlist()
}

/*
 * Affiche le player.
 */
function show_player( i ){
	
	$("#player_controller").css("opacity","1")
	$("#player_controller").css("height","320px")
	
}

/*
 * Actualize l'affichage de la playlist.
 */
function display_current_playlist(){

	$("#current_playlist").html("")
	
	$(current_playlist).each(function(i){
		if( i==current ){
			
			var item = $("<span class=\"item current\">" + current_playlist[i].title + " - "+ current_playlist[i].artist + "</span>")
			$("#current_playlist").append(item)
			// play_one
		}
		else{
			var item = $("<span class=\"item\">" + current_playlist[i].title + " - "+ current_playlist[i].artist + "</span>")
			$("#current_playlist").append(item)
		}
	})
}

function player_next(){

	player.pause()
	if( current == null ){
		current = 0

		player.src=current_playlist[current].src
		player_play()
	}
	else{
		current++
		if( current == current_playlist.length ){
			current=null
			close_player()
		}
		else{

			player.src=current_playlist[current].src
			player_play()
		}
	}
	// Actualise l'affichage de la playlist
	display_current_playlist()
}

function player_play( e ){

	player.play()
}

function player_pause( e ){
	
	player.pause()
}

function player_stop( e ){

	close_player()
	
	player.pause()
	player.currentTime = 0
}

/*
 * Ferme le player.
 */
function close_player(){

	$("#player_controller").css("opacity","0")
	$("#player_controller").css("height","0")
}

function player_soundup( e ){
	
	sound_level += 10
	
	if( sound_level < 0 ){
		sound_level = 0
	}
	$("#sound_bar").val( sound_level )
	player.volume = sound_level/100
}

function player_sounddown( e ){

	sound_level -= 10
	
	if( sound_level > 100 ){
		sound_level = 100
	}
	$("#sound_bar").val( sound_level )
	player.volume = sound_level/100
}

// Displaying functions

function show_list(){

	var ul = $("#contain_list")
	ul.html("")
	$(list).each( function( i ){

		var li = $('<li><span class="user">tony</span>'+
				'<span class="title">'+list[i].title+'</span>'+
				'<span class="artist">'+list[i].artist+'</span>'+
				'<span class="reputation"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJs8YNiGBse66rjRgYRvhpoWbrRttmpi4yc3zTeAqPldUPSqpv"></span>'+
				'<span class="comments">Pas de commentaires</span></li>')
		li.click( function(){play_one(i)} )
		ul.append(li)
	} )
}




