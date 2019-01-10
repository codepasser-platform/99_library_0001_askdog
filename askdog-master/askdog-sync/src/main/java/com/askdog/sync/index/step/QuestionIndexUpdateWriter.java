package com.askdog.sync.index.step;

import com.askdog.sync.index.QuestionIndex;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.askdog.sync.common.Json.toBytes;

@Component
@StepScope
public class QuestionIndexUpdateWriter implements ItemWriter<QuestionIndex> {

    private static final String INDEX_NAME = "askdog";
    private static final String INDEX_TYPE = "questions";

    @Autowired
    private Client client;

    @Override
    public void write(List<? extends QuestionIndex> indices) throws Exception {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (QuestionIndex index : indices) {
            bulkRequest.add(
                    client
                            .prepareUpdate(INDEX_NAME, INDEX_TYPE, index.getId())
                            .setDoc(toBytes(index))
            );
        }

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            System.out.println(bulkResponse.buildFailureMessage());
        }
    }

}
