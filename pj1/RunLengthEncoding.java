/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
  private DList2 list;
  private int width;
  private int height;

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */

  public RunLengthEncoding(int width, int height) {
    // Your solution here.
    this.width = width;
    this.height = height;
    list = new DList2();
    int[] pixels = {width*height,0,0,0};
    list.insertFront(pixels);
  }

  /**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   *  NOTE:  All four input arrays should have the same length (not zero).
   *  All pixel intensities in the first three arrays should be in the range
   *  0...255.  The sum of all the elements of the runLengths array should be
   *  width * height.  (Feel free to quit with an error message if any of these
   *  conditions are not met--though we won't be testing that.)
   */

  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {
    // Your solution here.
    this.width = width;
    this.height = height;
    list = new DList2();
    for (int i = runLengths.length-1; i >= 0; i--) {
      int[] pixels = {runLengths[i],red[i], green[i], blue[i]};
      list.insertFront(pixels);
    }
    System.out.println("here");
    System.out.println(this);
  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
    // Replace the following line with your solution.
    return width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
    // Replace the following line with your solution.
    RunIterator i = new RunIterator(list);
    return i;
    // You'll want to construct a new RunIterator, but first you'll need to
    // write a constructor in the RunIterator class.
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.
   */
  public PixImage toPixImage() {
    // Replace the following line with your solution. 
    //Outer loop: iterate over the runs.
    //Inner loop: iterate once for each pixel in the run.
    //Inner loop body: write a pixel, advance the x pointer; if it's at the end of a row,
    // reset x and advance the y pointer.

    PixImage image = new PixImage(width,height);
    RunIterator i = this.iterator();
    int[] nxt = i.next();
    int x = 0, y = 0;
    while (nxt != null) {

      for (int k = 0; k < nxt[0]; k++) {
        if (k > 0) {
          x++;
        }

        if (x == width) {
        x = 0;
        y++;
        }
      image.setPixel(x,y,(short) nxt[1],(short) nxt[2], (short) nxt[3]);
      }
      x++;
      nxt = i.next();
    }
    return image;
  }

  /**
   *  toString() returns a String representation of this RunLengthEncoding.
   *
   *  This method isn't required, but it should be very useful to you when
   *  you're debugging your code.  It's up to you how you represent
   *  a RunLengthEncoding as a String.
   *
   *  @return a String representation of this RunLengthEncoding.
   */
  public String toString() {

    // Replace the following line with your solution.
    DListNode2 b = list.head;
    System.out.println(""); 
    System.out.println("");
    while (b.next != list.head) {
      int[] a = (int[]) b.next.item ;
      System.out.print("[ ");
      System.out.print(a[0] + " // ");
      System.out.print(a[1] + " ");
      System.out.print(a[2] + " ");
      System.out.print(a[3]);
      System.out.println(" ]");
      b = b.next;
    }
    return "";
  }


  /**
   *  The following methods are required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   * 
   *  Note that you must encode the image in row-major format, i.e., the second
   *  pixel should be (1, 0) and not (0, 1).
   *
   *  @param image is the PixImage to run-length encode.
   */
  public RunLengthEncoding(PixImage image) {
    // Your solution here, but you should probably leave the following line
    // at the end.
    this.width = image.getWidth();
    this.height = image.getHeight();
    list = new DList2();
    int counter = 1, k = image.getWidth()-1, x = image.getWidth() - 2;
    for (int y = image.getHeight()-1; y >= 0; y--) {
      while (x >= 0) {
        if (k < 0) {
          k = image.getWidth()-1;
        }
        if ((k != 0 && image.getRed(x,y) == image.getRed(k,y) && 
        image.getGreen(x,y) == image.getGreen(k,y) &&
        image.getBlue(x,y) == image.getBlue(k,y)) || 
        (k == 0 && image.getRed(x,y) == image.getRed(k,y+1) && 
        image.getGreen(x,y) == image.getGreen(k,y+1) &&
        image.getBlue(x,y) == image.getBlue(k,y+1))) {
          counter++;
        } else {
          if (k == 0) {
            int[] run = {counter, image.getRed(k,y+1), image.getGreen(k,y+1), image.getBlue(k,y+1)};
            list.insertFront(run);
          counter = 1;
          } else {
            int[] run = {counter, image.getRed(k,y), image.getGreen(k,y), image.getBlue(k,y)};
            list.insertFront(run);
          counter = 1;
          }

        }
        x--;
        k--;
      }
      x = image.getWidth() - 1;
    }
    int[] run = {counter, image.getRed(0,0), image.getGreen(0,0), image.getBlue(0,0)};
    list.insertFront(run);
      check();
    }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  public void check() {
//    Your solution here.
    int counter = 0;
    int red = 0, green = 0, blue = 0;
    DListNode2 n = list.head.next;
    int[] item = (int[]) n.item;
    while (n != list.head && n.next != list.head) {
      counter += item[0];
      red = item[1];  
      green = item[2]; 
      blue = item[3];
      int[] nxt = (int[]) n.next.item;
      if (nxt[1] == red && nxt[2] == green && nxt[3] == blue) {
        System.out.println("Incorrect RLE");
        return;
      }
      n = n.next;
      item = (int[]) n.item;
    }
    if (n != list.head) {
      counter += item[0];
    }

    if (counter != width*height) {
      System.out.println("Incorrect number of pixels stored in RLE");
    }
  }


  /**
   *  The following method is required for Part IV.
   */

  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    int number = 0;;
    int lengthSum = 0;
    for(int j = 0; j <= y; j++) {
      if (j == y) {
        number += x+1;
      } else {
        number += width;
      }
    }
    RunIterator i = this.iterator();
    int temp = 0;

    while (number > 0) {
      int[] nxt = (int[]) i.next();
      temp = number;
      number -= nxt[0];
    }

    DListNode2 curr = i.getCurrent();
    curr = curr.prev; //getting the correct run
    int[] run = (int[]) curr.item;

    if (run[1] == red && run[2] == green && run[3] == blue) { //Don't need to change anything
      check();
      return;
    }

    DListNode2 previous = curr.prev;
    DListNode2 after = curr.next;

    if (previous != list.head) {
      int[] previousItem = (int[]) previous.item;
    }

    if (after != list.head) {
      int[] afterItem = (int[]) after.item;
    }

    if (temp > 1 && temp < run[0]) { //Works
      int[] run1 =  {temp-1, run[1], run[2], run[3]};
      int[] run2 =  {run[0] - temp, run[1], run[2], run[3]};
      list.insertBefore(curr, run1);
      list.insertAfter(curr, run2);
      run[0] = 1;
      run[1] = red;
      run[2] = green;
      run[3] = blue;
      check();
      return;
    }
    if (temp == 1) { //Works
      if (run[0] > 1) {
        if (previous == list.head) {
          int[] run3 = {1, red, green, blue};
          list.insertBefore(curr,run3);
          run[0] = run[0] - 1;
          check();
          return;
        }
        if (previous.item[1] == red && 
        previous.item[2] == green && 
        previous.item[3] == blue) {
          previous.item[0] = previous.item[0] + 1;
          curr.item[0] = curr.item[0] - 1;
          check();
          return;
        }
      }
      else if (run[0] == 1) {
        if (previous == list.head && after == list.head) {
          run[1] = red;
          run[2] = green;
          run[3] = blue;
          check();
          return;
        } if (previous == list.head) {
            if (after.item[1] == red && 
            after.item[2] == green && 
            after.item[3] == blue) {
              after.item[0] = after.item[0] + 1;
              list.removeNode(curr);
              check();
              return;
            } else {
              run[1] = red;
              run[2] = green;
              run[3] = blue;
              check();
              return;
            }
        }   
        if (after == list.head) {
          if (previous.item[1] == red && 
          previous.item[2] == green && 
          previous.item[3] == blue) {
            previous.item[0] = previous.item[0] + 1;
            list.removeNode(curr);
            check();
            return;
          } else {
            run[1] = red;
            run[2] = green;
            run[3] = blue;
            check();
            return;
          }
        }
        else { //If previous!=list.head && after!= list.head
          if (previous.item[1] == red && 
          previous.item[2] == green && 
          previous.item[3] == blue) {
            if (after.item[1] == red && 
            after.item[2] == green && 
            after.item[3] == blue) { //Remove both current and after nodes and add to previous one
              previous.item[0] = previous.item[0] + 1 + after.item[0];
              list.removeNode(curr);
              list.removeNode(after);
              check();
              return;
            }
            else {
              previous.item[0] = previous.item[0] + 1;
              list.removeNode(curr);
              check();
              return;
            }
          }
          else if (after.item[1] == red && 
            after.item[2] == green && 
            after.item[3] == blue) {
              after.item[0] = after.item[0] + 1;
              list.removeNode(curr);
              check();
              return;
          }
          else {
            run[1] = red;
            run[2] = green;
            run[3] = blue;
            check();
            return;
          }
        }
      } 
   }  
   else if (temp == run[0]) {
      if (after != list.head) {
        if (after.item[1] == red && 
        after.item[2] == green && 
        after.item[3] == blue) {
          after.item[0] = after.item[0] + 1;
          run[0] = run[0] - 1;
          check();
          return;
        }
        else {
          int[] run4 = {1, red, green, blue};
          list.insertAfter(curr, run4);
          run[0] = run[0] - 1;
          check();
          return;
        }
      }
      else {
        int[] run5 = {1, red, green, blue};
          list.insertAfter(curr, run5);
          run[0] = run[0] - 1;
      }
    }
    check();
  }


  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,
                 (short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }

  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    // mytest
    RunLengthEncoding im3 = new RunLengthEncoding(3,4);
    int[] a = {3,4,5}, b = {4,5,6}, c = {6,7,8}, d = {4,4,4};
    RunLengthEncoding im2 = new RunLengthEncoding(3, 4, a, b, c, d);
//    System.out.println(im2);
    //
    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    //System.out.print(image1);
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    //mytest
    //System.out.println(rle1.list);
    //
    rle1.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    //mytest
    //System.out.println(rle1.toPixImage());
    //
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);
    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
    //mytest
//    System.out.println(image1);
    //
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    //mytest
//    System.out.println(image1);
    //
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    //mytest
//    System.out.println(image1);
    //
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    //mytest
//    System.out.println(image1);
    //
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    //mytest
//    System.out.println(image1);
    //
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    //mytest
//    System.out.println(image1);
    //
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
    //mytest
//    System.out.println(image1);
    //
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");


    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");


    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 1, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 9 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");


    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x2 image.  Input image:");
    System.out.print(image4);
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");
  }
}
