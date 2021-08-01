package com.example.basicweb.controllers;

import com.example.basicweb.domain.Comment;
import com.example.basicweb.domain.CommentType;
import com.example.basicweb.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CommentController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<Comment> allComments = commentService.getAllCommentsForToday();
        Map<CommentType, List<Comment>> groupedComments = allComments.stream().collect(Collectors.groupingBy(Comment::getType));
        model.addAttribute("starComments", groupedComments.get(CommentType.STAR));
        model.addAttribute("deltaComments", groupedComments.get(CommentType.DELTA));
        model.addAttribute("plusComments", groupedComments.get(CommentType.PLUS));

        return "comment";
    }
}
