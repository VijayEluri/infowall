package infowall.domain.process;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import infowall.domain.model.ItemValuePair;
import infowall.domain.persistence.ItemValueRepository;
import infowall.testing.Mocks;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 *
 */
public class ItemValueProcessTest {
    private Mocks mocks;
    private ItemValueRepository repository;
    private ItemValueProcess process;

    @Before
    public void setUp() throws Exception {
        mocks = new Mocks();
        repository = mocks.createMock(ItemValueRepository.class);
        process = new ItemValueProcess(repository);
    }

    @Test
    public void testShowRecentValues() throws Exception {

        ItemValue curr = mocks.createMock(ItemValue.class);
        ItemValue prev = mocks.createMock(ItemValue.class);
        expect(repository.findMostRecentItemValues(eq(itemRef()),eq(2))).andReturn(newArrayList(curr,prev));

        mocks.replayAll();
        ItemValuePair pair = process.showRecentValues(itemRef());
        assertThat(pair.getCurrent(), is(curr));
        assertThat(pair.getPrevious(), is(prev));
        mocks.verifyAll();
    }

    @Test
    public void testStoreItemValue() {

        String dashboardId = "dash";
        String itemName = "name";
        String value = "{\"some\":\"valid_json\"}";

        repository.put(anyObject(ItemValue.class));

        mocks.replayAll();
        assertTrue(process.storeItemValue(dashboardId,itemName,value));
        mocks.verifyAll();
    }

    @Test
    public void testStoreItemValueIllegalJson(){
        String dashboardId = "dash";
        String itemName = "name";
        String value = "{illegal_json";


        mocks.replayAll();
        assertFalse(process.storeItemValue(dashboardId,itemName,value));
        mocks.verifyAll();
    }

    private DashboardItemRef itemRef() {
        return new DashboardItemRef("dashboardId","itemName");
    }
}