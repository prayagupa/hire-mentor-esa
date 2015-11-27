package service.scheduler

import java.util.{UUID, Timer, TimerTask}

import org.joda.time.DateTime

import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.util.control.Exception

/**
 * http://stackoverflow.com/a/4544908/432903
 * Created by prayagupd
 * on 11/27/15.
 */

class SchedulerImpl2 extends Scheduler {
  var tasksPool = mutable.Map[String, Tuple3[TimerTask, DateTime, Duration]]().empty
  val scheduler = new Timer

  def schedule(f: (String) => Unit,
               startTime: DateTime,
               duration: Duration): String = {

    val id = UUID.randomUUID().toString
    val task = new TimerTask {
      override def run(): Unit = {
        f("")
      }
    }
    tasksPool += (id ->(task, startTime, duration))
    id
  }


  def cancel(id: String): Unit = {
    val task = tasksPool.get(id)

    if (task.isEmpty) {
      throw new Exception(s"Task not found with id ${id}")
    }
    val tuple = task.get
    println(s"canceling ${id}")
    tuple._1.cancel()
    tasksPool.remove(id)
  }

  def run(): Unit = {
    tasksPool.foreach { case (id: String, (task: TimerTask, startTime: DateTime, duration)) =>
      scheduler.scheduleAtFixedRate(task, startTime.toDate, duration.toMillis)
    }
  }
}