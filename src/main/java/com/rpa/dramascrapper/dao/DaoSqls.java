package com.rpa.dramascrapper.dao;

public class DaoSqls {
    public static final String INSERT_DRAMA_INTO_RESERVED = "INSERT INTO reserved_drama_list(drama_id) VALUES (:id)";
    public static final String SELECT_RESERVED_DRAMAS = "SELECT drama.id, drama.name, drama.first_airing_date FROM reserved_drama_list as reserved JOIN dramas AS drama ON reserved.drama_id = drama.id";
    public static final String DELETE_DRAMAS_FROM_RESERVED = "DELETE FROM reserved_drama_list WHERE drama_id = :id";

    public static final String SELECT_WATCHED_DRAMA_BY_ID = "SELECT drama.id, drama.name, drama.first_airing_date, watched.last_watched_season FROM watched_drama_list as watched JOIN dramas AS drama ON (watched.drama_id = drama.id) WHERE drama.id = :dramaId";
    public static final String INSERT_DRAMAS_INTO_WATCHED = "INSERT INTO watched_drama_list(drama_id) values (:id)";
    public static final String SELECT_WATCHED_DRAMAS = "SELECT drama.id, drama.name, drama.first_airing_date, watched.last_watched_season FROM watched_drama_list as watched JOIN dramas AS drama ON watched.drama_id = drama.id";
    public static final String DELETE_DRAMAS_FROM_WATCHED= "DELETE FROM watched_drama_list WHERE drama_id = :dramaId";
    public static final String UPDATE_DRAMA_LAST_WATCHED_SEASON = "UPDATE watched_drama_list SET last_watched_season = :lastWatchedSeason WHERE drama_id = :dramaId";
}
