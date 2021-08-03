package cn.ecomb.park.apollo;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceGrayDelReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenReleaseDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

/**
 * @author brian.zhou
 * @date 2021/8/3
 */
@RestController
@RequestMapping(value = "apollo")
@Profile({"dev","test"})
public class ApolloController {

    //apollo中项目id
    private final static String appId = "framework-gateway";
    //apollo操作用户
    private final static String opUser = "lizz";
    //apollo中集群名称，apollo默认集群为default
    private final static String cluster = "aliyun";
    //apollo中集群内namespace名称
    private final static String namespace = "namespace-zone1";

    //apollo操作客户端
    private ApolloOpenApiClient apolloClient;

    public ApolloController(ApolloOpenApiClient client) {
        this.apolloClient = client;
    }


    /**
     * 获取环境列表，如
     * [{"clusters":["huawei","default"],"env":"PRO"},{"clusters":["default"],"env":"DEV"}]
     * @param server apollo中服务id
     * @return
     */
    @GetMapping("/envclusters/{server}")
    public Object getEnvclusters(@PathVariable String server) {
        return JSON.toJSONString(apolloClient.getEnvClusterInfo(server));
    }

    /**
     * 向apollo中新增配置项，为未发布状态。
     * post uri:apollo/dev/add
     * @param env 指定apollo的数据环境
     * @return
     */
    @PostMapping("/{env}/add}")
    public Object addParam(@PathVariable String env) {
        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey("timeout");
        openItemDTO.setValue("100");
        openItemDTO.setComment("超时时间");
        openItemDTO.setDataChangeCreatedBy(opUser);
        OpenItemDTO item = apolloClient.createItem(appId, env, cluster, namespace, openItemDTO);
        return JSON.toJSONString(item);
    }

    /**
     * 修改apollo中配置项，为未发布状态。
     * post uri:apollo/dev/update
     * @param env 指定apollo的数据环境
     * @return
     */
    @PostMapping("/{env}/update")
    public Object updateParam(@PathVariable String env) {
        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey("timeout");
        openItemDTO.setValue("200");
        openItemDTO.setComment("超时时间");
        openItemDTO.setDataChangeCreatedBy(opUser);
        apolloClient.createOrUpdateItem(appId, env, cluster, namespace, openItemDTO);
        return JSON.toJSONString(openItemDTO);
    }

    /**
     * 获取apollo中namespace的所有配置项
     * @param env 指定apollo的数据环境
     * @return
     */
    @GetMapping("/{env}/namespace")
    public Object getAllNameSpace(@PathVariable String env) {
        return JSON.toJSONString(apolloClient.getNamespace(appId, env, cluster, "application"));
    }


    /**
     * 获取某一项配置
     * @param env 指定apollo的数据环境
     * @param key 配置项key
     * @return
     */
    @GetMapping("/{env}/getParam")
    public Object getParam(@PathVariable String env, String key) {
        OpenItemDTO getItem = apolloClient.getItem(appId, env, cluster, namespace, key);
        return JSON.toJSONString(getItem);
    }

    /**
     * 刷新发布配置
     *
     * @param env 指定apollo的数据环境
     * @return
     */
    public Object releaseParam(@PathVariable String env) {
        NamespaceGrayDelReleaseDTO namespaceGrayDelReleaseDTO = new NamespaceGrayDelReleaseDTO();
        //配置版本名称
        namespaceGrayDelReleaseDTO.setReleaseTitle(System.currentTimeMillis() + "-release");
        //刷新说明
        namespaceGrayDelReleaseDTO.setReleaseComment("auto release");
        namespaceGrayDelReleaseDTO.setReleasedBy(opUser);
        OpenReleaseDTO openReleaseDTO = apolloClient.publishNamespace(appId, env, cluster, namespace, namespaceGrayDelReleaseDTO);
        return JSON.toJSONString(openReleaseDTO);
    }
}
