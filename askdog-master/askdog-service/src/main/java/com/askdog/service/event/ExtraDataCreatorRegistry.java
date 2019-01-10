package com.askdog.service.event;

import com.askdog.model.entity.inner.EventType;
import com.askdog.service.event.annotation.EventExtraData;
import com.askdog.service.event.annotation.ExtraDataCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;

@Component
public class ExtraDataCreatorRegistry {

    @Autowired
    private List<ExtraDataCreator> extraDataCreators;

    private EnumMap<EventType, ExtraDataCreator> extraDataCreatorMap;

    @PostConstruct
    private void init() {
        extraDataCreatorMap = new EnumMap<>(EventType.class);
        extraDataCreators.stream().forEach(creator -> {
            Class<? extends ExtraDataCreator> creatorClass = creator.getClass();
            EventExtraData extraData = creatorClass.getAnnotation(EventExtraData.class);
            extraDataCreatorMap.put(extraData.type(), creator);
        });
    }

    public Object prepare(@Nonnull EventType eventType, @Nonnull Object prepareParam) {
        ExtraDataCreator extraDataCreator = extraDataCreatorMap.get(eventType);
        return extraDataCreator.prepare(prepareParam);
    }

}
