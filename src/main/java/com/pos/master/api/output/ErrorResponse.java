package com.pos.master.api.output;

import java.util.List;

public record ErrorResponse(List<String> errorMessages) {

}
