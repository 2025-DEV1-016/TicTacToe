package com.bnpp.kata.tictactoe.exception;

import com.bnpp.kata.tictactoe.controller.TicTacToeController;
import com.bnpp.kata.tictactoe.mapper.GameMapper;
import com.bnpp.kata.tictactoe.service.TicTacToeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TicTacToeController.class)
@Import(GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicTacToeService ticTacToeService;

    @MockBean
    private GameMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should return 400 for invalid move position")
    void shouldReturn400ForInvalidMove() throws Exception {

        when(ticTacToeService.move("1", 99))
                .thenThrow(new IllegalArgumentException("Invalid position"));

        mockMvc.perform(post("/games/1/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"position\":99}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.extraErrorData").isNotEmpty())
                .andExpect(jsonPath("$.extraErrorData[0]").value(containsString("position")));
    }

    @Test
    @DisplayName("Should return 400 when cell is already occupied")
    void shouldReturn400ForCellOccupied() throws Exception {

        when(ticTacToeService.move("1", 0))
                .thenThrow(new IllegalArgumentException("Cell already occupied"));

        mockMvc.perform(post("/games/1/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"position\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorDetail").value("Cell already occupied"))
                .andExpect(jsonPath("$.extraErrorData").isEmpty());
    }

    @Test
    @DisplayName("should return 409 when game already finished")
    void shouldReturn409WhenGameFinished() throws Exception {

        when(ticTacToeService.move("1", 4))
                .thenThrow(new IllegalStateException("Game finished"));

        mockMvc.perform(post("/games/1/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"position\":4}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.errorDetail").value("Game finished"))
                .andExpect(jsonPath("$.extraErrorData").isEmpty());
    }

    @Test
    @DisplayName("Should return 500 for unexpected server error")
    void shouldReturn500ForUnexpectedError() throws Exception {

        when(ticTacToeService.move("1" , 1))
                .thenThrow(new RuntimeException("Something broke"));

        mockMvc.perform(post("/games/1/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"position\":1}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.errorDetail").value("Unexpected error"))
                .andExpect(jsonPath("$.extraErrorData").isEmpty());
    }

}
