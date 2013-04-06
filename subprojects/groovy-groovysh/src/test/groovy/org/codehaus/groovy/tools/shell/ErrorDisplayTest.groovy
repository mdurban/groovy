package org.codehaus.groovy.tools.shell

import jline.ConsoleReader

/**
 * Created with IntelliJ IDEA.
 * User: kruset
 * Date: 4/2/13
 * Time: 9:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ErrorDisplayTest extends ShellRunnerTestSupport {

    void testInput() {
        readerStubber.demand.readLine() { "foo" }
        shellMocker.use {
            readerStubber.use {
                Groovysh shellMock = new Groovysh()
                ConsoleReader readerStub = new ConsoleReader()

                InteractiveShellRunner shellRunner = new InteractiveShellRunner(shellMock, { ">" })
                shellRunner.reader = readerStub
                assertEquals("foo", shellRunner.readLine())
            }
        }
    }

    void testError() {
        shellMocker.demand.getRegistry(1) {[]}
        shellMocker.demand.getHistory(2) {[size: 0, maxsize: 1]}
        shellMocker.demand.setHistoryFull(1) {}
        shellMocker.demand.getHistoryFull(1) {false}
        readerStubber.demand.readLine() { throw new StringIndexOutOfBoundsException() }
        shellMocker.use {
            readerStubber.use {
                Groovysh shellMock = new Groovysh()
                ConsoleReader readerStub = new ConsoleReader()

                InteractiveShellRunner shellRunner = new InteractiveShellRunner(shellMock, { ">" })
                shellRunner.reader = readerStub
                // assert no exception
                shellRunner.run()
            }
        }
    }

    void testError2() {
        shellMocker.demand.getRegistry(1) {[]}
        shellMocker.demand.getHistory(2) {[size: 0, maxsize: 1]}
        shellMocker.demand.setHistoryFull(1) {}
        shellMocker.demand.getHistoryFull(1) {false}
        readerStubber.demand.readLine() { throw new Throwable("MockException") }
        shellMocker.use { readerStubber.use {
            Groovysh shellMock = new Groovysh()
            ConsoleReader readerStub = new ConsoleReader()

            InteractiveShellRunner shellRunner = new InteractiveShellRunner(shellMock, {">"})
            shellRunner.reader = readerStub
            // assert no exception
            shellRunner.run()
        }}
    }

}
