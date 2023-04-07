package com.isc.integration.salto

import com.google.common.collect.Lists
import com.iscweb.common.sis.ISisServiceTransport
import com.iscweb.common.sis.impl.tcp.BaseTcpConnectionPoolFactory
import com.iscweb.common.sis.impl.tcp.TcpConnectionPoolConfig
import com.iscweb.common.sis.model.SisResponseMessageDto
import org.junit.Assert
import spock.lang.Ignore
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * Test connection pool implementation Unit tests
 * Note: no real third party service connection required
 */
class ConnectionPoolTest extends Specification {

    private static Random rand = new Random(System.currentTimeMillis())

    def "testConnectionPool"() {
        given:

            TestTransport testTransport = new TestTransport(-1, false, null)
            BaseTcpConnectionPoolFactory poolFactory = new BaseTcpConnectionPoolFactory(new TcpConnectionPoolConfig()) {
                @Override
                protected ISisServiceTransport createTransport() {
                    return testTransport
                }
            }

        when:
            // Get the specified communication method from the connection pool
            ISisServiceTransport transport = poolFactory.getConnection()
            try {
                transport.send("TEST1")
                transport.send("TEST2")
            } finally {
                transport.close()
            }

            poolFactory.shutdown()

        then:
            testTransport.getMessages()
            testTransport.getMessages().size() == 2
    }

    def "testLimitTotalConnections"() {
        given:
            final long waitDelay = 2000
            final long connectionAllocationDelay = 200
            final List<ISisServiceTransport> allTransports = Collections.synchronizedList(Lists.newArrayList())
            final TcpConnectionPoolConfig config = new TcpConnectionPoolConfig()
            config.setMaxTotal(2)
            config.setMaxWaitMillis(waitDelay)

            final BaseTcpConnectionPoolFactory poolFactory = new BaseTcpConnectionPoolFactory(config) {
                @Override
                protected ISisServiceTransport createTransport() {
                    return createTestTransport(allTransports, connectionAllocationDelay, false, null)
                }
            }

            final int taskCount = 10
            final Map<Integer, Long> timing = new ConcurrentHashMap<>()
            final AtomicInteger counter = new AtomicInteger(0)
            final CountDownLatch start = new CountDownLatch(1)
            final CountDownLatch stop = new CountDownLatch(taskCount)
            Runnable task = { ->
                final int taskId = counter.incrementAndGet()
                try {
                    start.await()
                    long startTime = System.currentTimeMillis()
                    System.out.println("Executing task: " + taskId)

                    ISisServiceTransport conn = poolFactory.getConnection()
                    try {
                        conn.send("TEST_" + taskId)
                    } finally {
                        conn.close()
                    }

                    long endTime = System.currentTimeMillis()
                    timing.put(taskId, endTime - startTime)
                    stop.countDown()
                } catch (Exception e) {
                    System.err.println("Transport allocation timed out for #" + taskId)
                    e.printStackTrace()
                }
            }

        when:
            for (int i = 0; i < taskCount; i++) {
                new Thread(task).start()
            }

            System.out.println("Start all ...")
            long startTime = System.currentTimeMillis()
            start.countDown()

            System.out.println("Wait for all to complete ...")
            Assert.assertTrue("Execution timed out", stop.await(taskCount, TimeUnit.SECONDS))

            long endTime = System.currentTimeMillis()
            System.out.println("All done in " + (endTime - startTime) + " ms")
            for (Map.Entry<Integer, Long> entry : timing.entrySet()) {
                System.out.println("Task #" + entry.getKey() + " done in " + entry.getValue() + " ms.")
            }
            int count = allTransports.stream()
                    .map({ transport -> ((TestTransport) transport).getMessages().size() })
                    .reduce(0, { subtotal, element -> subtotal + element })
        then:
            Assert.assertEquals(taskCount, count)
    }

    @Ignore
    def "testTransportFailures"() {
        given:
            // TODO: emulate connection failures to enforce invalidation of the transports and re-creation

            final int totalConnectionFailures = 2
            final int totalSendFailures = 2

            final int totalConnections = 2
            final long waitDelay = 2000
            final long connectionAllocationDelay = 200
            final List<ISisServiceTransport> allTransports = Collections.synchronizedList(Lists.newArrayList())
            final TcpConnectionPoolConfig config = new TcpConnectionPoolConfig()
            config.setMaxTotal(totalConnections) // total connections
            config.setMaxWaitMillis(waitDelay) // max timeout to wait for a connection

            final AtomicInteger failureCounter = new AtomicInteger(totalSendFailures)
            final AtomicInteger transportCreateCounter = new AtomicInteger(0)
            final AtomicInteger connectionFailureCounter = new AtomicInteger(totalConnectionFailures)

            final BaseTcpConnectionPoolFactory poolFactory = new BaseTcpConnectionPoolFactory(config) {

                @Override
                protected ISisServiceTransport createTransport() {
                    int index = transportCreateCounter.incrementAndGet()
                    boolean failConnection = connectionFailureCounter.decrementAndGet() >= 0
                    // pretend we can't connect at the beginning
                    System.out.println("!!!! New connection #" + index)
                    return createTestTransport(allTransports, connectionAllocationDelay, failConnection, failureCounter)
                }

            }

            final int taskCount = 10
            final Map<Integer, Long> timing = new ConcurrentHashMap<>()
            final AtomicInteger counter = new AtomicInteger(0)
            final CountDownLatch start = new CountDownLatch(1)
            final CountDownLatch stop = new CountDownLatch(taskCount)

            Runnable task = { ->
                final int taskId = counter.incrementAndGet()
                try {
                    start.await()
                    long startTime = System.currentTimeMillis()
                    System.out.println("Executing task: " + taskId)

                    ISisServiceTransport conn = poolFactory.getConnection()
                    try {
                        conn.send("TEST_" + taskId)
                    } finally {
                        conn.close()
                    }

                    long endTime = System.currentTimeMillis()
                    timing.put(taskId, endTime - startTime)

                } catch (Exception e) {
                    System.err.println("Transport connection failure or allocation timed out for #" + taskId)
                    e.printStackTrace()
                } finally {
                    stop.countDown()
                }
            }

        when:
            for (int i = 0; i < taskCount; i++) {
                new Thread(task).start()
            }

            System.out.println("Start all ...")
            long startTime = System.currentTimeMillis()
            start.countDown()

            System.out.println("Wait for all to complete ...")
            Assert.assertTrue("Execution timed out", stop.await(taskCount, TimeUnit.SECONDS))

            long endTime = System.currentTimeMillis()
            System.out.println("All done in " + (endTime - startTime) + " ms")
            for (Map.Entry<Integer, Long> entry : timing.entrySet()) {
                System.out.println("Task #" + entry.getKey() + " done in " + entry.getValue() + " ms.")
            }

            // We emulated 4 connection related failures, so correspondent connections should fail and new connections created to replace broken once.
            Assert.assertEquals(totalConnections + 4, transportCreateCounter.get())

            int count = allTransports.stream()
                    .map({ transport -> ((TestTransport) transport).getMessages().size() })
                    .reduce(0, { subtotal, element -> subtotal + element })
        then:
            Assert.assertEquals(taskCount - (totalConnectionFailures + totalSendFailures), count)
    }

    static class TestTransport implements ISisServiceTransport {

        private final List<String> messages = Collections.synchronizedList(Lists.newArrayList())
        public static final String TEST_RESPONSE = "TEST_RESPONSE"
        private final AtomicBoolean started = new AtomicBoolean(Boolean.FALSE)

        private final long connectionDelay
        private final boolean failTheConnection
        private final AtomicInteger failureCounter

        TestTransport(long connectionDelay, boolean failTheConnection, final AtomicInteger failureCounter) {
            this.connectionDelay = connectionDelay
            this.failTheConnection = failTheConnection
            this.failureCounter = failureCounter
        }

        @Override
        SisResponseMessageDto send(String payload) throws IOException {
            if (null != failureCounter && rand.nextBoolean()) {
                if (failureCounter.decrementAndGet() >= 0)
                    throw new IOException("Emulate send failure because of I/O")
            }
            Assert.assertTrue("Connection is not started", started.get())
            messages.add(payload)
            System.out.println("Sending message: " + payload)
            return new SisResponseMessageDto(payload.getBytes(SisResponseMessageDto.ENCODING))
        }

        @Override
        void connect() throws IOException {
            try {
                // mimic connection establishing latency
                if (connectionDelay > 0) {
                    Thread.sleep(connectionDelay)
                }
                if (failTheConnection) {
                    throw new IOException("Emulate connection failure because of I/O")
                }
                started.set(Boolean.TRUE)
            } catch (Exception ignored) {
                throw new IOException("Unable to create connection")
            }
        }

        @Override
        boolean isConnected() {
            return started.get()
        }

        @Override
        void close() throws IOException {
            started.set(Boolean.FALSE)
        }

        List<String> getMessages() {
            return messages
        }
    }

    private static ISisServiceTransport createTestTransport(List<ISisServiceTransport> allTransports,
                                                            long connectionDelay, boolean failTheConnection, final AtomicInteger failureCounter) {
        final TestTransport testTransport = new TestTransport(connectionDelay, failTheConnection, failureCounter)
        allTransports.add(testTransport)
        return testTransport
    }
}
