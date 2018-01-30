CREATE OR REPLACE FUNCTION GET_HOURS_OR_MINUTES(STTIME IN VARCHAR2,ENTIME IN VARCHAR2, PATTERN IN VARCHAR2, RETURN_TYPE IN VARCHAR2) 
RETURN VARCHAR2 
IS
  HOUR1 NUMBER; 
	HOUR2 NUMBER; 
	MIN1 NUMBER; 
	MIN2 NUMBER; 
	T1 NUMBER; 
	T2 NUMBER; 
	T3 NUMBER; 
	T4 NUMBER; 
	T5 NUMBER; 
	H1 NUMBER; 
	M1 NUMBER; 
	H2 NUMBER; 
	M2 NUMBER; 
	ER3A NUMBER; 
	ER3B NUMBER; 
  CALCHOURS VARCHAR2(8);
  
BEGIN
  IF (STTIME='' OR ENTIME='' OR REPLACE(STTIME, ' ', '')=':' OR REPLACE(ENTIME, ' ', '')=':') THEN RETURN ''; END IF;

	H1 := TO_NUMBER(SUBSTR(STTIME,1,2));
	M1 := SUBSTR(STTIME, INSTR(STTIME,':')+1,2);
  
 
  H2 := TO_NUMBER(SUBSTR(ENTIME,1,2));
	M2 := SUBSTR(ENTIME, INSTR(ENTIME,':')+1,2);
  
  
  IF (UPPER(SUBSTR(STTIME, INSTR(UPPER(STTIME),' ') + 1,2))='AM') THEN ER3A := 0; ELSE ER3A := 1; END IF;
  IF (UPPER(SUBSTR(ENTIME, INSTR(UPPER(ENTIME),' ') + 1,2))='AM') THEN ER3B := 0; ELSE ER3B := 1; END IF;
  
	IF ((ER3A = 0) AND (H1 = 12)) THEN H1 := 0; END IF;
     
	IF ((ER3A = 0) AND (ER3B = 1)) THEN	  
		T1 := (60 * H1) + M1;
		IF (H2 = 12) THEN
		   T2 := ((H2) * 60) + M2;
		ELSE 
		   T2 := ((H2 + 12) * 60) + M2; 
    END IF;
		T3 := T2 - T1;
		T4 := FLOOR(T3 / 60);
		T5 := T3 - (T4 * 60);

	ELSIF ((ER3A = 1) AND (ER3B = 0)) THEN
		IF (H2 = 12) THEN H2 := 0; END IF;
		IF (H1 = 12) THEN H1 := 0; END IF;
		T1 := (60 * H1) + M1;
		T2 := ((H2 + 12) * 60) + M2;
		T3 := T2 - T1;
		T4 := FLOOR(T3 / 60);
		T5 := T3 - (T4 * 60);
  
	ELSIF ((ER3A = 0) AND (ER3B = 0)) THEN	   
		T1 := (H1 * 60) + M1;
		IF (H2 = 12) THEN H2 := 0; END IF;
		T2 := (H2 * 60) + M2;
		IF (T2 > T1) THEN 		   
			T3 := T2 - T1;
			T4 := FLOOR(T3 / 60);
			T5 := T3 - (T4 * 60);		  
		ELSE 	 	   
			T2 := ((H2 + 24) * 60) + M2;
			T3 := T2 - T1;
			T4 := FLOOR(T3 / 60);
			T5 := T3 - (T4 * 60);
    END IF;
  
	ELSIF ((ER3A = 1) AND (ER3B = 1)) THEN	   
		IF (H1<>12) THEN H1 := H1 + 12; END IF;
		IF (H2<>12) THEN H2 := H2 + 12; END IF;
		T1 := (H1 * 60) + M1;
		T2 := (H2 * 60) + M2;
		IF (T2 > T1) THEN 
			T3 := T2 - T1;
			T4 := FLOOR(T3 / 60);
			T5 := T3 - (T4 * 60);
		ELSE 
			T2 := ((H2 + 24) * 60) + M2;
			T3 := T2 - T1;
			T4 := FLOOR(T3 / 60);
			T5 := T3 - (T4 * 60);
    END IF;
    
  END IF;
	
	IF (LENGTH(T4) = 2 AND LENGTH(T5) = 2) THEN	   
      IF UPPER(RETURN_TYPE) = 'H' THEN
        CALCHOURS := (TO_CHAR(T4) || '');	     
      ELSE 
        CALCHOURS := (TO_CHAR(T5) || '');
      END IF;  
	ELSIF (LENGTH(T4) = 2 AND LENGTH(T5) = 1) THEN 	
     IF UPPER(RETURN_TYPE) = 'H' THEN
        CALCHOURS := (TO_CHAR(T4) || '');	  
     ELSE 
        CALCHOURS := ('0' || TO_CHAR(T5) || '');
      END IF; 
  
	ELSIF (LENGTH(T4) = 1 AND LENGTH(T5) = 1) THEN	  
     IF UPPER(RETURN_TYPE) = 'H' THEN
	   	CALCHOURS := ('0' || TO_CHAR(T4) || '');
     ELSE 
        CALCHOURS := ('0' || TO_CHAR(T5) || '');
     END IF; 
	   
	ELSIF (LENGTH(T4) = 1 AND LENGTH(T5) = 2) THEN
      IF UPPER(RETURN_TYPE) = 'H' THEN
	   	CALCHOURS := ('0' || TO_CHAR(T4) || '');
      ELSE 
        CALCHOURS := (TO_CHAR(T5) || '');
      END IF;
	END IF;
 
  RETURN CALCHOURS;
END; 
/