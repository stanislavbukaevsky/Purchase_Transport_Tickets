package com.github.stanislavbukaevsky.purchasetransporttickets.handler;

import java.util.List;

public record ResponseValidationException(List<Violation> violations) {
}
