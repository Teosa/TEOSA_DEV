-- Версии игры
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
-- LASTUSED  - признак последнего использования версии в приложннии
-- FLAGIMG   - картинка с флагом версии

-- Юзеры
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
-- LASTUSED - признак последнего использования в приложении для выбранной версии

----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- Заменя привязанной версии с английского сокращеня на ссылку на таблицу версии по ID
ALTER TABLE USERS DROP COLUMN  VERSION;
ALTER TABLE USERS ADD COLUMN VERSION INTEGER;
ALTER TABLE USERS ADD FOREIGN KEY (VERSION) REFERENCES GAMEVERSIONS(ID);
UPDATE USERS SET VERSION = 1;
ALTER TABLE USERS ALTER COLUMN VERSION SET NOT NULL;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- При смене признака последнего использования в таблице юзеров на Y (а так же при добавлении новой записи в таблицу), 
-- автоматически выставляют всем остальным записям с такой же версией признак N.

CREATE TRIGGER LASTUSED_USER
AFTER INSERT ON USERS
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE USERS SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND VERSION = N.VERSION AND ID <> N.ID;

CREATE TRIGGER LASTUSED_USER_UPD
AFTER UPDATE ON USERS
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE USERS SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND VERSION = N.VERSION AND ID <> N.ID;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- При смене признака последнего использования в таблице версий на Y, автоматически выставляет всем остальным записям признак N
CREATE TRIGGER LASTUSED_GAMEVERSIONS_UPD
AFTER UPDATE ON GAMEVERSIONS
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE GAMEVERSIONS SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND ID <> N.ID;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
--18/05/18
--Разбиение таблицы юзеров на USERS и ACCOUNTS со связочной таблицей USERTOACCOUNT.

-- Удаляем более не нужные триггеры
DROP TRIGGER LASTUSED_USER;
DROP TRIGGER LASTUSED_USER_UPD;

-- Аккаугты
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
-- LASTUSED - признак последнего использования в приложении для выбранной версии

-- Связочная таблица аккаунтов и юзеров
CREATE TABLE USERTOACCOUNT (
	USERID INTEGER NOT NULL,
	ACCOUNTID INTEGER NOT NULL,
	CONSTRAINT USER_FK FOREIGN KEY (USERID) REFERENCES USERS(ID),
	CONSTRAINT ACCOUNT_FK FOREIGN KEY (ACCOUNTID) REFERENCES ACCOUNTS(ID),
	CONSTRAINT IDS_UQ UNIQUE (USERID, ACCOUNTID)
);
-- USERID    - идентификатор Юзера
-- ACCOUNTID - идентификатор Аккаунта
---------------------------------------------------
--JAVA 
SELECT ID, PASSWORD, LASTUSED, VERSION FROM USERS;
INSERT INTO ACCOUNTS VALUES (DEFAULT, :password, :versionid, :lastused);
SELECT MAX(ID) FROM ACCOUNTS;
INSERT INTO USERTOACCOUNT VALUES (:userid, :accountid);
-- JAVA END
---------------------------------------------------
-- При смене признака последнего использования в таблице Аккаунтов на Y (а так же при добавлении новой записи в таблицу), 
-- автоматически выставляют всем остальным записям с такой же версией признак N.
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

-- Удаление колонок перенесенных в таблицу Аккаунты
ALTER TABLE USERS DROP COLUMN PASSWORD;
ALTER TABLE USERS DROP COLUMN LASTUSED;
ALTER TABLE USERS DROP COLUMN VERSION;

-- Добавляем контроль уникальности для Алиаса в таблице Юзеров
ALTER TABLE USERS ADD CONSTRAINT USERNAME_UQ UNIQUE (ALIAS);
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- 1.2.2
-- Таблица Аффиксы
CREATE TABLE AFFIXES (
	ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NAME VARCHAR(25) NOT NULL,
	CONSTRAINT AFFIXES_PK PRIMARY KEY (ID)
);

-- Связочная таблица аффиксов и аккаунтов
CREATE TABLE AFFIXTOACCOUNT (
ACCOUNTID INTEGER NOT NULL,
AFFIXID INTEGER NOT NULL,
CONSTRAINT AFFIX_IDS_UQ UNIQUE (ACCOUNTID, AFFIXID)
);
-- ACCOUNTID - идентификатор Аккаунта
-- AFFIXID   - идентификатор аффикса
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- 1.2.3
-- Таблица настроек прогона
CREATE TABLE PROGRAMS (
	ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NAME VARCHAR(255) NOT NULL,
	SETTINGS BLOB NOT NULL,
	CONSTRAINT PROGRAMS_PK PRIMARY KEY (ID),
	CONSTRAINT NAME_UQ UNIQUE (NAME)
);
-- NAME      - название настроек
-- SETTINGS  - блоб с настройками



