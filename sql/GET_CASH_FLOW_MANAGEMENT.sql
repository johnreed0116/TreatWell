CREATE OR REPLACE TYPE OBJECT_CASH_FLOW_MANAGEMENT IS OBJECT (
  GRP_ORDER NUMBER,
  GRP_DESCRIPTION VARCHAR2(64),
  COA_CDE VARCHAR2(32),
  AMNT NUMBER,
  VOUCHER_MASTER_ID NUMBER,
  VOUCHER_NBR VARCHAR2(16),
  VOUCHER_DTE VARCHAR2(16),
  CHEQUE_NBR VARCHAR2(32),
  CHEQUE_DTE VARCHAR2(16),
  PAYMENT_MODE VARCHAR2(8),
  LINE_NARRATION VARCHAR2(250)
);
/
CREATE OR REPLACE TYPE TABLE_CASH_FLOW_MANAGEMENT IS TABLE OF OBJECT_CASH_FLOW_MANAGEMENT;
/
CREATE OR REPLACE
FUNCTION GET_CASH_FLOW_MANAGEMENT(FP_AS_ON_DATE IN VARCHAR2, FP_BU_ID IN NUMBER, FP_COA_CDE IN VARCHAR2, FP_GRP_ORDER IN NUMBER, FP_COMPANY_ID IN NUMBER) 
RETURN TABLE_CASH_FLOW_MANAGEMENT PIPELINED IS
CURSOR CS_CFM_0 IS 
    SELECT BR.GRPORDER, BR.GRPDESCRIPTION, BR.COA_CDE, BR.CHQ_AMNT, NULL VOUCHER_MASTER_ID, NULL VOUCHER_NBR, NULL VOUCHER_DTE, 
        NULL CHEQUE_NBR, NULL CHEQUE_DTE, NULL PAYMENT_MODE, NULL LINE_NARRATION FROM ( 
        SELECT MAX(2) GRPORDER, MAX('ADD: CREDIT BY US NOT DEBITED BY BANK') GRPDESCRIPTION, VD.COA_CDE, SUM(VD.AMNT) CHQ_AMNT
        FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, BANK_ACCOUNT BA  
        WHERE VD.VOUCHER_MASTER_ID=VM.VOUCHER_MASTER_ID  
          AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID     
          AND VST.VOUCHER_TYP IN ('BPV','BTV') AND (VD.AMNT)<0 
          AND VM.STATUS_IND IS NULL AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y'
          AND VD.COA_CDE=BA.COA_CDE AND VM.COMPANY_ID=FP_COMPANY_ID
          AND VM.CHEQUE_DTE<=TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY')
          AND VM.SITE_ID IN (SELECT S.SITE_ID FROM SITE S WHERE S.COMPANY_ID=FP_COMPANY_ID AND 0=FP_BU_ID UNION ALL
              SELECT S.SITE_ID FROM SITE S, SITE_BUSINESS_UNIT SBU 
                WHERE S.SITE_ID=SBU.SITE_ID AND S.COMPANY_ID=SBU.COMPANY_ID 
                  AND SBU.COMPANY_ID=FP_COMPANY_ID AND SBU.BUSINESS_UNIT_ID=FP_BU_ID)
          AND VM.VOUCHER_MASTER_ID NOT IN (SELECT VM_1.REF_VOUCHER_MASTER_ID FROM VOUCHER_MASTER VM_1 WHERE VM_1.REF_VOUCHER_MASTER_ID IS NOT NULL AND VM_1.COMPANY_ID=FP_COMPANY_ID)
          AND (VD.CHQ_CLEARANCE_DTE IS NULL OR VD.CHQ_CLEARANCE_DTE > TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY')) 
        GROUP BY VD.COA_CDE
        UNION ALL 
        SELECT MAX(3) GRPORDER, MAX('LESS: DEBITED BY US NOT CREDIT BY BANK') GRPDESCRIPTION, VD.COA_CDE, SUM(VD.AMNT) CHQ_AMNT
        FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, BANK_ACCOUNT BA, VOUCHER_SUB_TYPE VST 
        WHERE VD.VOUCHER_MASTER_ID=VM.VOUCHER_MASTER_ID           
          AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID
          AND VM.STATUS_IND IS NULL AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y' 
          AND VST.VOUCHER_TYP IN ('BRV','BTV') AND (VD.AMNT)>0 
          AND VD.COA_CDE=BA.COA_CDE AND VM.COMPANY_ID=FP_COMPANY_ID
          AND VM.CHEQUE_DTE<=TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY')
          AND VM.SITE_ID IN (SELECT S.SITE_ID FROM SITE S WHERE S.COMPANY_ID=FP_COMPANY_ID AND 0=FP_BU_ID UNION ALL
              SELECT S.SITE_ID FROM SITE S, SITE_BUSINESS_UNIT SBU 
                WHERE S.SITE_ID=SBU.SITE_ID AND S.COMPANY_ID=SBU.COMPANY_ID 
                  AND SBU.COMPANY_ID=FP_COMPANY_ID AND SBU.BUSINESS_UNIT_ID=FP_BU_ID)
          AND VM.VOUCHER_MASTER_ID NOT IN (SELECT VM_1.REF_VOUCHER_MASTER_ID FROM VOUCHER_MASTER VM_1 WHERE VM_1.REF_VOUCHER_MASTER_ID IS NOT NULL AND VM_1.COMPANY_ID=FP_COMPANY_ID)
          AND (VD.CHQ_CLEARANCE_DTE IS NULL OR VD.CHQ_CLEARANCE_DTE > TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY')) 
        GROUP BY VD.COA_CDE  
        UNION ALL 
        SELECT MAX(1) GRPORDER, MAX('BANK BALANCE') GRPDESCRIPTION, BB.COA_CDE, SUM(BB.AMNT) LEDGER_BAL
        FROM BANK_BALANCE BB
        WHERE BB.COMPANY_ID=FP_COMPANY_ID  
          AND BB.BAL_DTE=TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY')
          AND BB.SITE_ID IN (SELECT S.SITE_ID FROM SITE S WHERE S.COMPANY_ID=FP_COMPANY_ID AND 0=FP_BU_ID UNION ALL
              SELECT S.SITE_ID FROM SITE S, SITE_BUSINESS_UNIT SBU 
                WHERE S.SITE_ID=SBU.SITE_ID AND S.COMPANY_ID=SBU.COMPANY_ID 
                  AND SBU.COMPANY_ID=FP_COMPANY_ID AND SBU.BUSINESS_UNIT_ID=FP_BU_ID)
        GROUP BY BB.COA_CDE
        UNION ALL         
        SELECT MAX(4) GRPORDER, MAX('BANK BORROWING LIMIT') GRPDESCRIPTION, VD.COA_CDE, MAX(NVL(BA.RUN_FIN_LIMIT,0)) FIN_LIMIT 
        FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, BANK_ACCOUNT BA  
        WHERE VD.VOUCHER_MASTER_ID=VM.VOUCHER_MASTER_ID  
          AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID     
          AND VST.VOUCHER_TYP IN ('BRV','BPV','BTV') 
          AND (VD.AMNT)<>0 AND NVL(BA.RUN_FIN_LIMIT,0)<>0
          AND VM.STATUS_IND IS NULL AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y'
          AND VD.COA_CDE=BA.COA_CDE AND VM.COMPANY_ID=FP_COMPANY_ID
          AND VM.CHEQUE_DTE<=TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY')
          AND VM.SITE_ID IN (SELECT S.SITE_ID FROM SITE S WHERE S.COMPANY_ID=FP_COMPANY_ID AND 0=FP_BU_ID UNION ALL
              SELECT S.SITE_ID FROM SITE S, SITE_BUSINESS_UNIT SBU 
                WHERE S.SITE_ID=SBU.SITE_ID AND S.COMPANY_ID=SBU.COMPANY_ID 
                  AND SBU.COMPANY_ID=FP_COMPANY_ID AND SBU.BUSINESS_UNIT_ID=FP_BU_ID)
          AND VM.VOUCHER_MASTER_ID NOT IN (SELECT VM_1.REF_VOUCHER_MASTER_ID FROM VOUCHER_MASTER VM_1 WHERE VM_1.REF_VOUCHER_MASTER_ID IS NOT NULL AND VM_1.COMPANY_ID=FP_COMPANY_ID)
          AND (VD.CHQ_CLEARANCE_DTE IS NULL OR VD.CHQ_CLEARANCE_DTE > TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY'))     
        GROUP BY VD.COA_CDE
    ) BR ORDER BY BR.COA_CDE ASC, BR.GRPORDER ASC;
  
CURSOR CS_CFM_2 IS 
     SELECT 2 GRPORDER, 'ADD: CREDIT BY US NOT DEBITED BY BANK' GRPDESCRIPTION, VD.COA_CDE, (VD.AMNT) CHQ_AMNT,
        VM.VOUCHER_MASTER_ID, ((VST.VOUCHER_SUB_TYP_DESC||'-'||VM.VOUCHER_NBR)) VOUCHER_NBR, TO_CHAR(VM.VOUCHER_DTE,'MON-DD-YYYY') VOUCHER_DTE, 
        VM.CHEQUE_NBR, TO_CHAR(VM.CHEQUE_DTE,'MON-DD-YYYY') CHEQUE_DTE, VD.LINE_NARRATION,
        (CASE WHEN VM.CHEQUE_NBR IS NOT NULL AND VM.PAYMENT_MODE IS NULL THEN 'CHQ' WHEN VM.PAYMENT_MODE IS NOT NULL THEN VM.PAYMENT_MODE ELSE NULL END) PAYMENT_MODE
        FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, VOUCHER_SUB_TYPE VST, BANK_ACCOUNT BA  
        WHERE VD.VOUCHER_MASTER_ID=VM.VOUCHER_MASTER_ID
          AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID     
          AND VM.STATUS_IND IS NULL AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y'
          AND VST.VOUCHER_TYP IN ('BPV','BTV') AND (VD.AMNT)<0 
          AND VD.COA_CDE=BA.COA_CDE AND VD.COA_CDE=FP_COA_CDE 
          AND VM.COMPANY_ID=FP_COMPANY_ID
          AND VM.CHEQUE_DTE<=TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY')
          AND VM.SITE_ID IN (SELECT S.SITE_ID FROM SITE S WHERE S.COMPANY_ID=FP_COMPANY_ID AND 0=FP_BU_ID UNION ALL
              SELECT S.SITE_ID FROM SITE S, SITE_BUSINESS_UNIT SBU 
                WHERE S.SITE_ID=SBU.SITE_ID AND S.COMPANY_ID=SBU.COMPANY_ID 
                  AND SBU.COMPANY_ID=FP_COMPANY_ID AND SBU.BUSINESS_UNIT_ID=FP_BU_ID)
          AND VM.VOUCHER_MASTER_ID NOT IN (SELECT VM_1.REF_VOUCHER_MASTER_ID FROM VOUCHER_MASTER VM_1 WHERE VM_1.REF_VOUCHER_MASTER_ID IS NOT NULL AND VM_1.COMPANY_ID=FP_COMPANY_ID)
          AND (VD.CHQ_CLEARANCE_DTE IS NULL OR VD.CHQ_CLEARANCE_DTE > TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY')) 
     ORDER BY VM.CHEQUE_DTE ASC, VM.CHEQUE_NBR ASC;

CURSOR CS_CFM_3 IS 
     SELECT 3 GRPORDER, 'LESS: DEBITED BY US NOT CREDIT BY BANK' GRPDESCRIPTION, VD.COA_CDE, (VD.AMNT) CHQ_AMNT,
        VM.VOUCHER_MASTER_ID, ((VST.VOUCHER_SUB_TYP_DESC||'-'||VM.VOUCHER_NBR)) VOUCHER_NBR, TO_CHAR(VM.VOUCHER_DTE,'MON-DD-YYYY') VOUCHER_DTE, 
        VM.CHEQUE_NBR, TO_CHAR(VM.CHEQUE_DTE,'MON-DD-YYYY') CHEQUE_DTE, VD.LINE_NARRATION,
        (CASE WHEN VM.CHEQUE_NBR IS NOT NULL AND VM.PAYMENT_MODE IS NULL THEN 'CHQ' WHEN VM.PAYMENT_MODE IS NOT NULL THEN VM.PAYMENT_MODE ELSE NULL END) PAYMENT_MODE
        FROM VOUCHER_DETAIL VD, VOUCHER_MASTER VM, BANK_ACCOUNT BA, VOUCHER_SUB_TYPE VST 
        WHERE VD.VOUCHER_MASTER_ID=VM.VOUCHER_MASTER_ID 
          AND VM.VOUCHER_SUB_TYP_ID=VST.VOUCHER_SUB_TYP_ID    
          AND VM.STATUS_IND IS NULL AND VM.CANCELLED_BY IS NULL AND VM.POSTED_IND='Y'
          AND VST.VOUCHER_TYP IN ('BRV','BTV') AND (VD.AMNT)>0
          AND VD.COA_CDE=BA.COA_CDE AND VD.COA_CDE=FP_COA_CDE           
          AND VM.COMPANY_ID=FP_COMPANY_ID
          AND VM.CHEQUE_DTE<=TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY') 
          AND VM.SITE_ID IN (SELECT S.SITE_ID FROM SITE S WHERE S.COMPANY_ID=FP_COMPANY_ID AND 0=FP_BU_ID UNION ALL
              SELECT S.SITE_ID FROM SITE S, SITE_BUSINESS_UNIT SBU 
                WHERE S.SITE_ID=SBU.SITE_ID AND S.COMPANY_ID=SBU.COMPANY_ID 
                  AND SBU.COMPANY_ID=FP_COMPANY_ID AND SBU.BUSINESS_UNIT_ID=FP_BU_ID)
          AND VM.VOUCHER_MASTER_ID NOT IN (SELECT VM_1.REF_VOUCHER_MASTER_ID FROM VOUCHER_MASTER VM_1 WHERE VM_1.REF_VOUCHER_MASTER_ID IS NOT NULL AND VM_1.COMPANY_ID=FP_COMPANY_ID)
          AND (VD.CHQ_CLEARANCE_DTE IS NULL OR VD.CHQ_CLEARANCE_DTE > TO_DATE(FP_AS_ON_DATE,'DD-MM-YYYY'))     
     ORDER BY VM.CHEQUE_DTE ASC, VM.CHEQUE_NBR ASC;
    
BEGIN
  IF (FP_GRP_ORDER = 0) THEN -- ZERO FOR SUMMARY
    FOR FL_CFM IN CS_CFM_0 LOOP
        PIPE ROW (OBJECT_CASH_FLOW_MANAGEMENT(FL_CFM.GRPORDER, FL_CFM.GRPDESCRIPTION, FL_CFM.COA_CDE, FL_CFM.CHQ_AMNT,
          FL_CFM.VOUCHER_MASTER_ID, FL_CFM.VOUCHER_NBR, FL_CFM.VOUCHER_DTE, FL_CFM.CHEQUE_NBR, FL_CFM.CHEQUE_DTE, FL_CFM.PAYMENT_MODE, FL_CFM.LINE_NARRATION));
    END LOOP;
  ELSIF (FP_GRP_ORDER = 2) THEN -- PRESENTS DETAIL 
    FOR FL_CFM IN CS_CFM_2 LOOP
    PIPE ROW (OBJECT_CASH_FLOW_MANAGEMENT(FL_CFM.GRPORDER, FL_CFM.GRPDESCRIPTION, FL_CFM.COA_CDE, FL_CFM.CHQ_AMNT,
      FL_CFM.VOUCHER_MASTER_ID, FL_CFM.VOUCHER_NBR, FL_CFM.VOUCHER_DTE, FL_CFM.CHEQUE_NBR, FL_CFM.CHEQUE_DTE, FL_CFM.PAYMENT_MODE, FL_CFM.LINE_NARRATION));
    END LOOP;
  ELSIF (FP_GRP_ORDER = 3) THEN -- DEPOSITS DETAIL
    FOR FL_CFM IN CS_CFM_3 LOOP
    PIPE ROW (OBJECT_CASH_FLOW_MANAGEMENT(FL_CFM.GRPORDER, FL_CFM.GRPDESCRIPTION, FL_CFM.COA_CDE, FL_CFM.CHQ_AMNT,
      FL_CFM.VOUCHER_MASTER_ID, FL_CFM.VOUCHER_NBR, FL_CFM.VOUCHER_DTE, FL_CFM.CHEQUE_NBR, FL_CFM.CHEQUE_DTE, FL_CFM.PAYMENT_MODE, FL_CFM.LINE_NARRATION));
    END LOOP;
  END IF;    
  RETURN;  
END;
/  