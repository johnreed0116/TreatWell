CREATE OR REPLACE TYPE OBJECT_SITE_SCORE IS OBJECT (
  SECTION_ID NUMBER, 
  SECTION_TITLE VARCHAR2(250), 
  SCORE NUMBER, 
  TOTAL NUMBER 
);
/
CREATE OR REPLACE TYPE TABLE_SITE_SCORE IS TABLE OF OBJECT_SITE_SCORE;
/
CREATE OR REPLACE
FUNCTION GET_SITE_SCORE(FP_SITE_ID IN NUMBER, FP_MONTH IN VARCHAR2) 
RETURN TABLE_SITE_SCORE PIPELINED IS
CURSOR CS_SITE_SCORE IS SELECT S.SECTION_ID,MAX(S.TITLE) SECTION_TITLE,SUM(CD.SCORE) SCORE, SUM(1*5) TOTAL  
                          FROM SUBSECTION SB,SECTION S,CHECKLIST_DETAIL CD, CHECKLIST_MASTER CM  
                          WHERE S.SECTION_ID=SB.SECTION_ID  
                            AND CM.CHECKLIST_MASTER_ID=CD.CHECKLIST_MASTER_ID  
                            AND SB.SUBSECTION_ID=CD.SUBSECTION_ID   
                            AND TO_CHAR(CM.REVIEW_DTE,'MON-YYYY')=UPPER(FP_MONTH)
                            AND (CM.SITE_ID,CM.SECTION_ID,CM.REVIEW_DTE) IN (     
                              SELECT CM.SITE_ID,CM.SECTION_ID,MAX(CM.REVIEW_DTE) REVIEW_DTE FROM CHECKLIST_MASTER CM      
                                WHERE TO_CHAR(CM.REVIEW_DTE,'MON-YYYY')=UPPER(FP_MONTH)
                                AND ((FP_SITE_ID=0) OR (CM.SITE_ID=FP_SITE_ID))
                                GROUP BY CM.SITE_ID,CM.SECTION_ID ) 
                          GROUP BY S.SECTION_ID  ORDER BY S.SECTION_ID;

BEGIN
  FOR FL_SS IN CS_SITE_SCORE LOOP
    PIPE ROW (OBJECT_SITE_SCORE(FL_SS.SECTION_ID, FL_SS.SECTION_TITLE, FL_SS.SCORE, FL_SS.TOTAL));
  END LOOP;  
  RETURN;  
END;
/