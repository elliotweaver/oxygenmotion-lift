package code
package snippet

import scala.xml.{NodeSeq, Text}

import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._

import util.Random.nextInt
import scala.math._

class TravelerPath {
  
  /* Config */
  val n = 16
  val x = n-1
  val w = 500
  val s = n
  
  /* Init */
  var answer = (List(0), 0)
  var success = true
  val nodes = 0 to x
  var set = List.fill(nodes.size)(nextInt).zipWithIndex.sortBy(identity).map(_._2).map(nodes(_))
  var roads = Array.ofDim[Int](n, n)
  var sum = 0
  var path = List(0)
  
  /* Build roads */
  for (i <- 0 to x;
      j <- 0 to x) {
      roads(i)(j) = nextInt(w)
  }
    
  /* Return a road value between two nodes */
  def roadValue(items: Int*): Int = {
    if (items(0) == -1) 0
    else roads(items.reduceLeft((a, b) => a max b))(items.reduceLeft((a, b) => a min b))
  }
  
  /* Return an entire path value */
  def pathValue(items: List[Int]): Int = {
    var value = items.foldLeft((0, -1))((r, c) => (r._1 + roadValue(r._2, c), c))
    value._1 + roadValue(items(0), value._2)
  }
  
  /* Get the best new path */
  def getBestPath(p: List[Int], q: Int): (List[Int], Int) = {
    
    (List(0), 0)
    
  }
  
  /* Travel! */
  def travel = {
    
    /* Go on trips */
    for (i <- 0 to s) {
      
      /* Reset for each trip */
      sum = 0
      path = List(0)
      set = List.fill(nodes.size)(nextInt).zipWithIndex.sortBy(identity).map(_._2).map(nodes(_))

      /* Trip Announcement */
      println("---set("+i+")---");
      set.foreach(println);
      
      /* Travel to each city */
      for (j <- 0 to x) {
        
        /* First City  */
        if (j == 0) {
          path = List(set(0))
          sum = 0
        }
          
        /* Second City */
        else if (j == 1) {
          path = List(set(0), set(1))
          sum = pathValue(path)
        }
        
        /* Third City */
        else if (j == 2) {
          path = List(set(0), set(1), set(2))
          sum = pathValue(path)
        }
        
        /* All other Cities */
        else {
          var best_path = getBestPath(path, set(j)) 
          path = best_path._1
          sum = best_path._2
        }
          
      }
      
      /* Print Trip Answer */
      println("Answer "+i+": "+answer)
      
      /* Various Tests */
      if (answer._2 == 0) {
        answer = (path, sum)
      }
      else if (sum < answer._2) {
        answer = (path, sum)
        success = false
      }
      
    }
    
    /* Print Final Answer */
    println("--------------------------------")
    println ("Status: "+success+" Final Answer: "+answer)
    println("--------------------------------")
    
    "#city" #> "hello"
    
  }
  
}