package infowall.domain.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 *
 */
public class ItemValueTest {

    @Test
    public void testEqualData(){
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode thisNode = mapper.createObjectNode();
        thisNode.put("foo","bar");

        ObjectNode thatNode = mapper.createObjectNode();
        thatNode.put("foo","bar");

        ItemValue thisValue = itemValue(thisNode);
        ItemValue thatValue = itemValue(thatNode);

        assertTrue(thisValue.equalData(thatValue));
    }

    @Test
    public void update(){
        ItemValue val = new ItemValue();
        val.setLastUpdate(new DateTime(1234L));
        val.setUpdateCount(23);

        DateTime now = new DateTime(2345L);
        val.update(now);
        
        assertThat(val.getLastUpdate(), is(now));
        assertThat(val.getUpdateCount(),is(24L));
    }

    @Test
    public void init(){
        ItemValue val = new ItemValue();

        assertNull(val.getLastUpdate());
        assertNull(val.getCreation());
        assertThat(val.getUpdateCount(),is(0L));

        DateTime now = new DateTime(2345L);
        val.init(now);

        assertThat(val.getLastUpdate(),is(now));
        assertThat(val.getCreation(),is(now));
        assertThat(val.getUpdateCount(),is(0L));
    }

    private ItemValue itemValue(ObjectNode thisNode) {
        ItemValue thisValue = new ItemValue();
        thisValue.setData(thisNode);
        return thisValue;
    }
}
