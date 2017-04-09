package guitests;

import org.junit.Before;
import org.junit.Test;

//@@author A0162266E
public class GoogleCommandTest extends TaskListGuiTest {

    @Before
    public void setUp() {
        commandBox.runCommand("glogout");
    }

    @Test
    public void gimport_notLoggedIn_failure() {
        commandBox.runCommand("gimport");
        assertResultMessage("You are not logged in");
    }

    @Test
    public void gexport_notLoggedIn_failure() {
        commandBox.runCommand("gexport");
        assertResultMessage("You are not logged in");
    }

    @Test
    public void glogout_notLoggedIn_failure() {
        commandBox.runCommand("glogout");
        assertResultMessage("You are not logged in");
    }

}
