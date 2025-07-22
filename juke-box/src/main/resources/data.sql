DROP TABLE IF EXISTS musicians;
DROP TABLE IF EXISTS music_albums;

CREATE TABLE music_albums (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    album_name VARCHAR(36) NOT NULL,
    date_of_release DATETIME2 NOT NULL,
    genre VARCHAR(36),
    price BIGINT NOT NULL,
    description VARCHAR(36));

CREATE TABLE musicians (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(36) NOT NULL,
    musician_type VARCHAR(36),
    music_album_id INT NOT NULL,
    FOREIGN KEY(music_album_id) REFERENCES music_albums);