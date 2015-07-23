/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V7.3.6                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database drop script                            */
/* Created on:            2015-07-22 22:21                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Drop foreign key constraints                                           */
/* ---------------------------------------------------------------------- */

ALTER TABLE score DROP CONSTRAINT player_session_score;

ALTER TABLE measurement DROP CONSTRAINT player_session_measurement;

ALTER TABLE player_session DROP CONSTRAINT player_player_session;

/* ---------------------------------------------------------------------- */
/* Drop table "measurement"                                               */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE measurement DROP CONSTRAINT PK_measurement;

DROP TABLE measurement;

/* ---------------------------------------------------------------------- */
/* Drop table "score"                                                     */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE score DROP CONSTRAINT PK_score;

DROP TABLE score;

/* ---------------------------------------------------------------------- */
/* Drop table "player_session"                                            */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE player_session DROP CONSTRAINT PK_player_session;

DROP TABLE player_session;

/* ---------------------------------------------------------------------- */
/* Drop table "player"                                                    */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE player DROP CONSTRAINT PK_player;

DROP TABLE player;
