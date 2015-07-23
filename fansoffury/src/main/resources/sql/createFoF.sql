/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V7.3.6                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2015-07-22 22:21                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Add tables                                                             */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "player"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE player (
    qr_code CHARACTER VARYING(40)  NOT NULL,
    CONSTRAINT PK_player PRIMARY KEY (qr_code)
);

/* ---------------------------------------------------------------------- */
/* Add table "player_session"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE player_session (
    id SERIAL  NOT NULL,
    qr_code CHARACTER VARYING(10)  NOT NULL,
    start_datetime TIMESTAMP  NOT NULL,
    end_datetime TIMESTAMP,
    headset CHARACTER VARYING(20)  NOT NULL,
    fan CHARACTER VARYING(20),
    measurement_type CHARACTER VARYING(10)  NOT NULL,
    CONSTRAINT PK_player_session PRIMARY KEY (id)
);

CREATE UNIQUE INDEX AK_player_session ON player_session (headset) where end_datetime is null;

/* ---------------------------------------------------------------------- */
/* Add table "score"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE score (
    session_id SERIAL  NOT NULL,
    score_datetime TIMESTAMP(6)  NOT NULL,
    CONSTRAINT PK_score PRIMARY KEY (session_id, score_datetime)
);

/* ---------------------------------------------------------------------- */
/* Add table "measurement"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE measurement (
    session_id SERIAL  NOT NULL,
    measure_type CHARACTER VARYING(20)  NOT NULL,
    measure_datetime TIMESTAMP(6)  NOT NULL,
    value INTEGER  NOT NULL,
    CONSTRAINT PK_measurement PRIMARY KEY (session_id, measure_type, measure_datetime)
);

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE score ADD CONSTRAINT player_session_score 
    FOREIGN KEY (session_id) REFERENCES player_session (id);

ALTER TABLE measurement ADD CONSTRAINT player_session_measurement 
    FOREIGN KEY (session_id) REFERENCES player_session (id);

ALTER TABLE player_session ADD CONSTRAINT player_player_session 
    FOREIGN KEY (qr_code) REFERENCES player (qr_code);
