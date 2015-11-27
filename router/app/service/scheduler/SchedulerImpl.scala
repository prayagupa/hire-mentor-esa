package service.scheduler

import java.util
import java.util.concurrent.{ScheduledFuture, TimeUnit, ScheduledExecutorService, Executors}
import java.util.{UUID, Timer, TimerTask}

import org.joda.time.DateTime

import scala.collection.mutable
import scala.concurrent.duration.Duration

/**
 * Created by prayagupd
 * on 11/27/15.
 */

case class TaskMap(var properties: Tuple3[TimerTask, DateTime, Duration], var future: ScheduledFuture[_]){

}

class SchedulerImpl extends Scheduler {

  val Core_Pool_Size : Int = 1
  var tasksPool = mutable.Map[String, TaskMap]().empty
  val scheduler : ScheduledExecutorService = Executors.newScheduledThreadPool(Core_Pool_Size)

  def schedule(f: (String) => Unit,
               startTime: DateTime,
               duration: Duration): String = {

    val id = UUID.randomUUID().toString
    val task = new TimerTask {
      override def run(): Unit = {
        f("")
      }
    }
    tasksPool += (id -> TaskMap((task, startTime, duration), null))
    id
  }


  @throws[IllegalArgumentException]
  def cancel(id: String): Unit = {
    val task = tasksPool.get(id)

    if (task.isEmpty) {
      throw new IllegalArgumentException(s"Task not found with id ${id}")
    }
    println(s"canceling task id ${id}")
    task.get.properties._1.cancel()

    if (tasksPool.get(id).get.future!=null) {
      tasksPool.get(id).get.future.cancel(true)
    }
    tasksPool.remove(id)

    println("Tasks size " + tasksPool.size)
  }

  @throws[Exception]
  def run(): Unit = {
    tasksPool.foreach {
      case (id: String, taskMap : TaskMap) =>
        tasksPool(id).future = scheduler.scheduleAtFixedRate(taskMap.properties._1, taskMap.properties._2.toDate.getSeconds,
          taskMap.properties._3.toSeconds, TimeUnit.SECONDS)
    }
  }
}
