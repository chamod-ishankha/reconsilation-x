package org.bytecub.reconsilationx.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.model.master.MCustomerLoanOd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link MCustomerLoanOd}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCustomerLoanOdDto implements Serializable {
    private Long sumCusLdIntIncId;
    private String custAccNum;
    private String acid;
    private String custName;
    private String cifId;
    private String sol;
    private String accMgrSol;
    private String accMgrName;
    private String schmCode;
    private String prevSchmCode;
    private String schmType;
    private String subClassification;
    private String crncyCode;
    private BigDecimal intRate;
    private String intTableName;
    private LocalDate accOpnDate;
    private Integer noOfDaysUpToMonthEnd;
    private BigDecimal outstandingBalOri;
    private BigDecimal outstandingBalLocal;
    private BigDecimal monthlyAvgBal;
    private Integer intProvDays;
    private String sfGlAccNum;
    private String iisGlAccNum;
    private String intDmdPattern;
    private String misCode1;
    private String iiGlAccNum;
    private BigDecimal curMonIntIncLocal;
    private BigDecimal prevMonIntIncLocal;
    private BigDecimal intAccruedLocal;
    private BigDecimal intDmdLocal;
    private BigDecimal provLocal;
    private BigDecimal monthlySfBalLocal;
    private Integer arrearsDays;
    private BigDecimal sfIIBalLocal;
    private BigDecimal iisBalLocal;
    private BigDecimal monthlyIntCashRecoveryLocal;
    private BigDecimal monthlyIntWaivedLocal;
    private BigDecimal backdatedIntRecoveryLocal;
    private Integer intRecoveryCoveredDays;
    private LocalDate accClsDate;
    private BigDecimal intAccruedOri;
    private BigDecimal intDmdOri;
    private BigDecimal provOri;
    private String yearMonth;
    private BigDecimal frxRate;
    private String provGlAccNum;
    private BigDecimal iisBalOri;
    private BigDecimal monthlyIntCashRecoveryOri;
    private BigDecimal curMonIntIncOri;
    private BigDecimal prevMonIntIncOri;
    private BigDecimal monthlySfBalOri;
    private BigDecimal backdatedIntRecoveryOri;
    private BigDecimal monthlyIntWaivedOri;
    private Boolean usable;
    private BigDecimal monIntProvOri;
    private BigDecimal monIntProvLocal;
    private String delFlg;
    private BigDecimal outstandingBalEabOri;
    private BigDecimal outstandingBalEabLocal;
    private String prodGrp;
    private Integer mainClassificationSystem;
    private BigDecimal sfIIBalOri;
    private String allowProv;
    private String icGlAccNum;
    private Boolean new1;
    private Boolean new2;
    private Integer new3;
    private String misCode2;
    private String misCode3;
    private String misCode4;
    private BigDecimal intReversalOri;
    private BigDecimal intReversalLocal;
    private BigDecimal dailyConvertedCashRecoveryLocal;
    private BigDecimal dailyConvertedLoanRevLocal;
    private BigDecimal notAllowedMonthlyIntProvOri;
    private BigDecimal notAllowedMonthlyIntProvLocal;
    private BigDecimal monthlyAvgBalLocal;
}