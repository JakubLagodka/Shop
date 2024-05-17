package bench.artshop.order.util;

import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import static org.zalando.problem.Status.*;

public class ProblemUtils {
    public static ThrowableProblem getOrderWithGivenIdNotFoundProblem(Long orderId) {
        return Problem.builder()
                .withTitle("Order not found!")
                .withStatus(NOT_FOUND)
                .withDetail("Order with id = " + orderId + " is no longer available")
                .build();
    }
    public static ThrowableProblem getUserWithGivenIdNotFoundProblem(Long userId) {
        return Problem.builder()
                .withTitle("User not found!")
                .withStatus(NOT_FOUND)
                .withDetail("User with id = " + userId + " is no longer available")
                .build();
    }
    public static ThrowableProblem getUserWithGivenLoginNotFoundProblem(String login) {
        return Problem.builder()
                .withTitle("User not found!")
                .withStatus(NOT_FOUND)
                .withDetail("User with login = " + login + " is no longer available")
                .build();
    }
    public static ThrowableProblem getRoleWithGivenNameNotFoundProblem(String role) {
        return Problem.builder()
                .withTitle("Role not found!")
                .withStatus(NOT_FOUND)
                .withDetail("Role with name = " + role + " is not available")
                .build();
    }
    public static ThrowableProblem getUserWithGivenLoginAlreadyExistsProblem(String login) {
        return Problem.builder()
                .withTitle("AlreadyExists!")
                .withStatus(CONFLICT)
                .withDetail("User with login = " + login + " Already Exists in database")
                .build();
    }
    public static ThrowableProblem getUserNotFoundProblem() {
        return Problem.builder()
                .withTitle("User not found!")
                .withStatus(NOT_FOUND)
                .withDetail("Current user is no longer available")
                .build();
    }
    public static ThrowableProblem getRoleWithGivenNameAlreadyExistsProblem(String name){
        return Problem.builder()
                .withTitle("AlreadyExists!")
                .withStatus(CONFLICT)
                .withDetail("Role with name = " + name + " Already Exists in database")
                .build();
    }

    public static ThrowableProblem getRoleWithGivenIdNotFoundProblem(Long id) {
        return Problem.builder()
                .withTitle("Role not found!")
                .withStatus(NOT_FOUND)
                .withDetail("Role with id = " + id + " is not available")
                .build();
    }
    public static ThrowableProblem getProductWithGivenIdNotFoundProblem(Long id) {
        return Problem.builder()
                .withTitle("Product not found!")
                .withStatus(NOT_FOUND)
                .withDetail("Product with id = " + id + " is not available")
                .build();
    }
}
