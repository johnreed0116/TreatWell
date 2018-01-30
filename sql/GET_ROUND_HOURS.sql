create or replace 
FUNCTION GET_ROUND_HOURS(FP_INPUT IN VARCHAR2)  
RETURN NUMBER IS
OUTPUT NUMBER;
OUTPUT_STR VARCHAR2(48);
HOURS VARCHAR2(32);
MINS VARCHAR2(32);
NEW_HOURS NUMBER;
NEW_MINS NUMBER;
INPUT VARCHAR2(48);
SIGN NUMBER;
BEGIN
  IF(INSTR(FP_INPUT,'-')>0) THEN
    SIGN := -1;
  ELSE 
    SIGN := 1;
  END IF;
  INPUT := REPLACE(FP_INPUT,'-','');
    IF(INSTR(INPUT,'.')=1) THEN
    INPUT :='0'||INPUT;
     END IF;
  IF (INPUT IS NOT NULL AND TO_NUMBER(INPUT) > 0 AND INSTR(INPUT,'.')>0) THEN
        HOURS := TO_NUMBER(SUBSTR(INPUT,1,INSTR(INPUT,'.')));   
        MINS := SUBSTR(INPUT, INSTR(INPUT,'.')+1,LENGTH(INPUT));
            IF(HOURS IS NOT NULL AND MINS IS NOT NULL) THEN
              IF( SUBSTR(MINS,1,1)= '0') THEN
                  MINS := '0.'||MINS;
              END IF;
              
              IF( LENGTH(MINS)= 1) THEN
                  MINS := '0.0'||MINS; 
              END IF;
              
              NEW_HOURS := TO_NUMBER(HOURS);
              NEW_MINS := TO_NUMBER(MINS);
              
              IF (NEW_MINS> 59) THEN 
                NEW_MINS := ABS(REMAINDER(TO_NUMBER(MINS),60));
                NEW_HOURS := NEW_HOURS + FLOOR(TO_NUMBER(MINS) / 60);
                IF(LENGTH(NEW_MINS)=1) THEN
                  NEW_MINS :=TO_NUMBER('0.0'||NEW_MINS);
                END IF;
              END IF;
           
              IF(NEW_MINS > 1) THEN
                 OUTPUT_STR := NEW_HOURS || '.' || NEW_MINS;
              ELSE
                OUTPUT_STR := TO_CHAR(NEW_HOURS + NEW_MINS);
              END IF;
                OUTPUT := TO_NUMBER(OUTPUT_STR);  
          END IF;
   ELSE 
    OUTPUT := TO_NUMBER(INPUT);
   END IF;

  RETURN (OUTPUT*SIGN);
END;