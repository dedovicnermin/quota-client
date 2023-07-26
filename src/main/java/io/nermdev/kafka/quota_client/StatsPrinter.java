package io.nermdev.kafka.quota_client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StatsPrinter {
  static final Logger log = LoggerFactory.getLogger(StatsPrinter.class);
  private static final long PRINT_INTERVAL_MS = 1_000;

  private final long startTime = System.currentTimeMillis();
  private long timestampOfLastPrint = startTime;
  private long lastRecordCount = 0;
  private long totalRecordCount = 0;

  public void accumulateRecord() {
    totalRecordCount++;
  }

  public void maybePrintStats() {
    final var now = System.currentTimeMillis();
    final var lastPrintAgo = now - timestampOfLastPrint;
    if (lastPrintAgo > PRINT_INTERVAL_MS) {
      final var elapsedTime = now - startTime;
      final var periodRecords = totalRecordCount - lastRecordCount;
      final var currentRate = rate(periodRecords, lastPrintAgo);
      final var averageRate = rate(totalRecordCount, elapsedTime);
      final String format = String.format("Elapsed: %,d s; " +
                      "Rate: current %,.0f rec/s, average %,.0f rec/s",
              elapsedTime / 1000, currentRate, averageRate);
      log.info(format);

      lastRecordCount = totalRecordCount;
      timestampOfLastPrint = now;
    }
  }
  
  private double rate(long quantity, long timeMs) {
    return quantity / (double) timeMs * 1000d;
  }
}