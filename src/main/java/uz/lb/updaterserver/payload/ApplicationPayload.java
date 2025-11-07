package uz.lb.updaterserver.payload;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.web.ProjectedPayload;

import java.util.List;

@Data
@ProjectedPayload
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationPayload {
    String name;
    String descriptions;
    List<Long> versionIds;
}
