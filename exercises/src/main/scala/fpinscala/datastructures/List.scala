package fpinscala.datastructures

sealed trait List[+A] // `List` data type, parameterized on a type, `A`
case object Nil extends List[Nothing] // A `List` data constructor representing the empty list
/* Another data constructor, representing nonempty lists. Note that `tail` is another `List[A]`,
which may be `Nil` or another `Cons`.
 */
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List { // `List` companion object. Contains functions for creating and working with lists.
  def sum(ints: List[Int]): Int = ints match { // A function that uses pattern matching to add up a list of integers
    case Nil => 0 // The sum of the empty list is 0.
    case Cons(x,xs) => x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] = // Variadic function syntax
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  // x is 3 as the first match is the 3rd case statement
  val x = List(1,2,3,4,5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101
  }

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil => a2
      case Cons(h,t) => Cons(h, append(t, a2))
    }

  def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = // Utility functions
    as match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }

  def sum2(ns: List[Int]) =
    foldRight(ns, 0)((x,y) => x + y)

  def product2(ns: List[Double]) =
    foldRight(ns, 1.0)(_ * _) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar


  def tail[A](l: List[A]): List[A] =
    l match {
      case Nil => l
      case Cons(h, t) => t
    }

  def setHead[A](l: List[A], h: A): List[A] =
    l match {
      case Nil => List(h)
      case Cons(lh, t) => Cons(h, t)
    }

  // this assumes that n >= 0
  def drop[A](l: List[A], n: Int): List[A] =
    l match {
      case Nil => l
      case Cons(h, t) => if (n == 0) l
                         else drop(t, n-1)
    }

  def dropWhile[A](l: List[A], f: A => Boolean): List[A] =
    l match {
      case Nil => l
      case Cons(h, t) => if (!(f(h))) l
                         else dropWhile(t, f)
    }

  def init[A](l: List[A]): List[A] =
    l match {
      case Nil => l
      case Cons(h, Cons(t, Nil)) => List(h)
      case Cons(h, t) => append(List(h), init(t))
    }

  def length[A](l: List[A]): Int =
    l match {
      case Nil => 0
      case _ => foldRight(l, 0)((x, y) => 1 + y)
    }

  def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B =
    l match {
      case Nil => z
      case Cons(h, t) => foldLeft(t, f(z, h))(f)
    }

  def sumFL(as: List[Int]): Int =
    foldLeft(as, 0)((x, y) => x + y)

  def productFL(as: List[Double]): Double =
    foldLeft(as, 1.0)((x, y) => x * y)

  def lengthFL[A](as: List[A]): Int =
    foldLeft(as, 0)((x, y) => x + 1)

  def reverse[A](as: List[A]): List[A] =
    foldLeft(as, Nil: List[A])((x, y) => Cons(y, x))

  def map[A,B](l: List[A])(f: A => B): List[B] = ???
}
