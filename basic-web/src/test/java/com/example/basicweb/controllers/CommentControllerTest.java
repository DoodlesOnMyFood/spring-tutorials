package com.example.basicweb.controllers;

import com.example.basicweb.domain.Comment;
import com.example.basicweb.domain.CommentType;
import com.example.basicweb.services.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    public void saveComments_HappyPath_ShouldReturnStatus302() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                post("/comment").with(csrf()).with(user("user").roles("USER"))
                        .param("plusComment", "TEST PLUS")
        );

        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(commentService, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(commentService);
    }

    @Test
    public void getComments_HappyPath_ShouldReturnStatus200() throws Exception{
        Comment comment = new Comment();
        comment.setComment("test plus");
        comment.setType(CommentType.PLUS);
        comment.setCreatedBy("user");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment comment2 = new Comment();
        comment2.setComment("test star");
        comment2.setType(CommentType.STAR);
        comment2.setCreatedBy("user");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<Comment> comments = Arrays.asList(comment, comment2);
        when(commentService.getAllCommentsForToday()).thenReturn(comments);

        ResultActions resultActions = mockMvc.perform(get("/").with(user("user").roles("USER")));

        resultActions.andExpect(status().isOk())
                .andExpect(view().name("comment"))
                .andExpect(model().attribute("plusComments", hasSize(1)))
                .andExpect(model().attribute("plusComments", hasItem(
                        allOf(
                                hasProperty("createdBy", is("user")),
                                hasProperty("comment", is("test plus"))
                        )
                )))
                .andExpect(model().attribute("starComments", hasSize(1)))
                .andExpect(model().attribute("starComments", hasItem(
                        allOf(
                                hasProperty("createdBy", is("user")),
                                hasProperty("comment", is("test star"))
                        )
                )));
        verify(commentService, times(1)).getAllCommentsForToday();
        verifyNoMoreInteractions(commentService);
    }
}