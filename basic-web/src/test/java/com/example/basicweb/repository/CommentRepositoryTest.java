package com.example.basicweb.repository;

import com.example.basicweb.domain.Comment;
import com.example.basicweb.domain.CommentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CommentRepository commentRepository;


    @Test
    public void findByCreatedYearAndMonthAndDay_HappyPath_ShouldReturn1Comment() {
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        testEntityManager.persist(comment);
        testEntityManager.flush();


        LocalDate now = LocalDate.now();
        List<Comment> comments = commentRepository.findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());

        assertEquals(comments.size(), 1);
        assertEquals(comments.get(0).getComment(), "Test");
    }

    @Test
    public void save_HappyPath_ShouldSave1Comment() {
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment saved = commentRepository.save(comment);
        System.out.println(saved.getId());

        assertEquals(testEntityManager.find(Comment.class, saved.getId()), saved);
    }
}
