package com.haenggu.controller;

import com.haenggu.http.response.BoardDetailResponse;
import com.haenggu.http.response.BoardListResponse;
import com.haenggu.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "board", description = "동행글 정보 관련 API")
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) { this.boardService = boardService;}

    @Operation(summary = "전체 동행글 목록 조회", description = "전체 동행글 목록을 조회합니다.", tags = "board",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 행사 목록 조회 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardListResponse.class))),
            })
    @GetMapping
    public ResponseEntity<BoardListResponse> getBoards(
            @PageableDefault @SortDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(new BoardListResponse(pageable, boardService.findBoards(pageable)));
    }

    @Operation(summary = "동행글 상세 조회", description = "동행글의 ID 값을 이용하여 동행글의 상세 내용을 조회합니다.", tags = "board",
            responses = {
                    @ApiResponse(responseCode = "200", description = "동행글 조회 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardDetailResponse.class))),
            })
    @GetMapping("/{idx}")
    public ResponseEntity<BoardDetailResponse> getBoardDetails(
            @Parameter(name = "idx", in = ParameterIn.PATH, description = "조회할 동행글의 아이디") @PathVariable("idx") UUID idx
    ) {
        return ResponseEntity.ok(boardService.findBoard(idx));
    }
}
