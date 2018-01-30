/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

/**
 *
 * @author Faraz
 */
public class ReconciliationVO {

    private String shiftReconciliationId;
    private String siteId;
    private String userName;
    private String reconDate;
    private String nfDiscount;
    //SSV
    private String shiftANo = "0";
    private String shiftBNo = "0";
    private String shiftCNo = "0";
    private String posA = "0";
    private String posB = "0";
    private String posC = "0";
    private String manualA = "0";
    private String manualB = "0";
    private String manualC = "0";
    private String cashA = "0";
    private String cashB = "0";
    private String cashC = "0";
    private String arA = "0";
    private String arB = "0";
    private String arC = "0";
    private String payoutA = "0";
    private String payoutB = "0";
    private String payoutC = "0";
    private String lottoPaidA = "0";
    private String lottoPaidB = "0";
    private String lottoPaidC = "0";
    private String driveOffA = "0";
    private String driveOffB = "0";
    private String driveOffC = "0";
    private String pointGasA = "0";
    private String pointGasB = "0";
    private String pointGasC = "0";
    private String salesA = "0";
    private String salesB = "0";
    private String salesC = "0";
    private String sheetBalanceA = "0";
    private String sheetBalanceB = "0";
    private String sheetBalanceC = "0";
    //MTD
    private String tobacco = "0";
    private String lotto = "0";
    private String other = "0";
    private String hotFood = "0";
    private String cigarettes = "0";
    private String beverage = "0";
    private String dairy = "0";
    private String confectionery = "0";
    private String novelty = "0";
    private String telephoneCard = "0";
    private String petroCanadaCard = "0";
    private String gstCollected = "0";
    private String pstCollected = "0";
    private String storeCash = "0";
    private String bankDeposit = "0";
    private String otherPayout = "0";
    private String usDollar = "0";
    private String redemption = "0";
    private String gstPetroPoints = "0";
    private String creditCardCharges = "0";
    //Fuel Reconciliation
    private String regularQuantityShiftA = "0";
    private String regularAmountShiftA = "0";
    private String regularQuantityShiftB = "0";
    private String regularAmountShiftB = "0";
    private String regularQuantityShiftC = "0";
    private String regularAmountShiftC = "0";
    private String regularEodQuantity = "0";
    private String regularEodAmount = "0";
    private String plusQuantityShiftA = "0";
    private String plusAmountShiftA = "0";
    private String plusQuantityShiftB = "0";
    private String plusAmountShiftB = "0";
    private String plusQuantityShiftC = "0";
    private String plusAmountShiftC = "0";
    private String plusEodQuantity = "0";
    private String plusEodAmount = "0";
    private String superCleanQuantityShiftA = "0";
    private String superCleanAmountShiftA = "0";
    private String superCleanQuantityShiftB = "0";
    private String superCleanAmountShiftB = "0";
    private String superCleanQuantityShiftC = "0";
    private String superCleanAmountShiftC = "0";
    private String superCleanEodQuantity = "0";
    private String superCleanEodAmount = "0";
    private String ultra94QuantityShiftA = "0";
    private String ultra94AmountShiftA = "0";
    private String ultra94QuantityShiftB = "0";
    private String ultra94AmountShiftB = "0";
    private String ultra94QuantityShiftC = "0";
    private String ultra94AmountShiftC = "0";
    private String ultra94EodQuantity = "0";
    private String ultra94EodAmount = "0";
    private String dieselQuantityShiftA = "0";
    private String dieselAmountShiftA = "0";
    private String dieselQuantityShiftB = "0";
    private String dieselAmountShiftB = "0";
    private String dieselQuantityShiftC = "0";
    private String dieselAmountShiftC = "0";
    private String dieselEodQuantity = "0";
    private String dieselEodAmount = "0";
    private String nfdQuantityShiftA = "0";
    private String nfdAmountShiftA = "0";
    private String nfdQuantityShiftB = "0";
    private String nfdAmountShiftB = "0";
    private String nfdQuantityShiftC = "0";
    private String nfdAmountShiftC = "0";
    private String nfdEodQuantity = "0";
    private String nfdEodAmount = "0";
    //EOD
    private String netDiscountShiftA = "0";
    private String netDiscountShiftB = "0";
    private String netDiscountShiftC = "0";
    private String netDiscountEod = "0";
    private String netDiscountDifference = "0";
    private String crindDiscountShiftA = "0";
    private String crindDiscountShiftB = "0";
    private String crindDiscountShiftC = "0";
    private String crindDiscountEod = "0";
    private String posSettlementShiftA = "0";
    private String posSettlementShiftB = "0";
    private String posSettlementShiftC = "0";
    private String posSettlementEod = "0";
    private String dueShiftA = "0";
    private String dueShiftB = "0";
    private String dueShiftC = "0";
    private String dueEod = "0";
    private String totalFuelSales = "0";
    private String fuelCommission = "0";
    private String commissionGst = "0";
    private String totalDiffAmount = "0";
    private String totalReconciliation = "0";
    private String companyId;
    /*POS*/
    private String[] addedShift;
    private String[] addedShiftNum;
    private String[] addedGsaName;
    private String[] addedGsaId;
    private String[] addedTimeFrom;
    private String[] addedTimeTo;
    private String[] addedPosName;
    private String[] addedFuelSales;
    private String[] addedDiscount;
    private String[] addedItemSales;
    private String[] addedGst;
    private String[] addedPst;
    private String[] addedPosAmount;
    private String[] addedPayout;
    private String[] addedReceivable;
    private String[] addedOtherCoupons;
    private String[] addedCanadianCash;
    private String[] addedUsDollar;
    private String[] addedTotalCollection;
    private String[] addedTotalTransaction;
    private String[] addedFuelOnlyTransaction;
    private String[] addedPPTransaction;

    private String[] addedRegularLtr;
    private String[] addedRegularAmount;
    private String[] addedPlusLtr;
    private String[] addedPlusAmount;
    private String[] addedSuperCleanLtr;
    private String[] addedSuperCleanAmount;
    private String[] addedUltra94Ltr;
    private String[] addedUltra94Amount;
    private String[] addedDieselLtr;
    private String[] addedDieselAmount;

    /**
     * @return the shiftANo
     */
    public String getShiftANo() {
        return shiftANo;
    }

    /**
     * @param shiftANo the shiftANo to set
     */
    public void setShiftANo(String shiftANo) {
        this.shiftANo = shiftANo;
    }

    /**
     * @return the shiftBNo
     */
    public String getShiftBNo() {
        return shiftBNo;
    }

    /**
     * @param shiftBNo the shiftBNo to set
     */
    public void setShiftBNo(String shiftBNo) {
        this.shiftBNo = shiftBNo;
    }

    /**
     * @return the shiftCNo
     */
    public String getShiftCNo() {
        return shiftCNo;
    }

    /**
     * @param shiftCNo the shiftCNo to set
     */
    public void setShiftCNo(String shiftCNo) {
        this.shiftCNo = shiftCNo;
    }

    /**
     * @return the posA
     */
    public String getPosA() {
        return posA;
    }

    /**
     * @param posA the posA to set
     */
    public void setPosA(String posA) {
        this.posA = posA;
    }

    /**
     * @return the posB
     */
    public String getPosB() {
        return posB;
    }

    /**
     * @param posB the posB to set
     */
    public void setPosB(String posB) {
        this.posB = posB;
    }

    /**
     * @return the posC
     */
    public String getPosC() {
        return posC;
    }

    /**
     * @param posC the posC to set
     */
    public void setPosC(String posC) {
        this.posC = posC;
    }

    /**
     * @return the manualA
     */
    public String getManualA() {
        return manualA;
    }

    /**
     * @param manualA the manualA to set
     */
    public void setManualA(String manualA) {
        this.manualA = manualA;
    }

    /**
     * @return the manualB
     */
    public String getManualB() {
        return manualB;
    }

    /**
     * @param manualB the manualB to set
     */
    public void setManualB(String manualB) {
        this.manualB = manualB;
    }

    /**
     * @return the manualC
     */
    public String getManualC() {
        return manualC;
    }

    /**
     * @param manualC the manualC to set
     */
    public void setManualC(String manualC) {
        this.manualC = manualC;
    }

    /**
     * @return the cashA
     */
    public String getCashA() {
        return cashA;
    }

    /**
     * @param cashA the cashA to set
     */
    public void setCashA(String cashA) {
        this.cashA = cashA;
    }

    /**
     * @return the cashB
     */
    public String getCashB() {
        return cashB;
    }

    /**
     * @param cashB the cashB to set
     */
    public void setCashB(String cashB) {
        this.cashB = cashB;
    }

    /**
     * @return the cashC
     */
    public String getCashC() {
        return cashC;
    }

    /**
     * @param cashC the cashC to set
     */
    public void setCashC(String cashC) {
        this.cashC = cashC;
    }

    /**
     * @return the arA
     */
    public String getArA() {
        return arA;
    }

    /**
     * @param arA the arA to set
     */
    public void setArA(String arA) {
        this.arA = arA;
    }

    /**
     * @return the arB
     */
    public String getArB() {
        return arB;
    }

    /**
     * @param arB the arB to set
     */
    public void setArB(String arB) {
        this.arB = arB;
    }

    /**
     * @return the arC
     */
    public String getArC() {
        return arC;
    }

    /**
     * @param arC the arC to set
     */
    public void setArC(String arC) {
        this.arC = arC;
    }

    /**
     * @return the payoutA
     */
    public String getPayoutA() {
        return payoutA;
    }

    /**
     * @param payoutA the payoutA to set
     */
    public void setPayoutA(String payoutA) {
        this.payoutA = payoutA;
    }

    /**
     * @return the payoutB
     */
    public String getPayoutB() {
        return payoutB;
    }

    /**
     * @param payoutB the payoutB to set
     */
    public void setPayoutB(String payoutB) {
        this.payoutB = payoutB;
    }

    /**
     * @return the payoutC
     */
    public String getPayoutC() {
        return payoutC;
    }

    /**
     * @param payoutC the payoutC to set
     */
    public void setPayoutC(String payoutC) {
        this.payoutC = payoutC;
    }

    /**
     * @return the lottoPaidA
     */
    public String getLottoPaidA() {
        return lottoPaidA;
    }

    /**
     * @param lottoPaidA the lottoPaidA to set
     */
    public void setLottoPaidA(String lottoPaidA) {
        this.lottoPaidA = lottoPaidA;
    }

    /**
     * @return the lottoPaidB
     */
    public String getLottoPaidB() {
        return lottoPaidB;
    }

    /**
     * @param lottoPaidB the lottoPaidB to set
     */
    public void setLottoPaidB(String lottoPaidB) {
        this.lottoPaidB = lottoPaidB;
    }

    /**
     * @return the lottoPaidC
     */
    public String getLottoPaidC() {
        return lottoPaidC;
    }

    /**
     * @param lottoPaidC the lottoPaidC to set
     */
    public void setLottoPaidC(String lottoPaidC) {
        this.lottoPaidC = lottoPaidC;
    }

    /**
     * @return the driveOffA
     */
    public String getDriveOffA() {
        return driveOffA;
    }

    /**
     * @param driveOffA the driveOffA to set
     */
    public void setDriveOffA(String driveOffA) {
        this.driveOffA = driveOffA;
    }

    /**
     * @return the driveOffB
     */
    public String getDriveOffB() {
        return driveOffB;
    }

    /**
     * @param driveOffB the driveOffB to set
     */
    public void setDriveOffB(String driveOffB) {
        this.driveOffB = driveOffB;
    }

    /**
     * @return the driveOffC
     */
    public String getDriveOffC() {
        return driveOffC;
    }

    /**
     * @param driveOffC the driveOffC to set
     */
    public void setDriveOffC(String driveOffC) {
        this.driveOffC = driveOffC;
    }

    /**
     * @return the pointGasA
     */
    public String getPointGasA() {
        return pointGasA;
    }

    /**
     * @param pointGasA the pointGasA to set
     */
    public void setPointGasA(String pointGasA) {
        this.pointGasA = pointGasA;
    }

    /**
     * @return the pointGasB
     */
    public String getPointGasB() {
        return pointGasB;
    }

    /**
     * @param pointGasB the pointGasB to set
     */
    public void setPointGasB(String pointGasB) {
        this.pointGasB = pointGasB;
    }

    /**
     * @return the pointGasC
     */
    public String getPointGasC() {
        return pointGasC;
    }

    /**
     * @param pointGasC the pointGasC to set
     */
    public void setPointGasC(String pointGasC) {
        this.pointGasC = pointGasC;
    }

    /**
     * @return the salesA
     */
    public String getSalesA() {
        return salesA;
    }

    /**
     * @param salesA the salesA to set
     */
    public void setSalesA(String salesA) {
        this.salesA = salesA;
    }

    /**
     * @return the salesB
     */
    public String getSalesB() {
        return salesB;
    }

    /**
     * @param salesB the salesB to set
     */
    public void setSalesB(String salesB) {
        this.salesB = salesB;
    }

    /**
     * @return the salesC
     */
    public String getSalesC() {
        return salesC;
    }

    /**
     * @param salesC the salesC to set
     */
    public void setSalesC(String salesC) {
        this.salesC = salesC;
    }

    /**
     * @return the sheetBalanceA
     */
    public String getSheetBalanceA() {
        return sheetBalanceA;
    }

    /**
     * @param sheetBalanceA the sheetBalanceA to set
     */
    public void setSheetBalanceA(String sheetBalanceA) {
        this.sheetBalanceA = sheetBalanceA;
    }

    /**
     * @return the sheetBalanceB
     */
    public String getSheetBalanceB() {
        return sheetBalanceB;
    }

    /**
     * @param sheetBalanceB the sheetBalanceB to set
     */
    public void setSheetBalanceB(String sheetBalanceB) {
        this.sheetBalanceB = sheetBalanceB;
    }

    /**
     * @return the sheetBalanceC
     */
    public String getSheetBalanceC() {
        return sheetBalanceC;
    }

    /**
     * @param sheetBalanceC the sheetBalanceC to set
     */
    public void setSheetBalanceC(String sheetBalanceC) {
        this.sheetBalanceC = sheetBalanceC;
    }

    /**
     * @return the tobacco
     */
    public String getTobacco() {
        return tobacco;
    }

    /**
     * @param tobacco the tobacco to set
     */
    public void setTobacco(String tobacco) {
        this.tobacco = tobacco;
    }

    /**
     * @return the lotto
     */
    public String getLotto() {
        return lotto;
    }

    /**
     * @param lotto the lotto to set
     */
    public void setLotto(String lotto) {
        this.lotto = lotto;
    }

    /**
     * @return the other
     */
    public String getOther() {
        return other;
    }

    /**
     * @param other the other to set
     */
    public void setOther(String other) {
        this.other = other;
    }

    /**
     * @return the hotFood
     */
    public String getHotFood() {
        return hotFood;
    }

    /**
     * @param hotFood the hotFood to set
     */
    public void setHotFood(String hotFood) {
        this.hotFood = hotFood;
    }

    /**
     * @return the telephoneCard
     */
    public String getTelephoneCard() {
        return telephoneCard;
    }

    /**
     * @param telephoneCard the telephoneCard to set
     */
    public void setTelephoneCard(String telephoneCard) {
        this.telephoneCard = telephoneCard;
    }

    /**
     * @return the petroCanadaCard
     */
    public String getPetroCanadaCard() {
        return petroCanadaCard;
    }

    /**
     * @param petroCanadaCard the petroCanadaCard to set
     */
    public void setPetroCanadaCard(String petroCanadaCard) {
        this.petroCanadaCard = petroCanadaCard;
    }

    /**
     * @return the gstCollected
     */
    public String getGstCollected() {
        return gstCollected;
    }

    /**
     * @param gstCollected the gstCollected to set
     */
    public void setGstCollected(String gstCollected) {
        this.gstCollected = gstCollected;
    }

    /**
     * @return the pstCollected
     */
    public String getPstCollected() {
        return pstCollected;
    }

    /**
     * @param pstCollected the pstCollected to set
     */
    public void setPstCollected(String pstCollected) {
        this.pstCollected = pstCollected;
    }

    /**
     * @return the storeCash
     */
    public String getStoreCash() {
        return storeCash;
    }

    /**
     * @param storeCash the storeCash to set
     */
    public void setStoreCash(String storeCash) {
        this.storeCash = storeCash;
    }

    /**
     * @return the bankDeposit
     */
    public String getBankDeposit() {
        return bankDeposit;
    }

    /**
     * @param bankDeposit the bankDeposit to set
     */
    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    /**
     * @return the otherPayout
     */
    public String getOtherPayout() {
        return otherPayout;
    }

    /**
     * @param otherPayout the otherPayout to set
     */
    public void setOtherPayout(String otherPayout) {
        this.otherPayout = otherPayout;
    }

    /**
     * @return the usDollar
     */
    public String getUsDollar() {
        return usDollar;
    }

    /**
     * @param usDollar the usDollar to set
     */
    public void setUsDollar(String usDollar) {
        this.usDollar = usDollar;
    }

    /**
     * @return the regularQuantityShiftA
     */
    public String getRegularQuantityShiftA() {
        return regularQuantityShiftA;
    }

    /**
     * @param regularQuantityShiftA the regularQuantityShiftA to set
     */
    public void setRegularQuantityShiftA(String regularQuantityShiftA) {
        this.regularQuantityShiftA = regularQuantityShiftA;
    }

    /**
     * @return the regularAmountShiftA
     */
    public String getRegularAmountShiftA() {
        return regularAmountShiftA;
    }

    /**
     * @param regularAmountShiftA the regularAmountShiftA to set
     */
    public void setRegularAmountShiftA(String regularAmountShiftA) {
        this.regularAmountShiftA = regularAmountShiftA;
    }

    /**
     * @return the regularQuantityShiftB
     */
    public String getRegularQuantityShiftB() {
        return regularQuantityShiftB;
    }

    /**
     * @param regularQuantityShiftB the regularQuantityShiftB to set
     */
    public void setRegularQuantityShiftB(String regularQuantityShiftB) {
        this.regularQuantityShiftB = regularQuantityShiftB;
    }

    /**
     * @return the regularAmountShiftB
     */
    public String getRegularAmountShiftB() {
        return regularAmountShiftB;
    }

    /**
     * @param regularAmountShiftB the regularAmountShiftB to set
     */
    public void setRegularAmountShiftB(String regularAmountShiftB) {
        this.regularAmountShiftB = regularAmountShiftB;
    }

    /**
     * @return the regularQuantityShiftC
     */
    public String getRegularQuantityShiftC() {
        return regularQuantityShiftC;
    }

    /**
     * @param regularQuantityShiftC the regularQuantityShiftC to set
     */
    public void setRegularQuantityShiftC(String regularQuantityShiftC) {
        this.regularQuantityShiftC = regularQuantityShiftC;
    }

    /**
     * @return the regularAmountShiftC
     */
    public String getRegularAmountShiftC() {
        return regularAmountShiftC;
    }

    /**
     * @param regularAmountShiftC the regularAmountShiftC to set
     */
    public void setRegularAmountShiftC(String regularAmountShiftC) {
        this.regularAmountShiftC = regularAmountShiftC;
    }

    /**
     * @return the regularEodQuantity
     */
    public String getRegularEodQuantity() {
        return regularEodQuantity;
    }

    /**
     * @param regularEodQuantity the regularEodQuantity to set
     */
    public void setRegularEodQuantity(String regularEodQuantity) {
        this.regularEodQuantity = regularEodQuantity;
    }

    /**
     * @return the regularEodAmount
     */
    public String getRegularEodAmount() {
        return regularEodAmount;
    }

    /**
     * @param regularEodAmount the regularEodAmount to set
     */
    public void setRegularEodAmount(String regularEodAmount) {
        this.regularEodAmount = regularEodAmount;
    }

    /**
     * @return the plusQuantityShiftA
     */
    public String getPlusQuantityShiftA() {
        return plusQuantityShiftA;
    }

    /**
     * @param plusQuantityShiftA the plusQuantityShiftA to set
     */
    public void setPlusQuantityShiftA(String plusQuantityShiftA) {
        this.plusQuantityShiftA = plusQuantityShiftA;
    }

    /**
     * @return the plusAmountShiftA
     */
    public String getPlusAmountShiftA() {
        return plusAmountShiftA;
    }

    /**
     * @param plusAmountShiftA the plusAmountShiftA to set
     */
    public void setPlusAmountShiftA(String plusAmountShiftA) {
        this.plusAmountShiftA = plusAmountShiftA;
    }

    /**
     * @return the plusQuantityShiftB
     */
    public String getPlusQuantityShiftB() {
        return plusQuantityShiftB;
    }

    /**
     * @param plusQuantityShiftB the plusQuantityShiftB to set
     */
    public void setPlusQuantityShiftB(String plusQuantityShiftB) {
        this.plusQuantityShiftB = plusQuantityShiftB;
    }

    /**
     * @return the plusAmountShiftB
     */
    public String getPlusAmountShiftB() {
        return plusAmountShiftB;
    }

    /**
     * @param plusAmountShiftB the plusAmountShiftB to set
     */
    public void setPlusAmountShiftB(String plusAmountShiftB) {
        this.plusAmountShiftB = plusAmountShiftB;
    }

    /**
     * @return the plusQuantityShiftC
     */
    public String getPlusQuantityShiftC() {
        return plusQuantityShiftC;
    }

    /**
     * @param plusQuantityShiftC the plusQuantityShiftC to set
     */
    public void setPlusQuantityShiftC(String plusQuantityShiftC) {
        this.plusQuantityShiftC = plusQuantityShiftC;
    }

    /**
     * @return the plusAmountShiftC
     */
    public String getPlusAmountShiftC() {
        return plusAmountShiftC;
    }

    /**
     * @param plusAmountShiftC the plusAmountShiftC to set
     */
    public void setPlusAmountShiftC(String plusAmountShiftC) {
        this.plusAmountShiftC = plusAmountShiftC;
    }

    /**
     * @return the plusEodQuantity
     */
    public String getPlusEodQuantity() {
        return plusEodQuantity;
    }

    /**
     * @param plusEodQuantity the plusEodQuantity to set
     */
    public void setPlusEodQuantity(String plusEodQuantity) {
        this.plusEodQuantity = plusEodQuantity;
    }

    /**
     * @return the plusEodAmount
     */
    public String getPlusEodAmount() {
        return plusEodAmount;
    }

    /**
     * @param plusEodAmount the plusEodAmount to set
     */
    public void setPlusEodAmount(String plusEodAmount) {
        this.plusEodAmount = plusEodAmount;
    }

    /**
     * @return the superCleanQuantityShiftA
     */
    public String getSuperCleanQuantityShiftA() {
        return superCleanQuantityShiftA;
    }

    /**
     * @param superCleanQuantityShiftA the superCleanQuantityShiftA to set
     */
    public void setSuperCleanQuantityShiftA(String superCleanQuantityShiftA) {
        this.superCleanQuantityShiftA = superCleanQuantityShiftA;
    }

    /**
     * @return the superCleanAmountShiftA
     */
    public String getSuperCleanAmountShiftA() {
        return superCleanAmountShiftA;
    }

    /**
     * @param superCleanAmountShiftA the superCleanAmountShiftA to set
     */
    public void setSuperCleanAmountShiftA(String superCleanAmountShiftA) {
        this.superCleanAmountShiftA = superCleanAmountShiftA;
    }

    /**
     * @return the superCleanQuantityShiftB
     */
    public String getSuperCleanQuantityShiftB() {
        return superCleanQuantityShiftB;
    }

    /**
     * @param superCleanQuantityShiftB the superCleanQuantityShiftB to set
     */
    public void setSuperCleanQuantityShiftB(String superCleanQuantityShiftB) {
        this.superCleanQuantityShiftB = superCleanQuantityShiftB;
    }

    /**
     * @return the superCleanAmountShiftB
     */
    public String getSuperCleanAmountShiftB() {
        return superCleanAmountShiftB;
    }

    /**
     * @param superCleanAmountShiftB the superCleanAmountShiftB to set
     */
    public void setSuperCleanAmountShiftB(String superCleanAmountShiftB) {
        this.superCleanAmountShiftB = superCleanAmountShiftB;
    }

    /**
     * @return the superCleanQuantityShiftC
     */
    public String getSuperCleanQuantityShiftC() {
        return superCleanQuantityShiftC;
    }

    /**
     * @param superCleanQuantityShiftC the superCleanQuantityShiftC to set
     */
    public void setSuperCleanQuantityShiftC(String superCleanQuantityShiftC) {
        this.superCleanQuantityShiftC = superCleanQuantityShiftC;
    }

    /**
     * @return the superCleanAmountShiftC
     */
    public String getSuperCleanAmountShiftC() {
        return superCleanAmountShiftC;
    }

    /**
     * @param superCleanAmountShiftC the superCleanAmountShiftC to set
     */
    public void setSuperCleanAmountShiftC(String superCleanAmountShiftC) {
        this.superCleanAmountShiftC = superCleanAmountShiftC;
    }

    /**
     * @return the superCleanEodQuantity
     */
    public String getSuperCleanEodQuantity() {
        return superCleanEodQuantity;
    }

    /**
     * @param superCleanEodQuantity the superCleanEodQuantity to set
     */
    public void setSuperCleanEodQuantity(String superCleanEodQuantity) {
        this.superCleanEodQuantity = superCleanEodQuantity;
    }

    /**
     * @return the superCleanEodAmount
     */
    public String getSuperCleanEodAmount() {
        return superCleanEodAmount;
    }

    /**
     * @param superCleanEodAmount the superCleanEodAmount to set
     */
    public void setSuperCleanEodAmount(String superCleanEodAmount) {
        this.superCleanEodAmount = superCleanEodAmount;
    }

    /**
     * @return the ultra94QuantityShiftA
     */
    public String getUltra94QuantityShiftA() {
        return ultra94QuantityShiftA;
    }

    /**
     * @param ultra94QuantityShiftA the ultra94QuantityShiftA to set
     */
    public void setUltra94QuantityShiftA(String ultra94QuantityShiftA) {
        this.ultra94QuantityShiftA = ultra94QuantityShiftA;
    }

    /**
     * @return the ultra94AmountShiftA
     */
    public String getUltra94AmountShiftA() {
        return ultra94AmountShiftA;
    }

    /**
     * @param ultra94AmountShiftA the ultra94AmountShiftA to set
     */
    public void setUltra94AmountShiftA(String ultra94AmountShiftA) {
        this.ultra94AmountShiftA = ultra94AmountShiftA;
    }

    /**
     * @return the ultra94QuantityShiftB
     */
    public String getUltra94QuantityShiftB() {
        return ultra94QuantityShiftB;
    }

    /**
     * @param ultra94QuantityShiftB the ultra94QuantityShiftB to set
     */
    public void setUltra94QuantityShiftB(String ultra94QuantityShiftB) {
        this.ultra94QuantityShiftB = ultra94QuantityShiftB;
    }

    /**
     * @return the ultra94AmountShiftB
     */
    public String getUltra94AmountShiftB() {
        return ultra94AmountShiftB;
    }

    /**
     * @param ultra94AmountShiftB the ultra94AmountShiftB to set
     */
    public void setUltra94AmountShiftB(String ultra94AmountShiftB) {
        this.ultra94AmountShiftB = ultra94AmountShiftB;
    }

    /**
     * @return the ultra94QuantityShiftC
     */
    public String getUltra94QuantityShiftC() {
        return ultra94QuantityShiftC;
    }

    /**
     * @param ultra94QuantityShiftC the ultra94QuantityShiftC to set
     */
    public void setUltra94QuantityShiftC(String ultra94QuantityShiftC) {
        this.ultra94QuantityShiftC = ultra94QuantityShiftC;
    }

    /**
     * @return the ultra94AmountShiftC
     */
    public String getUltra94AmountShiftC() {
        return ultra94AmountShiftC;
    }

    /**
     * @param ultra94AmountShiftC the ultra94AmountShiftC to set
     */
    public void setUltra94AmountShiftC(String ultra94AmountShiftC) {
        this.ultra94AmountShiftC = ultra94AmountShiftC;
    }

    /**
     * @return the ultra94EodQuantity
     */
    public String getUltra94EodQuantity() {
        return ultra94EodQuantity;
    }

    /**
     * @param ultra94EodQuantity the ultra94EodQuantity to set
     */
    public void setUltra94EodQuantity(String ultra94EodQuantity) {
        this.ultra94EodQuantity = ultra94EodQuantity;
    }

    /**
     * @return the ultra94EodAmount
     */
    public String getUltra94EodAmount() {
        return ultra94EodAmount;
    }

    /**
     * @param ultra94EodAmount the ultra94EodAmount to set
     */
    public void setUltra94EodAmount(String ultra94EodAmount) {
        this.ultra94EodAmount = ultra94EodAmount;
    }

    /**
     * @return the dieselQuantityShiftA
     */
    public String getDieselQuantityShiftA() {
        return dieselQuantityShiftA;
    }

    /**
     * @param dieselQuantityShiftA the dieselQuantityShiftA to set
     */
    public void setDieselQuantityShiftA(String dieselQuantityShiftA) {
        this.dieselQuantityShiftA = dieselQuantityShiftA;
    }

    /**
     * @return the dieselAmountShiftA
     */
    public String getDieselAmountShiftA() {
        return dieselAmountShiftA;
    }

    /**
     * @param dieselAmountShiftA the dieselAmountShiftA to set
     */
    public void setDieselAmountShiftA(String dieselAmountShiftA) {
        this.dieselAmountShiftA = dieselAmountShiftA;
    }

    /**
     * @return the dieselQuantityShiftB
     */
    public String getDieselQuantityShiftB() {
        return dieselQuantityShiftB;
    }

    /**
     * @param dieselQuantityShiftB the dieselQuantityShiftB to set
     */
    public void setDieselQuantityShiftB(String dieselQuantityShiftB) {
        this.dieselQuantityShiftB = dieselQuantityShiftB;
    }

    /**
     * @return the dieselAmountShiftB
     */
    public String getDieselAmountShiftB() {
        return dieselAmountShiftB;
    }

    /**
     * @param dieselAmountShiftB the dieselAmountShiftB to set
     */
    public void setDieselAmountShiftB(String dieselAmountShiftB) {
        this.dieselAmountShiftB = dieselAmountShiftB;
    }

    /**
     * @return the dieselQuantityShiftC
     */
    public String getDieselQuantityShiftC() {
        return dieselQuantityShiftC;
    }

    /**
     * @param dieselQuantityShiftC the dieselQuantityShiftC to set
     */
    public void setDieselQuantityShiftC(String dieselQuantityShiftC) {
        this.dieselQuantityShiftC = dieselQuantityShiftC;
    }

    /**
     * @return the dieselAmountShiftC
     */
    public String getDieselAmountShiftC() {
        return dieselAmountShiftC;
    }

    /**
     * @param dieselAmountShiftC the dieselAmountShiftC to set
     */
    public void setDieselAmountShiftC(String dieselAmountShiftC) {
        this.dieselAmountShiftC = dieselAmountShiftC;
    }

    /**
     * @return the dieselEodQuantity
     */
    public String getDieselEodQuantity() {
        return dieselEodQuantity;
    }

    /**
     * @param dieselEodQuantity the dieselEodQuantity to set
     */
    public void setDieselEodQuantity(String dieselEodQuantity) {
        this.dieselEodQuantity = dieselEodQuantity;
    }

    /**
     * @return the dieselEodAmount
     */
    public String getDieselEodAmount() {
        return dieselEodAmount;
    }

    /**
     * @param dieselEodAmount the dieselEodAmount to set
     */
    public void setDieselEodAmount(String dieselEodAmount) {
        this.dieselEodAmount = dieselEodAmount;
    }

    /**
     * @return the nfdQuantityShiftA
     */
    public String getNfdQuantityShiftA() {
        return nfdQuantityShiftA;
    }

    /**
     * @param nfdQuantityShiftA the nfdQuantityShiftA to set
     */
    public void setNfdQuantityShiftA(String nfdQuantityShiftA) {
        this.nfdQuantityShiftA = nfdQuantityShiftA;
    }

    /**
     * @return the nfdAmountShiftA
     */
    public String getNfdAmountShiftA() {
        return nfdAmountShiftA;
    }

    /**
     * @param nfdAmountShiftA the nfdAmountShiftA to set
     */
    public void setNfdAmountShiftA(String nfdAmountShiftA) {
        this.nfdAmountShiftA = nfdAmountShiftA;
    }

    /**
     * @return the nfdQuantityShiftB
     */
    public String getNfdQuantityShiftB() {
        return nfdQuantityShiftB;
    }

    /**
     * @param nfdQuantityShiftB the nfdQuantityShiftB to set
     */
    public void setNfdQuantityShiftB(String nfdQuantityShiftB) {
        this.nfdQuantityShiftB = nfdQuantityShiftB;
    }

    /**
     * @return the nfdAmountShiftB
     */
    public String getNfdAmountShiftB() {
        return nfdAmountShiftB;
    }

    /**
     * @param nfdAmountShiftB the nfdAmountShiftB to set
     */
    public void setNfdAmountShiftB(String nfdAmountShiftB) {
        this.nfdAmountShiftB = nfdAmountShiftB;
    }

    /**
     * @return the nfdQuantityShiftC
     */
    public String getNfdQuantityShiftC() {
        return nfdQuantityShiftC;
    }

    /**
     * @param nfdQuantityShiftC the nfdQuantityShiftC to set
     */
    public void setNfdQuantityShiftC(String nfdQuantityShiftC) {
        this.nfdQuantityShiftC = nfdQuantityShiftC;
    }

    /**
     * @return the nfdAmountShiftC
     */
    public String getNfdAmountShiftC() {
        return nfdAmountShiftC;
    }

    /**
     * @param nfdAmountShiftC the nfdAmountShiftC to set
     */
    public void setNfdAmountShiftC(String nfdAmountShiftC) {
        this.nfdAmountShiftC = nfdAmountShiftC;
    }

    /**
     * @return the nfdEodQuantity
     */
    public String getNfdEodQuantity() {
        return nfdEodQuantity;
    }

    /**
     * @param nfdEodQuantity the nfdEodQuantity to set
     */
    public void setNfdEodQuantity(String nfdEodQuantity) {
        this.nfdEodQuantity = nfdEodQuantity;
    }

    /**
     * @return the nfdEodAmount
     */
    public String getNfdEodAmount() {
        return nfdEodAmount;
    }

    /**
     * @param nfdEodAmount the nfdEodAmount to set
     */
    public void setNfdEodAmount(String nfdEodAmount) {
        this.nfdEodAmount = nfdEodAmount;
    }

    /**
     * @return the netDiscountShiftA
     */
    public String getNetDiscountShiftA() {
        return netDiscountShiftA;
    }

    /**
     * @param netDiscountShiftA the netDiscountShiftA to set
     */
    public void setNetDiscountShiftA(String netDiscountShiftA) {
        this.netDiscountShiftA = netDiscountShiftA;
    }

    /**
     * @return the netDiscountShiftB
     */
    public String getNetDiscountShiftB() {
        return netDiscountShiftB;
    }

    /**
     * @param netDiscountShiftB the netDiscountShiftB to set
     */
    public void setNetDiscountShiftB(String netDiscountShiftB) {
        this.netDiscountShiftB = netDiscountShiftB;
    }

    /**
     * @return the netDiscountShiftC
     */
    public String getNetDiscountShiftC() {
        return netDiscountShiftC;
    }

    /**
     * @param netDiscountShiftC the netDiscountShiftC to set
     */
    public void setNetDiscountShiftC(String netDiscountShiftC) {
        this.netDiscountShiftC = netDiscountShiftC;
    }

    /**
     * @return the netDiscountEod
     */
    public String getNetDiscountEod() {
        return netDiscountEod;
    }

    /**
     * @param netDiscountEod the netDiscountEod to set
     */
    public void setNetDiscountEod(String netDiscountEod) {
        this.netDiscountEod = netDiscountEod;
    }

    /**
     * @return the netDiscountDifference
     */
    public String getNetDiscountDifference() {
        return netDiscountDifference;
    }

    /**
     * @param netDiscountDifference the netDiscountDifference to set
     */
    public void setNetDiscountDifference(String netDiscountDifference) {
        this.netDiscountDifference = netDiscountDifference;
    }

    /**
     * @return the crindDiscountShiftA
     */
    public String getCrindDiscountShiftA() {
        return crindDiscountShiftA;
    }

    /**
     * @param crindDiscountShiftA the crindDiscountShiftA to set
     */
    public void setCrindDiscountShiftA(String crindDiscountShiftA) {
        this.crindDiscountShiftA = crindDiscountShiftA;
    }

    /**
     * @return the crindDiscountShiftB
     */
    public String getCrindDiscountShiftB() {
        return crindDiscountShiftB;
    }

    /**
     * @param crindDiscountShiftB the crindDiscountShiftB to set
     */
    public void setCrindDiscountShiftB(String crindDiscountShiftB) {
        this.crindDiscountShiftB = crindDiscountShiftB;
    }

    /**
     * @return the crindDiscountShiftC
     */
    public String getCrindDiscountShiftC() {
        return crindDiscountShiftC;
    }

    /**
     * @param crindDiscountShiftC the crindDiscountShiftC to set
     */
    public void setCrindDiscountShiftC(String crindDiscountShiftC) {
        this.crindDiscountShiftC = crindDiscountShiftC;
    }

    /**
     * @return the crindDiscountEod
     */
    public String getCrindDiscountEod() {
        return crindDiscountEod;
    }

    /**
     * @param crindDiscountEod the crindDiscountEod to set
     */
    public void setCrindDiscountEod(String crindDiscountEod) {
        this.crindDiscountEod = crindDiscountEod;
    }

    /**
     * @return the posSettlementShiftA
     */
    public String getPosSettlementShiftA() {
        return posSettlementShiftA;
    }

    /**
     * @param posSettlementShiftA the posSettlementShiftA to set
     */
    public void setPosSettlementShiftA(String posSettlementShiftA) {
        this.posSettlementShiftA = posSettlementShiftA;
    }

    /**
     * @return the posSettlementShiftB
     */
    public String getPosSettlementShiftB() {
        return posSettlementShiftB;
    }

    /**
     * @param posSettlementShiftB the posSettlementShiftB to set
     */
    public void setPosSettlementShiftB(String posSettlementShiftB) {
        this.posSettlementShiftB = posSettlementShiftB;
    }

    /**
     * @return the posSettlementShiftC
     */
    public String getPosSettlementShiftC() {
        return posSettlementShiftC;
    }

    /**
     * @param posSettlementShiftC the posSettlementShiftC to set
     */
    public void setPosSettlementShiftC(String posSettlementShiftC) {
        this.posSettlementShiftC = posSettlementShiftC;
    }

    /**
     * @return the posSettlementEod
     */
    public String getPosSettlementEod() {
        return posSettlementEod;
    }

    /**
     * @param posSettlementEod the posSettlementEod to set
     */
    public void setPosSettlementEod(String posSettlementEod) {
        this.posSettlementEod = posSettlementEod;
    }

    /**
     * @return the dueShiftA
     */
    public String getDueShiftA() {
        return dueShiftA;
    }

    /**
     * @param dueShiftA the dueShiftA to set
     */
    public void setDueShiftA(String dueShiftA) {
        this.dueShiftA = dueShiftA;
    }

    /**
     * @return the dueShiftB
     */
    public String getDueShiftB() {
        return dueShiftB;
    }

    /**
     * @param dueShiftB the dueShiftB to set
     */
    public void setDueShiftB(String dueShiftB) {
        this.dueShiftB = dueShiftB;
    }

    /**
     * @return the dueShiftC
     */
    public String getDueShiftC() {
        return dueShiftC;
    }

    /**
     * @param dueShiftC the dueShiftC to set
     */
    public void setDueShiftC(String dueShiftC) {
        this.dueShiftC = dueShiftC;
    }

    /**
     * @return the dueEod
     */
    public String getDueEod() {
        return dueEod;
    }

    /**
     * @param dueEod the dueEod to set
     */
    public void setDueEod(String dueEod) {
        this.dueEod = dueEod;
    }

    /**
     * @return the totalFuelSales
     */
    public String getTotalFuelSales() {
        return totalFuelSales;
    }

    /**
     * @param totalFuelSales the totalFuelSales to set
     */
    public void setTotalFuelSales(String totalFuelSales) {
        this.totalFuelSales = totalFuelSales;
    }

    /**
     * @return the fuelCommission
     */
    public String getFuelCommission() {
        return fuelCommission;
    }

    /**
     * @param fuelCommission the fuelCommission to set
     */
    public void setFuelCommission(String fuelCommission) {
        this.fuelCommission = fuelCommission;
    }

    /**
     * @return the commissionGst
     */
    public String getCommissionGst() {
        return commissionGst;
    }

    /**
     * @param commissionGst the commissionGst to set
     */
    public void setCommissionGst(String commissionGst) {
        this.commissionGst = commissionGst;
    }

    /**
     * @return the siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the reconDate
     */
    public String getReconDate() {
        return reconDate;
    }

    /**
     * @param reconDate the reconDate to set
     */
    public void setReconDate(String reconDate) {
        this.reconDate = reconDate;
    }

    /**
     * @return the nfDiscount
     */
    public String getNfDiscount() {
        return nfDiscount;
    }

    /**
     * @param nfDiscount the nfDiscount to set
     */
    public void setNfDiscount(String nfDiscount) {
        this.nfDiscount = nfDiscount;
    }

    /**
     * @return the shiftReconciliationId
     */
    public String getShiftReconciliationId() {
        return shiftReconciliationId;
    }

    /**
     * @param shiftReconciliationId the shiftReconciliationId to set
     */
    public void setShiftReconciliationId(String shiftReconciliationId) {
        this.shiftReconciliationId = shiftReconciliationId;
    }

    /**
     * @return the redemption
     */
    public String getRedemption() {
        return redemption;
    }

    /**
     * @param redemption the redemption to set
     */
    public void setRedemption(String redemption) {
        this.redemption = redemption;
    }

    /**
     * @return the gstPetroPoints
     */
    public String getGstPetroPoints() {
        return gstPetroPoints;
    }

    /**
     * @param gstPetroPoints the gstPetroPoints to set
     */
    public void setGstPetroPoints(String gstPetroPoints) {
        this.gstPetroPoints = gstPetroPoints;
    }

    /**
     * @return the creditCardCharges
     */
    public String getCreditCardCharges() {
        return creditCardCharges;
    }

    /**
     * @param creditCardCharges the creditCardCharges to set
     */
    public void setCreditCardCharges(String creditCardCharges) {
        this.creditCardCharges = creditCardCharges;
    }

    /**
     * @return the totalDiffAmount
     */
    public String getTotalDiffAmount() {
        return totalDiffAmount;
    }

    /**
     * @param totalDiffAmount the totalDiffAmount to set
     */
    public void setTotalDiffAmount(String totalDiffAmount) {
        this.totalDiffAmount = totalDiffAmount;
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the totalReconciliation
     */
    public String getTotalReconciliation() {
        return totalReconciliation;
    }

    /**
     * @param totalReconciliation the totalReconciliation to set
     */
    public void setTotalReconciliation(String totalReconciliation) {
        this.totalReconciliation = totalReconciliation;
    }

    /**
     * @return the addedShift
     */
    public String[] getAddedShift() {
        return addedShift;
    }

    /**
     * @param addedShift the addedShift to set
     */
    public void setAddedShift(String[] addedShift) {
        this.addedShift = addedShift;
    }

    /**
     * @return the addedShiftNum
     */
    public String[] getAddedShiftNum() {
        return addedShiftNum;
    }

    /**
     * @param addedShiftNum the addedShiftNum to set
     */
    public void setAddedShiftNum(String[] addedShiftNum) {
        this.addedShiftNum = addedShiftNum;
    }

    /**
     * @return the addedGsaName
     */
    public String[] getAddedGsaName() {
        return addedGsaName;
    }

    /**
     * @param addedGsaName the addedGsaName to set
     */
    public void setAddedGsaName(String[] addedGsaName) {
        this.addedGsaName = addedGsaName;
    }

    /**
     * @return the addedTimeFrom
     */
    public String[] getAddedTimeFrom() {
        return addedTimeFrom;
    }

    /**
     * @param addedTimeFrom the addedTimeFrom to set
     */
    public void setAddedTimeFrom(String[] addedTimeFrom) {
        this.addedTimeFrom = addedTimeFrom;
    }

    /**
     * @return the addedTimeTo
     */
    public String[] getAddedTimeTo() {
        return addedTimeTo;
    }

    /**
     * @param addedTimeTo the addedTimeTo to set
     */
    public void setAddedTimeTo(String[] addedTimeTo) {
        this.addedTimeTo = addedTimeTo;
    }

    /**
     * @return the addedPosName
     */
    public String[] getAddedPosName() {
        return addedPosName;
    }

    /**
     * @param addedPosName the addedPosName to set
     */
    public void setAddedPosName(String[] addedPosName) {
        this.addedPosName = addedPosName;
    }

    /**
     * @return the addedFuelSales
     */
    public String[] getAddedFuelSales() {
        return addedFuelSales;
    }

    /**
     * @param addedFuelSales the addedFuelSales to set
     */
    public void setAddedFuelSales(String[] addedFuelSales) {
        this.addedFuelSales = addedFuelSales;
    }

    /**
     * @return the addedDiscount
     */
    public String[] getAddedDiscount() {
        return addedDiscount;
    }

    /**
     * @param addedDiscount the addedDiscount to set
     */
    public void setAddedDiscount(String[] addedDiscount) {
        this.addedDiscount = addedDiscount;
    }

    /**
     * @return the addedItemSales
     */
    public String[] getAddedItemSales() {
        return addedItemSales;
    }

    /**
     * @param addedItemSales the addedItemSales to set
     */
    public void setAddedItemSales(String[] addedItemSales) {
        this.addedItemSales = addedItemSales;
    }

    /**
     * @return the addedGst
     */
    public String[] getAddedGst() {
        return addedGst;
    }

    /**
     * @param addedGst the addedGst to set
     */
    public void setAddedGst(String[] addedGst) {
        this.addedGst = addedGst;
    }

    /**
     * @return the addedPosAmount
     */
    public String[] getAddedPosAmount() {
        return addedPosAmount;
    }

    /**
     * @param addedPosAmount the addedPosAmount to set
     */
    public void setAddedPosAmount(String[] addedPosAmount) {
        this.addedPosAmount = addedPosAmount;
    }

    /**
     * @return the addedPayout
     */
    public String[] getAddedPayout() {
        return addedPayout;
    }

    /**
     * @param addedPayout the addedPayout to set
     */
    public void setAddedPayout(String[] addedPayout) {
        this.addedPayout = addedPayout;
    }

    /**
     * @return the addedReceivable
     */
    public String[] getAddedReceivable() {
        return addedReceivable;
    }

    /**
     * @param addedReceivable the addedReceivable to set
     */
    public void setAddedReceivable(String[] addedReceivable) {
        this.addedReceivable = addedReceivable;
    }

    /**
     * @return the addedOtherCoupons
     */
    public String[] getAddedOtherCoupons() {
        return addedOtherCoupons;
    }

    /**
     * @param addedOtherCoupons the addedOtherCoupons to set
     */
    public void setAddedOtherCoupons(String[] addedOtherCoupons) {
        this.addedOtherCoupons = addedOtherCoupons;
    }

    /**
     * @return the addedCanadianCash
     */
    public String[] getAddedCanadianCash() {
        return addedCanadianCash;
    }

    /**
     * @param addedCanadianCash the addedCanadianCash to set
     */
    public void setAddedCanadianCash(String[] addedCanadianCash) {
        this.addedCanadianCash = addedCanadianCash;
    }

    /**
     * @return the addedUsDollar
     */
    public String[] getAddedUsDollar() {
        return addedUsDollar;
    }

    /**
     * @param addedUsDollar the addedUsDollar to set
     */
    public void setAddedUsDollar(String[] addedUsDollar) {
        this.addedUsDollar = addedUsDollar;
    }

    /**
     * @return the addedTotalCollection
     */
    public String[] getAddedTotalCollection() {
        return addedTotalCollection;
    }

    /**
     * @param addedTotalCollection the addedTotalCollection to set
     */
    public void setAddedTotalCollection(String[] addedTotalCollection) {
        this.addedTotalCollection = addedTotalCollection;
    }

    /**
     * @return the addedTotalTransaction
     */
    public String[] getAddedTotalTransaction() {
        return addedTotalTransaction;
    }

    /**
     * @param addedTotalTransaction the addedTotalTransaction to set
     */
    public void setAddedTotalTransaction(String[] addedTotalTransaction) {
        this.addedTotalTransaction = addedTotalTransaction;
    }

    /**
     * @return the addedFuelOnlyTransaction
     */
    public String[] getAddedFuelOnlyTransaction() {
        return addedFuelOnlyTransaction;
    }

    /**
     * @param addedFuelOnlyTransaction the addedFuelOnlyTransaction to set
     */
    public void setAddedFuelOnlyTransaction(String[] addedFuelOnlyTransaction) {
        this.addedFuelOnlyTransaction = addedFuelOnlyTransaction;
    }

    /**
     * @return the addedRegularLtr
     */
    public String[] getAddedRegularLtr() {
        return addedRegularLtr;
    }

    /**
     * @param addedRegularLtr the addedRegularLtr to set
     */
    public void setAddedRegularLtr(String[] addedRegularLtr) {
        this.addedRegularLtr = addedRegularLtr;
    }

    /**
     * @return the addedRegularAmount
     */
    public String[] getAddedRegularAmount() {
        return addedRegularAmount;
    }

    /**
     * @param addedRegularAmount the addedRegularAmount to set
     */
    public void setAddedRegularAmount(String[] addedRegularAmount) {
        this.addedRegularAmount = addedRegularAmount;
    }

    /**
     * @return the addedPlusLtr
     */
    public String[] getAddedPlusLtr() {
        return addedPlusLtr;
    }

    /**
     * @param addedPlusLtr the addedPlusLtr to set
     */
    public void setAddedPlusLtr(String[] addedPlusLtr) {
        this.addedPlusLtr = addedPlusLtr;
    }

    /**
     * @return the addedPlusAmount
     */
    public String[] getAddedPlusAmount() {
        return addedPlusAmount;
    }

    /**
     * @param addedPlusAmount the addedPlusAmount to set
     */
    public void setAddedPlusAmount(String[] addedPlusAmount) {
        this.addedPlusAmount = addedPlusAmount;
    }

    /**
     * @return the addedSuperCleanLtr
     */
    public String[] getAddedSuperCleanLtr() {
        return addedSuperCleanLtr;
    }

    /**
     * @param addedSuperCleanLtr the addedSuperCleanLtr to set
     */
    public void setAddedSuperCleanLtr(String[] addedSuperCleanLtr) {
        this.addedSuperCleanLtr = addedSuperCleanLtr;
    }

    /**
     * @return the addedSuperCleanAmount
     */
    public String[] getAddedSuperCleanAmount() {
        return addedSuperCleanAmount;
    }

    /**
     * @param addedSuperCleanAmount the addedSuperCleanAmount to set
     */
    public void setAddedSuperCleanAmount(String[] addedSuperCleanAmount) {
        this.addedSuperCleanAmount = addedSuperCleanAmount;
    }

    /**
     * @return the addedUltra94Ltr
     */
    public String[] getAddedUltra94Ltr() {
        return addedUltra94Ltr;
    }

    /**
     * @param addedUltra94Ltr the addedUltra94Ltr to set
     */
    public void setAddedUltra94Ltr(String[] addedUltra94Ltr) {
        this.addedUltra94Ltr = addedUltra94Ltr;
    }

    /**
     * @return the addedUltra94Amount
     */
    public String[] getAddedUltra94Amount() {
        return addedUltra94Amount;
    }

    /**
     * @param addedUltra94Amount the addedUltra94Amount to set
     */
    public void setAddedUltra94Amount(String[] addedUltra94Amount) {
        this.addedUltra94Amount = addedUltra94Amount;
    }

    /**
     * @return the addedDieselLtr
     */
    public String[] getAddedDieselLtr() {
        return addedDieselLtr;
    }

    /**
     * @param addedDieselLtr the addedDieselLtr to set
     */
    public void setAddedDieselLtr(String[] addedDieselLtr) {
        this.addedDieselLtr = addedDieselLtr;
    }

    /**
     * @return the addedDieselAmount
     */
    public String[] getAddedDieselAmount() {
        return addedDieselAmount;
    }

    /**
     * @param addedDieselAmount the addedDieselAmount to set
     */
    public void setAddedDieselAmount(String[] addedDieselAmount) {
        this.addedDieselAmount = addedDieselAmount;
    }

    /**
     * @return the addedPst
     */
    public String[] getAddedPst() {
        return addedPst;
    }

    /**
     * @param addedPst the addedPst to set
     */
    public void setAddedPst(String[] addedPst) {
        this.addedPst = addedPst;
    }

    /**
     * @return the addedGsaId
     */
    public String[] getAddedGsaId() {
        return addedGsaId;
    }

    /**
     * @param addedGsaId the addedGsaId to set
     */
    public void setAddedGsaId(String[] addedGsaId) {
        this.addedGsaId = addedGsaId;
    }

    /**
     * @return the addedPPTransaction
     */
    public String[] getAddedPPTransaction() {
        return addedPPTransaction;
    }

    /**
     * @param addedPPTransaction the addedPPTransaction to set
     */
    public void setAddedPPTransaction(String[] addedPPTransaction) {
        this.addedPPTransaction = addedPPTransaction;
    }

    /**
     * @return the cigarettes
     */
    public String getCigarettes() {
        return cigarettes;
    }

    /**
     * @param cigarettes the cigarettes to set
     */
    public void setCigarettes(String cigarettes) {
        this.cigarettes = cigarettes;
    }

    /**
     * @return the beverage
     */
    public String getBeverage() {
        return beverage;
    }

    /**
     * @param beverage the beverage to set
     */
    public void setBeverage(String beverage) {
        this.beverage = beverage;
    }

    /**
     * @return the dairy
     */
    public String getDairy() {
        return dairy;
    }

    /**
     * @param dairy the dairy to set
     */
    public void setDairy(String dairy) {
        this.dairy = dairy;
    }

    /**
     * @return the confectionery
     */
    public String getConfectionery() {
        return confectionery;
    }

    /**
     * @param confectionery the confectionery to set
     */
    public void setConfectionery(String confectionery) {
        this.confectionery = confectionery;
    }

    /**
     * @return the novelty
     */
    public String getNovelty() {
        return novelty;
    }

    /**
     * @param novelty the novelty to set
     */
    public void setNovelty(String novelty) {
        this.novelty = novelty;
    }
}
