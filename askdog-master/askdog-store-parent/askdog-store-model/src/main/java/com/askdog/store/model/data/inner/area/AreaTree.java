package com.askdog.store.model.data.inner.area;

import com.askdog.store.model.data.Area;
import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface AreaTree<P extends Area, C extends Area> {

    @Nullable
    String getParentId();

    @Nullable
    P getParent();

    void setParent(@Nonnull P parent);

    @Nullable
    List<C> getChildren();

    void setChildren(@Nonnull List<C> children);

    int getLevel();

    boolean isLeaf();

    enum AreaType {
        HEAD, PROVINCE, CITY, COUNTY, TOWN, VILLAGE
    }

    enum AreaLevel {
        HEAD(AreaType.HEAD, 0),
        PROVINCE(AreaType.PROVINCE, 1),
        CITY(AreaType.CITY, 2),
        COUNTY(AreaType.COUNTY, 3),
        TOWN(AreaType.TOWN, 4),
        VILLAGE(AreaType.VILLAGE, 5);

        private final AreaType type;
        private final int level;

        AreaLevel(AreaType type, int level) {
            this.type = type;
            this.level = level;
        }

        public AreaType type() {
            return type;
        }

        public int level() {
            return level;
        }

        public static AreaLevel valueOf(int value) {
            for (AreaLevel areaLevel : AreaLevel.values()) {
                if (Objects.equal(areaLevel.level(), value)) {
                    return areaLevel;
                }
            }
            return HEAD;
        }
    }

}
