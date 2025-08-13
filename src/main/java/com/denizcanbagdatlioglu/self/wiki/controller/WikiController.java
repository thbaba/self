package com.denizcanbagdatlioglu.self.wiki.controller;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.common.exception.NoEntityFoundException;
import com.denizcanbagdatlioglu.self.wiki.application.FigureService;
import com.denizcanbagdatlioglu.self.wiki.controller.dto.FigureBriefResponse;
import com.denizcanbagdatlioglu.self.wiki.controller.dto.FigureResponse;
import com.denizcanbagdatlioglu.self.wiki.controller.mapper.FigureMapper;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Archetype;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Symbol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wiki")
@RequiredArgsConstructor
public class WikiController {

    private final FigureService<Archetype> archetypeService;
    private final FigureService<Symbol> symbolService;
    private final FigureMapper mapper;

    @GetMapping("/archetype")
    public ResponseEntity<List<FigureBriefResponse>> getArchetypes(
            @RequestParam(name="pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name="page", defaultValue = "0") int page
    ) {
        pageSize = Math.max(Math.min(pageSize, 20), 1);
        page = Math.max(0, page);

        var response = archetypeService.getFigures(pageSize, page)
                .stream()
                .map(mapper::toBriefResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/archetype/{id}")
    public ResponseEntity<FigureResponse> getArchetype(@PathVariable(name = "id") ID id) {
        var maybeArchetype = archetypeService.getFigure(id);
        return maybeArchetype
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoEntityFoundException("There is no such archetype."));
    }

    @GetMapping("/symbol")
    public ResponseEntity<List<FigureBriefResponse>> getSymbols(
            @RequestParam(name="pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name="page", defaultValue = "0") int page
    ) {
        pageSize = Math.max(Math.min(pageSize, 20), 1);
        page = Math.max(0, page);

        var symbols = symbolService.getFigures(pageSize, page)
                .stream()
                .map(mapper::toBriefResponse)
                .toList();
        return ResponseEntity.ok(symbols);
    }

    @GetMapping("/symbol/{id}")
    public ResponseEntity<FigureResponse> getSymbol(@PathVariable(name = "id") ID id) {
        var maybeSymbol = symbolService.getFigure(id);
        return maybeSymbol
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoEntityFoundException("There is no such symbol."));
    }

}
