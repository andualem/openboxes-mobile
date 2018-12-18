package org.health.supplychain.constants;

/**
 * Created by aworkneh on 2/28/2018.
 */

public class Constants {

    public static final int DML_CREATED = 1;
    public static final int DML_UPDATED = 2;
    public static final int DML_DELETED = 3;

    public static final int STATUS_INACTIVE = 2;
    public static final int STATUS_ACTIVE = 1;

    public static final int NO_REDEMPTION = 1;
    public static final int FULL_REDEMPTION = 2;
    public static final int PARTIAL_REDEMPTION = 3;

    public static final int OPERATION_FAILURE = 0;
    public static final int OPERATION_SUCCESS = 1;

    public static final int SYNC_SUCCESS = 1;
    public static final int SYNC_FAILURE = 0;

    public static final int INVALID_ID = 0;

    public static final String SYNC_SUCCESS_MESSAGE = "%s successfully synced.";
    public static final String SYNC_FAILURE_MESSAGE = "Failed to sync %s.";
    public static final String SYNC_FAILURE_SHORT_MESSAGE = "Failed";

    public static final String OPERATION = "OPERATION";

    public static final String ENTITLEMENT = "ENTITLEMENT";

    public static final String REDEMPTION = "REDEMPTION";

    public static final String PRICE_SHARING = "PRICE_SHARING";

    public static final String REDEMPTION_TYPE = "REDEMPTION_TYPE";

    public static final String ID = "id";

    public static final String  VOUCHER_SALES_ID = "voucher_sales_id";

    public static final String  LOAN_TRANSACTION_ID = "loan_transaction_id";

    public static final String  VOUCHER_ID = "voucher_id";

    public static final String ID_LIST = "id_list";

    public static final String SUMMARY_INFO = "summary_info";

    public static final int REQUEST_CODE_CAMERA = 1888;

    public static final String  DATE_FORMAT = "MMM dd yyyy";

    public static final String  DATE_TIME_FORMAT = "MMM dd yyyy H:m";

    public static final String DATESTAMP_FORMAT = "yyyMMdd";

    public static final String SOD = "SOD";

    public static final String EOD = "EOD";

    public static final String TIMESTAMP_FORMAT = "yyyMMddHms";

    public static final String PAYMENT_MODE = "payment_mode";

    public static final int PAYMENT_MODE_CASH = 1;

    public static final int PAYMENT_MODE_LOAN = 2;

    public static final String  NO_CONTENT_TO_SYNC =  "There is no content to sync.";

    public static final int VOUCHER_STATUS_NOT_ISSUED = 0;

    public static final int VOUCHER_STATUS_ISSUED = 1;

    public static final int VOUCHER_STATUS_REISSUE = 2;

    public static final int VOUCHER_STATUS_REISSUED = 3;

    public static final int VOUCHER_STATUS_REDEEMED = 4;

    public static final int VOUCHER_STATUS_VOID = 5;

    public static final int VOUCHER_STATUS_PARTIALLY_VOID = 6;

    public static final int VOUCHER_STATUS_DELETED = 7;


    public static final int RULE_TYPE_PERCENTAGE = 1;

    public static final int RULE_TYPE_FLAT_RATE = 2;

    public static final String QUERY_SUM_RESULT = "SUM_RESULT";

    public static final String QUERY_COUNT_RESULT = "COUNT_RESULT";

//    public static final String TOTAL_VOUCHERS = "TOTAL_VOUCHERS";
//
//    public static final String TOTAL_REDEMPTION = "TOTAL_REDEMPTION";

    public static final String TRANSACTION_COUNT = "TRANSACTION_COUNT";

    public static final String TOTAL_SALES_FOR_SOLID = "TOTAL_SALES_FOR_SOLID";

    public static final String TOTAL_SALES_FOR_LIQUID = "TOTAL_SALES_FOR_LIQUID";

    public static final String TOTAL_SOLD_IN_LITTER = "TOTAL_SOLD_IN_LITTER";

    public static final String TOTAL_SOLD_IN_QUINTAL = "TOTAL_SOLD_IN_QUINTAL";

    public static final String AUTHENTICATION_STATUS = "AUTHENTICATION_STATUS";

    public static final String AUTHENTICATION_SUCCESS = "SUCCESS";

    public static final String AUTHENTICATION_FAILURE = "FAILURE";

    public static final String AUTHENTICATED_ROLE_NAME = "AUTHENTICATED_ROLE_NAME";

    public static final String AUTHENTICATED_ROLE_FI = "ROLE_FI_AGENT";

    public static final String AUTHENTICATED_ROLE_PC = "ROLE_PC_OFFICER";

    public static final String AUTHENTICATED_ROLE_CASHIER = "ROLE_FI_CASHIER";

    public static final long ONE_DAY_IN_MILISECOND = 86400000; // 60*60*24*1000 = 86400000

    public static final long SYNC_TOLERANCE_DATE = 5;

    public static final int SYNC_OPERATION_UPLOAD = 1;

    public static final int SYNC_OPERATION_DOWNLOAD = 2;

    public static final int HAS_UNSYNCED_RECORD = 1;

    public static final int HAS_NO_UNSYNCED_RECORD = 0;

    public static final int NO_DATA_TO_UPLOAD = 1;

    public static final String OPEN_OPTIONS = "open_options";

    public static final String OPEN_AS_READONLY = "open_as_readonly";

    public static final String OPEN_AS_EDITABLE = "open_as_editable";

    public static final int COMMISSION_UNIT_QUINTAL = 1;

    public static final int COMMISSION_UNIT_LITTER = 2;

    public static final int COMMISSION_UNIT_TRANSACTION = 3;

    public static final int SALES_MODE_CASH = 1;

    public static final int SALES_MODE_CREDIT = 2;

    public static final int SALES_MODE_CASH_CREDIT = 3;

    public static final int INPUT_TYPE_SOLID = 1;

    public static final int INPUT_TYPE_LIQUID = 2;

    public static final int LOAN_STATUS_UNPAID = 1;

    public static final int LOAN_STATUS_PAID = 2;

    public static final long LOAN_MAX_REPAYMENT_DAY = 240; // 240 is no of days in eight Months which is credit or loan period.

    public static final int DATA_SOURCE_OFFLINE = 1; // 1 - offline; 2 - online

    public static final int DATA_SOURCE_ONLINE = 1;

    public static final long LOAN_30_DAY = 30;

    public static final long LOAN_90_DAY = 90;

    public static final int LOAN_LOGIC_SIMPLE = 1;

    public static final int LOAN_LOGIC_COMPOUND = 2;

    public static final int LOAN_FULLY_PAID = 1;

    public static final int SYNC_GROUP_CUSTOMER = 1;

    public static final int SYNC_GROUP_VOUCHER = 2;

    public static final int SYNC_GROUP_INPUT = 3;

    public static final int SYNC_GROUP_LOAN = 4;

    public static final int SYNC_GROUP_SETTING = 5;

    public static final int SYNC_GROUP_REDEMPTION = 6;

    public static final long SESSION_LOGOUT_DELAY = 60000 * 5; // 5 mins

    public static final String TIME_ZONE_EAT_SHORT = "EAT";

    public static final String TIME_ZONE_EAT_LONG = "Eastern African Time";

    public static final String TIME_ZONE_GMT = "GMT+3";

    public static final String TIME_SYNC_STATUS = "TIME_SYNC_STATUS";

    public static final String TIME_SYNC_FAILURE = "Time mismatch. Please adjust the mobile time.";

    public static final String TIME_SYNC_SERVER_TIME= "TIME_SYNC_SERVER_TIME";

    public static final long TIME_SYNC_TOLERANCE_MINUTE = 60000 * 5;

    public static final int MESSAGE_TYPE_SUCCESS = 1;

    public static final int MESSAGE_TYPE_FAILURE = 2;

    public static final int MESSAGE_TYPE_INFORMATION = 3;



    //newly added constant values
    public static final String HEADER_COOKIE = "Set-Cookie";
    public static final String HEADER_JSESSION = "JSESSIONID";
    public static final int TRANSACTION_ISSUANCE = 1;
    public static final int TRANSACTION_SHIPMENT = 2;
    public static final int TRANSACTION_RECEIVE = 3;
    public static final int TRANSACTION_REQUEST = 4;
    public static final int TRANSACTION_ISSUANCE_FOR_REQUEST = 5;
    public static final int TRANSACTION_SHIPMENT_FOR_REQUEST = 6;
    public static final int TRANSACTION_RECEIVE_FOR_REQUEST = 7;

    public static final String SHIPMENT_ID = "SHIPMENT_ID";
    public static final String REQUEST_UUID = "REQUEST_UUID";


    public static final String ASSOCIATION_TYPE_SUBSTITUTE = "SUBSTITUTE";

    public static final String ASSOCIATION_TYPE_EQUIVALENT = "EQUIVALENT";

}
