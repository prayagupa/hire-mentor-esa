package service.scheduler

import org.joda.time.DateTime

import scala.concurrent.duration.Duration

/**
 * Created by prayagupd
 * on 11/27/15.
 */

trait Scheduler {
  /**
   * adds the given task {@function} to the tasks pool
   * which will be executed at {@startTime} with delay of {@frequency}
   * @param function
   * @param startTime
   * @param frequency
   * @return nothing
   */
  def schedule(function: (String) => Unit,
               startTime : DateTime,
               frequency : Duration): String

  /**
   * cancels the task given an {@id} of the task
   * @param id
   * throws {#Exception} if id not found
   */
  @throws[IllegalArgumentException]
  def cancel(id : String ): Unit

  /**
   * run the tasks scheduled from the tasks pool
   * @throws Exception if the task is already running
   */
  @throws[Exception]
  def run(): Unit
}
