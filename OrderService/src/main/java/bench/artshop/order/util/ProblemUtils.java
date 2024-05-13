package bench.artshop.order.util;

import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import static org.zalando.problem.Status.*;

public class ProblemUtils {
    public static ThrowableProblem getOrderNotFoundProblem(Long orderId) {
        return Problem.builder()
                .withTitle("Order not found!")
                .withStatus(NOT_FOUND)
                .withDetail("Order with id = " + orderId + " is no longer available")
                .build();
    }
    public static ThrowableProblem getAccessDeniedProblem() {
        return Problem.builder()
                .withTitle("Access denied!")
                .withStatus(FORBIDDEN)
                .withDetail("Access denied! Try to login to get access!")
                .build();
    }
    public static ThrowableProblem getUserWithGivenIdNotFoundProblem(Long userId) {
        return Problem.builder()
                .withTitle("User not found!")
                .withStatus(NOT_FOUND)
                .withDetail("User with id = " + userId + " is no longer available")
                .build();
    }
    public static ThrowableProblem getUserNotFoundProblem() {
        return Problem.builder()
                .withTitle("User not found!")
                .withStatus(NOT_FOUND)
                .withDetail("Current user is no longer available")
                .build();
    }
    public static ThrowableProblem getInternalServerErrorProblem() {
        return Problem.builder()
                .withTitle("Other InternalServerError occured!")
                .withStatus(INTERNAL_SERVER_ERROR)
                .build();
    }
}
