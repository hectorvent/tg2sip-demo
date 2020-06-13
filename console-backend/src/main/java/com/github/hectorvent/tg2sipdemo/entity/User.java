package com.github.hectorvent.tg2sipdemo.entity;

import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;

@Entity
@Table(name = "users")
@RegisterForReflection
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, name = "telegram_id")
    public Long telegramId;

    @Column(name = "telegram_name" )
    public String telegramName;

    @Column(name = "telegram_username")
    public String telegramUsername;

    @Column(name = "telegram_phone")
    public String telegramPhone;

    @Column(name = "default_sound")
    public String defaultSound;

    @Column(name = "show_as_public", nullable = false)
    public boolean showAsPublic;

    @JsonbTransient
    @OneToMany(mappedBy = "user")
    public List<Cdr> cdrs;

    public static User findById(Long id) {
        return find("id", id).firstResult();
    }

    public static User findByTelegramId(Long telegramId) {
        return find("telegramId", telegramId).firstResult();
    }

    public static User findByTelegramUsername(String telegramUsername) {
        return find("telegramUsername", telegramUsername).firstResult();
    }
}
