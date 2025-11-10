package org.bytecub.reconsilationx.model.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.CUSTOMER_LOAN_OD_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CUSTOMER_LOAN_OD_TABLE, indexes = {})
public class MCustomerLoanOd {
    @Id
    @SequenceGenerator(name = CUSTOMER_LOAN_OD_TABLE, allocationSize = 1, sequenceName = CUSTOMER_LOAN_OD_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = CUSTOMER_LOAN_OD_TABLE)
    @Column(name = "SUM_CUS_LD_INT_INC_ID", nullable = false)
    private Long sumCusLdIntIncId;

    @Column(name = "CUSTOMER_ACCOUNT_NUMBER", length = 50)
    private String custAccNum;

    @Column(name = "ACID", length = 50)
    private String acid;

    @Column(name = "CUSTOMER_NAME", length = 200)
    private String custName;

    @Column(name = "CIF_ID", length = 50)
    private String cifId;

    @Column(name = "SOL", length = 10)
    private String sol;

    @Column(name = "ACCOUNT_MANAGER_SOL", length = 10)
    private String accMgrSol;

    @Column(name = "ACCOUNT_MANAGER_NAME", length = 100)
    private String accMgrName;

    @Column(name = "SCHEME_CODE", length = 20)
    private String schmCode;

    @Column(name = "PREVIOUS_SCHEME_CODE", length = 20)
    private String prevSchmCode;

    @Column(name = "SCHEME_TYPE", length = 50)
    private String schmType;

    @Column(name = "SUB_CLASSIFICATION", length = 50)
    private String subClassification;

    @Column(name = "CURRENCY_CODE", length = 10)
    private String crncyCode;

    @Column(name = "INTEREST_RATE", precision = 10, scale = 4)
    private BigDecimal intRate;

    @Column(name = "INTEREST_TABLE_NAME", length = 50)
    private String intTableName;

    @Column(name = "ACCOUNT_OPENING_DATE")
    private LocalDate accOpnDate;

    @Column(name = "NUMBER_OF_DAYS_UPTO_MONTHEND")
    private Integer noOfDaysUpToMonthEnd;

    @Column(name = "OUTSTANDING_BALANCE_ORIGINAL", precision = 20, scale = 4)
    private BigDecimal outstandingBalOri;

    @Column(name = "OUTSTANDING_BALANCE_LKR", precision = 20, scale = 4)
    private BigDecimal outstandingBalLocal;

    @Column(name = "MONTHLY_AVERAGE_BALANCE", precision = 20, scale = 4)
    private BigDecimal monthlyAvgBal;

    @Column(name = "INTEREST_PROVISION_DAYS")
    private Integer intProvDays;

    @Column(name = "SHORTFALL_GL_NUMBER", length = 20)
    private String sfGlAccNum;

    @Column(name = "IIS_GL_NUMBER", length = 20)
    private String iisGlAccNum;

    @Column(name = "INTEREST_DEMAND_PATTERN", length = 20)
    private String intDmdPattern;

    @Column(name = "MIS_CODE_APPLIED_1", length = 100)
    private String misCode1;

    @Column(name = "INTEREST_INCOME_GL_NUMBER", length = 20)
    private String iiGlAccNum;

    @Column(name = "CURRENCT_MONTH_INTEREST_INCOME_LKR", precision = 20, scale = 4)
    private BigDecimal curMonIntIncLocal;

    @Column(name = "PREVIOUS_MONTH_INTEREST_INCOME_LKR", precision = 20, scale = 4)
    private BigDecimal prevMonIntIncLocal;

    @Column(name = "INTEREST_ACCRUED_LKR", precision = 20, scale = 4)
    private BigDecimal intAccruedLocal;

    @Column(name = "INTEREST_DEMANDED_LKR", precision = 20, scale = 4)
    private BigDecimal intDmdLocal;

    @Column(name = "INTEREST_PROVISION_LKR", precision = 20, scale = 4)
    private BigDecimal provLocal;

    @Column(name = "MONTHLY_SHORTFALL_BALANCE_LKR", precision = 20, scale = 4)
    private BigDecimal monthlySfBalLocal;

    @Column(name = "ARREARS_DAYS")
    private Integer arrearsDays;

    @Column(name = "SHORTFALL_II_BALANCE_LKR", precision = 20, scale = 4)
    private BigDecimal sfIIBalLocal;

    @Column(name = "IIS_BALANCE_LKR", precision = 20, scale = 4)
    private BigDecimal iisBalLocal;

    @Column(name = "MONTHLY_INTEREST_CASH_RECOVERY_LKR", precision = 20, scale = 4)
    private BigDecimal monthlyIntCashRecoveryLocal;

    @Column(name = "MONTHLY_INTEREST_WAIVED_LKR", precision = 20, scale = 4)
    private BigDecimal monthlyIntWaivedLocal;

    @Column(name = "BACKDATED_INTEREST_RECOVERY_LKR", precision = 20, scale = 4)
    private BigDecimal backdatedIntRecoveryLocal;

    @Column(name = "INTEREST_RECOVERY_COVERED_DAYS")
    private Integer intRecoveryCoveredDays;

    @Column(name = "ACCOUNT_CLOSE_DATE")
    private LocalDate accClsDate;

    @Column(name = "INTEREST_ACCRUED_ORI", precision = 20, scale = 4)
    private BigDecimal intAccruedOri;

    @Column(name = "INTEREST_DEMANDED_ORI", precision = 20, scale = 4)
    private BigDecimal intDmdOri;

    @Column(name = "INTEREST_PROVISION_ORI", precision = 20, scale = 4)
    private BigDecimal provOri;

    @Column(name = "YEAR_MONTH", length = 12)
    private String yearMonth;

    @Column(name = "FRX_RATE", precision = 8, scale = 4)
    private BigDecimal frxRate;

    @Column(name = "PROVISION_GL_NUMBER", length = 20)
    private String provGlAccNum;

    @Column(name = "IIS_BALANCE_ORI", precision = 20, scale = 4)
    private BigDecimal iisBalOri;

    @Column(name = "MONTHLY_INTEREST_CASH_RECOVERY_Ori", precision = 20, scale = 4)
    private BigDecimal monthlyIntCashRecoveryOri;

    @Column(name = "CURRENCT_MONTH_INTEREST_INCOME_ORI", precision = 20, scale = 4)
    private BigDecimal curMonIntIncOri;

    @Column(name = "PREVIOUS_MONTH_INTEREST_INCOME_Ori", precision = 20, scale = 4)
    private BigDecimal prevMonIntIncOri;

    @Column(name = "MONTHLY_SHORTFALL_BALANCE_ORI", precision = 20, scale = 4)
    private BigDecimal monthlySfBalOri;

    @Column(name = "BACKDATED_INTEREST_RECOVERY_ORI", precision = 20, scale = 4)
    private BigDecimal backdatedIntRecoveryOri;

    @Column(name = "MONTHLY_INTEREST_WAIVED_ORI", precision = 20, scale = 4)
    private BigDecimal monthlyIntWaivedOri;

    @Column(name = "USABLE")
    private Boolean usable;

    @Column(name = "MONTHLY_INTEREST_PROVISION_ORI", precision = 20, scale = 4)
    private BigDecimal monIntProvOri;

    @Column(name = "MONTHLY_INTEREST_PROVISION_LKR", precision = 20, scale = 4)
    private BigDecimal monIntProvLocal;

    @Column(name = "DEL_FLG", length = 1)
    private String delFlg;

    @Column(name = "OUTSTANDING_BALANCE_EAB_ORI", precision = 20, scale = 4)
    private BigDecimal outstandingBalEabOri;

    @Column(name = "OUTSTANDING_BALANCE_EAB_LKR", precision = 20, scale = 4)
    private BigDecimal outstandingBalEabLocal;

    @Column(name = "PRODUCT_GROUP", length = 100)
    private String prodGrp;

    @Column(name = "MAIN_CLASSIFICATION_SYSTEM")
    private Integer mainClassificationSystem;

    @Column(name = "SHORTFALL_II_BALANCE_ORI", precision = 20, scale = 4)
    private BigDecimal sfIIBalOri;

    @Column(name = "ALLOW_PROVIION", length = 1)
    private String allowProv;

    @Column(name = "INTEREST_CONVERSION_GL_NUMBER", length = 20)
    private String icGlAccNum;

    @Column(name = "NEW_1")
    private Boolean new1;

    @Column(name = "NEW_2")
    private Boolean new2;

    @Column(name = "NEW_3")
    private Integer new3;

    @Column(name = "MIS_CODE_APPLIED_2", length = 100)
    private String misCode2;

    @Column(name = "MIS_CODE_APPLIED_3", length = 100)
    private String misCode3;

    @Column(name = "MIS_CODE_APPLIED_4", length = 100)
    private String misCode4;

    @Column(name = "INT_REVERSAL_ORI", precision = 20, scale = 4)
    private BigDecimal intReversalOri;

    @Column(name = "INT_REVERSAL_LKR", precision = 20, scale = 4)
    private BigDecimal intReversalLocal;

    @Column(name = "DAILY_CONVERTED_CASH_RECOVERY_LKR", precision = 20, scale = 4)
    private BigDecimal dailyConvertedCashRecoveryLocal;

    @Column(name = "DAILY_CONVERTED_LOAN_REV_LKR", precision = 20, scale = 4)
    private BigDecimal dailyConvertedLoanRevLocal;

    @Column(name = "NOT_ALLOWED_MONTHLY_INTEREST_PROVISION_ORI", precision = 20, scale = 4)
    private BigDecimal notAllowedMonthlyIntProvOri;

    @Column(name = "NOT_ALLOWED_MONTHLY_INTEREST_PROVISION_LKR", precision = 20, scale = 4)
    private BigDecimal notAllowedMonthlyIntProvLocal;

    @Column(name = "MONTHLY_AVERAGE_BALANCE_LKR", precision = 20, scale = 4)
    private BigDecimal monthlyAvgBalLocal;
}
