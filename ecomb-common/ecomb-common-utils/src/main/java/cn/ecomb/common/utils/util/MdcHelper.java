package cn.ecomb.common.utils.util;

import cn.ecomb.common.utils.idgenerator.IdWorker;
import org.slf4j.MDC;

import java.util.Map;

/**
 * 随行日志mdc设置
 * @author jaffee
 */
public class MdcHelper {

    private static final String UNIQUE_ID = "traceId";
    private static final String APP_ID = "appId";
    private static final String IMEI = "imei";


    public static void insertMDC(String appId,String oid) {
        if (appId != null && oid != null) {
            MDC.put(APP_ID, appId);
            MDC.put(IMEI, oid);
        }

    }


    public static Map<String, String> getMDC() {
        Map<String, String> map = MDC.getCopyOfContextMap();
        return map;
    }

    public static void insertUnique() {
        MDC.put(UNIQUE_ID, IdWorker.nextId() + "");
    }
}
