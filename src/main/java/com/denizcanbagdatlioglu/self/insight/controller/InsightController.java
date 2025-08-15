package com.denizcanbagdatlioglu.self.insight.controller;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.config.jwt.JwtAuthentication;
import com.denizcanbagdatlioglu.self.insight.application.InsightService;
import com.denizcanbagdatlioglu.self.insight.controller.dto.InsightCreationRequest;
import com.denizcanbagdatlioglu.self.insight.controller.dto.InsightResponse;
import com.denizcanbagdatlioglu.self.insight.controller.mapper.InsightResponseMapper;
import com.denizcanbagdatlioglu.self.insight.domain.entity.Insight;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insight")
@RequiredArgsConstructor
public class InsightController {

    private final InsightService service;

    private final InsightResponseMapper mapper;

    @PostMapping
    public ResponseEntity<Void> saveInsight(
            @RequestBody InsightCreationRequest request,
            JwtAuthentication authentication
    ) {
        boolean created = service.saveInsight(request.title(), request.content(), authentication.getName());

        return created ? ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.internalServerError().build();
    }

    @GetMapping
    public ResponseEntity<List<InsightResponse>> listInsights(
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "page", defaultValue = "0") int page,
            JwtAuthentication authentication
    ) {
        var response = service.getInsights(pageSize, page)
                .stream()
                .filter(insight -> insight.author().id().equals(ID.of(authentication.getName())))
                .map(mapper::toResponse).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsightResponse> listInsight(
            @PathVariable(name = "id") String idStr,
            JwtAuthentication authentication
    ) {
        ID id = ID.of(idStr);
        Optional<Insight> maybeInsight = service.getInsight(id);
        return maybeInsight
                .filter(insight -> insight.author().id().equals(ID.of(authentication.getName())))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
