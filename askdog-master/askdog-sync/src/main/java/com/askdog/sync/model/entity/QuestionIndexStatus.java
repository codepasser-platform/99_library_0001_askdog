package com.askdog.sync.model.entity;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "batch_index_status")
public class QuestionIndexStatus {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    private String questionId;

    private Date updateTime;

    public static class Status {

    }

}
