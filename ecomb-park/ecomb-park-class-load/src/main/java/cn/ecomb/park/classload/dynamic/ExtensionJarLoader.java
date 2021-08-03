package cn.ecomb.park.classload.dynamic;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 扩展 jar 类加载器
 * 用于隔离扩展的不同版本
 *
 * @author brian.zhou
 * @date 2021/7/12
 */
@Data
public class ExtensionJarLoader extends ClassLoader {

    /**
     * 扩展编号，不能为空
     */
    @NotNull
    private String extensionId;
    /**
     * 扩展名字
     */
    private String extensionName;
    /**
     * 扩展版本，不能为空
     */
    @NotNull
    private String version;

    /**
     * 服务扩展 jar 内具体服务
     * K，服务接口
     * V，实现类的说明类
     */
    private LinkedHashMap<Class, ExtensionClass> featureMap;

    /**
     * Java本身的类，使用 jdkClassLoader 加载
     */
    private ClassLoader jdkClassLoader;

    public ExtensionJarLoader(ClassLoader jdkClassLoader) {
//        this.jdkClassLoader = jdkClassLoader;
//        this.lib = System.getProperty("user.dir") + "/BOOT-INF/lib";
//        this.classes = System.getProperty("user.dir") + "/BOOT-INF/classes";
//        map = new HashMap<String, byte[]>(64);
//        preReadJarFile();
//        classPathMap.put("cn.ecomb.park.spi.IServiceProvider.IServiceProviderOneImpl", )
    }

    private Map classPathMap = new HashMap();


    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result = null;
        try {
            // 这里使用 JDK 的类加载器，加载 java.lang 包里面的类
            result = jdkClassLoader.loadClass(name);
        } catch (Exception e) {

        }
        if (result != null) {
            return result;
        }
//        这里要解析出配置文件里面的实现类全路径
//        String classPath = classPathMap.get(name);
        String classPath = name;
        File file = new File(classPath);
        if (!file.exists())
            throw new ClassNotFoundException();
        byte[] classByte = getClassData(file);
//        byte[] classByte = getClassFromFileOrMap(name);
        if (classByte.length == 0)
            throw new ClassNotFoundException();
        return defineClass(classByte, 0, classByte.length);
    }

    private byte[] getClassData(File file) {
        try {
            InputStream ins = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }



    /**
     * lib:表示加载的文件在jar包中
     * 类似tomcat就是{PROJECT}/WEB-INF/lib/
     */
    private String lib;

    /**
     * classes:表示加载的文件是单纯的class文件
     * 类似tomcat就是{PROJECT}/WEB-INF/classes/
     */
    private String classes;

    /**
     * 采取将所有的jar包中的class读取到内存中
     * 然后如果需要读取的时候，再从map中查找
     */
    private Map<String, byte[]> map;

    /**
     * 从指定的classes文件夹下找到文件
     * @param name
     * @return
     */
    private byte[] getClassFromFileOrMap(String name) {
        String classPath = classes + name.replace('.', File.separatorChar) + ".class";
        File file = new File(classPath);
        if(file.exists()){
            InputStream input = null;
            try {
                input = new FileInputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];
                int bytesNumRead = 0;
                while ((bytesNumRead = input.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesNumRead);
                }
                return baos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(input != null){
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }else{
            if(map.containsKey(name)) {
                //去除map中的引用，避免GC无法回收无用的class文件
                return map.remove(name);
            }
        }
        return null;
    }

    /**
     * 预读lib下面的包
     */
    private void preReadJarFile(){
        List<File> list = scanDir();
        for(File f : list){
            JarFile jar;
            try {
                jar = new JarFile(f);
                readJAR(jar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取一个jar包内的class文件，并存在当前加载器的map中
     * @param jar
     * @throws IOException
     */
    private void readJAR(JarFile jar) throws IOException{
        Enumeration<JarEntry> en = jar.entries();
        while (en.hasMoreElements()){
            JarEntry je = en.nextElement();
            String name = je.getName();
            if (name.endsWith(".class")){
                String clss = name.replace(".class", "").replaceAll("/", ".");
                if(this.findLoadedClass(clss) != null) continue;

                InputStream input = jar.getInputStream(je);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];
                int bytesNumRead = 0;
                while ((bytesNumRead = input.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesNumRead);
                }
                byte[] cc = baos.toByteArray();
                input.close();
                map.put(clss, cc);//暂时保存下来
            }
        }
    }

    /**
     * 扫描lib下面的所有jar包
     * @return
     */
    private List<File> scanDir() {
        List<File> list = new ArrayList<File>();
        File[] files = new File(lib).listFiles();
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".jar"))
                list.add(f);
        }
        return list;
    }


    /**
     * 添加一个jar包到加载器中去。
     * @param jarPath
     * @throws IOException
     */
    public void addJar(String jarPath) throws IOException {
        File file = new File(jarPath);
        if (file.exists()) {
            JarFile jar = new JarFile(file);
            readJAR(jar);
        }
    }





    }
