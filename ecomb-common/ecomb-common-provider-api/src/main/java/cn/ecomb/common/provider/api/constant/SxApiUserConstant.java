package cn.ecomb.common.provider.api.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouzg
 * @date 2019/8/5
 */
public enum SxApiUserConstant {

    SUI_XING(Arrays.asList("5e8ee649e92b")),
    KOU_BEI(Arrays.asList("77a42fbb415a")),
    XIAO_MI(Arrays.asList("1845de4cb75c")),
    OPPO(Arrays.asList("8d44efc20dc2","e994b41725dc")),
    VIVO(Arrays.asList("e7cf3bccc7fa")),
    SMART_PARKING(Arrays.asList("f37fd43a9f0d")),
    TEST_ELBS(Arrays.asList("af28a8e71a97")),
    MEIZU(Arrays.asList("f8a58c9f343e","dc1dd8c0868c")),
    HUAWEI_QUICK(Arrays.asList("3fd77527375a")),
    INVESTOR(Arrays.asList("01a9c38d1abd"))
    ;


    /**
     * 对接此系统应用appId
     */
    private List<String> appIds;

    private static final Map<String, SxApiUserConstant> appIdMap = new HashMap<>();

    SxApiUserConstant(List<String> appIds) {
        this.appIds = appIds;
    }

    public List<String> appIds() {
        return this.appIds;
    }

    public String appId() {
        return this.appIds.get(0);
    }
}
