package com.inbyte.component.common.dict;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.inbyte.component.common.dict.config.DictScanner;
import com.inbyte.component.common.dict.dao.DictMapper;
import com.inbyte.component.common.dict.model.DictItemBrief;
import com.inbyte.commons.model.dto.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 字典服务
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@Slf4j
@Service
public class DictService implements InitializingBean {

    private static final String DICT_SUFFIX = "dict";

    @Override
    public void afterPropertiesSet() {
        init("com.inbyte.dict.dict", "com.inbyte.common.model.dict");
    }

    @Autowired
    private DictMapper dictMapper;// 创建缓存实例

    /**
     * 静态字典
     */
    private static final Map<String, Map<String, String>> SYSTEM_DICT_CACHE = new ConcurrentHashMap<>();

    /**
     * 业务动态字典
     * 字典缓存
     * 10分钟过期
     */
    private Cache<String, Map<String, String>> BUSINESS_DICT_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100) // 设置缓存的最大大小
            .expireAfterWrite(10, TimeUnit.MINUTES) // 设置缓存项写入后过期时间
            .build();

    /**
     * 业务动态字典
     * 字典缓存
     * 10分钟过期
     */
    private Cache<String, Map<String, String>> TREE_DICT_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100) // 设置缓存的最大大小
            .expireAfterWrite(10, TimeUnit.MINUTES) // 设置缓存项写入后过期时间
            .build();

    private static final String CODE = "code";
    private static final String NAME = "name";

    /**
     * 字典加载入口
     *
     * @param dictPath 字典目录 例：com.maidao.dict
     */
    public void init(String... dictPath) {
        for (String path : dictPath) {
            Set<Class<?>> classes = DictScanner.scanDictBean(path);
            for (Class clz : classes) {
                boolean anEnum = clz.isEnum();
                if (anEnum) {
                    Enum[] enums = (Enum[]) clz.getEnumConstants();
                    load(enums);
                }
            }
        }
    }

    public void load(Enum[] enums) {
        try {
            String dictName = "";
            Map<String, String> dict = new LinkedHashMap<>();
            for (int i = 0; i < enums.length; i++) {
                if (i == 0) {
                    dictName = enums[i].getClass().getSimpleName();
                }
                Field code = enums[i].getClass().getField(CODE);
                Field name = enums[i].getClass().getField(NAME);
                dict.put(String.valueOf(code.get(enums[i])),
                        String.valueOf(name.get(enums[i])));
            }
            SYSTEM_DICT_CACHE.put(dictName.toLowerCase().replace(DICT_SUFFIX, ""), dict);
        } catch (Exception e) {
            log.warn("字典{}命名不规范, 请增加字段code和name, 否则无法加入字典池", enums);
        }
    }

    /**
     * 获取字典信息
     *
     * @param dictName
     * @return
     */
    public Map<String, String> getDict(String dictName) {
        dictName = dictName.toLowerCase();
        Map<String, String> dict = SYSTEM_DICT_CACHE.get(dictName);
        if (dict != null) {
            return dict;
        }

        dict = BUSINESS_DICT_CACHE.getIfPresent(dictName);
        if (dict != null) {
            return dict;
        }
        try {
            List<DictItemBrief> list = dictMapper.list(dictName);
            dict = list.stream()
                    .sorted(Comparator.comparingInt(DictItemBrief::getOrdinal)) // 根据ordinal升序排序
                    .collect(Collectors.toMap(
                            k -> k.getCode().toString(),   // 获取键的方法
                            v -> v.getName(),   // 获取值的方法
                            (existing, replacement) -> existing, // 如果键冲突时保留原有的值
                            LinkedHashMap::new // 指定Map的实现为LinkedHashMap
                    ));

            BUSINESS_DICT_CACHE.put(dictName, dict);
        } catch (Exception e) {
            log.warn("字典表未同步, 无法获取数据库字典信息");
        }
        return dict;
    }

    /**
     * 获取字典
     *
     * @param dictClass
     * @return
     */
    public Map<String, String> getDict(Class dictClass) {
        return getDict(dictClass.getSimpleName().toLowerCase().replace("dict", ""));
    }

    /**
     * 获取字典名称
     *
     * @param dictClass
     * @param code
     * @return
     */
    public String getName(Class dictClass, Integer code) {
        return getDict(dictClass).get(code);
    }

    /**
     * 获取字典名称
     *
     * @param dictName
     * @param code
     * @return
     */
    public String getName(String dictName, Integer code) {
        return getDict(dictName).get(code);
    }

    /**
     * 获取字典名称
     *
     * @param dictClass
     * @param codes
     * @return
     */
    public String getNames(Class dictClass, List<Integer> codes) {
        StringBuilder sb = new StringBuilder();
        Map<String, String> dict = getDict(dictClass);
        for (int i = 0; i < codes.size(); i++) {
            sb.append(dict.get(codes.get(i))).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 获取字典名称
     *
     * @param dictName
     * @param codes
     * @return
     */
    public String getNames(String dictName, List<Integer> codes) {
        StringBuilder sb = new StringBuilder();
        Map<String, String> dict = getDict(dictName);
        for (int i = 0; i < codes.size(); i++) {
            sb.append(dict.get(codes.get(i))).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public List<DictItemBrief> getDictTree(String dictName) {
        List<DictItemBrief> list = dictMapper.list(dictName);
        Map<Integer, List<DictItemBrief>> map = list.stream().collect(Collectors.groupingBy(DictItemBrief::getParentId));
        list.forEach(node -> node.setChildren(map.getOrDefault(node.getItemId(), new ArrayList<>())));
        return map.get(0);
    }

    /**
     * 获取字典列表
     *
     * @param clz
     * @return
     */
    public List<Dict> getListDict(Class clz) {
        List<Dict> list = new ArrayList<>();
        Enum[] enums = (Enum[]) clz.getEnumConstants();
        try {
            for (int i = 0; i < enums.length; i++) {
                Field code = enums[i].getClass().getField(CODE);
                Field name = enums[i].getClass().getField(NAME);
                list.add(new Dict((Integer) code.get(enums[i]),
                        String.valueOf(name.get(enums[i]))));
            }
        } catch (Exception e) {
            log.error("字典{}命名不规范, 请增加字段code和name, 否则无法加入字典池", clz.getName());
        }
        return list;
    }
}
