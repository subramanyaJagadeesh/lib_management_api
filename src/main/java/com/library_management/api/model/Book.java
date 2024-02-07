package com.library_management.api.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Book implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false, columnDefinition = "Integer default 0")
    private Long inventorySize;

    @Column(nullable = false)
    private Date publishedDate;

    @Column
    private String description;

    @Column
    private String img;

    public void setPublishedDate(String date) throws ParseException {
        this.publishedDate = new SimpleDateFormat("yyyy/MM/dd").parse(date);
    }
}
