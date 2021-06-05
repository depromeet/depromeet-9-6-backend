package batch;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AlaramBatchDTO {
    private String to;
    private AlaramBatchDataDTO data;
}