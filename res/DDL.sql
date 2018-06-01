-- ¬ерсии игры
CREATE TABLE GAMEVERSIONS (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   NAME CHAR(3) NOT NULL,
   FULLNAME VARCHAR(20),   
   URL VARCHAR(255),
   LASTUSED CHAR(1) DEFAULT 'N' NOT NULL ,
   FLAGIMG BLOB(2K),
   CONSTRAINT GAMEVER_PK PRIMARY KEY (ID),
   CONSTRAINT LASTUSED_CHK CHECK (LASTUSED IN ('Y','N')),
   UNIQUE(NAME)
)
-- NAME      - сокращенное наименование версии (англ.)
-- FULLNAME  - наименование версии
-- URL       - ссылка на странцу игры данной версии
-- LASTUSED  - признак последнего использовани€ версии в приложннии
-- FLAGIMG   - картинка с флагом версии

-- ёзеры
CREATE TABLE USERS (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   ALIAS VARCHAR(30) NOT NULL,
   PASSWORD VARCHAR(30) NOT NULL,
   VERSION CHAR(3) NOT NULL,
   LASTUSED CHAR(1) DEFAULT 'N' NOT NULL ,
   CONSTRAINT ID_PK PRIMARY KEY (ID),
   CONSTRAINT USERS_LASTUSED_CHK CHECK (LASTUSED IN ('Y','N')),
   FOREIGN KEY (VERSION) REFERENCES GAMEVERSIONS (NAME),
   UNIQUE (ALIAS, VERSION)
)
-- ALIAS    - логин 
-- PASSWORD - пароль 
-- VERSION  - ID версии игры
-- LASTUSED - признак последнего использовани€ в приложении дл€ выбранной версии

----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- «амен€ прив€занной версии с английского сокращен€ на ссылку на таблицу версии по ID
ALTER TABLE USERS DROP COLUMN  VERSION;
ALTER TABLE USERS ADD COLUMN VERSION INTEGER;
ALTER TABLE USERS ADD FOREIGN KEY (VERSION) REFERENCES GAMEVERSIONS(ID);
UPDATE USERS SET VERSION = 1;
ALTER TABLE USERS ALTER COLUMN VERSION SET NOT NULL;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- ѕри смене признака последнего использовани€ в таблице юзеров на Y (а так же при добавлении новой записи в таблицу), 
-- автоматически выставл€ют всем остальным запис€м с такой же версией признак N.

CREATE TRIGGER LASTUSED_USER
AFTER INSERT ON USER
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE USER SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND VERSION = N.VERSION AND ID <> N.ID;

CREATE TRIGGER LASTUSED_USER_UPD
AFTER UPDATE ON USER
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE USER SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND VERSION = N.VERSION AND ID <> N.ID;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- ѕри смене признака последнего использовани€ в таблице версий на Y, автоматически выставл€ет всем остальным запис€м признак N
CREATE TRIGGER LASTUSED_GAMEVERSIONS_UPD
AFTER UPDATE ON GAMEVERSIONS
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE GAMEVERSIONS SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND ID <> N.ID;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
--18/05/18
--–азбиение таблицы юзеров на USERS и ACCOUNTS со св€зочной таблицей USERTOACCOUNT.

-- ”дал€ем более не нужные триггеры
DROP TRIGGER LASTUSED_USER;
DROP TRIGGER LASTUSED_USER_UPD;

-- јккаугты
CREATE TABLE ACCOUNTS (
ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
PASSWORD VARCHAR(50) NOT NULL, 
VERSION INTEGER NOT NULL,
LASTUSED CHAR DEFAULT 'N' NOT NULL ,
CONSTRAINT ACCOUNTSID_PK PRIMARY KEY (ID),
CONSTRAINT GAMEVERSION_FK FOREIGN KEY (VERSION) REFERENCES GAMEVERSIONS(ID),
CONSTRAINT ACCOUNTS_LASTUSED_CHK CHECK (LASTUSED IN ('Y','N'))
);
-- PASSWORD - пароль 
-- VERSION  - ID версии игры
-- LASTUSED - признак последнего использовани€ в приложении дл€ выбранной версии

-- —в€зочна€ таблица аккаунтов и юзеров
CREATE TABLE USERTOACCOUNT (
USERID INTEGER NOT NULL,
ACCOUNTID INTEGER NOT NULL,
CONSTRAINT USER_FK FOREIGN KEY (USERID) REFERENCES USERS(ID),
CONSTRAINT ACCOUNT_FK FOREIGN KEY (ACCOUNTID) REFERENCES ACCOUNTS(ID),
CONSTRAINT IDS_UQ UNIQUE (USERID, ACCOUNTID)
);
-- USERID    - идентификатор ёзера
-- ACCOUNTID - идентификатор јккаунта
---------------------------------------------------
--JAVA 
SELECT ID, PASSWORD, LASTUSED, VERSION FROM USERS;
INSERT INTO ACCOUNTS VALUES (DEFAULT, :password, :versionid, :lastused);
SELECT MAX(ID) FROM ACCOUNTS;
INSERT INTO USERTOACCOUNT VALUES (:userid, :accountid);
-- JAVA END
---------------------------------------------------
-- ѕри смене признака последнего использовани€ в таблице јккаунтов на Y (а так же при добавлении новой записи в таблицу), 
-- автоматически выставл€ют всем остальным запис€м с такой же версией признак N.
CREATE TRIGGER LASTUSED_ACCOUNTS_INS
AFTER INSERT ON ACCOUNTS
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE ACCOUNTS SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND VERSION = N.VERSION AND ID <> N.ID;

CREATE TRIGGER LASTUSED_ACCOUNTS_UPD
AFTER UPDATE ON ACCOUNTS
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE ACCOUNTS SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND VERSION = N.VERSION AND ID <> N.ID;

-- ”даление колонок перенесенных в таблицу јккаунты
ALTER TABLE USERS DROP COLUMN PASSWORD;
ALTER TABLE USERS DROP COLUMN LASTUSED;
ALTER TABLE USERS DROP COLUMN VERSION;

-- ƒобавл€ем контроль уникальности дл€ јлиаса в таблице ёзеров
ALTER TABLE USERS ADD CONSTRAINT USERNAME_UQ UNIQUE (ALIAS);
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- “аблица настроек прогона
CREATE TABLE PROGRAMS (
 ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 NAME VARCHAR(255) NOT NULL,
 SETTINGS BLOB NOT NULL,
 CONSTRAINT NAME_UQ UNIQUE (NAME)
);
-- NAME      - название настроек
-- SETTINGS  - блоб с настройками



