package com.claudio.codefellowship.models.post;

import com.claudio.codefellowship.models.user.ApplicationUser;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    ApplicationUser applicationUser;

    String body;
    Date time;

    public Post(){}

    public Post(ApplicationUser applicationUser, String body){
        this.applicationUser = applicationUser;
        this.body = body;
        this.time = new Date(Calendar.getInstance().getTime().getTime());
    }

    public ApplicationUser getApplicationUser(){ return applicationUser; }
    public void setApplicationUser(ApplicationUser applicationUser){ this.applicationUser = applicationUser; }

    public String getBody(){return body;}
    public void setBody(String body){this.body = body;}

    public Date getTime(){return time;}
    public void setTime(){this.time = time;}
}
