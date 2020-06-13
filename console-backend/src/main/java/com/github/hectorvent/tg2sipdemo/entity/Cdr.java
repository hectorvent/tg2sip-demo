package com.github.hectorvent.tg2sipdemo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;

@Entity
@RegisterForReflection
public class Cdr extends PanacheEntityBase {

    @Id
    public Long id;

    @Column(name = "call_type")
    public String callType;

    @Column(name = "call_id")
    public String callid;

    @Column(name = "start_date")
    public LocalDateTime startDate;

    @Column(name = "end_date")
    public LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    public static Cdr findById(final int id) {
        return find("id", id).firstResult();
    }

    public static List<Cdr> findByUsername(final String username) {
        return find("user.telegramUsername", username).list();
    }

}
