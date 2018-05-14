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
-- При смене признака последнего использования в таблице версий на Y, автоматически выставляет всем остальным записям признак N
CREATE TRIGGER LASTUSED_GAMEVERSIONS_UPD
AFTER UPDATE ON GAMEVERSIONS
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE GAMEVERSIONS SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND ID <> N.ID;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------


