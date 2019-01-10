package com.askdog.store.bootstrap;

import com.askdog.store.model.data.inner.area.*;

import javax.annotation.Nonnull;
import java.util.List;

public interface DataReader {

    @Nonnull
    List<AreaProvince> province(String directory);

    @Nonnull
    List<AreaCity> city(String directory);

    @Nonnull
    List<AreaCounty> county(String directory);

    @Nonnull
    List<AreaTown> town(String directory);

    @Nonnull
    List<AreaVillage> village(String directory);

}