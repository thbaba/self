package com.denizcanbagdatlioglu.self.analysis.repository.dto;

import java.util.List;

public record AIRequest(
        String model,
        List<AIMessage> messages,
        boolean stream
) {

}
