import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.application.PathManager;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

public class TestInvokeLaterInspection extends UsefulTestCase {

    protected CodeInsightTestFixture myFixture;

    final String testDataPath = "./src/test/resources";

    @Before
    public void setUp() throws Exception {


        final IdeaTestFixtureFactory fixtureFactory = IdeaTestFixtureFactory.getFixtureFactory();
        final TestFixtureBuilder<IdeaProjectTestFixture> testFixtureBuilder =
                fixtureFactory.createFixtureBuilder(getName());
        myFixture = JavaTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(testFixtureBuilder.getFixture());
        assert testDataPath != null;
        myFixture.setTestDataPath(testDataPath);
        final JavaModuleFixtureBuilder builder = testFixtureBuilder.addModule(JavaModuleFixtureBuilder.class);

        builder.addContentRoot(myFixture.getTempDirPath()).addSourceRoot("");
        builder.setMockJdkLevel(JavaModuleFixtureBuilder.MockJdkLevel.jdk15);
        myFixture.setUp();
    }


    @After
    public void tearDown() throws Exception {
        myFixture.tearDown();
        myFixture = null;

        

    }

    protected void doTest(String testName, String hint) throws Throwable {
        myFixture.configureByFile(testName + ".before.java");
        myFixture.enableInspections(InvokeLaterInspection.class);
        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();


        boolean inspectionExists = false;
        for (HighlightInfo info : highlightInfos) {
            if (info.getDescription() != null &&  info.getDescription().equals(InvokeLaterInspection.problemDescription)) {
                inspectionExists = true;
            }
        }

        Assert.assertTrue(inspectionExists);
    }

    /**
     * Test the "==" case
     * Note the hint must match CriQuickFix#getName
     *
     * @throws Throwable
     */
    @Test
    public void testDefaultInspectionIsDetected() throws Throwable {
//    doTest("before", "Use equals()");
        doTest("test1", InvokeLaterQuickFix.QUICK_FIX_NAME);
    }

    @Test
    public void testInspectionIsDetectedWithStaticImport() throws Throwable {
        doTest("test2", InvokeLaterQuickFix.QUICK_FIX_NAME);
    }

    /**
     * Test the "!=" case
     * Note the hint must match CriQuickFix#getName
     * @throws Throwable
     */
//    @Test
//    public void test2() throws Throwable {
//        doTest("before1", InvokeLaterQuickFix.QUICK_FIX_NAME);
//    }
}
