package com.askdog.service.impl.cell;

import com.askdog.model.data.Report;
import com.askdog.model.data.inner.TargetType;
import com.askdog.model.repository.mongo.ReportRepository;
import com.askdog.service.bo.PureReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.askdog.model.data.builder.ReportBuilder.reportBuilder;

@Component
@Transactional
public class ReportCell {

    @Autowired
    private ReportRepository reportRepository;

    public void report(@Nullable String userId, @Nonnull TargetType targetType, @Nonnull String target, @Nonnull PureReport pureReport) {
        Report report = reportBuilder()
                .type(pureReport.getType())
                .message(pureReport.getMessage())
                .target(target)
                .targetType(targetType)
                .user(userId)
                .build();
        reportRepository.save(report);
    }

}
