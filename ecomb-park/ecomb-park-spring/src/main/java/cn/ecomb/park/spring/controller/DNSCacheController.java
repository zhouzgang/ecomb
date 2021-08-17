package cn.ecomb.park.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author brian.zhou
 * @date 2021/8/9
 */
@RestController
@RequestMapping("/dns")
public class DNSCacheController {

    @GetMapping("/print")
    public void print(@RequestParam("domain") String domain) throws Exception {
        System.out.println("start loop, domain:" + domain + "\n\n");
        System.out.println("sun.net.inetaddr.ttl: " + System.getProperty("sun.net.inetaddr.ttl"));
        System.out.println("sun.net.inetaddr.negative.ttl: " + System.getProperty("sun.net.inetaddr.negative.ttl"));
        for(int i = 0; i < 10; ++i) {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("current time:" + sdf.format(d));
            InetAddress addr1 = InetAddress.getByName(domain);
            String addressCache = "addressCache";
            System.out.println(addressCache);
            printDNSCache(addressCache);
            System.out.println("getHostAddress:" + addr1.getHostAddress());
            System.out.println("*******************************************");
            System.out.println("\n");
            Thread.sleep(1000);
        }

        System.out.println("end loop");
    }

    private static void printDNSCache(String cacheName) throws Exception {
        Class<InetAddress> klass = InetAddress.class;
        Field acf = klass.getDeclaredField(cacheName);
        acf.setAccessible(true);
        Object addressCache = acf.get(null);
        Class cacheKlass = addressCache.getClass();
        Field cf = cacheKlass.getDeclaredField("cache");
        cf.setAccessible(true);
        Map<String, Object> cache = (Map<String, Object>) cf.get(addressCache);
        for (Map.Entry<String, Object> hi : cache.entrySet()) {
            Object cacheEntry = hi.getValue();
            Class cacheEntryKlass = cacheEntry.getClass();
            Field expf = cacheEntryKlass.getDeclaredField("expiration");
            expf.setAccessible(true);
            long expires = (Long) expf.get(cacheEntry);

            Field af = cacheEntryKlass.getDeclaredField("addresses");
            af.setAccessible(true);
            InetAddress[] addresses = (InetAddress[]) af.get(cacheEntry);
            List<String> ads = new ArrayList<String>(addresses.length);
            for (InetAddress address : addresses) {
                ads.add(address.getHostAddress());
            }

            System.out.println(hi.getKey() + " "+new Date(expires) +" " +ads);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("start loop\n\n");
        for(int i = 0; i < 30; ++i) {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("current time:" + sdf.format(d));
            InetAddress addr1 = InetAddress.getByName("www.google.com");
            String addressCache = "addressCache";
            System.out.println(addressCache);
            printDNSCache(addressCache);
            System.out.println("getHostAddress:" + addr1.getHostAddress());
            System.out.println("*******************************************");
            System.out.println("\n");
            Thread.sleep(1000);
        }

        System.out.println("end loop");
    }
}
