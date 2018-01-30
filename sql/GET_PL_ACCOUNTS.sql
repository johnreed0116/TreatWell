CREATE OR REPLACE TYPE OBJECT_PL_ACCOUNTS IS OBJECT (
  SITE_ID NUMBER,
  MONTH_NBR NUMBER,
  SEQ_NBR NUMBER,
  TITLE VARCHAR2(128), 
  DISPLAY_TXT VARCHAR2(128),
  SUB_TITLE VARCHAR2(128),   
  COA_CDE VARCHAR2(32),
  AMNT NUMBER,
  MAM_ID NUMBER
);
/
CREATE OR REPLACE TYPE TABLE_PL_ACCOUNTS IS TABLE OF OBJECT_PL_ACCOUNTS;
/
CREATE OR REPLACE
FUNCTION GET_PL_ACCOUNTS(FP_MANAGEMENT_ACCOUNTS_ID IN NUMBER, FP_FROM_DTE IN VARCHAR2, FP_END_DTE IN VARCHAR2, FP_BU_ID IN VARCHAR2, FP_SITE_ID IN NUMBER, FP_FINYEAR_ID IN NUMBER, FP_COMPANY_ID IN NUMBER, FP_USER_NAME IN VARCHAR2, FP_CATEGORY IN VARCHAR2, FP_GROUP_BY IN VARCHAR2) 
RETURN TABLE_PL_ACCOUNTS PIPELINED IS
CURSOR CS_PL_ACCOUNTS IS 
  SELECT MAM.MANAGEMENT_ACCOUNTS_MASTER_ID, MAM.SEQ_NBR, MAM.TITLE, MAM.DISPLAY_TXT, MAD.SUB_TITLE, MAD.COA_CDE, 
  MAD.SOURCE_TYP DETAIL_SOURCE_TYP, MAD.SOURCE_TXT, MAM.SOURCE_TYP MASTER_SOURCE_TYP, MAD.MAP_COA_CDE, MAD.VOUCHER_SUB_TYP, MAD.EXCL_COA_CDE
  FROM MANAGEMENT_ACCOUNTS MA, MANAGEMENT_ACCOUNTS_MASTER MAM, MANAGEMENT_ACCOUNTS_DETAIL MAD
    WHERE MA.MANAGEMENT_ACCOUNTS_ID=MAM.MANAGEMENT_ACCOUNTS_ID
      AND MAM.MANAGEMENT_ACCOUNTS_MASTER_ID=MAD.MANAGEMENT_ACCOUNTS_MASTER_ID(+)
      AND MA.MANAGEMENT_ACCOUNTS_ID=FP_MANAGEMENT_ACCOUNTS_ID      
      ORDER BY MAM.SEQ_NBR;

CURSOR CS_DATA_BY_COA(CP_COA_CDE IN VARCHAR2,CP_FROM_DTE IN VARCHAR2,CP_SOURCE_TYP IN VARCHAR2,CP_SOURCE_TXT IN VARCHAR2,CP_SITE_ID IN VARCHAR2,
  CP_VOUCHER_SUB_TYP IN VARCHAR2,CP_EXCL_COA_CDE IN VARCHAR2) IS  
  SELECT VD.COA_CDE, SUM(VD.AMNT) AMNT FROM (
    SELECT VD.COA_CDE, 
      SUM(CASE WHEN CP_SOURCE_TXT='VAL' THEN
          CASE WHEN (CP_SOURCE_TYP='DR' AND VD.AMNT>0) THEN NVL(VD.AMNT,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD.AMNT<0) THEN NVL(VD.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD.AMNT,0) ELSE 0 END ELSE
          CASE WHEN (CP_SOURCE_TYP='DR' AND VD.QTY>0) THEN NVL(VD.QTY,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD.QTY<0) THEN NVL(VD.QTY,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD.QTY,0) ELSE 0 END END     
          ) AMNT
      FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST
      WHERE VM.VOUCHER_MASTER_ID=VD.VOUCHER_MASTER_ID 
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC NOT IN ('CJV','YAJV')
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y' 
               AND COA.COA_CDE NOT IN (SELECT REGEXP_SUBSTR(CP_EXCL_COA_CDE, '[^,]+', 1, LEVEL) VCH_SUB_TYP FROM DUAL 
               CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_EXCL_COA_CDE, '[^,]+')) + 1)
             START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       AND VM.SITE_ID IN (SELECT REGEXP_SUBSTR(CP_SITE_ID, '[^,]+', 1, LEVEL) SITE_ID FROM DUAL
              CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_SITE_ID, '[^,]+')) + 1) 
       AND VST.VOUCHER_SUB_TYP_DESC IN (
           SELECT VOUCHER_SUB_TYP_DESC FROM VOUCHER_SUB_TYPE WHERE CP_VOUCHER_SUB_TYP IS NULL
           UNION ALL
           SELECT REGEXP_SUBSTR(CP_VOUCHER_SUB_TYP, '[^,]+', 1, LEVEL) VCH_SUB_TYP FROM DUAL WHERE CP_VOUCHER_SUB_TYP IS NOT NULL
                  CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_VOUCHER_SUB_TYP, '[^,]+')) + 1
          )       
       GROUP BY VD.COA_CDE 
    UNION ALL
    SELECT VD.COA_CDE, 
      SUM(CASE WHEN (CP_SOURCE_TYP='DR' AND VD_1.AMNT>0) THEN NVL(VD_1.AMNT,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD_1.AMNT<0) THEN NVL(VD_1.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD_1.AMNT,0) ELSE 0 END) AMNT
      FROM VOUCHER_DETAIL VD_1, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, SITE S, CHART_OF_ACCOUNT VD, COA_TYPE CT
      WHERE VM.VOUCHER_MASTER_ID=VD_1.VOUCHER_MASTER_ID AND VM.SITE_ID=S.SITE_ID AND S.BASED_ON='NON-COMMISSION'       
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC IN ('SI','SR')
       AND (S.BUYSELL_START_DTE IS NOT NULL AND VM.VOUCHER_DTE >= S.BUYSELL_START_DTE) 
       AND VD.ALT_COA_CDE IS NOT NULL AND VD_1.COA_CDE=VD.ALT_COA_CDE 
       AND VD.COA_TYPE_ID=CT.COA_TYPE_ID AND UPPER(CT.DESCRIPTION)='REVENUE'
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
                            START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       AND VM.SITE_ID IN (SELECT REGEXP_SUBSTR(CP_SITE_ID, '[^,]+', 1, LEVEL) SITE_ID FROM DUAL
              CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_SITE_ID, '[^,]+')) + 1)                             
       GROUP BY VD.COA_CDE
    UNION ALL
    SELECT VD.COA_CDE, 
      SUM(CASE WHEN (CP_SOURCE_TYP='DR') THEN ABS(NVL(VD_1.COST_VALUE,0)) 
               WHEN (CP_SOURCE_TYP='CR') THEN NVL(VD_1.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN (CASE WHEN VD_1.AMNT<0 THEN ABS(NVL(VD_1.COST_VALUE,0)) ELSE NVL(VD_1.AMNT,0) END) ELSE 0 END) AMNT
      FROM VOUCHER_DETAIL VD_1, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, SITE S, CHART_OF_ACCOUNT VD, COA_TYPE CT
      WHERE VM.VOUCHER_MASTER_ID=VD_1.VOUCHER_MASTER_ID AND VM.SITE_ID=S.SITE_ID AND S.BASED_ON='NON-COMMISSION'       
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC IN ('SI','SR')
       AND (S.BUYSELL_START_DTE IS NOT NULL AND VM.VOUCHER_DTE >= S.BUYSELL_START_DTE) 
       AND VD.ALT_COA_CDE IS NOT NULL AND VD_1.COA_CDE=VD.ALT_COA_CDE 
       AND VD.COA_TYPE_ID=CT.COA_TYPE_ID AND UPPER(CT.DESCRIPTION)='EXPENSE'
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
                            START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       AND VM.SITE_ID IN (SELECT REGEXP_SUBSTR(CP_SITE_ID, '[^,]+', 1, LEVEL) SITE_ID FROM DUAL
              CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_SITE_ID, '[^,]+')) + 1)                           
       GROUP BY VD.COA_CDE  
  ) VD GROUP BY VD.COA_CDE ORDER BY VD.COA_CDE;
  
CURSOR CS_DATA_BY_SITE(CP_COA_CDE IN VARCHAR2,CP_FROM_DTE IN VARCHAR2,CP_SOURCE_TYP IN VARCHAR2,CP_SOURCE_TXT IN VARCHAR2,CP_SITE_ID IN VARCHAR2,
  CP_VOUCHER_SUB_TYP IN VARCHAR2,CP_EXCL_COA_CDE IN VARCHAR2) IS  
  SELECT VD.COA_CDE, VD.SITE_ID, SUM(VD.AMNT) AMNT FROM (
    SELECT VD.COA_CDE, VM.SITE_ID,
      SUM(CASE WHEN CP_SOURCE_TXT='VAL' THEN
          CASE WHEN (CP_SOURCE_TYP='DR' AND VD.AMNT>0) THEN NVL(VD.AMNT,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD.AMNT<0) THEN NVL(VD.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD.AMNT,0) ELSE 0 END ELSE
          CASE WHEN (CP_SOURCE_TYP='DR' AND VD.QTY>0) THEN NVL(VD.QTY,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD.QTY<0) THEN NVL(VD.QTY,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD.QTY,0) ELSE 0 END END     
          ) AMNT
      FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST
      WHERE VM.VOUCHER_MASTER_ID=VD.VOUCHER_MASTER_ID 
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC NOT IN ('CJV','YAJV')
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
               AND COA.COA_CDE NOT IN (SELECT REGEXP_SUBSTR(CP_EXCL_COA_CDE, '[^,]+', 1, LEVEL) VCH_SUB_TYP FROM DUAL 
               CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_EXCL_COA_CDE, '[^,]+')) + 1)
             START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       AND VM.SITE_ID IN (SELECT REGEXP_SUBSTR(CP_SITE_ID, '[^,]+', 1, LEVEL) SITE_ID FROM DUAL
              CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_SITE_ID, '[^,]+')) + 1)    
       AND VST.VOUCHER_SUB_TYP_DESC IN (
           SELECT VOUCHER_SUB_TYP_DESC FROM VOUCHER_SUB_TYPE WHERE CP_VOUCHER_SUB_TYP IS NULL
           UNION ALL
           SELECT REGEXP_SUBSTR(CP_VOUCHER_SUB_TYP, '[^,]+', 1, LEVEL) VCH_SUB_TYP FROM DUAL WHERE CP_VOUCHER_SUB_TYP IS NOT NULL
                  CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_VOUCHER_SUB_TYP, '[^,]+')) + 1
          )       
       GROUP BY VM.SITE_ID, VD.COA_CDE 
    UNION ALL
    SELECT VD.COA_CDE, VM.SITE_ID,
      SUM(CASE WHEN (CP_SOURCE_TYP='DR' AND VD_1.AMNT>0) THEN NVL(VD_1.AMNT,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD_1.AMNT<0) THEN NVL(VD_1.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD_1.AMNT,0) ELSE 0 END) AMNT
      FROM VOUCHER_DETAIL VD_1, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, SITE S, CHART_OF_ACCOUNT VD, COA_TYPE CT
      WHERE VM.VOUCHER_MASTER_ID=VD_1.VOUCHER_MASTER_ID AND VM.SITE_ID=S.SITE_ID AND S.BASED_ON='NON-COMMISSION'       
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC IN ('SI','SR')
       AND (S.BUYSELL_START_DTE IS NOT NULL AND VM.VOUCHER_DTE >= S.BUYSELL_START_DTE) 
       AND VD.ALT_COA_CDE IS NOT NULL AND VD_1.COA_CDE=VD.ALT_COA_CDE 
       AND VD.COA_TYPE_ID=CT.COA_TYPE_ID AND UPPER(CT.DESCRIPTION)='REVENUE'
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
                            START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       AND VM.SITE_ID IN (SELECT REGEXP_SUBSTR(CP_SITE_ID, '[^,]+', 1, LEVEL) SITE_ID FROM DUAL
              CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_SITE_ID, '[^,]+')) + 1)                             
       GROUP BY VM.SITE_ID, VD.COA_CDE
    UNION ALL
    SELECT VD.COA_CDE, VM.SITE_ID, 
      SUM(CASE WHEN (CP_SOURCE_TYP='DR') THEN ABS(NVL(VD_1.COST_VALUE,0)) 
               WHEN (CP_SOURCE_TYP='CR') THEN NVL(VD_1.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN (CASE WHEN VD_1.AMNT<0 THEN ABS(NVL(VD_1.COST_VALUE,0)) ELSE NVL(VD_1.AMNT,0) END) ELSE 0 END) AMNT
      FROM VOUCHER_DETAIL VD_1, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, SITE S, CHART_OF_ACCOUNT VD, COA_TYPE CT
      WHERE VM.VOUCHER_MASTER_ID=VD_1.VOUCHER_MASTER_ID AND VM.SITE_ID=S.SITE_ID AND S.BASED_ON='NON-COMMISSION'       
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC IN ('SI','SR')
       AND (S.BUYSELL_START_DTE IS NOT NULL AND VM.VOUCHER_DTE >= S.BUYSELL_START_DTE) 
       AND VD.ALT_COA_CDE IS NOT NULL AND VD_1.COA_CDE=VD.ALT_COA_CDE 
       AND VD.COA_TYPE_ID=CT.COA_TYPE_ID AND UPPER(CT.DESCRIPTION)='EXPENSE'
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
                            START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       AND VM.SITE_ID IN (SELECT REGEXP_SUBSTR(CP_SITE_ID, '[^,]+', 1, LEVEL) SITE_ID FROM DUAL
              CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_SITE_ID, '[^,]+')) + 1)                           
       GROUP BY VM.SITE_ID, VD.COA_CDE  
  ) VD GROUP BY VD.SITE_ID, VD.COA_CDE ORDER BY VD.SITE_ID, VD.COA_CDE;
    
CURSOR CS_DATA_BY_MONTH(CP_COA_CDE IN VARCHAR2,CP_FROM_DTE IN VARCHAR2,CP_SOURCE_TYP IN VARCHAR2,CP_SOURCE_TXT IN VARCHAR2,
  CP_VOUCHER_SUB_TYP IN VARCHAR2,CP_EXCL_COA_CDE IN VARCHAR2) IS  
  SELECT VD.COA_CDE, VD.MONTH_NBR, SUM(VD.AMNT) AMNT FROM (
    SELECT VD.COA_CDE, EXTRACT (MONTH FROM (VM.VOUCHER_DTE)) MONTH_NBR,  
      SUM(CASE WHEN CP_SOURCE_TXT='VAL' THEN
          CASE WHEN (CP_SOURCE_TYP='DR' AND VD.AMNT>0) THEN NVL(VD.AMNT,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD.AMNT<0) THEN NVL(VD.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD.AMNT,0) ELSE 0 END ELSE
          CASE WHEN (CP_SOURCE_TYP='DR' AND VD.QTY>0) THEN NVL(VD.QTY,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD.QTY<0) THEN NVL(VD.QTY,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD.QTY,0) ELSE 0 END END     
          ) AMNT
      FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, SITE S
      WHERE VM.VOUCHER_MASTER_ID=VD.VOUCHER_MASTER_ID AND VM.SITE_ID=S.SITE_ID        
       AND VM.SITE_ID=FP_SITE_ID AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y'
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC NOT IN ('CJV','YAJV')
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
               AND COA.COA_CDE NOT IN (SELECT REGEXP_SUBSTR(CP_EXCL_COA_CDE, '[^,]+', 1, LEVEL) VCH_SUB_TYP FROM DUAL 
               CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_EXCL_COA_CDE, '[^,]+')) + 1)
             START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       AND VST.VOUCHER_SUB_TYP_DESC IN (
           SELECT VOUCHER_SUB_TYP_DESC FROM VOUCHER_SUB_TYPE WHERE CP_VOUCHER_SUB_TYP IS NULL
           UNION ALL
           SELECT REGEXP_SUBSTR(CP_VOUCHER_SUB_TYP, '[^,]+', 1, LEVEL) VCH_SUB_TYP FROM DUAL WHERE CP_VOUCHER_SUB_TYP IS NOT NULL
                  CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(CP_VOUCHER_SUB_TYP, '[^,]+')) + 1
          )                            
       GROUP BY EXTRACT (MONTH FROM (VM.VOUCHER_DTE)), VD.COA_CDE
    UNION ALL
    SELECT VD.COA_CDE, EXTRACT (MONTH FROM (VM.VOUCHER_DTE)) MONTH_NBR,  
      SUM(CASE WHEN (CP_SOURCE_TYP='DR' AND VD_1.AMNT>0) THEN NVL(VD_1.AMNT,0) 
               WHEN (CP_SOURCE_TYP='CR' AND VD_1.AMNT<0) THEN NVL(VD_1.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN NVL(VD_1.AMNT,0) ELSE 0 END) AMNT
      FROM VOUCHER_DETAIL VD_1, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, SITE S, CHART_OF_ACCOUNT VD, COA_TYPE CT
      WHERE VM.VOUCHER_MASTER_ID=VD_1.VOUCHER_MASTER_ID AND VM.SITE_ID=S.SITE_ID AND S.BASED_ON='NON-COMMISSION'       
       AND VM.SITE_ID=FP_SITE_ID AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' 
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC IN ('SI','SR')
       AND (S.BUYSELL_START_DTE IS NOT NULL AND VM.VOUCHER_DTE >= S.BUYSELL_START_DTE) 
       AND VD.ALT_COA_CDE IS NOT NULL AND VD_1.COA_CDE=VD.ALT_COA_CDE 
       AND VD.COA_TYPE_ID=CT.COA_TYPE_ID AND UPPER(CT.DESCRIPTION)='REVENUE'
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
                            START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       GROUP BY EXTRACT (MONTH FROM (VM.VOUCHER_DTE)), VD.COA_CDE 
    UNION ALL
    SELECT VD.COA_CDE, EXTRACT (MONTH FROM (VM.VOUCHER_DTE)) MONTH_NBR,  
      SUM(CASE WHEN (CP_SOURCE_TYP='DR') THEN ABS(NVL(VD_1.COST_VALUE,0)) 
               WHEN (CP_SOURCE_TYP='CR') THEN NVL(VD_1.AMNT,0)
               WHEN (CP_SOURCE_TYP='BOTH') THEN (CASE WHEN VD_1.AMNT<0 THEN ABS(NVL(VD_1.COST_VALUE,0)) ELSE NVL(VD_1.AMNT,0) END) ELSE 0 END) AMNT
      FROM VOUCHER_DETAIL VD_1, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, SITE S, CHART_OF_ACCOUNT VD, COA_TYPE CT
      WHERE VM.VOUCHER_MASTER_ID=VD_1.VOUCHER_MASTER_ID AND VM.SITE_ID=S.SITE_ID AND S.BASED_ON='NON-COMMISSION'       
       AND VM.SITE_ID=FP_SITE_ID AND VM.COMPANY_ID=FP_COMPANY_ID
       AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' 
       AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_SUB_TYP_DESC IN ('SI','SR')
       AND (S.BUYSELL_START_DTE IS NOT NULL AND VM.VOUCHER_DTE >= S.BUYSELL_START_DTE) 
       AND VD.ALT_COA_CDE IS NOT NULL AND VD_1.COA_CDE=VD.ALT_COA_CDE 
       AND VD.COA_TYPE_ID=CT.COA_TYPE_ID AND UPPER(CT.DESCRIPTION)='EXPENSE'
       AND VM.VOUCHER_DTE BETWEEN TO_DATE(CP_FROM_DTE,'DD-MM-YYYY') AND TO_DATE(FP_END_DTE,'DD-MM-YYYY')
       AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
                            START WITH COA.COA_CDE=CP_COA_CDE CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE)
       GROUP BY EXTRACT (MONTH FROM (VM.VOUCHER_DTE)), VD.COA_CDE   
  ) VD GROUP BY VD.MONTH_NBR, VD.COA_CDE ORDER BY VD.MONTH_NBR, VD.COA_CDE;
       
V_BUYSELL_DTE DATE;       
V_FROM_DTE VARCHAR2(16);
V_SITE_ID VARCHAR2(250);

BEGIN  
  V_SITE_ID := '';
  SELECT TRIM(S.SITE_ID) INTO V_SITE_ID FROM (
    SELECT LISTAGG(S.SITE_ID, ',') WITHIN GROUP (ORDER BY S.SITE_ID) SITE_ID  FROM SITE S, SITE_BUSINESS_UNIT SBU, USER_SITE US
      WHERE S.SITE_ID=SBU.SITE_ID AND S.COMPANY_ID=SBU.COMPANY_ID AND S.STORE_IND='Y' AND SBU.COMPANY_ID=FP_COMPANY_ID 
        AND S.SITE_ID=US.SITE_ID AND US.USER_NME=FP_USER_NAME AND S.SITE_CATEGORY IN ('PETRO','FOOD','OTHER') 
        AND ((S.SITE_CATEGORY=FP_CATEGORY) OR (FP_CATEGORY='ALL'))
        AND SBU.BUSINESS_UNIT_ID IN (SELECT REGEXP_SUBSTR(FP_BU_ID, '[^,]+', 1, LEVEL) BU_ID FROM DUAL
              CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(FP_BU_ID, '[^,]+')) + 1)
        AND ((S.SITE_ID=FP_SITE_ID) OR (0>=FP_SITE_ID)) 
   ) S; -- ZERO OR -1 FOR ALL SITES UNDER BU
      
  FOR FL_MA IN CS_PL_ACCOUNTS LOOP    
    IF (FL_MA.COA_CDE IS NULL OR FL_MA.MASTER_SOURCE_TYP='FORMULA') THEN
      PIPE ROW (OBJECT_PL_ACCOUNTS(NULL, NULL, FL_MA.SEQ_NBR, FL_MA.TITLE, FL_MA.DISPLAY_TXT, NVL(FL_MA.SUB_TITLE,FL_MA.TITLE), NULL, 0, FL_MA.MANAGEMENT_ACCOUNTS_MASTER_ID));
    ELSE 
      IF (FP_GROUP_BY='MONTH') THEN 
        FOR FL_DATA IN CS_DATA_BY_MONTH(FL_MA.COA_CDE,FP_FROM_DTE,FL_MA.DETAIL_SOURCE_TYP,FL_MA.SOURCE_TXT,FL_MA.VOUCHER_SUB_TYP,FL_MA.EXCL_COA_CDE) LOOP  
          IF (FL_MA.TITLE='SALES' OR FL_MA.TITLE='OTHER INCOME') THEN
            PIPE ROW (OBJECT_PL_ACCOUNTS(NULL, FL_DATA.MONTH_NBR, FL_MA.SEQ_NBR, FL_MA.TITLE, FL_MA.DISPLAY_TXT, FL_MA.SUB_TITLE, NVL(FL_MA.MAP_COA_CDE,FL_DATA.COA_CDE), -1*(FL_DATA.AMNT), FL_MA.MANAGEMENT_ACCOUNTS_MASTER_ID));        
          ELSE 
            PIPE ROW (OBJECT_PL_ACCOUNTS(NULL, FL_DATA.MONTH_NBR, FL_MA.SEQ_NBR, FL_MA.TITLE, FL_MA.DISPLAY_TXT, FL_MA.SUB_TITLE, NVL(FL_MA.MAP_COA_CDE,FL_DATA.COA_CDE), FL_DATA.AMNT, FL_MA.MANAGEMENT_ACCOUNTS_MASTER_ID));        
          END IF;
        END LOOP;  
      ELSIF (FP_GROUP_BY='SITE') THEN 
        FOR FL_DATA IN CS_DATA_BY_SITE(FL_MA.COA_CDE,FP_FROM_DTE,FL_MA.DETAIL_SOURCE_TYP,FL_MA.SOURCE_TXT,V_SITE_ID,FL_MA.VOUCHER_SUB_TYP,FL_MA.EXCL_COA_CDE) LOOP 
          IF (FL_MA.TITLE='SALES' OR FL_MA.TITLE='OTHER INCOME') THEN
            PIPE ROW (OBJECT_PL_ACCOUNTS(FL_DATA.SITE_ID, NULL, FL_MA.SEQ_NBR, FL_MA.TITLE, FL_MA.DISPLAY_TXT, FL_MA.SUB_TITLE, NVL(FL_MA.MAP_COA_CDE,FL_DATA.COA_CDE), -1*(FL_DATA.AMNT), FL_MA.MANAGEMENT_ACCOUNTS_MASTER_ID));        
          ELSE 
            PIPE ROW (OBJECT_PL_ACCOUNTS(FL_DATA.SITE_ID, NULL, FL_MA.SEQ_NBR, FL_MA.TITLE, FL_MA.DISPLAY_TXT, FL_MA.SUB_TITLE, NVL(FL_MA.MAP_COA_CDE,FL_DATA.COA_CDE), FL_DATA.AMNT, FL_MA.MANAGEMENT_ACCOUNTS_MASTER_ID));        
          END IF;
        END LOOP;  
      ELSE
        FOR FL_DATA IN CS_DATA_BY_COA(FL_MA.COA_CDE,FP_FROM_DTE,FL_MA.DETAIL_SOURCE_TYP,FL_MA.SOURCE_TXT,V_SITE_ID,FL_MA.VOUCHER_SUB_TYP,FL_MA.EXCL_COA_CDE) LOOP 
          IF (FL_MA.TITLE='SALES' OR FL_MA.TITLE='OTHER INCOME') THEN
            PIPE ROW (OBJECT_PL_ACCOUNTS(NULL, NULL, FL_MA.SEQ_NBR, FL_MA.TITLE, FL_MA.DISPLAY_TXT, FL_MA.SUB_TITLE, NVL(FL_MA.MAP_COA_CDE,FL_DATA.COA_CDE), -1*(FL_DATA.AMNT), FL_MA.MANAGEMENT_ACCOUNTS_MASTER_ID));        
          ELSE 
            PIPE ROW (OBJECT_PL_ACCOUNTS(NULL, NULL, FL_MA.SEQ_NBR, FL_MA.TITLE, FL_MA.DISPLAY_TXT, FL_MA.SUB_TITLE, NVL(FL_MA.MAP_COA_CDE,FL_DATA.COA_CDE), FL_DATA.AMNT, FL_MA.MANAGEMENT_ACCOUNTS_MASTER_ID));        
          END IF;
        END LOOP;          
      END IF;
    END IF;  
  END LOOP;   
END;
/