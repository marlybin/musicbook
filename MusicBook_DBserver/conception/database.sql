/* Un utilisateur. */
 CREATE TABLE User(
 	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY , 
	name VARCHAR(40) NOT NULL ,
	password VARCHAR(40) NOT NULL ,
	mail VARCHAR(60) ,
	date DATE
);

/* Genre des musique. */
CREATE TABLE GenreMusic (
	id INT  AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (40) NOT NULL
);

/* Relation d'amitié entre deux personnes. */
CREATE TABLE Amis(
	id1_user INT ,
	id2_user INT ,
	type VARCHAR(40) ,
	FOREIGN KEY (id1_user)REFERENCES User (id),
	FOREIGN KEY (id2_user)REFERENCES User (id)
);

/* Les musiques. */
CREATE TABLE Music(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
	id_user INT , 
	title VARCHAR(40) ,
	artiste VARCHAR(40),
	genre INT,
	FOREIGN KEY(genre) REFERENCES GenreMusic(id),
	FOREIGN KEY(id_user) REFERENCES User(id)
);

/* Une communauté. */
CREATE TABLE Community (
	id INT  AUTO_INCREMENT PRIMARY KEY ,
	id_user INT,
	title VARCHAR(60),
	FOREIGN KEY(id_user) REFERENCES User(id)
);

/* Table relationnelle de membres appartenant a une communauté. */
CREATE TABLE Community_member (id_user INT, 
	id_community INT, 
	grade INT,
	FOREIGN KEY (id_user)REFERENCES User (id),
	FOREIGN KEY (id_community)REFERENCES Community (id)
);