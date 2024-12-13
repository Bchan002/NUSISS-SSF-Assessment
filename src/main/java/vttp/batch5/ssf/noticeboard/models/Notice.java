package vttp.batch5.ssf.noticeboard.models;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
    
    @NotNull(message="title cannot be nulll")
    @Size(min=3, max=128, message="The notice title's length must be between 3 and 128 characters")
    private String title;

    @Email(message="Must be in email format")
    @NotBlank(message="Email must not be blank")
    private String poster;

    @FutureOrPresent(message="Must be a date in the future")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="Date must be filled up")
    private Date postDate;

    @NotBlank(message="Must select at least 1 category")
    private String categories;

    @NotBlank(message="Contents of the notice must not be blank")
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    

}
