package com.krokogator.spring.resources.report.comment;

import com.krokogator.spring.resources.comment.Comment;
import com.krokogator.spring.resources.report.Report;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "COMMENT")
public class CommentReport extends Report {

    @ManyToOne
    private Comment comment;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    //public IdReferenceDTO getComment() { return new IdReferenceDTO(comment.getId()); }
}
