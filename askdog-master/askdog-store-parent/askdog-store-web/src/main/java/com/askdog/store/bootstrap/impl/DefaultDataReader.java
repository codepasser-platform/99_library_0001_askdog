package com.askdog.store.bootstrap.impl;

import com.askdog.common.utils.Json;
import com.askdog.store.bootstrap.DataReader;
import com.askdog.store.model.data.Area;
import com.askdog.store.model.data.inner.area.*;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.elasticsearch.common.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class DefaultDataReader implements DataReader {

    @Nonnull
    @Override
    public List<AreaProvince> province(String directory) {
        List<AreaProvince> areas = Lists.newArrayList();
        File file = new File(directory);
        if (!file.exists() || !file.isDirectory()) {
            return areas;
        }
        File[] jsonFiles = file.listFiles();
        if (jsonFiles == null || jsonFiles.length == 0) {
            return areas;
        }
        for (File jsonFile : jsonFiles) {
            try {
                areas.addAll(readerArea(jsonFile, AreaProvince[].class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return areas;
    }

    @Nonnull
    @Override
    public List<AreaCity> city(String directory) {
        List<AreaCity> areas = Lists.newArrayList();
        File file = new File(directory);
        if (!file.exists() || !file.isDirectory()) {
            return areas;
        }
        File[] jsonFiles = file.listFiles();
        if (jsonFiles == null || jsonFiles.length == 0) {
            return areas;
        }
        for (File jsonFile : jsonFiles) {
            try {
                areas.addAll(readerArea(jsonFile, AreaCity[].class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return areas;
    }

    @Nonnull
    @Override
    public List<AreaCounty> county(String directory) {
        List<AreaCounty> areas = Lists.newArrayList();
        File file = new File(directory);
        if (!file.exists() || !file.isDirectory()) {
            return areas;
        }
        File[] jsonFiles = file.listFiles();
        if (jsonFiles == null || jsonFiles.length == 0) {
            return areas;
        }
        for (File jsonFile : jsonFiles) {
            try {
                areas.addAll(readerArea(jsonFile, AreaCounty[].class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return areas;
    }

    @Nonnull
    @Override
    public List<AreaTown> town(String directory) {
        List<AreaTown> areas = Lists.newArrayList();
        File file = new File(directory);
        if (!file.exists() || !file.isDirectory()) {
            return areas;
        }
        File[] jsonFiles = file.listFiles();
        if (jsonFiles == null || jsonFiles.length == 0) {
            return areas;
        }
        for (File jsonFile : jsonFiles) {
            try {
                areas.addAll(readerArea(jsonFile, AreaTown[].class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return areas;
    }

    @Nonnull
    @Override
    public List<AreaVillage> village(String directory) {
        List<AreaVillage> areas = Lists.newArrayList();
        File file = new File(directory);
        if (!file.exists() || !file.isDirectory()) {
            return areas;
        }
        File[] jsonFiles = file.listFiles();
        if (jsonFiles == null || jsonFiles.length == 0) {
            return areas;
        }
        for (File jsonFile : jsonFiles) {
            try {
                areas.addAll(readerArea(jsonFile, AreaVillage[].class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return areas;
    }

    @Nonnull
    private <T extends Area> List<T> readerArea(File file, Class clazz) throws FileNotFoundException {
        List<T> areas = Lists.newArrayList();
        try (BufferedReader reader = Files.newReader(file, Charsets.UTF_8)) {
            StringBuffer jsonData = new StringBuffer();
            String txt;
            while ((txt = reader.readLine()) != null) {
                jsonData.append(txt);
            }
            String json = jsonData.toString();
            if (!Strings.isNullOrEmpty(json)) {
                T[] areaArray = (T[]) Json.readValue(json, clazz);
                if (areaArray != null) {
                    areas = Arrays.asList(areaArray);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return areas;
    }

}
