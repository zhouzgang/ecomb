package cn.ecomb.park.hello;



import cn.ecomb.common.utils.util.FastJsonUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author brian.zhou
 * @date 7/18/22
 */
public class FastJsonDemo {

    public static void main(String[] args) {
        String str = "[{\"instPayNo\":\"70300002215901822213390336\",\"contractNo\":\"GL5XG3BCJWQX5S62\",\"channelSeqNo\":\"QNB2T2SMP9BFQ632\",\"instContext\":{\"reason\":\"T00997:8081:10/2024\",\"errorCode\":\"AUTHORISATION\",\"cardNo\":\"u_30000_15670626SGmc5264718081\",\"errorMsg\":\"T00997:8081:10/2024\",\"merchantAccountCode\":\"LALAMOVE\",\"operations\":[\"CANCEL\",\"CAPTURE\",\"REFUND\"],\"paymentMethod\":\"mc\",\"additionalData\":{\"fraudCheck-25-CVCAuthResultCheck\":\"0\",\"avsResult\":\"0 Unknown\",\"cardSummary\":\"8081\",\"shopperCountry\":\"SG\",\"refusalReasonRaw\":\"85 : No reason to decline a request for account number verification, address verification, CVV2 verification, or a credit voucher or merchandise return\",\"eci\":\"02\",\"fraudCheck-29-LiabilityShiftCheck\":\"0\",\"totalFraudScore\":\"10\",\"threeDSVersion\":\"1.0.2\",\"hmacSignature\":\"BIQaWKAWr2Gen6CyxrBQq2FGCEclMGXSIYcpUgTBub8=\",\"expiryDate\":\"10/2024\",\"xid\":\"UU5CMlQyU01QOUJGUTYzMgAAAAA=\",\"cavvAlgorithm\":\"3\",\"cardBin\":\"526471\",\"recurring.recurringDetailReference\":\"GL5XG3BCJWQX5S62\",\"fraudCheck--1-Pre-Auth-Risk-Total\":\"10\",\"recurringProcessingModel\":\"CardOnFile\",\"threeDAuthenticated\":\"true\",\"cvcResultRaw\":\"M\",\"acquirerReference\":\"219508626474\",\"liabilityShift\":\"true\",\"authCode\":\"T00997\",\"fraudResultType\":\"GREEN\",\"cardHolderName\":\"Raihanah\",\"fraudManualReview\":\"false\",\"threeDOffered\":\"true\",\"threeDOfferedResponse\":\"Y\",\"issuerCountry\":\"SG\",\"cvcResult\":\"1 Matches\",\"cavv\":\"jIrqBXO3Dk/vChEAAAicBScAAAA=\",\"threeDAuthenticatedResponse\":\"Y\",\"recurring.shopperReference\":\"u_30000_15670626\",\"fundingSource\":\"DEBIT\",\"cardFunction\":\"Consumer\",\"acquirerCode\":\"AdyenMasterCard_SG_18622\"},\"eventDate\":\"2022-07-14T08:41:55.000+0000\"},\"status\":\"SUCCESS\"}]";

        Map<String, Object> req = JSON.parseObject(str, new TypeReference<Map<String, Object>>(){});
        Map<String, Object> instContext = JSON.parseObject((String) req.get("instContext").toString(), new TypeReference<Map<String, Object>>(){});
        Map<String, Object> additionalData = JSON.parseObject(instContext.get("additionalData").toString(), Map.class);
        System.out.println(additionalData);
    }
}
