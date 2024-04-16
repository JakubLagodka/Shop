package bench.artshop.order.util;

import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import static org.zalando.problem.Status.NOT_FOUND;

public class ProblemUtils {
    public static ThrowableProblem getOrderNotFoundProblem(Long orderId) {
        return Problem.builder()
                .withTitle("Order not found!")
                .withStatus(NOT_FOUND)
                .withDetail("Order with id = " + orderId + " is no longer available")
                .build();
    }
    public static ThrowableProblem getInternalServerErrorProblem() {
        return Problem.builder()
                .withTitle("Other InternalServerError occured!")
                .withStatus(NOT_FOUND)
                .build();
    }
}
