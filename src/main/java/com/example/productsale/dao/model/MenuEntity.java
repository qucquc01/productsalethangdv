package com.example.productsale.dao.model;

import com.example.productsale.enums.ActiveEnum;
import com.example.productsale.enums.LevelEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu")
@Where(clause = "deleted=0")
@Setter
@Getter
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "menu_generator")
    @SequenceGenerator(
            name = "menu_generator",
            sequenceName = "menu_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private Integer location;

    @Column(name = "link")
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private MenuEntity parent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<MenuEntity> children;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "level")
    private LevelEnum level;

    @Column(name = "sub_menu")
    private String subMenu;

    @Column(name = "active")
    private Boolean active;
}
