package com.claudio.codefellowship.models.user;


import com.claudio.codefellowship.models.post.Post;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)//http://www.java2s.com/Tutorials/Java/JPA/0150__JPA_Column_Unique_Nullable.htm
    String username;

    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    String bio;
    String profileImg = "https://www.pngfind.com/pngs/m/93-938050_png-file-transparent-white-user-icon-png-download.png";

    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    public ApplicationUser(){} // don't forget to do this
    public ApplicationUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio, String profileImg){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.profileImg = profileImg;
    }

    public long getId(){return id;}
    public void setId(long id){this.id= id;}

    @Override
    public String getUsername(){ return username; }
    public void setUsername(){this.username = username;}

    @Override
    public String getPassword(){ return password; }
    public void setPassword(String passwordhere){this.password = password;}


    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getProfileImg(){return profileImg;}
    public void setProfileImg(String profileImg){this.profileImg = profileImg;}

    public List<Post> getPosts(){return posts;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
