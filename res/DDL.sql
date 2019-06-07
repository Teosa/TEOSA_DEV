-- ������ ����
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
-- NAME      - ����������� ������������ ������ (����.)
-- FULLNAME  - ������������ ������
-- URL       - ������ �� ������� ���� ������ ������
-- LASTUSED  - ������� ���������� ������������� ������ � ����������
-- FLAGIMG   - �������� � ������ ������

-- �����
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
-- ALIAS    - ����� 
-- PASSWORD - ������ 
-- VERSION  - ID ������ ����
-- LASTUSED - ������� ���������� ������������� � ���������� ��� ��������� ������

----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- ������ ����������� ������ � ����������� ��������� �� ������ �� ������� ������ �� ID
ALTER TABLE USERS DROP COLUMN  VERSION;
ALTER TABLE USERS ADD COLUMN VERSION INTEGER;
ALTER TABLE USERS ADD FOREIGN KEY (VERSION) REFERENCES GAMEVERSIONS(ID);
UPDATE USERS SET VERSION = 1;
ALTER TABLE USERS ALTER COLUMN VERSION SET NOT NULL;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- ��� ����� �������� ���������� ������������� � ������� ������ �� Y (� ��� �� ��� ���������� ����� ������ � �������), 
-- ������������� ���������� ���� ��������� ������� � ����� �� ������� ������� N.

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
-- ��� ����� �������� ���������� ������������� � ������� ������ �� Y, ������������� ���������� ���� ��������� ������� ������� N
CREATE TRIGGER LASTUSED_GAMEVERSIONS_UPD
AFTER UPDATE ON GAMEVERSIONS
REFERENCING NEW AS N
FOR EACH ROW MODE DB2SQL
WHEN (N.LASTUSED = 'Y')
UPDATE GAMEVERSIONS SET LASTUSED = 'N' WHERE LASTUSED = 'Y' AND ID <> N.ID;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
--18/05/18
--��������� ������� ������ �� USERS � ACCOUNTS �� ��������� �������� USERTOACCOUNT.

-- ������� ����� �� ������ ��������
DROP TRIGGER LASTUSED_USER;
DROP TRIGGER LASTUSED_USER_UPD;

-- ��������
CREATE TABLE ACCOUNTS (
	ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	PASSWORD VARCHAR(50) NOT NULL, 
	VERSION INTEGER NOT NULL,
	LASTUSED CHAR DEFAULT 'N' NOT NULL ,
	CONSTRAINT ACCOUNTSID_PK PRIMARY KEY (ID),
	CONSTRAINT GAMEVERSION_FK FOREIGN KEY (VERSION) REFERENCES GAMEVERSIONS(ID),
	CONSTRAINT ACCOUNTS_LASTUSED_CHK CHECK (LASTUSED IN ('Y','N'))
);
-- PASSWORD - ������ 
-- VERSION  - ID ������ ����
-- LASTUSED - ������� ���������� ������������� � ���������� ��� ��������� ������

-- ��������� ������� ��������� � ������
CREATE TABLE USERTOACCOUNT (
	USERID INTEGER NOT NULL,
	ACCOUNTID INTEGER NOT NULL,
	CONSTRAINT USER_FK FOREIGN KEY (USERID) REFERENCES USERS(ID),
	CONSTRAINT ACCOUNT_FK FOREIGN KEY (ACCOUNTID) REFERENCES ACCOUNTS(ID),
	CONSTRAINT IDS_UQ UNIQUE (USERID, ACCOUNTID)
);
-- USERID    - ������������� �����
-- ACCOUNTID - ������������� ��������
---------------------------------------------------
--JAVA 
SELECT ID, PASSWORD, LASTUSED, VERSION FROM USERS;
INSERT INTO ACCOUNTS VALUES (DEFAULT, :password, :versionid, :lastused);
SELECT MAX(ID) FROM ACCOUNTS;
INSERT INTO USERTOACCOUNT VALUES (:userid, :accountid);
-- JAVA END
---------------------------------------------------
-- ��� ����� �������� ���������� ������������� � ������� ��������� �� Y (� ��� �� ��� ���������� ����� ������ � �������), 
-- ������������� ���������� ���� ��������� ������� � ����� �� ������� ������� N.
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

-- �������� ������� ������������ � ������� ��������
ALTER TABLE USERS DROP COLUMN PASSWORD;
ALTER TABLE USERS DROP COLUMN LASTUSED;
ALTER TABLE USERS DROP COLUMN VERSION;

-- ��������� �������� ������������ ��� ������ � ������� ������
ALTER TABLE USERS ADD CONSTRAINT USERNAME_UQ UNIQUE (ALIAS);
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- 1.2.2
-- ������� �������
CREATE TABLE AFFIXES (
	ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NAME VARCHAR(25) NOT NULL,
	CONSTRAINT AFFIXES_PK PRIMARY KEY (ID)
);

-- ��������� ������� �������� � ���������
CREATE TABLE AFFIXTOACCOUNT (
ACCOUNTID INTEGER NOT NULL,
AFFIXID INTEGER NOT NULL,
CONSTRAINT AFFIX_IDS_UQ UNIQUE (ACCOUNTID, AFFIXID)
);
-- ACCOUNTID - ������������� ��������
-- AFFIXID   - ������������� �������
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- 1.2.3
-- ������� �������� �������
CREATE TABLE PROGRAMS (
	ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NAME VARCHAR(255) NOT NULL,
	SETTINGS BLOB NOT NULL,
	CONSTRAINT PROGRAMS_PK PRIMARY KEY (ID),
	CONSTRAINT NAME_UQ UNIQUE (NAME)
);
-- NAME      - �������� ��������
-- SETTINGS  - ���� � �����������



