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

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TicTacToeController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    private static final String GAME_ID = "1";

    private static final String MOVES_URL = "/games/" + GAME_ID + "/moves";
    private static final String GET_GAME_URL = "/games/" + GAME_ID;


    private static final String INVALID_POSITION_REQUEST = "{\"position\":99}";
    private static final String VALID_POSITION_REQUEST = "{\"position\":0}";

    private static final String INVALID_POSITION_MSG = "Invalid position";
    private static final String CELL_OCCUPIED_MSG = "Cell already occupied";
    private static final String GAME_FINISHED_MSG = "Game finished";
    private static final String GENERIC_ERROR_MSG = "Unexpected error";
    private static final String GAME_NOT_FOUND_MSG = "Game not found: " + GAME_ID;

    private static final int BAD_REQUEST = 400;
    private static final int CONFLICT = 409;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int NOT_FOUND = 404;

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

        when(ticTacToeService.move(GAME_ID, 99))
                .thenThrow(new IllegalArgumentException(INVALID_POSITION_MSG));

        mockMvc.perform(post(MOVES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(INVALID_POSITION_REQUEST))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(BAD_REQUEST))
                .andExpect(jsonPath("$.extraErrorData").isNotEmpty())
                .andExpect(jsonPath("$.extraErrorData[0]").value(containsString("position")));
    }

    @Test
    @DisplayName("Should return 400 when cell is already occupied")
    void shouldReturn400ForCellOccupied() throws Exception {

        when(ticTacToeService.move(GAME_ID, 0))
                .thenThrow(new IllegalArgumentException(CELL_OCCUPIED_MSG));

        mockMvc.perform(post(MOVES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_POSITION_REQUEST))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorDetail").value(CELL_OCCUPIED_MSG))
                .andExpect(jsonPath("$.extraErrorData").isEmpty());
    }

    @Test
    @DisplayName("should return 409 when game already finished")
    void shouldReturn409WhenGameFinished() throws Exception {

        when(ticTacToeService.move(GAME_ID, 4))
                .thenThrow(new IllegalStateException(GAME_FINISHED_MSG));

        mockMvc.perform(post(MOVES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"position\":4}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(CONFLICT))
                .andExpect(jsonPath("$.errorDetail").value(GAME_FINISHED_MSG))
                .andExpect(jsonPath("$.extraErrorData").isEmpty());
    }

    @Test
    @DisplayName("Should return 500 for unexpected server error")
    void shouldReturn500ForUnexpectedError() throws Exception {

        when(ticTacToeService.move(GAME_ID, 1))
                .thenThrow(new RuntimeException("Something broke"));

        mockMvc.perform(post(MOVES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"position\":1}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(INTERNAL_SERVER_ERROR))
                .andExpect(jsonPath("$.errorDetail").value(GENERIC_ERROR_MSG))
                .andExpect(jsonPath("$.extraErrorData").isEmpty());
    }

    @Test
    @DisplayName("should return 404 with game id in error message")
    void shouldReturn404WithGameIdInMessage() throws Exception {

        when(ticTacToeService.getGame(GAME_ID))
                .thenThrow(new NoSuchElementException(GAME_NOT_FOUND_MSG));

        mockMvc.perform(get(GET_GAME_URL))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(NOT_FOUND))
                .andExpect(jsonPath("$.errorDetail").value(GAME_NOT_FOUND_MSG))
                .andExpect(jsonPath("$.extraErrorData").isEmpty());
    }

}
