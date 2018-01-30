--SELECT * FROM TABLE(GET_SALES_COMPARISON('Mar-2016',5,11,1,TABLE_PARAM_NV(GA_PARAM_NV(11,'Mar-2016'),GA_PARAM_NV(9,'Mar-2015'),GA_PARAM_NV(7,'Mar-2014'))));
CREATE TYPE GA_PARAM_NV AS OBJECT ( 
  NUM_VAL NUMBER,
  VAR_VAL VARCHAR2(32)
);
/
CREATE OR REPLACE TYPE TABLE_PARAM_NV AS TABLE OF GA_PARAM_NV;
/
CREATE OR REPLACE TYPE OBJECT_SALES_COMPARISON IS OBJECT (
  MONTH_NME VARCHAR(12),
  COA_CDE VARCHAR2(32), 
  TITLE VARCHAR2(128),
  TARGET NUMBER, 
  SALE NUMBER
);
/
CREATE OR REPLACE TYPE TABLE_SALES_COMPARISON IS TABLE OF OBJECT_SALES_COMPARISON;
/
CREATE OR REPLACE
FUNCTION GET_SALES_COMPARISON(FP_MONTH IN VARCHAR2, FP_SITE_ID IN NUMBER, FP_FINYEAR_ID IN NUMBER, FP_COMPANY_ID IN NUMBER, FP_TABLE_PARAM IN TABLE_PARAM_NV) 
RETURN TABLE_SALES_COMPARISON PIPELINED IS
CURSOR CS_SALES_COMPARISON IS 
        SELECT SP.MONTH_NME, SP.COA_CDE, MAX(COA.TITLE) TITLE, SUM(SP.TARGET) TARGET, 
        SUM(CASE WHEN SP.COA_CDE LIKE '375-02%' THEN SP.SALE ELSE ABS(SP.SALE) END) SALE 
        FROM ( SELECT UPPER(ST.MONTH_NME) MONTH_NME, COA.COA_CDE, NVL(ST.TARGET,0) TARGET, 0 SALE FROM (
                  SELECT ST.MONTH_NME, ST.COA_CDE, ST.TARGET FROM SALES_TARGET ST
                  WHERE ST.SITE_ID=FP_SITE_ID  AND ST.COMPANY_ID=FP_COMPANY_ID  AND ST.FIN_YEAR_ID=FP_FINYEAR_ID AND ST.MONTH_NME=FP_MONTH
             ) ST, CHART_OF_ACCOUNT COA 
             WHERE COA.COA_CDE=ST.COA_CDE(+) AND COA.COMPANY_ID=FP_COMPANY_ID 
             AND COA.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
                                   START WITH COA.COA_CDE IN (SELECT CAD.COA_CDE FROM COA_ASSOCIATION_DETAIL CAD, COA_ASSOCIATION CA
                                   WHERE CAD.COA_ASSOCIATION_ID=CA.COA_ASSOCIATION_ID AND CA.ASSOCIATION_TITLE IN ('SALES TARGET'))
                                   CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE UNION ALL
                                 SELECT COA.COA_CDE FROM (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA  WHERE COA.ENTRY_LEVEL_IND='Y' 
                                   START WITH COA.COA_CDE IN (SELECT CAD.COA_CDE FROM COA_ASSOCIATION_DETAIL CAD, COA_ASSOCIATION CA 
                                   WHERE CAD.COA_ASSOCIATION_ID=CA.COA_ASSOCIATION_ID AND CA.ASSOCIATION_TITLE IN ('DAILY SHEET ITEMS')) 
                                   CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE
                                 ) COA, SITE_INVENTORY SI WHERE COA.COA_CDE=SI.COA_CDE AND SI.SITE_ID=FP_SITE_ID) 
             UNION ALL
             SELECT DDS.MONTH_NME, DDS.COA_CDE, MIN(0) TARGET, SUM(DDS.SAL_QTY) SALE
              FROM ( SELECT DDS.COA_CDE, UPPER(DDS.MONTH_NME) MONTH_NME, NVL(DDS.SAL_QTY,0) SAL_QTY FROM DEPT_DAILY_SALE DDS
                      WHERE  DDS.SITE_ID=FP_SITE_ID AND DDS.COMPANY_ID=FP_COMPANY_ID AND (DDS.FIN_YEAR_ID,DDS.MONTH_NME) IN (SELECT NUM_VAL,VAR_VAL FROM TABLE(FP_TABLE_PARAM))            
               ) DDS GROUP BY DDS.MONTH_NME,DDS.COA_CDE 
             UNION ALL
             SELECT SW.MONTH_NME, ('500-01-06-0020') COA_CDE, 0 TARGET,
              (CASE WHEN SW.TOT_TRAN<>0 THEN ROUND((SW.PP_TRAN/SW.TOT_TRAN)*100) ELSE 0 END) SW_PERC FROM (
                SELECT TO_CHAR(DSR.RECON_DTE,'MON-YYYY') MONTH_NME, SUM(DSR.TOT_TRAN) TOT_TRAN, SUM(DSR.PP_TRAN) PP_TRAN FROM (
                    SELECT DSR.RECON_DTE, SUM(NVL(SP.TOTAL_TRANSACTION,0)+NVL(SP.FUEL_TRANSACTION,0)) TOT_TRAN, 
                    MAX(NVL(SP.PP_TRANSACTION,0)) PP_TRAN FROM SHIFT_POS SP,DAILY_SHIFT_RECON DSR
                    WHERE SP.DAILY_SHIFT_RECON_ID=DSR.DAILY_SHIFT_RECON_ID AND DSR.COMPANY_ID=FP_COMPANY_ID
                      AND DSR.SITE_ID=FP_SITE_ID AND TO_CHAR(DSR.RECON_DTE,'MON-YYYY') IN (SELECT UPPER(VAR_VAL) FROM TABLE(FP_TABLE_PARAM))
                    GROUP BY DSR.RECON_DTE
                  ) DSR GROUP BY TO_CHAR(DSR.RECON_DTE,'MON-YYYY')                    
               ) SW
             UNION ALL
             SELECT VD.MONTH_NME, VD.COA_CDE, MIN(0) TARGET,
               SUM(CASE WHEN VD.COA_CDE LIKE '500-01-01%' THEN CASE WHEN VD.QTY<0 THEN VD.QTY ELSE 0 END ELSE VD.AMNT END) SALE
              FROM ( SELECT VD.COA_CDE, TO_CHAR(VM.VOUCHER_DTE,'MON-YYYY') MONTH_NME, VD.AMNT, NVL(VD.QTY,0) QTY FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST
                      WHERE VM.VOUCHER_MASTER_ID=VD.VOUCHER_MASTER_ID AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID 
                      AND (VM.FIN_YEAR_ID,TO_CHAR(VM.VOUCHER_DTE,'MON-YYYY')) IN (SELECT NUM_VAL,UPPER(VAR_VAL) FROM TABLE(FP_TABLE_PARAM))
                      AND VST.VOUCHER_SUB_TYP_DESC NOT IN ('FAL','FAG','CJV','AJV')
                      AND VM.SITE_ID=FP_SITE_ID  AND VM.COMPANY_ID=FP_COMPANY_ID  AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y'
                      AND VD.COA_CDE IN (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA WHERE COA.ENTRY_LEVEL_IND='Y'
                                          START WITH COA.COA_CDE IN (SELECT CAD.COA_CDE FROM COA_ASSOCIATION_DETAIL CAD, COA_ASSOCIATION CA
                                          WHERE CAD.COA_ASSOCIATION_ID=CA.COA_ASSOCIATION_ID AND CA.ASSOCIATION_TITLE IN ('SALES TARGET'))
                                          CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE UNION ALL
                                         SELECT COA.COA_CDE FROM (SELECT COA.COA_CDE FROM CHART_OF_ACCOUNT COA  WHERE COA.ENTRY_LEVEL_IND='Y' 
                                           START WITH COA.COA_CDE IN (SELECT CAD.COA_CDE FROM COA_ASSOCIATION_DETAIL CAD, COA_ASSOCIATION CA 
                                           WHERE CAD.COA_ASSOCIATION_ID=CA.COA_ASSOCIATION_ID AND CA.ASSOCIATION_TITLE IN ('DAILY SHEET ITEMS')) 
                                           CONNECT BY PRIOR COA.COA_CDE=COA.PARENT_CDE
                                         ) COA, SITE_INVENTORY SI WHERE COA.COA_CDE=SI.COA_CDE AND SI.SITE_ID=FP_SITE_ID)
              ) VD GROUP BY VD.MONTH_NME,VD.COA_CDE
        ) SP, CHART_OF_ACCOUNT COA WHERE SP.COA_CDE=COA.COA_CDE GROUP BY SP.MONTH_NME, SP.COA_CDE ORDER BY SP.MONTH_NME, SP.COA_CDE ASC;
BEGIN
    FOR FL_SP IN CS_SALES_COMPARISON LOOP
      PIPE ROW (OBJECT_SALES_COMPARISON(FL_SP.MONTH_NME,FL_SP.COA_CDE, FL_SP.TITLE, FL_SP.TARGET, FL_SP.SALE));          
    END LOOP;
  RETURN;  
END;
/




