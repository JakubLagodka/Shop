package bench.artshop.order.util;

import bench.artshop.order.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {
    public static List<UserDto> userDtos = new ArrayList<>();

    public static void prepareData() {
        userDtos.add(new UserDto(543L, "Jolanta", "Ciekawska", "jola", "password","jola@poczta.com"));

        userDtos.add(new UserDto(544L, "Jan", "Ciekawski", "jan", "password","jan@poczta.com"));   }

}
