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

class Traveler {
  
  /* 29 locations in Western Sahara */
  val coords = List(
	( 20833.3333, 17100.0000 ),
	( 20900.0000, 17066.6667 ),
	( 21300.0000, 13016.6667 ),
	( 21600.0000, 14150.0000 ),
	( 21600.0000, 14966.6667 ),
	( 21600.0000, 16500.0000 ),
	( 22183.3333, 13133.3333 ),
	( 22583.3333, 14300.0000 ),
	( 22683.3333, 12716.6667 ),
	( 23616.6667, 15866.6667 ),
	( 23700.0000, 15933.3333 ),
	( 23883.3333, 14533.3333 ),
	( 24166.6667, 13250.0000 ),
	( 25149.1667, 12365.8333 ),
	( 26133.3333, 14500.0000 ),
	( 26150.0000, 10550.0000 ),
	( 26283.3333, 12766.6667 ),
	( 26433.3333, 13433.3333 ),
	( 26550.0000, 13850.0000 ),
	( 26733.3333, 11683.3333 ),
	( 27026.1111, 13051.9444 ),
	( 27096.1111, 13415.8333 ),
	( 27153.6111, 13203.3333 ),
	( 27166.6667, 9833.3333 ),
	( 27233.3333, 10450.0000 ),
	( 27233.3333, 11783.3333 ),
	( 27266.6667, 10383.3333 ),
	( 27433.3333, 12400.0000 ),
	( 27462.5000, 12992.2222 )
  )
       
  /*
  var rows = List(
    List(0, 633, 257,  91, 412, 150,  80, 134, 259, 505, 353, 324,  70, 211, 268, 246, 121),
    List(633,   0, 390, 661, 227, 488, 572, 530, 555, 289, 282, 638, 567, 466, 420, 745, 518),
    List(257, 390,   0, 228, 169, 112, 196, 154, 372, 262, 110, 437, 191,  74,  53, 472, 142),
    List(91, 661, 228,   0, 383, 120,  77, 105, 175, 476, 324, 240,  27, 182, 239, 237,  84),
    List(412, 227, 169, 383,   0, 267, 351, 309, 338, 196,  61, 421, 346, 243, 199, 528, 297),
    List(150, 488, 112, 120, 267,   0,  63,  34, 264, 360, 208, 329,  83, 105, 123, 364,  35),
    List(80, 572, 196,  77, 351,  63,   0,  29, 232, 444, 292, 297,  47, 150, 207, 332,  29),
    List(134, 530, 154, 105, 309,  34,  29,   0, 249, 402, 250, 314,  68, 108, 165, 349,  36),
    List(259, 555, 372, 175, 338, 264, 232, 249,   0, 495, 352,  95, 189, 326, 383, 202, 236),
    List(505, 289, 262, 476, 196, 360, 444, 402, 495,   0, 154, 578, 439, 336, 240, 685, 390),
    List(353, 282, 110, 324,  61, 208, 292, 250, 352, 154,   0, 435, 287, 184, 140, 542, 238),
    List(324, 638, 437, 240, 421, 329, 297, 314,  95, 578, 435,   0, 254, 391, 448, 157, 301),
    List(70, 567, 191,  27, 346,  83,  47,  68, 189, 439, 287, 254,   0, 145, 202, 289,  55),
    List(211, 466,  74, 182, 243, 105, 150, 108, 326, 336, 184, 391, 145,   0,  57, 426,  96),
    List(268, 420,  53, 239, 199, 123, 207, 165, 383, 240, 140, 448, 202,  57,   0, 483, 153),
    List(246, 745, 472, 237, 528, 364, 332, 349, 202, 685, 542, 157, 289, 426, 483,   0, 336),
    List(121, 518, 142,  84, 297,  35,  29,  36, 236, 390, 238, 301,  55,  96, 153, 336,   0)
  )
  */
  
   var rows = List(
    List(633, 257,  91, 412, 150,  80, 134, 259, 505, 353, 324,  70, 211, 268, 246, 121),
    List(390, 661, 227, 488, 572, 530, 555, 289, 282, 638, 567, 466, 420, 745, 518, 0),
    List(228, 169, 112, 196, 154, 372, 262, 110, 437, 191,  74,  53, 472, 142, 0, 0),
    List(383, 120,  77, 105, 175, 476, 324, 240,  27, 182, 239, 237,  84, 0, 0, 0),
    List(267, 351, 309, 338, 196,  61, 421, 346, 243, 199, 528, 297, 0, 0, 0, 0),
    List(63,  34, 264, 360, 208, 329,  83, 105, 123, 364,  35, 0, 0, 0, 0, 0),
    List(29, 232, 444, 292, 297,  47, 150, 207, 332,  29, 0, 0, 0, 0, 0, 0),
    List(249, 402, 250, 314,  68, 108, 165, 349,  36, 0, 0, 0, 0, 0, 0, 0),
    List(495, 352,  95, 189, 326, 383, 202, 236, 0, 0, 0, 0, 0, 0, 0, 0),
    List(154, 578, 439, 336, 240, 685, 390, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    List(435, 287, 184, 140, 542, 238, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    List(254, 391, 448, 157, 301, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    List(145, 202, 289,  55, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    List(57, 426,  96, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    List(483, 153, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    List(336, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  )
  
  
  val logging = false
  var loggin2 = false
  var answer = (List(0), 0)
  val n = 16
  val x = n-1
  val w = 500
  val s = n
  val optimal = 27603
  val msg = "29 locations in Western Sahara"
  val nodes = 0 to x
  var set = List.fill(nodes.size)(nextInt).zipWithIndex.sortBy(identity).map(_._2).map(nodes(_))
  var roads = Array.ofDim[Int](n, n)
  var sum = 0
  var path = List(0)
  
  println("---------------------")
  println("Cities: "+n+" Travelers: "+s)
  println("---------------------")
  
  //build roads
  for (i <- 0 to x;
      j <- 0 to x) {
      //roads(i)(j) = coordsToRoad(i, j)
      //println(rows(i)(j))
      //println("i: "+i+" j: "+j)
      //roads(i)(j) = nextInt(w)
      roads(i)(j) = rows(i)(j)
  }
  
  /* converts a set of coordinates to a single road */
  def coordsToRoad(a: Int, b: Int): Int = {
    val PI = 3.141592
    
    var deg = coords(a)._1.toInt
    var min = coords(a)._1 - deg
    var a1 = PI * (deg + 5.0 * min/ 3.0) / 180.0
    
    deg = coords(a)._2.toInt
    min = coords(a)._2 - deg
    var a2 = PI * (deg + 5.0 * min/ 3.0) / 180.0
    
    deg = coords(b)._1.toInt
    min = coords(b)._1 - deg
    var b1 = PI * (deg + 5.0 * min/ 3.0) / 180.0
    
    deg = coords(b)._2.toInt
    min = coords(b)._2 - deg
    var b2 = PI * (deg + 5.0 * min/ 3.0) / 180.0
    
    val RRR = 6378.388
    /*
    var q1 = cos( a2 - b2 )
    var q2 = cos( a1 - b1 )
    var q3 = cos( a1 + b1 )
    */
    var q1 = cos( b2 - a2 )
    var q2 = cos( b1 - a1 )
    var q3 = cos( b1 + a1 )
    ( RRR * acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0).toInt
  }
    
  /* Return a road value between two nodes */
  def rVal(items: Int*): Int = {
    if (items(0) == -1) 0
    else roads(items.reduceLeft((a, b) => a max b))(items.reduceLeft((a, b) => a min b))
  }
  
  /* Return an entire path value */
  def pVal(items: List[Int]): Int = {
    items.foldLeft((0, -1))((r, c) => (r._1 + rVal(r._2, c), c))._1
  }
  
  /* Combintations */
  def multi[A](as: List[A], k: Int): List[List[A]] = 
    (List.fill(k)(as)).flatten.combinations(k).toList
    
  /* Permutations */
  def permutations[T](xs: List[T]): List[List[T]] = xs match {
         case Nil => List(Nil)
         case _   => for(x <- xs;ys <- permutations(xs diff List(x))) yield x::ys
     }
  
  def fPathCheckAll(p: List[Int], p1: Int): (List[Int], Int) = {
    var current = fPath(p, p1)
    var i = current._1
    var has_new = true
    while(has_new) {
	  has_new = false
      i.foreach(
	        a => {
	          var tmp = fPath(current._1 diff List(a), a)
	          if (tmp._2 < current._2) {
	            current = tmp
	            has_new = true
	          }
	        } 
	    )
	  if (has_new) {
	    i = current._1
	  }
    }
    current
  }
  
  def fPath(p: List[Int], p1: Int): (List[Int], Int) = {
    var cpath = List(0)
    var csum = 0
    var lpath = List(0)
    var lsum = 0
    var length = p.length
    //empty
    if (length == 0) p
    //single
    if (length == 1) p ::: List(p1)
    //multiple
    if (length >= 2) {
      
      //head
      lpath = List(p1) ::: p
      lsum = pVal(lpath)
      if (logging) { println("---"); println("hPath: "+lpath+" val: "+lsum); }
      
      //tail
      cpath = p ::: List(p1)
      csum = pVal(cpath)
      if (csum < lsum) { lsum = csum; lpath = cpath; }
      if (logging) println("tPath: "+cpath+" val: "+csum)
      
      //squish
      for (i <- 0 to (length-2)) {
        var part = p.splitAt(i+1)
        
        //basic
        cpath = part._1 ::: List(p1) ::: part._2
        csum = pVal(cpath)
        if (csum < lsum) { lsum = csum; lpath = cpath; }
        if (logging) println("sbPath: "+cpath+" val: "+csum)
        
        //1 reverse
        cpath = part._1.reverse ::: List(p1) ::: part._2
        csum = pVal(cpath)
        if (csum < lsum) { lsum = csum; lpath = cpath; }
        if (logging) println("slPath: "+cpath+" val: "+csum)
        
	        //1 reverse 1
	        cpath = part._1.reverse ::: part._2 ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("slPath: "+cpath+" val: "+csum)
	        
	        //1 reverse 2
	        cpath =  part._2 ::: part._1.reverse ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("slPath: "+cpath+" val: "+csum)
	        
	        //1 reverse 3
	        cpath =  List(p1) ::: part._2 ::: part._1.reverse
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("slPath: "+cpath+" val: "+csum)
	        
	        //1 reverse 4
	        cpath =  List(p1) ::: part._1.reverse::: part._2 
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("slPath: "+cpath+" val: "+csum)
        
        //2 reverse
        cpath = part._1 ::: List(p1) ::: part._2.reverse
        csum = pVal(cpath)
        if (csum < lsum) { lsum = csum; lpath = cpath; }
        if (logging) println("srPath: "+cpath+" val: "+csum)
        
        	//2 reverse 1
	        cpath = List(p1) ::: part._2.reverse ::: part._1
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("srPath: "+cpath+" val: "+csum)
	        
	        //2 reverse 2
	        cpath = List(p1) ::: part._1 ::: part._2.reverse
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("srPath: "+cpath+" val: "+csum)
	        
	        //2 reverse 3
	        cpath =  part._1 ::: part._2.reverse ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("srPath: "+cpath+" val: "+csum)
	        
	        //2 reverse 3
	        cpath =  part._2.reverse ::: part._1 ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("srPath: "+cpath+" val: "+csum)
        
        //double reverse
        cpath = part._1.reverse ::: List(p1) ::: part._2.reverse
        csum = pVal(cpath)
        if (csum < lsum) { lsum = csum; lpath = cpath; }
        if (logging) println("sdPath: "+cpath+" val: "+csum)
        
	        //double reverse 1
	        cpath = List(p1) ::: part._2.reverse ::: part._1.reverse
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("sdPath: "+cpath+" val: "+csum)
	        
	        //double reverse 2
	        cpath = List(p1) ::: part._1.reverse ::: part._2.reverse
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("sdPath: "+cpath+" val: "+csum)
	        
	        //double reverse 3
	        cpath =  part._1.reverse ::: part._2.reverse ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("sdPath: "+cpath+" val: "+csum)
	        
	        //double reverse 4
	        cpath =  part._2.reverse ::: part._1.reverse ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("sdPath: "+cpath+" val: "+csum)
        
      }
      
    }
    if (length >= 3) {
      
      //parted segments
      for (i <- 0 to (length-1);
           j <- (i+2) to (length-1)) {
        
        var c1n = List(p1)
        var c1r = c1n.reverse
        
        var c2n = p.slice(i+1, j)
        var c2r = c2n.reverse
        
        var c3n = p.slice(0, i+1)
        var c3r = c3n.reverse
        
        var c4n = p.slice(j, length)
        var c4r = c4n.reverse
        
          
          permutations(List(c1r, c2n, c3n, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) };  lsum = csum; lpath = cpath; if (loggin2) { println("               21: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2n, c3n, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               22: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2n, c3r, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               23: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2n, c3r, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               24: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2r, c3n, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               25: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2r, c3n, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               26: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2r, c3r, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               27: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2r, c3r, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               28: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2r, c3n, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               29: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2r, c3n, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               30: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2r, c3r, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               31: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2r, c3r, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               32: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2n, c3r, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               33: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2n, c3r, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               34: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2n, c3n, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               35: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2n, c3n, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               36: "+cpath+" val: "+csum); } }
            }    
          )

      }
      
    }
    (lpath, lsum)
  }
  
  def travel = {
    
    //evaluate a new set
    for (i <- 0 to s) {  
      sum = 0
      path = List()
      set = List.fill(nodes.size)(nextInt).zipWithIndex.sortBy(identity).map(_._2).map(nodes(_))
      if (logging) println("---set("+i+")---")
      if (logging) set.foreach(println)
      if (loggin2) { println("---set("+i+")---"); set.foreach(println); }
      for (j <- 0 to x) {
        //head
        if (j == 0) {
          path = List(set(0))
          sum = 0
          if (logging) println("Path: "+path)
        }
          
        //trivial
        else if (j == 1) {
          path = List(set(0), set(1))
          sum = pVal(path)
          if (logging) println("Path: "+path)
        }
          
        //middle
        else {
          var path_data = fPathCheckAll(path, set(j)) 
          path = path_data._1
          sum = path_data._2
          if (logging) println(j+". Path: "+path)
        }
          
        //tail: do nothing
      }
      if (answer._2 == 0) answer = (path, sum)
      else if (sum < answer._2) answer = (path, sum)
      println("Answer "+i+": "+answer)
      //println("---")
    }
    
    println("--------------------------------")
    println ("Final Answer: "+answer)
    println("--------------------------------")
    
    "#city" #> "hello"
    
  }
  
}










/*
package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._

import util.Random.nextInt

class Traveler {
  
  val logging = false
  var loggin2 = false
  var answer = (List(0), 0)
  val n = 60
  val x = n-1
  val w = 500
  val s = n
  val nodes = 0 to x
  var set = List.fill(nodes.size)(nextInt).zipWithIndex.sortBy(identity).map(_._2).map(nodes(_))
  var roads = Array.ofDim[Int](n, n)
  var sum = 0
  var path = List(0)
  
  println("---------------------")
  println("Cities: "+n+" Travelers: "+s+" Width: "+w)
  println("---------------------")
  
  //build roads
  for (i <- 0 to x;
      j <- i to x)
    if (j >= i) roads(i)(j) = nextInt(w)
  
  /* Return a road value between two nodes */
  def rVal(items: Int*): Int = {
    if (items(0) == -1) 0
    else roads(items.reduceLeft((a, b) => a min b))(items.reduceLeft((a, b) => a max b))
  }
  
  /* Return an entire path value */
  def pVal(items: List[Int]): Int = {
    items.foldLeft((0, -1))((r, c) => (r._1 + rVal(r._2, c), c))._1
  }
  
  def multi[A](as: List[A], k: Int): List[List[A]] = 
    (List.fill(k)(as)).flatten.combinations(k).toList
    
  def permutations[T](xs: List[T]): List[List[T]] = xs match {
         case Nil => List(Nil)
         case _   => for(x <- xs;ys <- permutations(xs diff List(x))) yield x::ys
     }
  
  def fPathCheckAll(p: List[Int], p1: Int): (List[Int], Int) = {
    var current = fPath(p, p1)
    var i = current._1
    var has_new = true
    while(has_new) {
	  has_new = false
      i.foreach(
	        a => {
	          var tmp = fPath(current._1 diff List(a), a)
	          //println(current._2 +" "+ tmp._2)
	          if (tmp._2 < current._2) {
	            current = tmp
	            has_new = true
	            if (loggin2) println("true")
	          }
	        } 
	    )
	  if (has_new) {
	    i = current._1
	    if (loggin2) println("TRUE SET") 
	  }
    }
    current
  }
  
  def fPath(p: List[Int], p1: Int): (List[Int], Int) = {
    var cpath = List(0)
    var csum = 0
    var lpath = List(0)
    var lsum = 0
    var length = p.length
    //empty
    if (length == 0) p
    //single
    if (length == 1) p ::: List(p1)
    //multiple
    if (length >= 2) {
      
      //head
      lpath = List(p1) ::: p
      lsum = pVal(lpath)
      if (logging) { println("---"); println("hPath: "+lpath+" val: "+lsum); }
      
      //tail
      cpath = p ::: List(p1)
      csum = pVal(cpath)
      if (csum < lsum) { lsum = csum; lpath = cpath; }
      if (logging) println("tPath: "+cpath+" val: "+csum)
      
      //squish
      for (i <- 0 to (length-2)) {
        var part = p.splitAt(i+1)
        
        //basic
        cpath = part._1 ::: List(p1) ::: part._2
        csum = pVal(cpath)
        if (csum < lsum) { lsum = csum; lpath = cpath; }
        if (logging) println("sbPath: "+cpath+" val: "+csum)
        
        //1 reverse
        cpath = part._1.reverse ::: List(p1) ::: part._2
        csum = pVal(cpath)
        if (csum < lsum) { lsum = csum; lpath = cpath; }
        if (logging) println("slPath: "+cpath+" val: "+csum)
        
	        //1 reverse 1
	        cpath = part._1.reverse ::: part._2 ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("slPath: "+cpath+" val: "+csum)
	        
	        //1 reverse 2
	        cpath =  part._2 ::: part._1.reverse ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("slPath: "+cpath+" val: "+csum)
	        
	        //1 reverse 3
	        cpath =  List(p1) ::: part._2 ::: part._1.reverse
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("slPath: "+cpath+" val: "+csum)
	        
	        //1 reverse 4
	        cpath =  List(p1) ::: part._1.reverse::: part._2 
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("slPath: "+cpath+" val: "+csum)
        
        //2 reverse
        cpath = part._1 ::: List(p1) ::: part._2.reverse
        csum = pVal(cpath)
        if (csum < lsum) { lsum = csum; lpath = cpath; }
        if (logging) println("srPath: "+cpath+" val: "+csum)
        
        	//2 reverse 1
	        cpath = List(p1) ::: part._2.reverse ::: part._1
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("srPath: "+cpath+" val: "+csum)
	        
	        //2 reverse 2
	        cpath = List(p1) ::: part._1 ::: part._2.reverse
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("srPath: "+cpath+" val: "+csum)
	        
	        //2 reverse 3
	        cpath =  part._1 ::: part._2.reverse ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("srPath: "+cpath+" val: "+csum)
	        
	        //2 reverse 3
	        cpath =  part._2.reverse ::: part._1 ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("srPath: "+cpath+" val: "+csum)
        
        //double reverse
        cpath = part._1.reverse ::: List(p1) ::: part._2.reverse
        csum = pVal(cpath)
        if (csum < lsum) { lsum = csum; lpath = cpath; }
        if (logging) println("sdPath: "+cpath+" val: "+csum)
        
	        //double reverse 1
	        cpath = List(p1) ::: part._2.reverse ::: part._1.reverse
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("sdPath: "+cpath+" val: "+csum)
	        
	        //double reverse 2
	        cpath = List(p1) ::: part._1.reverse ::: part._2.reverse
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("sdPath: "+cpath+" val: "+csum)
	        
	        //double reverse 3
	        cpath =  part._1.reverse ::: part._2.reverse ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("sdPath: "+cpath+" val: "+csum)
	        
	        //double reverse 4
	        cpath =  part._2.reverse ::: part._1.reverse ::: List(p1)
	        csum = pVal(cpath)
	        if (csum < lsum) { lsum = csum; lpath = cpath; }
	        if (logging) println("sdPath: "+cpath+" val: "+csum)
        
      }
      
    }
    if (length >= 3) {
      
      //parted segments
      for (i <- 0 to (length-1);
           j <- (i+2) to (length-1)) {
        
        var c1n = List(p1)
        var c1r = c1n.reverse
        
        var c2n = p.slice(i+1, j)
        var c2r = c2n.reverse
        
        var c3n = p.slice(0, i+1)
        var c3r = c3n.reverse
        
        var c4n = p.slice(j, length)
        var c4r = c4n.reverse
        
          
          permutations(List(c1r, c2n, c3n, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) };  lsum = csum; lpath = cpath; if (loggin2) { println("               21: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2n, c3n, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               22: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2n, c3r, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               23: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2n, c3r, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               24: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2r, c3n, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               25: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2r, c3n, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               26: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2r, c3r, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               27: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1r, c2r, c3r, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               28: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2r, c3n, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               29: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2r, c3n, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               30: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2r, c3r, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               31: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2r, c3r, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               32: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2n, c3r, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               33: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2n, c3r, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               34: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2n, c3n, c4r)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               35: "+cpath+" val: "+csum); } }
            }    
          )
	      permutations(List(c1n, c2n, c3n, c4n)).foreach(
            a => {
              //println("perms: "+a)
              cpath = a(0) ::: a(1) ::: a(2) ::: a(3)
              csum = pVal(cpath)
    	      if (csum < lsum) { if (loggin2) { println("               pa: "+p+" p1: "+p1) }; lsum = csum; lpath = cpath; if (loggin2) { println("               36: "+cpath+" val: "+csum); } }
            }    
          )

      }
      
    }
    (lpath, lsum)
  }
  
  def travel = {
    
    //evaluate a new set
    for (i <- 0 to s) {  
      sum = 0
      path = List()
      set = List.fill(nodes.size)(nextInt).zipWithIndex.sortBy(identity).map(_._2).map(nodes(_))
      if (logging) println("---set("+i+")---")
      if (logging) set.foreach(println)
      if (loggin2) { println("---set("+i+")---"); set.foreach(println); }
      for (j <- 0 to x) {
        //head
        if (j == 0) {
          path = List(set(0))
          sum = 0
          if (logging) println("Path: "+path)
        }
          
        //trivial
        else if (j == 1) {
          path = List(set(0), set(1))
          sum = pVal(path)
          if (logging) println("Path: "+path)
        }
          
        //middle
        else {
          var path_data = fPathCheckAll(path, set(j)) 
          path = path_data._1
          sum = path_data._2
          if (logging) println(j+". Path: "+path)
        }
          
        //tail: do nothing
      }
      if (answer._2 == 0) answer = (path, sum)
      else if (sum < answer._2) answer = (path, sum)
      println("Answer "+i+": "+answer)
      //println("---")
    }
    
    println("--------------------------------")
    println ("Final Answer: "+answer)
    println("--------------------------------")
    
    "#city" #> "hello"
    
  }
  
}
*/