package com.example.basicweb.services;

import com.example.basicweb.domain.Comment;
import com.example.basicweb.domain.CommentType;
import com.example.basicweb.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CommentServiceTest {

    @MockBean
    private CommentRepository commentRepository;

    private CommentService commentService;

    @Before
    public void setUp() throws Exception {
        commentService = new CommentService(commentRepository);
    }

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment(){
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        List<Comment> comments = Collections.singletonList(comment);

        LocalDate now = LocalDate.now();

        when(commentRepository.findByCreatedYearAndMonthAndDay(
                now.getYear(),now.getMonth().getValue(), now.getDayOfMonth())
        ).thenReturn(comments);

        List<Comment> actualComments = commentService.getAllCommentsForToday();

        verify(commentRepository, times(1)).findByCreatedYearAndMonthAndDay(
                now.getYear(),now.getMonth().getValue(), now.getDayOfMonth()
        );
        assertEquals(comments, actualComments);
    }

    @Test
    public void saveAll_HappyPath_ShouldSave2Comments() {
        Comment comment = new Comment();
        comment.setComment("Test_1");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        comment.setCreatedBy("user1");

        Comment comment2 = new Comment();
        comment2.setComment("Test_2");
        comment2.setType(CommentType.DELTA);
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        comment2.setCreatedBy("user2");

        List<Comment> comments = Arrays.asList(comment, comment2);

        when(commentRepository.saveAll(comments)).thenReturn(comments);

        List<Comment> savedComments = commentService.saveAll(comments);

        verify(commentRepository, times(1)).saveAll(comments);
        assertEquals(savedComments, comments);

    }
}