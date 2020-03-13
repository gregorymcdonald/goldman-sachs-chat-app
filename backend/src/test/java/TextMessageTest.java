import org.junit.Assert;
import org.junit.Test;

public class TextMessageTest {
    @Test
    public void AllParametersConstructor_ValidInput_Succeeds() {
        String name = "Gregory";
        String message = "Hello World!";
        TextMessage textMessage = new TextMessage(name, message);
        Assert.assertEquals(name, textMessage.getName());
        Assert.assertEquals(message, textMessage.getMessage());
    }

    @Test
    public void CopyConstructor_ValidInput_Succeeds() {
        TextMessage original = new TextMessage("Gregory", "Hello World!");
        TextMessage copy = new TextMessage(original);
        Assert.assertNotSame(original, copy);
        Assert.assertEquals(original.getName(), copy.getName());
        Assert.assertEquals(original.getMessage(), copy.getMessage());
    }
}