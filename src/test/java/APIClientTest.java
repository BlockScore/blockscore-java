import com.blockscore.exceptions.NoApiKeyFoundException;
import com.blockscore.net.BlockscoreApiClient;
import org.junit.Assert;
import org.junit.Test;

/**
 * Basic API client tests.
 * Created by Tony Dieppa on 10/1/14.
 */
public class APIClientTest {
    @Test
    public void testBadAPIKey() {
        NoApiKeyFoundException error = null;
        BlockscoreApiClient apiClient = new BlockscoreApiClient();
        try {
            apiClient.listCompanies();
        } catch (NoApiKeyFoundException e) {
            error = e;
        }
        Assert.assertNotNull(error);
    }
}
