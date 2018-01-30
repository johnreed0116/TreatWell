-- CONNECT SYS AS SYSDBA
/*
grant create any job to alberta;
grant create external job to alberta;
grant manage scheduler to alberta;
*/

--conn alberta and run the script

CREATE TABLE BACKUP_PATH
(SQL_PATH_TXT VARCHAR2(500),
BACKUP_PATH_TXT VARCHAR2(500),
SOURCE_ID VARCHAR2(200),
SOURCE_NME VARCHAR2(200),
COMPANY_NME VARCHAR2(100));

INSERT INTO BACKUP_PATH
VALUES ('C:\app\Administrator\product\11.2.0\dbhome_1\BIN\sqlplus.exe','C:\BACKUP','alberta','alberta','KAR');
COMMIT;

CREATE OR REPLACE PROCEDURE PROC_GET_BACKUP(DIRECTORY_NME IN  VARCHAR2 DEFAULT NULL)  IS
   V_SQL_PATH VARCHAR2(1000);
   V_BACKUP_PATH VARCHAR2(500);
   V_PASS VARCHAR2(100);
   V_FILE_NME VARCHAR2(200);
   V_DIRECTORY_PATH VARCHAR2(200);
   V_BUFFER VARCHAR2(1000);
   V_USER_NME VARCHAR2(500);   
BEGIN
   SELECT SUBSTR(SQL_PATH_TXT,1,INSTR(SQL_PATH_TXT,'\',-1,1))||'EXP.EXE',SOURCE_ID,SOURCE_NME,TO_CHAR(SYSDATE,'DD-MM-YYYY-HH-MI-AM')||'-'||COMPANY_NME||'.DMP',BACKUP_PATH_TXT INTO V_SQL_PATH,V_USER_NME,V_PASS,V_FILE_NME,V_BACKUP_PATH FROM BACKUP_PATH;
   IF DIRECTORY_NME IS NULL THEN
     V_DIRECTORY_PATH := V_BACKUP_PATH||'\'||V_FILE_NME;
   ELSE
     V_DIRECTORY_PATH := DIRECTORY_NME;
   END IF;
   V_BUFFER := V_SQL_PATH||' USERID='||V_USER_NME||'/'||V_PASS||'  full=y buffer=999999 file='||V_DIRECTORY_PATH;
   DBMS_SCHEDULER.CREATE_JOB(JOB_NAME => 'JOB_BACKUP',JOB_TYPE => 'executable', JOB_ACTION => V_BUFFER, ENABLED => TRUE, AUTO_DROP =>FALSE,repeat_interval => 'freq=HOURLY;interval=10',START_DATE => SYSDATE+1);
   DBMS_SCHEDULER.RUN_JOB('JOB_BACKUP',TRUE);
   DBMS_SCHEDULER.DROP_JOB('JOB_BACKUP',TRUE);   
EXCEPTION WHEN OTHERS THEN
   DBMS_SCHEDULER.DROP_JOB('JOB_BACKUP',TRUE);
END;
/

BEGIN
 dbms_scheduler.create_schedule(  
  schedule_name  => 'INTERVAL_EVERY_12_HOURS',  
   start_date    => TRUNC(SYSDATE-1)+18/24,
   repeat_interval => 'freq=HOURLY;interval=12',  
   comments     => 'Runtime: afer 12 hours interval');  
END;
/
 
BEGIN
dbms_scheduler.create_program
(program_name=> 'PROG_GET_BACKUP',
 program_type=> 'STORED_PROCEDURE',
 program_action=> 'PROC_GET_BACKUP',
 enabled=>TRUE,
 comments=>'Program that will call PROC_GET_BACKUP procedure to get backup.'
 );
END;
/
 
 
 BEGIN
-- Connect both dbms_scheduler parts by creating the final job
dbms_scheduler.create_job
 (job_name => 'GET_BACKUP_JOB',
  program_name=> 'PROG_GET_BACKUP',
  schedule_name=>'INTERVAL_EVERY_12_HOURS',
  enabled=>TRUE,
  auto_drop=>FALSE,
  comments=>'Job to get backup of database after 12 hours');
END;
/
