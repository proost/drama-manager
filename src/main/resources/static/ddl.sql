CREATE TABLE dramas (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    first_airing_date DATE NOT NULL,
    CONSTRAINT drama_series UNIQUE (name, first_airing_date),
    primary key(id)
);

CREATE TABLE reserved_drama_list (
    id INT NOT NULL AUTO_INCREMENT,
    drama_id INT,
    primary key(id)
);

CREATE TABLE watched_drama_list (
    id INT NOT NULL AUTO_INCREMENT,
    drama_id INT,
    last_watched_season INT DEFAULT 0,
    primary key (id)
);