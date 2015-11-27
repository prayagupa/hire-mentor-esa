package service.scheduler

import java.util.concurrent.TimeUnit

import org.joda.time.DateTime

import scala.concurrent.duration.Duration

/**
 * At a minimum, your Scheduler should expose an API that allows it to be used to :
● Support the ability to schedule multiple tasks
● Support the ability to cancel scheduled tasks by ID
● Run the scheduled tasks at ‘startTime’ with a frequency of ‘x’
 * Created by prayagupd
 * on 11/27/15.
 */

object SchedulerApp {

  def main(args: Array[String]) {
    try {
      val scheduler: Scheduler = new SchedulerImpl()

      val helloId = scheduler.schedule((id: String) => {
        println("Hello world")
      }, DateTime.now(), Duration(1, TimeUnit.SECONDS))
      scheduler.run()
      scheduler.cancel(helloId)
    } catch {
      case e: Exception => {
        println(e.getMessage)
        e.printStackTrace()
      }
    }
  }
}
